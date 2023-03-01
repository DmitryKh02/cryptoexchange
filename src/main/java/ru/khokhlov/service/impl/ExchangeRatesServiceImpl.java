package ru.khokhlov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khokhlov.entity.ExchangeRates;

import ru.khokhlov.repository.ExchangeRatesRepository;
import ru.khokhlov.repository.UserRepository;
import ru.khokhlov.service.ExchangeRatesService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    @Autowired
    ExchangeRatesRepository exchangeRatesRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public String setRates(Map<String, String> allValues) {
        String answer ="";
        String key = allValues.get("secret_key");
        String currency = allValues.get("base_currency");

        if(userRepository.isItAdmin(key).equals("admin")){
            double rub = 0, btc = 0, ton = 0;

            switch (currency) {
                case "RUB": {
                    rub = 1;
                    btc = Double.parseDouble(allValues.get("BTC"));
                    ton = Double.parseDouble(allValues.get("TON"));
                }
                break;
                case "TON": {
                    ton = 1;
                    btc = Double.parseDouble(allValues.get("BTC"));
                    rub = Double.parseDouble(allValues.get("RUB"));
                }
                break;
                case "BTC": {
                    btc = 1;
                    ton = Double.parseDouble(allValues.get("TON"));
                    rub = Double.parseDouble(allValues.get("RUB"));
                }
                break;
            }

            exchangeRatesRepository.setRates(rub,ton,btc);
        }
        else {
            answer = "Access is denied!";
        }

        return answer;
    }

    @Override
    public List<String> getRates(Map<String, String> allValues) {
        List<String> resultRates = new ArrayList<>();
        String key = allValues.get("secret_key");
        String currency = allValues.get("currency");

        if(userRepository.isKeyUnique(key) > 0) {
            ExchangeRates rates = exchangeRatesRepository.getRates();
            BigDecimal rub = rates.getRub(), btc = rates.getBtc(), ton = rates.getTon();


            switch (currency) {
                case "RUB": {
                    resultRates.add("TON: " + ton.divide(rub,8,RoundingMode.FLOOR));
                    resultRates.add("BTC: " + btc.divide(rub,8,RoundingMode.FLOOR));
                }
                break;
                case "TON": {
                    resultRates.add("RUB: " + rub.divide(ton,2,RoundingMode.FLOOR));
                    resultRates.add("BTC: " + btc.divide(ton,8,RoundingMode.FLOOR));
                }
                break;
                case "BTC": {
                    resultRates.add("RUB: " + rub.divide(btc,2,RoundingMode.FLOOR));
                    resultRates.add("TON: " + ton.divide(btc,8,RoundingMode.FLOOR));
                }
                break;
            }
        }
        else
        {
            resultRates.add("Wrong data!");
        }

        return  resultRates;
    }
}
