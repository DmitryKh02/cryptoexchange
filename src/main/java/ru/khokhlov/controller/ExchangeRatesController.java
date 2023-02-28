package ru.khokhlov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.khokhlov.service.ExchangeRatesService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchange/rates")
public class ExchangeRatesController {
    private final ExchangeRatesService exchangeRatesService;

    public ExchangeRatesController(ExchangeRatesService exchangeRatesService){
        this.exchangeRatesService = exchangeRatesService;
    }

    @GetMapping("/get")
    public List<String> getRates(@RequestParam Map<String, String> allValues){
        return exchangeRatesService.getRates(allValues);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/change")
    public String changeRates(@RequestParam Map<String, String> allParam){
        return exchangeRatesService.setRates(allParam);
    }
}

