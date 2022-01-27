package com.example.converter_currency.services;

import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.models.CurrencyRate;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalculateService {
    private final ConversionRepository conversionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    private static final String RUB = "RUB";

    public double calculateValue(String firstCurrency, String secondCurrency, double amount) {
        LocalDate localDate = LocalDate.now();
        Optional<CurrencyRate> cr1 = getCurrencyRate(localDate, firstCurrency);
        Optional<CurrencyRate> cr2 = getCurrencyRate(localDate, secondCurrency);
        if (cr1.isEmpty() || cr2.isEmpty()) {
            new InitCurrencyService().init(currencyRateRepository, currencyRepository);
            cr1 = getCurrencyRate(localDate, firstCurrency);
            cr2 = getCurrencyRate(localDate, secondCurrency);
        }
        Currency c1 = currencyRepository.findByCharCode(firstCurrency).orElseThrow();
        Currency c2 = currencyRepository.findByCharCode(secondCurrency).orElseThrow();
        int nominalC1 = c1.getNominal();
        int nominalC2 = c2.getNominal();

        double result = Math.round(amount * cr1.get().getRate() / nominalC1 / cr2.get().getRate() * nominalC2);
        Conversion conversion = new Conversion()
                .setDate(localDate)
                .setFirstValue(amount)
                .setFirstCurrency(c1.getCharCode())
                .setSecondValue(result)
                .setSecondCurrency(c2.getCharCode());
        conversionRepository.save(conversion);
        return result;
    }

    private Optional<CurrencyRate> getCurrencyRate(LocalDate localDate, String currency) {
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByDateAndCharCode(localDate, currency);
        Optional<CurrencyRate> cr;
        if (!currency.equals(RUB)) {
            cr = currencyRate;
        } else {
            cr = currencyRateRepository.findByCharCode(RUB);
        }
        return cr;
    }

    public List<Conversion> getConversions(String firstCurrency, String secondCurrency, LocalDate date) {
        List<Conversion> conversions = conversionRepository.findByFirstCurrencyAndSecondCurrencyAndDate(firstCurrency, secondCurrency, date);
        return conversions.stream().filter(c -> c.getDate().isAfter(LocalDate.now().minusDays(7))).toList();
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
