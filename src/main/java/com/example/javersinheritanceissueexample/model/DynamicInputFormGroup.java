package com.example.javersinheritanceissueexample.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class DynamicInputFormGroup extends InputFormGroup
{
    List<InputControl> inputControlList = new LinkedList<>();
}
