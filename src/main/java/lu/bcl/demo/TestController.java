package lu.bcl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    Environment env;

    @GetMapping
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();

        for (PropertySource<?> propertySource1 : ((AbstractEnvironment) env).getPropertySources()) {
            if (propertySource1 instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource1).getSource());
            }
        }
        return map;
    }

}
