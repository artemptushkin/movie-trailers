package ru.example.movies.movietrailers.service;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;

public interface OmdbService {

	SearchResults searchMovies(String query);

	SearchResults searchMovies(String query, Integer page);

	OmdbVideoBasic findMovieById(String imdbID);

	SearchResults searchMovies(String query, Integer page, Integer year);
}
