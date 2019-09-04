package ru.example.movies.movietrailers.controller;

import com.omertron.omdbapi.OMDBException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.io.IOException;

@ControllerAdvice
public class SearchControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(OMDBException.class)
	public ResponseEntity<Object> handleOmbdException(OMDBException exception) {
		return new ResponseEntity<>(exception, new HttpHeaders(), HttpStatus.resolve(exception.getResponseCode()));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IOException.class)
	public void handleIOException() {}
}
