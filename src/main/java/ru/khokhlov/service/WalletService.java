package ru.khokhlov.service;

import ru.khokhlov.entity.Wallet;

import java.util.List;
import java.util.Map;

public interface WalletService {
    Wallet searchWallet(String key);

    void createWallet(Wallet wallet);

    List<String> getBalance(String key);

    List<String> addCurrency(Map<String, String> value);

    String getCurrency(Map<String, String> value);

    String getAllCurrencyBalance(Map<String, String> allParam);

    List<String> exchange(Map<String, String> allParam);
}
