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

	private static final String SNIPPET = "snippet";

	private final YouTube youTube;
	private final YoutubeProperties youtubeProperties;

	@Override
	@Cacheable(value = "youtubeSearchCache", sync = true)
	public SearchResult search(String title) {
		try {
			log.debug("youtube search invoked");
			return youTube.search()
				.list(SNIPPET)
				.setKey(youtubeProperties.getApi().getKey())
				.setMaxResults(20L)
				.setType(youtubeProperties.getType())
				.setOrder(youtubeProperties.getOrder())
				.setQ(format(youtubeProperties.getSearchQueryMask(), title))
				.setFields(youtubeProperties.getJsonQuery())
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
