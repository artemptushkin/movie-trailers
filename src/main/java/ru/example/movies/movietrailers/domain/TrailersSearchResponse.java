package ru.example.movies.movietrailers.domain;

import lombok.Value;
import java.util.List;

@Value
public class TrailersSearchResponse {
	private final List<MovieTrailer> trailers;
	private final Integer page;
	private final Integer pageCount;
	private final Integer totalCount;
}
