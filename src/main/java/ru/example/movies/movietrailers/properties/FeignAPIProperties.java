package ru.example.movies.movietrailers.properties;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class FeignAPIProperties {
	@NotNull
	private String key;
	@NotNull
	private String url;
}
