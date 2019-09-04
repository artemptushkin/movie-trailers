package ru.example.movies.movietrailers.controller;

import com.google.api.services.youtube.model.SearchResult;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.example.movies.movietrailers.domain.MovieTrailer;
import ru.example.movies.movietrailers.domain.Suggestion;
import ru.example.movies.movietrailers.domain.SuggestionResponse;
import ru.example.movies.movietrailers.domain.TrailersSearchResponse;
import ru.example.movies.movietrailers.helper.MovieTrailerConverter;
import ru.example.movies.movietrailers.helper.TrailersCollectorFactory;
import ru.example.movies.movietrailers.service.AsyncYoutubeService;
import ru.example.movies.movietrailers.service.OmdbService;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movieTrailers")
public class SearchController {
	private final OmdbService omdbService;
	private final MovieTrailerConverter movieTrailerConverter;
	private final AsyncYoutubeService youtubeService;
	private final TrailersCollectorFactory collectorFactory;

	@GetMapping("/suggestion")
	public SuggestionResponse proceedSuggest(
		@RequestParam String query) {
		SearchResults searchResults = omdbService.searchMovies(query);

		return Optional.of(searchResults)
			.filter(SearchResults::isResponse)
			.map(SearchResults::getResults)
			.orElse(Collections.emptyList())
			.stream()
			.map(searchResult -> new Suggestion(searchResult.getTitle(), searchResult.getImdbID(), searchResult.getPoster()))
			.collect(collectingAndThen(toList(), SuggestionResponse::new));
	}

	@GetMapping
	@SneakyThrows
	public TrailersSearchResponse searchTrailers(
		@RequestParam String query,
		@RequestParam(required = false, defaultValue = "1") Integer page) {
		SearchResults searchResults = omdbService.searchMovies(query, page);

		return Optional.of(searchResults)
			.filter(SearchResults::isResponse)
			.map(SearchResults::getResults)
			.orElse(Collections.emptyList())
			.parallelStream()
			.map(this::fetchYoutubeDataForVideo)
			.collect(collectorFactory.trailersResponseCollector(page, searchResults.getTotalResults()));
	}

	@GetMapping("/{imdbID}")
	@SneakyThrows
	public MovieTrailer findTrailer(@PathVariable String imdbID) {
		OmdbVideoBasic videoBasic = omdbService.findMovieById(imdbID);

		return fetchYoutubeDataForVideo(videoBasic).get();
	}

	@SneakyThrows
	private CompletableFuture<MovieTrailer> fetchYoutubeDataForVideo(OmdbVideoBasic videoBasic) {

		CompletableFuture<SearchResult> youtubeResponse = youtubeService.search(videoBasic.getTitle());
		return youtubeResponse.thenCombine(
			supplyAsync(() -> videoBasic),
			(searchResult, omdbVideoBasic) -> movieTrailerConverter.apply(omdbVideoBasic, searchResult));
	}
}
