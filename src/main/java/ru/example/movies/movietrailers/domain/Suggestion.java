package ru.example.movies.movietrailers.domain;

import lombok.Value;

@Value
public class Suggestion {
	private final String title;
	private final String imdbID;
	private final String poster;
}
