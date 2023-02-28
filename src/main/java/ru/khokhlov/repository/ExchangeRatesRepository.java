package ru.khokhlov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.khokhlov.entity.ExchangeRates;


@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Long> {
    @Query(
            value = "SELECT * FROM rates where id = 0",
            nativeQuery = true)
    ExchangeRates getRates();

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE rates SET rub = :rub, ton=:ton, btc =:btc WHERE id = 0",
            nativeQuery = true)
    void setRates(@Param("rub") double rub, @Param("ton") double ton, @Param("btc") double btc);
}
