package ru.khokhlov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "wallet")
public class Wallet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rub")
    private double rubWallet;

    @Column(name = "btc")
    private double btcWallet;

    @Column(name = "ton")
    private double tonWallet;


    @Column(name = "wallet_number")
    private String walletNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "key")
    private String key;

    public Wallet(){

    }

    public Wallet(Long id,
                  double rubWallet,
                  double btcWallet,
                  double tonWallet,
                  String walletNumber,
                  String cardNumber,
                  String key){
        this.id = id;

        this.rubWallet = rubWallet;
        this.btcWallet = btcWallet;
        this.tonWallet = tonWallet;

        this.walletNumber = walletNumber;
        this.cardNumber = cardNumber;
        this.key = key;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return id.equals(wallet.id);
    }

    @Override
    public String toString(){
        return "ID: " + id +
                "RUB: " + rubWallet +
                "BCT: " + btcWallet +
                "TON: " + tonWallet +
                "WALLET_NUMBER: " + walletNumber +
                "CARD_NUMBER: " + cardNumber + "\n";
    }
}
