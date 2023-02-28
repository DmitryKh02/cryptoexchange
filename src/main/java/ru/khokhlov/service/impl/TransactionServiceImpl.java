package ru.khokhlov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import ru.khokhlov.entity.Transaction;
import ru.khokhlov.repository.TransactionRepository;
import ru.khokhlov.repository.UserRepository;
import ru.khokhlov.service.TransactionService;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String from = allValues.get("date-from");
        String to = allValues.get("date-to");

        String answer = null;

        if(userRepository.isItAdmin(key).equals("admin")) {
            try {
                DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

                Date begin = formatter.parse(from);
                Date end = formatter.parse(to);

                Timestamp dateBegin = new Timestamp(begin.getTime());
                Timestamp dateEnd = new Timestamp(end.getTime());

                int count = transactionRepository.countTransactionByDate(dateBegin, dateEnd);

                answer = "transaction_count:" + count;
            }
            catch (ParseException e) {
                System.out.println("Exception :" + e);
            }
        }
        else
            answer = "Wrong Data!";

        return answer;
    }
}
