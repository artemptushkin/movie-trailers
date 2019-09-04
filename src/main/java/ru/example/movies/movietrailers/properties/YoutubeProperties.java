package ru.example.movies.movietrailers.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties("youtube")
public class YoutubeProperties {
	@NotNull
	private FeignAPIProperties api;
	@NotNull
	private String urlMask;
}
