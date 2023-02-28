package ru.khokhlov.service;

import ru.khokhlov.entity.User;

import java.util.List;

public interface UserService {
    String createUser(User user);

    List<User> getAllUsers();

    boolean isUserExists(String key);

    boolean isAdmin(String key);
}
