package ru.example.movies.movietrailers.configuration;

import com.omertron.omdbapi.OmdbApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.example.movies.movietrailers.properties.ImdbProperties;

@Configuration
@EnableConfigurationProperties(ImdbProperties.class)
public class ImdbConfiguration {

	@Bean
	public OmdbApi omdbApi(ImdbProperties properties) {
		return new OmdbApi(properties.getApi().getKey());
	}
}
