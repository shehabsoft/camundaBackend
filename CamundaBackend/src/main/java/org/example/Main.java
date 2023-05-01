package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class Main  extends SpringBootServletInitializer implements WebApplicationInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(Main.class);
    }

    public static void main(String... args) {
        LOGGER.debug("FitnessApiApplication before start.");
        SpringApplication.run(Main.class, args);
        LOGGER.debug("FitnessApiApplication after started.");
    }
}