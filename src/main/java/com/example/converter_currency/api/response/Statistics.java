package com.example.converter_currency.api.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class Statistics {
    private Set<ConversionWithStatistics> conversions;

}
