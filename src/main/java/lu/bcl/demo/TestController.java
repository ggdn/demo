package lu.bcl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    Environment env;

    @GetMapping
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("KUBERNETES_NODE_NAME", env.getProperty("KUBERNETES_NODE_NAME"));
        map.put("KUBERNETES_POD_NAME", env.getProperty("KUBERNETES_POD_NAME"));
        map.put("KUBERNETES_POD_NAMESPACE", env.getProperty("KUBERNETES_POD_NAMESPACE"));
        map.put("KUBERNETES_POD_IP", env.getProperty("KUBERNETES_POD_IP"));
        return map;
    }

}
