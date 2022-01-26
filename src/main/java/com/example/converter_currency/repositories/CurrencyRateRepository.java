package com.example.converter_currency.repositories;

import com.example.converter_currency.models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {
}
