package ru.khokhlov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import ru.khokhlov.entity.Transaction;
import ru.khokhlov.repository.TransactionRepository;
import ru.khokhlov.repository.UserRepository;
import ru.khokhlov.service.TransactionService;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void addTransaction(String key) {
        Transaction transaction = new Transaction(0L,key, LocalDate.now());
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public String getCountTransaction(Map<String, String> allValues) {
        String key = allValues.get("secret_key");
        String dateFrom = allValues.get("date-from");
        String dateTo = allValues.get("date-to");

        String answer;

        if(userRepository.isItAdmin(key).equals("admin")) {
            int count = transactionRepository.countTransactionByDate(dateFrom, dateTo);

            answer = Integer.toString(count);
        }
        else
            answer = "Wrong Data!";

        return answer;
    }
}
