package com.example.converter_currency.services;

import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.models.CurrencyRate;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

        Double rateCr1 = cr1.get().getRate();
        Double rateCr2 = cr2.get().getRate();
        double result = Math.round(amount * (rateCr1 / nominalC1) / (rateCr2 * nominalC2) * 100.0 / 100.0);
        Conversion conversion = new Conversion()
                .setDate(localDate)
                .setFirstValue(amount)
                .setFirstCurrency(c1.getCharCode())
                .setSecondValue(result)
                .setSecondCurrency(c2.getCharCode())
                .setFirstRate(rateCr1)
                .setSecondRate(rateCr2);
        conversionRepository.save(conversion);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Conversion> getStatistics() {
        List<Conversion> conversions = conversionRepository.findByFirstCurrencyAndSecondCurrencyAndDate(firstCurrency, secondCurrency, date);
        return conversions.stream().filter(c -> c.getDate().isAfter(LocalDate.now().minusDays(7))).toList();
    }

    @Transactional(readOnly = true)
    public List<Conversion> getConversions() {
        List<Conversion> conversions = conversionRepository.findAll();
        return conversions.stream().filter(c -> c.getDate().isAfter(LocalDate.now().minusDays(7))).toList();
    }

    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    private Optional<CurrencyRate> getCurrencyRate(LocalDate localDate, String currency) {
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByDateAndCharCode(localDate, currency);
        Optional<CurrencyRate> cr;
        if (!currency.equals(RUB)) {
            cr = currencyRate.stream().filter(c -> c.getDate().equals(LocalDate.now())).findFirst();
        } else {
            cr = currencyRateRepository.findByCharCode(RUB);
        }
        return cr;
    }
}
