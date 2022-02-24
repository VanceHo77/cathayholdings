package com.cathayholdings.homework.model.respository;

import com.cathayholdings.homework.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    boolean existsByCode(String code);

    Optional<Currency> findByCode(String code);
}
