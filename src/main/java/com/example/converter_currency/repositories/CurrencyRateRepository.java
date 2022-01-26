package com.example.converter_currency.repositories;

import com.example.converter_currency.models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {
    Optional<CurrencyRate> findByDateAndCharCode(LocalDate localDate, String firstCurrency);
}
