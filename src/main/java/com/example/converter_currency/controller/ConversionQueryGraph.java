package com.example.converter_currency.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.converter_currency.api.response.ConversionWithStatistics;
import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.services.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConversionQueryGraph implements GraphQLQueryResolver {
    private final CalculateService calculateService;

    public List<Conversion> conversion() {
        return this.calculateService.getConversions();
    }

    public List<Currency> currency() {
        return this.calculateService.getAllCurrencies();
    }

    public Set<ConversionWithStatistics> statistics() {
        return this.calculateService.getStatistics();
    }
}

