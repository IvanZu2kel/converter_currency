package com.example.converter_currency.services;

import com.example.converter_currency.models.Currency;
import com.example.converter_currency.models.CurrencyRate;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalculateService {
    private final ConversionRepository conversionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public double calculateValue(String firstCurrency, String secondCurrency, double amount) {
        Currency c1 = currencyRepository.findByCharCode(firstCurrency).orElseThrow();
        Currency c2 = currencyRepository.findByCharCode(secondCurrency).orElseThrow();
        int nominalC1 = c1.getNominal();
        int nominalC2 = c2.getNominal();

        LocalDate localDate = LocalDate.now();
        CurrencyRate cr1 = null, cr2 = null;

        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByDateAndCharCode(localDate, firstCurrency);
//        if (currencyRate.)


        return 0;
    }
}
