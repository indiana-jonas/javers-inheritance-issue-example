package com.example.javersinheritanceissueexample;

import com.example.javersinheritanceissueexample.model.DynamicInputFormGroup;
import com.example.javersinheritanceissueexample.model.InputControl;
import com.example.javersinheritanceissueexample.model.InputForm;
import com.example.javersinheritanceissueexample.model.StaticInputFormGroup;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JaversInheritanceIssueExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JaversInheritanceIssueExampleApplication.class, args);
	}

	@Autowired
	FormRepository formRepository;
	@Autowired
	Javers javers;

	@Override
	public void run(String... args) throws Exception
	{
		StaticInputFormGroup staticInputFormGroup = new StaticInputFormGroup();
		staticInputFormGroup.setId("100");
		staticInputFormGroup.setName("staticInputFormGroup");
		staticInputFormGroup.setInputControl(new InputControl("staticInputFormGroupInputControl"));

		DynamicInputFormGroup dynamicInputFormGroup = new DynamicInputFormGroup();
		dynamicInputFormGroup.setId("200");
		dynamicInputFormGroup.setName("dynamicInputFormGroup");
		dynamicInputFormGroup.getInputControlList().add(new InputControl("dynamicInputFormGroupInputControl"));

		InputForm inputForm = new InputForm();
		inputForm.setId("inputFormId");
		inputForm.setName("inputForm");
		inputForm.getInputFormGroups().add(staticInputFormGroup);
		inputForm.getInputFormGroups().add(dynamicInputFormGroup);

		formRepository.save(inputForm);

		//Change the value
		dynamicInputFormGroup.getInputControlList().get(0).setValue("New Value");

		formRepository.save(inputForm);

		//Change the value again
		dynamicInputFormGroup.getInputControlList().get(0).setValue("New Value 2");

		formRepository.save(inputForm);

		List<InputForm> all = formRepository.findAll();
		System.out.println(all.toString());

		List<Change> changes = javers.findChanges(QueryBuilder.byClass(InputForm.class).withChildValueObjects().build());

		for(Change change : changes)
		{
			if(change instanceof ValueChange)
			{
				String path = ((ValueChange) change).getPropertyNameWithPath();
				JqlQuery query = QueryBuilder.byValueObject(InputForm.class, path).build();

				//This throws
				/*
				JaversException: PROPERTY_NOT_FOUND: Property 'inputControlList' not found in class 'com.example.javerspolymorphismissue.model.InputFormGroup'. If the name is correct - check annotations. Properties with @DiffIgnore or @Transient are not visible for JaVers.
				*/
				List<Change> valueChanges = javers.findChanges(query);

				System.out.println(valueChanges.toString());
			}
		}

	}
}
