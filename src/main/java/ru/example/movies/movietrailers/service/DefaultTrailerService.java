package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.model.SearchResult;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.domain.MovieTrailer;
import ru.example.movies.movietrailers.domain.TrailersSearchResponse;
import ru.example.movies.movietrailers.helper.MovieTrailerConverter;
import ru.example.movies.movietrailers.helper.TrailersCollectorFactory;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTrailerService implements TrailerService {

	private final MovieTrailerConverter movieTrailerConverter;
	private final AsyncYoutubeService youtubeService;
	private final OmdbService omdbService;
	private final TrailersCollectorFactory collectorFactory;

	@Override
	@SneakyThrows
	@Cacheable("findMovieByIdCache")
	public MovieTrailer findTrailerById(String imdbID) {
		OmdbVideoBasic videoBasic = omdbService.findMovieById(imdbID);
		return fetchYoutubeDataForVideo(videoBasic).get();
	}

	@Override
	@Cacheable("searchMovieByQueryCache")
	public TrailersSearchResponse search(String query, Integer page) {
		return searchBy(query, page);
	}

	@Override
	@Cacheable("searchMovieByQueryCache")
	public TrailersSearchResponse search(String query, Integer page, Integer year) {
		return searchBy(query, page, year);
	}

	@PreDestroy
	@CacheEvict(allEntries = true, value = "searchMovieByQueryCache")
	@Scheduled(fixedDelay = 60000)
	public void evictSearchMovieByQueryCache() {
		log.debug("searchMovieByQueryCache has been evicted");
	}

	@PreDestroy
	@CacheEvict(allEntries = true, value = "findMovieByIdCache")
	@Scheduled(fixedDelay = 80000)
	public void evictFindMovieByIdCache() {
		log.debug("findMovieByIdCache has been evicted");
	}

	private TrailersSearchResponse searchBy(String query, Integer page) {
		SearchResults searchResults = omdbService.searchMovies(query, page);
		return getTrailersSearchResponse(page, searchResults);
	}

	private TrailersSearchResponse searchBy(String query, Integer page, Integer year) {
		SearchResults searchResults = omdbService.searchMovies(query, page, year);
		return getTrailersSearchResponse(page, searchResults);
	}

	private TrailersSearchResponse getTrailersSearchResponse(Integer page, SearchResults searchResults) {
		return Optional.of(searchResults)
			.filter(SearchResults::isResponse)
			.map(SearchResults::getResults)
			.orElse(Collections.emptyList())
			.parallelStream()
			.map(this::fetchYoutubeDataForVideo)
			.collect(collectorFactory.trailersResponseCollector(page, searchResults.getTotalResults()));
	}

	@SneakyThrows
	private CompletableFuture<MovieTrailer> fetchYoutubeDataForVideo(OmdbVideoBasic videoBasic) {
		CompletableFuture<SearchResult> youtubeResponse = youtubeService.search(videoBasic.getTitle());
		return youtubeResponse.thenCombine(
			supplyAsync(() -> videoBasic),
			(searchResult, omdbVideoBasic) -> movieTrailerConverter.apply(omdbVideoBasic, searchResult));
	}
}
