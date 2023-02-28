package ru.khokhlov.service;

import java.util.List;
import java.util.Map;

public interface ExchangeRatesService {
    String setRates(Map<String, String> allValues);
    List<String> getRates(Map<String, String> allValues);
}
