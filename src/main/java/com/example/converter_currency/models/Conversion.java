package com.example.converter_currency.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "conversions")
@Accessors(chain = true)
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "first_currency")
    private String firstCurrency;
    @Column(name = "second_currency")
    private String secondCurrency;
    @Column(name = "first_value")
    private Double firstValue;
    @Column(name = "second_value")
    private Double secondValue;
    @Column(name = "first_rate")
    private Double firstRate;
    @Column(name = "second_rate")
    private Double secondRate;
    @Column
    private LocalDate date;
}
