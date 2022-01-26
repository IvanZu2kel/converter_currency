package com.example.converter_currency.controller;

import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversionGraph {
    private final ConversionRepository conversionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
//    private final RestTemplate http;

    @GraphQLQuery(name = "conversion")
    public Conversion conversion(@GraphQLArgument(name = "id") Long id) {
        return conversionRepository.getById(id);
    }

    @GraphQLQuery(name = "currency")
    public Currency currency(@GraphQLArgument(name = "id") String id) {
        return currencyRepository.getById(id);
    }
}

