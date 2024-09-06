package com.banana.bananawhatsapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia","com.banana.bananawhatsapp.servicios","com.banana.bananawhatsapp.controladores"})
@EntityScan(basePackages = {"com.banana.bananawhatsapp.modelos"})
@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = {"com.banana.bananawhatsapp.persistencia"})
public class SpringConfig {

}
