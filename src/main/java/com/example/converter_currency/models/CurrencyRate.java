package com.example.converter_currency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "currency_rates")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;
    @Column
    private LocalDate date;
    @Column(name = "char_code")
    private String charCode;
    @Column
    private Double rate;
}
