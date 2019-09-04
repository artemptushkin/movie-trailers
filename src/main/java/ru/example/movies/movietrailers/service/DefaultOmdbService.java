package ru.example.movies.movietrailers.service;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.feign.ImdbFeignClient;
import ru.example.movies.movietrailers.properties.ImdbProperties;
import javax.annotation.PreDestroy;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultOmdbService implements OmdbService {

	private static final Integer DEFAULT_PAGE = 1;

	private final ImdbFeignClient omdbApi;
	private final ImdbProperties imdbProperties;

	@Override
	@SneakyThrows
	@Cacheable("searchMovieByQueryCache")
	public SearchResults searchMovies(String query) {
		return fetchSearchResults(query, DEFAULT_PAGE);
	}

	@Override
	@SneakyThrows
	@Cacheable("searchMovieByQueryCache")
	public SearchResults searchMovies(String query, Integer page) {
		return fetchSearchResults(query, page);
	}

	@Override
	@SneakyThrows
	@Cacheable("findMovieByIdCache")
	public OmdbVideoBasic findMovieById(String imdbID) {
		return omdbApi.getInfo(
			imdbProperties.getApi().getKey(),
			imdbID
		);
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

	private SearchResults fetchSearchResults(String query, Integer page) {
		return omdbApi.search(
			imdbProperties.getApi().getKey(),
			"movie",
			query,
			page
		);
	}
}
