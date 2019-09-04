package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.model.SearchResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class DefaultAsyncYoutubeService implements AsyncYoutubeService {
	private final YoutubeService youtubeService;
	private final Executor asyncExecutor;

	public DefaultAsyncYoutubeService(YoutubeService youtubeService,
	                                  Executor asyncExecutor) {
		this.youtubeService = youtubeService;
		this.asyncExecutor = asyncExecutor;
	}

	@Async
	@Override
	public CompletableFuture<SearchResult> search(String title) {
		return CompletableFuture.supplyAsync(() -> youtubeService.search(title), asyncExecutor);
	}
}
