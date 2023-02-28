package ru.khokhlov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khokhlov.entity.User;
import ru.khokhlov.entity.Wallet;
import ru.khokhlov.repository.UserRepository;
import ru.khokhlov.repository.WalletRepository;
import ru.khokhlov.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    int KEY_LENGTH = 25;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;

    @Override
    public String createUser(User user) {
        String answer;
        
        if(isUniqueUser(user)) {
            answer = createUniqueKey();
            user.setKey(answer);
            userRepository.saveAndFlush(user);

            Wallet userwallet = new Wallet(user.getId(),0,0,0,"", "",answer);
            walletRepository.saveAndFlush(userwallet);
        }
        else 
            answer = "Such user already exists!";
        
        return answer;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUserExists(String key) {
        return userRepository.isKeyUnique(key) > 0;
    }


    @Override
    public boolean isAdmin(String key) {
        return Objects.equals(userRepository.isItAdmin(key), "admin");
    }


    private boolean isUniqueUser(User user){
        return userRepository.isUserUnique(user.getUsername(), user.getEmail()) == 0;
    }

    private String createUniqueKey() {
        boolean isKeyExists = true;
        String key;

        do{
            key = generateKey();
            isKeyExists = (userRepository.isKeyUnique(key) > 0);
        }while(isKeyExists);

        return key;
    }

    private String generateKey(){
        return new Random().
                ints(KEY_LENGTH, '0', '{').
                mapToObj(i -> String.valueOf((char)i)).
                collect(Collectors.joining());
    }
}


