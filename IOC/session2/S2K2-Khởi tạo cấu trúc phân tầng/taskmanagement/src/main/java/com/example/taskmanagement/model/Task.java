package com.example.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private int assignedTo; // ID của người dùng được giao
}