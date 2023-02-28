package ru.khokhlov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "rates")
public class ExchangeRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal rub;

    private BigDecimal ton;

    private BigDecimal btc;

    public ExchangeRates() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRates exchangeRates = (ExchangeRates) o;
        return id.equals(exchangeRates.id);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "RUB: " + rub +
                "TON: " + ton +
                "BTC: " + btc + "\n";
    }
}
