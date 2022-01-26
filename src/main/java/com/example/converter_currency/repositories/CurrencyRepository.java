package com.example.converter_currency.repositories;

import com.example.converter_currency.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    @Query("select c from Currency c where c.charCode = :charCode")
    Optional<Currency> findByCharCode(String charCode);
}
