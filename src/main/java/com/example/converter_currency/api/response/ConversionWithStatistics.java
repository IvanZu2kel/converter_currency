package com.example.converter_currency.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConversionWithStatistics {
    @JsonProperty(value = "first_currency")
    private String firstCurrency;
    @JsonProperty(value = "second_currency")
    private String secondCurrency;
    @JsonProperty(value = "avg_rage")
    private Double avgRage;
    @JsonProperty(value = "volume")
    private long volume;
}
