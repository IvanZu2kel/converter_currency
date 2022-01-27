package com.example.converter_currency.services;

import com.example.converter_currency.api.response.ConversionWithStatistics;
import com.example.converter_currency.exception.NotFoundException;
import com.example.converter_currency.models.Conversion;
import com.example.converter_currency.models.Currency;
import com.example.converter_currency.models.CurrencyRate;
import com.example.converter_currency.repositories.ConversionRepository;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CalculateService {
    private final ConversionRepository conversionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    private static final String RUB = "RUB";

    /**
     * Операция с конвертацией кол-ва одной валюты - в другую
     * @param firstCurrency
     * @param secondCurrency
     * @param amount
     * @return
     */
    @Transactional
    public double calculateValue(String firstCurrency, String secondCurrency, double amount) throws NotFoundException {
        LocalDate localDate = LocalDate.now();
        Optional<CurrencyRate> cr1 = getCurrencyRate(localDate, firstCurrency);
        Optional<CurrencyRate> cr2 = getCurrencyRate(localDate, secondCurrency);
        if (cr1.isEmpty() || cr2.isEmpty()) {
            new InitCurrencyService().init(currencyRateRepository, currencyRepository);
            cr1 = getCurrencyRate(localDate, firstCurrency);
            cr2 = getCurrencyRate(localDate, secondCurrency);
        }
        Optional<Currency> c1 = currencyRepository.findByCharCode(firstCurrency);
        Optional<Currency> c2 = currencyRepository.findByCharCode(secondCurrency);
        if (cr2.isPresent() && cr1.isPresent() && c1.isPresent() && c2.isPresent()) {
            Double rateCr1 = cr1.get().getRate();
            Double rateCr2 = cr2.get().getRate();
            double value = amount * (rateCr1 / c1.get().getNominal()) / (rateCr2 * c2.get().getNominal()) * 100.0 / 100.0;
            double scale = Math.pow(10, 2);
            double result = Math.ceil(value * scale) / scale;
            Conversion conversion = new Conversion()
                    .setDate(localDate)
                    .setFirstValue(amount)
                    .setFirstCurrency(c1.get().getCharCode())
                    .setSecondValue(result)
                    .setSecondCurrency(c2.get().getCharCode())
                    .setFirstRate(rateCr1)
                    .setSecondRate(rateCr2);
            conversionRepository.save(conversion);
            return result;
        }
        throw new NotFoundException();
    }

    /**
     * Получение статистики конвертаций
     * @return
     */
    @Transactional(readOnly = true)
    public Set<ConversionWithStatistics> getStatistics() {
        List<Conversion> conversions = conversionRepository.findAll().stream().filter(c -> c.getDate().isAfter(LocalDate.now().minusDays(7))).toList();
        Set<ConversionWithStatistics> convStat = new HashSet<>();
        for (Conversion c : conversions) {
            String key = c.getFirstCurrency() + c.getSecondCurrency();
            List<Conversion> conv = conversions.stream().filter(cv ->
                    key.equals(cv.getFirstCurrency() + cv.getSecondCurrency())).toList();
            double summa = 0;
            double sumRage = 0;
            for (Conversion cv : conv) {
                summa += cv.getSecondValue();
                sumRage += cv.getSecondRate();
            }
            int count = conv.size();
            ConversionWithStatistics conversionWithStatistics = new ConversionWithStatistics()
                    .setFirstCurrency(c.getFirstCurrency())
                    .setSecondCurrency(c.getSecondCurrency())
                    .setAvgRage((long) sumRage / count)
                    .setVolume((long) summa);
            convStat.add(conversionWithStatistics);
        }
        return convStat;
    }

    /**
     * Получение спика всех конвертаций
     * @return
     */
    @Transactional(readOnly = true)
    public List<Conversion> getConversions() {
        return conversionRepository.findAll();
    }

    /**
     * Получение списка всех валют
     * @return
     */
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

    public List<CurrencyRate> getAllCurrencyRates() {
        return currencyRateRepository.findAll();
    }
}
