package com.example.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String role;
}