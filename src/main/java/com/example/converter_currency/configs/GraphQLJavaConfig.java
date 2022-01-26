package com.example.converter_currency.configs;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.repositories.ConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GraphQLJavaConfig {
    private final ConversionRepository conversionRepository;
//    private final RestTemplate restTemplate;

    @Bean
    public GraphQLQueryResolver graphQLQueryResolver() {
        return new GraphQLQueryResolver() {
          public Conversion conversion(Long id) {
              return  conversionRepository.getById(id);
          }
        };
    }
}
