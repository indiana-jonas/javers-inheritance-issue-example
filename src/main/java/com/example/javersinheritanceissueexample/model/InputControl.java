package com.example.javersinheritanceissueexample.model;


import lombok.Data;

@Data
public class InputControl
{
    public InputControl(String name) {
        this.name = name;
    }
    String name;
    String value = "";
}
