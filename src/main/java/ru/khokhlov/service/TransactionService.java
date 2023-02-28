package ru.khokhlov.service;

import java.util.Map;

public interface TransactionService {
    void addTransaction(String key);

    String getCountTransaction(Map<String,String> allValues);
}
