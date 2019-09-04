package ru.example.movies.movietrailers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Youtube search result is empty")
public class YoutubeEmptySearchException extends RuntimeException {
}
