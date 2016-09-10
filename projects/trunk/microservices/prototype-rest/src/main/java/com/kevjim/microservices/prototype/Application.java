package com.kevjim.microservices.prototype;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarFile;

//@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    private static final String APPLICATION_NAME = "proto";
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * The main method and launch point of this service.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        System.setProperty("spring.config.name", APPLICATION_NAME);
        /**
         * We're setting the spring.application.name to APPLICATION_NAME-Hostname so that when it registers
         * itself to consul he gets a unique folder name.  This lets us run multiple instances of distops
         * on the same consul backend.   This name can be overrode from the command line if multiple instances are run
         * on the same hostname.
         */
        //System.setProperty("spring.application.name", APPLICATION_NAME);
        System.setProperty("spring.application.name", APPLICATION_NAME.concat("-").concat(getHostName()));
        System.setProperty("spring.application.instance_id", getHostName());

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.setId(APPLICATION_NAME);


        Properties manifestProperties = getManifestProperties(ctx);
        if (manifestProperties == null) {
            logger.warn("Unable to find Manifest File");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
/*
            for (String key : manifestProperties.stringPropertyNames()) {
                String value = manifestProperties.getProperty(key);
                logger.info(key + " => " + value);
            }
*/
            try {
                String manifestJSON = objectMapper.writeValueAsString(manifestProperties);
                System.setProperty(Application.class.getSimpleName(), manifestJSON);
                logger.info("Populated Manifest File into System");
            } catch (JsonProcessingException e) {
                logger.warn("Error Creating JSON from Manifest File", e);
            }

        }

    }

    private static Properties getManifestProperties(ConfigurableApplicationContext applicationContext) {

        Enumeration resEnum = null;
        try {
            // Get a list of all manifest files found in the jars loaded by the app
            resEnum = applicationContext.getClassLoader().getResources(JarFile.MANIFEST_NAME);
        } catch (IOException e) {
            logger.warn("Error finding Manifest Files", e);
            return null;
        }

        while (resEnum.hasMoreElements()) {
            try {
                URL url = (URL) resEnum.nextElement();
                InputStream is = url.openStream();
                if (is != null) {
                    Properties properties = new Properties();
                    try {
                        properties.load(is);
                        String test = properties.getProperty("Start-Class");
                        if (test != null && test.contains(Application.class.getSimpleName())) {
                            logger.trace(url.toString());
                            return properties;
                        }
                    } catch (IOException ignored) {

                    }
                }
            } catch (Exception ignored) {
                // Silently ignore wrong manifests on classpath
            }
        }
        return null;
    }


    private static String getHostName() {
        // try InetAddress.LocalHost first;
        //  NOTE -- InetAddress.getLocalHost().getHostName() will not work in certain environments.
        try {
            String result = InetAddress.getLocalHost().getHostName();
            if (StringUtils.isNotEmpty(result))
                return result;
        } catch (UnknownHostException e) {
            // failed;  try alternate means.
        }

        // Try OS populated environment properties.
        String host = System.getenv("COMPUTERNAME");
        if (host != null)
            return host;
        host = System.getenv("HOSTNAME");
        if (host != null)
            return host;

        // Undetermined.
        return "UndeterminedHostName";
    }

}
