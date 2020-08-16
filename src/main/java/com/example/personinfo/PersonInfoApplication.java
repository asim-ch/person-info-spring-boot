package com.example.personinfo;

import java.util.Date;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.personinfo.dtos.PersonDTO;
import com.example.personinfo.entities.Person;
import com.example.personinfo.utils.DateUtils;

/**
 * This is the main configuration class to configure the appliaction and also inject
 * required beans in the application context.
 *
 * @author Asim shahzad
 * @since 2020-08-15
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
     * This bean is used to inject the dependency for model mapper along with
     * its configuration, we will use this to map the DTOs with Models and vice
     * versa.
     * 
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapperBean() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Converters to convert dates to appropriate forms
        Converter<String, Date> stringToDateConverter = ctx -> ctx.getSource() != null
                ? DateUtils.convertStringToDate(ctx.getSource()) : null;
        Converter<Date, String> dateToStringConverter = ctx -> ctx.getSource() != null
                ? DateUtils.convertDateToString(ctx.getSource()) : null;

        TypeMap<PersonDTO, Person> personDtoMap = modelMapper.createTypeMap(PersonDTO.class, Person.class);
        TypeMap<Person, PersonDTO> personMap = modelMapper.createTypeMap(Person.class, PersonDTO.class);

        personDtoMap.addMappings(
                mapper -> mapper.using(stringToDateConverter).map(PersonDTO::getBirthday, Person::setBirthday));
        personMap.addMappings(
                mapper -> mapper.using(dateToStringConverter).map(Person::getBirthday, PersonDTO::setBirthday));

        return modelMapper;
    }
}
