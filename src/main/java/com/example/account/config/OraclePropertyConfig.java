package com.example.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Profile("oracle")
public class OraclePropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();

        Resource[] resources = new ClassPathResource[] {
            new ClassPathResource("application.properties"),
            new ClassPathResource("environment-oracle.properties")
        };

        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);

        return ppc;
    }
}
