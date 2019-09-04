package ru.example.movies.movietrailers.service;

import ru.example.movies.movietrailers.domain.MovieTrailer;
import ru.example.movies.movietrailers.domain.TrailersSearchResponse;

public interface TrailerService {
	MovieTrailer findTrailerById(String imdbID);

	TrailersSearchResponse search(String query, Integer page);

	TrailersSearchResponse search(String query, Integer page, Integer year);
}
