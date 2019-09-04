package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.model.SearchResult;
import java.util.concurrent.CompletableFuture;

public interface AsyncYoutubeService {
	CompletableFuture<SearchResult> search(String title);
}
