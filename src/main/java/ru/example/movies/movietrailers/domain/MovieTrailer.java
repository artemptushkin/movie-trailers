package ru.example.movies.movietrailers.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieTrailer {
	private String movieTrailerUrl;
	private String title;
	private Integer year;
	private String imdbID;
	private String poster;
}
