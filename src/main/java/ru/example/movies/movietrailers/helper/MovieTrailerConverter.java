package ru.example.movies.movietrailers.helper;

import com.google.api.services.youtube.model.SearchResult;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.example.movies.movietrailers.domain.MovieTrailer;
import java.util.function.BiFunction;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class MovieTrailerConverter implements BiFunction<OmdbVideoBasic, SearchResult, MovieTrailer> {

	private final YoutubeURLBuilder urlBuilder;
	@Override
	public MovieTrailer apply(OmdbVideoBasic omdbVideoBasic, SearchResult searchResult) {
		return MovieTrailer
			.builder()
			.imdbID(omdbVideoBasic.getImdbID())
			.movieTrailerUrl(urlBuilder
				.setId(searchResult.getId().getVideoId())
				.build())
			.poster(omdbVideoBasic.getPoster())
			.title(omdbVideoBasic.getTitle())
			.year(Integer.valueOf(omdbVideoBasic.getYear()))
			.build();
	}
}
