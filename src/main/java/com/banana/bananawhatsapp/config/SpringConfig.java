package com.banana.bananawhatsapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia","com.banana.bananawhatsapp.servicios"})
@EntityScan(basePackages = {"com.banana.bananawhatsapp.modelos"})
@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfig {

}
