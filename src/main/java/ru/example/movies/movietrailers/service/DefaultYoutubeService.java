package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.exception.YoutubeEmptySearchException;
import ru.example.movies.movietrailers.properties.YoutubeProperties;
import java.io.IOException;

import static java.text.MessageFormat.format;


@Service
@RequiredArgsConstructor
public class DefaultYoutubeService implements YoutubeService {

	private final YouTube youTube;
	private final YoutubeProperties youtubeProperties;

	@Override
	public SearchResult search(String title) {
		try {
			return youTube.search()
				.list("snippet")
				.setKey(youtubeProperties.getApi().getKey())
				.setMaxResults(20L)
				.setType("video")
				.setOrder("relevance")
				.setQ(format("{0} trailer", title))
				.setFields("items(id(videoId),snippet(title, thumbnails(high)))")
				.execute()
				.getItems()
				.stream()
				.findFirst()
				.orElseThrow(YoutubeEmptySearchException::new);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
