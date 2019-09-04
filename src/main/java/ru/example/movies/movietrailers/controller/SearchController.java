package ru.example.movies.movietrailers.controller;

import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.example.movies.movietrailers.domain.MovieTrailer;
import ru.example.movies.movietrailers.domain.Suggestion;
import ru.example.movies.movietrailers.domain.SuggestionResponse;
import ru.example.movies.movietrailers.domain.TrailersSearchResponse;
import ru.example.movies.movietrailers.service.OmdbService;
import ru.example.movies.movietrailers.service.TrailerService;
import java.util.Collections;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movieTrailers")
public class SearchController {
	private final OmdbService omdbService;
	private final TrailerService trailerService;

	@GetMapping("/suggestion")
	public SuggestionResponse proceedSuggest(@RequestParam String query) {
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
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam(required = false) Integer year) {
		return year == null ? trailerService.search(query, page) : trailerService.search(query, page, year);
	}

	@GetMapping("/{imdbID}")
	@SneakyThrows
	public MovieTrailer findTrailer(@PathVariable String imdbID) {
		return trailerService.findTrailerById(imdbID);
	}
}
