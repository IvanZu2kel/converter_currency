package com.example.converter_currency.api.request;

import com.example.converter_currency.models.Currency;
import com.example.converter_currency.models.CurrencyRate;
import lombok.Data;

import java.util.List;

@Data
public class DataFromXml {
    private List<Currency> currencies;
    private List<CurrencyRate> currencyRates;
}
