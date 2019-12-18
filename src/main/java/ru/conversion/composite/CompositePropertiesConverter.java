package ru.conversion.composite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CompositePropertiesConverter implements Converter<String, CompositeProperties> {

    @Autowired
    CompositeProperties compositeProperties;

    @Override
    public CompositeProperties convert(String s) {
        return compositeProperties;
    }
}
