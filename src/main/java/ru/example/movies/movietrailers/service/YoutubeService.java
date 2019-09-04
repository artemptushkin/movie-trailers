package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.model.SearchResult;

public interface YoutubeService {
	SearchResult search(String movieTitle);
}
