package com.kevjim.prototype.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevjim.prototype.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

//import javax.jms.JMSException;
import java.io.IOException;
import java.util.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class ProtoController extends WebSecurityConfigurerAdapter {
    private static final String LOCALE = "locale";
    private static final String EN = "en";
    private static Logger logger = LoggerFactory.getLogger(ProtoController.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @RequestMapping(value = "/protoHealth", method = RequestMethod.GET)
    public String protoHealth() {
        return String.format("Health check for %s at %s", this.getClass().getSimpleName(), new Date().toString());
    }

    @RequestMapping(value = "/preferredLocale", method = RequestMethod.GET)
    public List<String> getPreferredLocale() {
        return Arrays.asList(EN);
    }

    /**
     * This returns Version information that could be useful in troubleshooting the application.
     * At a minimum this contains the build date, svn revision, branch name etc...
     *
     * @return List of the Manifest Properties
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public Map<String, String> getVersion() {
        String manifest = System.getProperty(Application.class.getSimpleName());
        if (manifest == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        try {
            map = objectMapper.readValue(manifest, new TypeReference<HashMap<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

}
