package ru.example.movies.movietrailers.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.example.movies.movietrailers.domain.MovieTrailer;
import ru.example.movies.movietrailers.domain.TrailersSearchResponse;
import ru.example.movies.movietrailers.properties.ImdbProperties;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class TrailersCollectorFactory {

	private final ImdbProperties imdbProperties;

	public Collector<CompletableFuture<MovieTrailer>, ?, TrailersSearchResponse> trailersResponseCollector(Integer page, Integer totalResults) {
		return collectingAndThen(toList(), getFinisher(page, totalResults));
	}

	private Function<List<CompletableFuture<MovieTrailer>>, TrailersSearchResponse> getFinisher(Integer page, Integer totalResults) {
		return movieTrailers -> new TrailersSearchResponse(
			movieTrailers.stream().map(CompletableFuture::join).collect(toList()),
			page,
			(int) Math.ceil( (double) totalResults / imdbProperties.getSearchPageSize()),
			totalResults
		);
	}
}
