package com.example.converter_currency.repositories;

import com.example.converter_currency.models.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
