package ru.example.movies.movietrailers.service;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.feign.ImdbFeignClient;
import ru.example.movies.movietrailers.properties.ImdbProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultOmdbService implements OmdbService {

	private static final Integer DEFAULT_PAGE = 1;

	private final ImdbFeignClient omdbApi;
	private final ImdbProperties imdbProperties;

	@Override
	public SearchResults searchMovies(String query) {
		return fetchSearchResults(query, DEFAULT_PAGE);
	}

	@Override
	public SearchResults searchMovies(String query, Integer page) {
		return fetchSearchResults(query, page);
	}

	@Override
	public SearchResults searchMovies(String query, Integer page, Integer year) {
		return fetchSearchResults(query, page, year);
	}

	@Override
	@SneakyThrows
	public OmdbVideoBasic findMovieById(String imdbID) {
		return omdbApi.getInfo(
			imdbProperties.getApi().getKey(),
			imdbID
		);
	}

	private SearchResults fetchSearchResults(String query, Integer page) {
		return omdbApi.search(
			imdbProperties.getApi().getKey(),
			"movie",
			query,
			page
		);
	}

	private SearchResults fetchSearchResults(String query, Integer page, Integer year) {
		return omdbApi.searchByYear(
			imdbProperties.getApi().getKey(),
			"movie",
			query,
			page,
			year
		);
	}
}
