package ru.khokhlov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "key")
    String key;
    @Column(name = "date")
    LocalDate date;

    public Transaction() {
    }

    public Transaction(Long id, String key, LocalDate date){
        this.id = id;
        this.key = key;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return key.equals(transaction.key);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "KEY: " + key +
                "DATE: " + date + "\n";
    }

}
