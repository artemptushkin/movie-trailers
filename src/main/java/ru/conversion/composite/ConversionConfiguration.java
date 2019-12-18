package ru.conversion.composite;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
@EnableConfigurationProperties(value = {CompositeProperties.class, SomeProperties.class})
public class ConversionConfiguration implements WebMvcConfigurer {
}
