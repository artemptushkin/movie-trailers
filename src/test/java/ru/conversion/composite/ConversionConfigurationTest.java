package ru.conversion.composite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import ru.example.movies.movietrailers.MovieTrailersApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MovieTrailersApplication.class})
public class ConversionConfigurationTest {

    @Autowired
    CompositeProperties compositeProperties;

    @Autowired
    SomeProperties someProperties;

    @Autowired
    WebApplicationContext applicationContext;

    @Test
    public void test() {
        assertNotNull(compositeProperties);

        assertEquals(compositeProperties, someProperties.getComposite());
    }
}
