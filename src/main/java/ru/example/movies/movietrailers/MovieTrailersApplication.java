package ru.example.movies.movietrailers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MovieTrailersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTrailersApplication.class, args);
	}

}
