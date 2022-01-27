package com.example.converter_currency.controller;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.converter_currency.services.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversionMutationGraph implements GraphQLMutationResolver {
    private final CalculateService calculateService;

    public Double calculateValue(final String firstCurrency, final String secondCurrency, final double amount) {
        return this.calculateService.calculateValue(firstCurrency, secondCurrency, amount);
    }

}
