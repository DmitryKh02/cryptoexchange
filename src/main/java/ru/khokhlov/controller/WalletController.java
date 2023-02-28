package ru.khokhlov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.khokhlov.entity.Wallet;
import ru.khokhlov.service.WalletService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchange/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void addWallet(@RequestBody Wallet wallet){
        walletService.createWallet(wallet);
    }

    @GetMapping("/balance")
    public List<String> getBalance(@RequestParam String key){
        return walletService.getBalance(key);
    }

    @GetMapping("/currency-balance")
    public String getAllCurrencyBalance(@RequestParam Map<String, String> allParam){
        return walletService.getAllCurrencyBalance(allParam);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public List<String> addCurrency(@RequestParam Map<String, String> allParam){
        return walletService.addCurrency(allParam);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/withdrawal-of-funds")
    public String getCurrency(@RequestParam Map<String, String> allParam){
        return walletService.getCurrency(allParam);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/transaction")
    public List<String> exchange(@RequestParam Map<String, String> allParam){
        return walletService.exchange(allParam);
    }
}
