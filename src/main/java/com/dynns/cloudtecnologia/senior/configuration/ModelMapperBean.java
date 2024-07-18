package com.dynns.cloudtecnologia.senior.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
