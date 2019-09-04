package ru.example.movies.movietrailers.service;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.feign.ImdbFeignClient;
import ru.example.movies.movietrailers.properties.ImdbProperties;

@Service
@RequiredArgsConstructor
public class DefaultOmdbService implements OmdbService {

	private static final Integer DEFAULT_PAGE = 1;

	private final ImdbFeignClient omdbApi;
	private final ImdbProperties imdbProperties;

	@Override
	@SneakyThrows
	public SearchResults searchMovies(String query) {
		return fetchSearchResults(query, DEFAULT_PAGE);
	}

	@Override
	@SneakyThrows
	public SearchResults searchMovies(String query, Integer page) {
		return fetchSearchResults(query, page);
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
}
