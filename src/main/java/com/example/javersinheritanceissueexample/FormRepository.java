package com.example.javersinheritanceissueexample;

import com.example.javersinheritanceissueexample.model.InputForm;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface FormRepository extends MongoRepository<InputForm, String>
{
}
