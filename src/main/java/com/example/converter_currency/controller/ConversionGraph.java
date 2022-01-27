package com.example.converter_currency.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.services.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConversionGraph implements GraphQLQueryResolver {
    private final ConversionRepository conversionRepository;
    private final CalculateService calculateService;
//    private final RestTemplate http;

    public List<Conversion> conversion() {
        return this.calculateService.getConversions();
    }

    public List<Currency> currency() {
        return this.calculateService.getAllCurrencies();
    }
}

