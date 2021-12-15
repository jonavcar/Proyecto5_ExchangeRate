package com.banck.exchangerate.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.banck.exchangerate.infraestructure.repository.ExchangerateCrudRepository;
import com.banck.exchangerate.aplication.model.ExchangerateRepository;

/**
 *
 * @author jonavcar
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public ExchangerateRepository exchangerateRepository() {
        return new ExchangerateCrudRepository();
    }
}
