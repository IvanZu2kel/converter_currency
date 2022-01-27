package com.example.converter_currency.repositories;

import com.example.converter_currency.models.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

}
