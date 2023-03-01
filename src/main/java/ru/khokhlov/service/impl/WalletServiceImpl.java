package ru.khokhlov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khokhlov.entity.Currency;
import ru.khokhlov.entity.ExchangeRates;
import ru.khokhlov.entity.Wallet;
import ru.khokhlov.repository.ExchangeRatesRepository;
import ru.khokhlov.repository.UserRepository;
import ru.khokhlov.repository.WalletRepository;
import ru.khokhlov.service.TransactionService;
import ru.khokhlov.service.WalletService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExchangeRatesRepository exchangeRatesRepository;

    @Autowired
    TransactionService transactionService;

    @Override
    public Wallet searchWallet(String key) {
        return walletRepository.findWalletByKey(key);
    }

    @Override
    public void createWallet(Wallet wallet) {
        walletRepository.saveAndFlush(wallet);
    }
    
    @Override
    public List<String> getBalance(String key) {
        Wallet wallet = searchWallet(key);
        List<String> balance = new ArrayList<>();

        balance.add("rub:" + wallet.getRubWallet());
        balance.add("ton:" + wallet.getTonWallet());
        balance.add("btc:" + wallet.getBtcWallet());

        return balance;
    }

    @Override
    public List<String> addCurrency(Map<String,String> allValues){
        String key =  allValues.get("secret_key");
        double refill;
        List<String> answer = new ArrayList<>();

        if(userRepository.isKeyUnique(key) != 0) {

            for (Currency cur : Currency.values()) {
                if (allValues.containsKey(cur.toString())) {
                    refill = Double.parseDouble(allValues.get(cur.toString()));
                    replenishment(cur, refill, key);
                    refill = 0;
                }
            }

            answer = getBalance(key);

            transactionService.addTransaction(key);
        }
        else
            answer.add("Wrong data!");

        return answer;
    }

    @Override
    public String getCurrency(Map<String, String> value) {
        String key = value.get("secret_key");
        double count = - Double.parseDouble(value.get("count"));
        String answer = "Wrong Data!";

        if(userRepository.isKeyUnique(key) != 0){
            String walletInfo;

            if(value.containsKey("credit_card")) {
                walletInfo = value.get("credit_card");
                walletRepository.setCard(walletInfo, key);
            }
            else {
                walletInfo = value.get("wallet");
                walletRepository.setWallet(walletInfo, key);
            }

            Currency currency = getCurrency(value.get("currency"));
            answer = replenishment(currency, count, key);

            transactionService.addTransaction(key);
        }

        return answer;
    }

    @Override
    public String getAllCurrencyBalance(Map<String, String> allParam) {
        String key = allParam.get("secret_key");
        Currency currency = getCurrency(allParam.get("currency"));

        String answer = "Access is denied!";

        if(Objects.equals(userRepository.isItAdmin(key), "admin")) {


            switch (currency) {
                case RUB: {
                    answer = "RUB: " + walletRepository.getSumOfRub();
                }
                break;
                case TON: {
                    answer = "TON: " + walletRepository.getSumOfTon();
                }
                break;
                case BTC: {
                    answer = "Btc: " + walletRepository.getSumOfBtc();
                }
                break;
            }
        }

        return answer;
    }

    @Override
    public List<String> exchange(Map<String, String> allParam) {
        String key = allParam.get("secret_key");
        String from = allParam.get("currency_from");
        String to = allParam.get("currency_to");
        double amountFrom = Double.parseDouble(allParam.get("amount"));
        double amountTo = 0;

        if(userRepository.isKeyUnique(key) != 0){
            ExchangeRates rates = exchangeRatesRepository.getRates();
            BigDecimal rub = rates.getRub(), btc = rates.getBtc(), ton = rates.getTon();

            Currency currencyFrom = getCurrency(from);
            Currency currencyTo = getCurrency(to);

            if(!Objects.equals(replenishment(currencyFrom, -amountFrom, key), "Currency not found")){

                BigDecimal factor = getFactor(currencyFrom,currencyTo,rub,ton,btc);
                amountTo = factor.multiply(BigDecimal.valueOf(amountFrom), MathContext.UNLIMITED).doubleValue();

                replenishment(currencyTo, amountTo, key);

                transactionService.addTransaction(key);
            }
        }

        List<String> answer = new ArrayList<>();
        answer.add("currency_from:" + from);
        answer.add("currency_to:" + to);
        answer.add("amount_from:" + amountFrom);
        answer.add("amount_to:" + amountTo);
        return answer;
    }

    private BigDecimal getFactor(Currency from ,Currency to, BigDecimal rub, BigDecimal ton, BigDecimal btc){
        BigDecimal factor = BigDecimal.valueOf(0.0);

        switch (from){
            case RUB:{
                if ("TON".equals(to.toString()))
                    factor = ton.divide(rub,8, RoundingMode.FLOOR);
                else
                    factor = btc.divide(rub,8, RoundingMode.FLOOR);

            }
            break;
            case TON:{
                if ("RUB".equals(to.toString()))
                    factor = rub.divide(ton,8, RoundingMode.FLOOR);
                else
                    factor = btc.divide(ton,8, RoundingMode.FLOOR);
            }
            break;
            case BTC: {
                if ("RUB".equals(to.toString()))
                    factor = rub.divide(btc,8, RoundingMode.FLOOR);
                else
                    factor = ton.divide(btc,8, RoundingMode.FLOOR);

            }
            break;
        }

        return factor;
    }

    private String replenishment(Currency cur, double value, String key){
        String answer="Currency not found!";

        switch (cur){
            case RUB:{
                if (walletRepository.getRub(key) < Math.abs(value) && value<0)
                    answer = "Insufficient funds!";
                else {
                    walletRepository.addRub(value, key);
                    answer = "Rub: " + walletRepository.getRub(key);
                }
            }
            break;
            case TON:{
                if (walletRepository.getTon(key) < Math.abs(value) && value<0)
                    answer = "Insufficient funds!";
                else {
                    walletRepository.addTon(value, key);
                    answer = "Ton: " + walletRepository.getTon(key);
                }
            }
            break;
            case BTC: {
                if (walletRepository.getBtc(key) < Math.abs(value) && value<0)
                    answer = "Insufficient funds!";
                else {
                    walletRepository.addBtc(value, key);
                    answer = "Btc: " + walletRepository.getBtc(key);
                }
            }
            break;
        }
        return answer;
    }

    private Currency getCurrency(String value){
        Currency currency = Currency.RUB;

        for (Currency cur: Currency.values()) {
            if(Objects.equals(value, cur.toString()))
                currency = cur;
        }

        return currency;
    }

}
