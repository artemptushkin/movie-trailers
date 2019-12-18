package ru.conversion.composite;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("some")
public class SomeProperties {
    private CompositeProperties composite;
}
