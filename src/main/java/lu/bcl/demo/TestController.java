package lu.bcl.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    Environment env;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping
    public Map<String, String> test() {
        log.info("test");
        Map<String, String> map = new HashMap<>();
        map.put("KUBERNETES_NODE_NAME", env.getProperty("KUBERNETES_NODE_NAME"));
        map.put("KUBERNETES_POD_NAME", env.getProperty("KUBERNETES_POD_NAME"));
        map.put("KUBERNETES_POD_NAMESPACE", env.getProperty("KUBERNETES_POD_NAMESPACE"));
        map.put("KUBERNETES_POD_IP", env.getProperty("KUBERNETES_POD_IP"));
        return map;
    }

    @GetMapping("/restricted")
    public Map<String, Object> restrictedArea(OAuth2AuthenticationToken authentication) {
        Map<String, Object> data = new LinkedHashMap<>();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        data.put("userName", authentication.getName());
        data.put("attributes", authentication.getPrincipal().getAttributes());
        data.put("clientName", authorizedClient.getClientRegistration().getClientName());
        return data;
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }

}
