package ru.conversion.composite;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties("i.am.composite")
public class CompositeProperties {
    private String name;
    private String type;
}
