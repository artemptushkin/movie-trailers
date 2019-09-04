package ru.example.movies.movietrailers.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@RequiredArgsConstructor
public class BuilderFactory implements FactoryBean<YoutubeURLBuilder> {

	private final String urlMask;

	@Override
	public YoutubeURLBuilder getObject() {
		return new YoutubeURLBuilder(urlMask);
	}

	@Override
	public Class<?> getObjectType() {
		return YoutubeURLBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
