package ru.khokhlov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.khokhlov.service.ExchangeRatesService;
import ru.khokhlov.service.TransactionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchange/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/get-count")
    public String getCountTransaction(@RequestParam Map<String, String> allValues){
        return transactionService.getCountTransaction(allValues);
    }
}
