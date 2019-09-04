package ru.example.movies.movietrailers.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.example.movies.movietrailers.exception.YoutubeEmptySearchException;
import ru.example.movies.movietrailers.properties.YoutubeProperties;
import javax.annotation.PreDestroy;
import java.io.IOException;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultYoutubeService implements YoutubeService {

	private final YouTube youTube;
	private final YoutubeProperties youtubeProperties;

	@Override
	@Cacheable(value = "youtubeSearchCache", sync = true)
	public SearchResult search(String title) {
		try {
			log.debug("youtube search invoked");
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

	@PreDestroy
	@CacheEvict(allEntries = true, value = "youtubeSearchCache")
	@Scheduled(fixedDelay = 300000)
	public void evict() {
		log.debug("youtubeSearchCache has been evicted");
	}
}
