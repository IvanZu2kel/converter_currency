package com.example.converter_currency;

import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConverterCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterCurrencyApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CurrencyRateRepository currencyRateRepository, CurrencyRepository currencyRepository) {
        return null;
    }
}
