package ru.example.movies.movietrailers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.conversion.composite.ConversionConfiguration;

@EnableFeignClients
@Import(ConversionConfiguration.class)
@SpringBootApplication
public class MovieTrailersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTrailersApplication.class, args);
	}

}
