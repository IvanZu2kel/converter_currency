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
    @Column
    private String firstCurrency;
    @Column
    private String secondCurrency;
    @Column
    private Double firstValue;
    @Column
    private Double secondValue;
    @Column
    private LocalDate date;
}
