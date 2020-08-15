package com.example.personinfo;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
* This is the main configuration class to configure the EHCache manager
* to leverage cache functionalities in our application.
*
* @author  Asim shahzad
* @since   2020-08-15 
*/
@SpringBootApplication
@EntityScan(basePackages = { "com.example.personinfo.entities" })
@EnableJpaRepositories
@EnableJpaAuditing
public class PersonInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonInfoApplication.class, args);
    }

    /**
     * This bean is used to inject the dependency for model mapper along with its configuration,
     * we will use this to map the DTOs with Models and vice versa.
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapperBean() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
