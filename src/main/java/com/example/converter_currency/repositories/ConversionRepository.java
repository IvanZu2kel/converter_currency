package com.example.converter_currency.repositories;

import com.example.converter_currency.models.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    @Query("select SUM(c.secondValue) from Conversion c " +
            "where c.firstCurrency = :firstCurrency " +
            "and c.secondCurrency = :secondCurrency group by c.secondValue")
    Optional<Long> findSumByCurrency(String firstCurrency, String secondCurrency);
}
