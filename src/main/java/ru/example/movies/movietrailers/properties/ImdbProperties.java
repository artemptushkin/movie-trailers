package ru.example.movies.movietrailers.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties("imdb")
public class ImdbProperties {
	@NotNull
	private FeignAPIProperties api;
	@NotNull
	private Integer searchPageSize;
}
