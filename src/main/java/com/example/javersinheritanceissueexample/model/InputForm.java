package com.example.javersinheritanceissueexample.model;

import lombok.Data;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.Id;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class InputForm
{
    @Id
    String id;
    String name;
    List<InputFormGroup> inputFormGroups = new LinkedList<>();
}
