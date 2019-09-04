package ru.example.movies.movietrailers.domain;

import lombok.Value;
import java.util.List;

@Value
public class SuggestionResponse {
	private final List<Suggestion> suggestions;
}
