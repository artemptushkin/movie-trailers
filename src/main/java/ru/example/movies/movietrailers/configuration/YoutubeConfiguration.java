package ru.example.movies.movietrailers.configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.example.movies.movietrailers.helper.BuilderFactory;
import ru.example.movies.movietrailers.properties.YoutubeProperties;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
@EnableConfigurationProperties(YoutubeProperties.class)
public class YoutubeConfiguration {

	@Bean
	public YouTube youTube() throws GeneralSecurityException, IOException {
		return new YouTube(
			GoogleNetHttpTransport.newTrustedTransport(),
			JacksonFactory.getDefaultInstance(),
			null
		);
	}

	@Bean
	public BuilderFactory youtubeURLBuilder(YoutubeProperties youtubeProperties) {
		return new BuilderFactory(youtubeProperties.getUrlMask());
	}
}
