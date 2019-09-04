package ru.example.movies.movietrailers.helper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.Builder;
import java.text.MessageFormat;

@RequiredArgsConstructor
public class YoutubeURLBuilder implements Builder<String> {

	private final String urlMask;
	@Setter
	@Accessors(chain = true)
	private String id;

	@Override
	public String build() {
		return MessageFormat.format(urlMask, id);
	}
}
