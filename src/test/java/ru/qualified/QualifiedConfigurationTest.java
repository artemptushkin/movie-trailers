package ru.qualified;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.qualified.example.Constants;
import ru.qualified.example.SomeInterface;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = QualifiedConfiguration.class)
public class QualifiedConfigurationTest {

    @Autowired
    Map<String, SomeInterface> interfaceMap;

    @Test
    public void test() {
        assertTrue(interfaceMap.containsKey(Constants.SUPER_TYPE));
        assertTrue(interfaceMap.containsKey(Constants.VUPER_TYPE));
        assertTrue(interfaceMap.containsKey("Artem"));
    }
}
