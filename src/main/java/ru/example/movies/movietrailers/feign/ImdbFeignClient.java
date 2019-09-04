package ru.example.movies.movietrailers.feign;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.SearchResults;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${imdb.api.url}", name = "imdb")
public interface ImdbFeignClient {

	@GetMapping
	SearchResults search(
		@RequestParam String apikey,
		@RequestParam String type,
		@RequestParam(value = "s") String query,
		@RequestParam Integer page
	);

	@GetMapping
	OmdbVideoBasic getInfo(
		@RequestParam String apikey,
		@RequestParam(value = "i") String imdbID
	);
}
