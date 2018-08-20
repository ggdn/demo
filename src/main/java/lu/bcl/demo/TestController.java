package lu.bcl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
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
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("HOSTNAME")).getSource());
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("KUBERNETES_NODE_NAME")).getSource());
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("KUBERNETES_POD_NAME")).getSource());
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("KUBERNETES_POD_NAMESPACE")).getSource());
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("KUBERNETES_POD_IP")).getSource());
        map.putAll(((MapPropertySource)((AbstractEnvironment) env).getPropertySources().get("KUBERNETES_POD_SERVICE_ACCOUNT")).getSource());
        return map;
    }

}
