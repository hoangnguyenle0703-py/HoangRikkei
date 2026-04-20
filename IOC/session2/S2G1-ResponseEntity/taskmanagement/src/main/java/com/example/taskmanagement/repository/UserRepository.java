package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = Arrays.asList(
            new User(1, "nguyenvana", "ana@gmail.com", "ADMIN"),
            new User(2, "tranvanb", "ban@gmail.com", "USER"),
            new User(3, "lethic", "clt@gmail.com", "USER")
    );

    public List<User> findAll() { return users; }
}