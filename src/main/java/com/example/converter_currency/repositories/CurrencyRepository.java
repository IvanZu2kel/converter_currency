package com.example.converter_currency.repositories;

import com.example.converter_currency.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
