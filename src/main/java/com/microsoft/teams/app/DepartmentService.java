package com.microsoft.teams.app;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Department_23;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 







@Component
public class DepartmentService {
	
	@Autowired
	DepartmentImpl departmentImpl;
	

	
	public String createDepartmentAdaptiveCard() {

		// ============= create department Json structure done =======================

		List<Department_23> departmentList = departmentImpl.findAll();
		System.out.println(departmentList);

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		List<ActionSet> actList = new ArrayList<>();

		String json = null;

		Container con = new Container();
		con.setType("Container");

		Item it1 = new Item();
		it1.setType("TextBlock");
		it1.setText("You require support from which department ?");
		it1.setWeight("bolder");
		it1.setSize("medium");

		ArrayList<Item> item = new ArrayList<>();
		item.add(it1);
		con.setItems(item);

		conlist.add(con);

		Container con2 = new Container();
		con2.setType("Input.ChoiceSet");
		con2.setId("Department");
		con2.setIsMultiSelect(false);
		con2.setStyle("expanded");
		con2.setValue("0");

		ArrayList<Choices> choiceList = new ArrayList<>();
		for (Department_23 dep : departmentList) {

			Choices choice = new Choices();
			choice.setTitle(dep.getDeptName());

			choice.setValue(dep.getId());
			choiceList.add(choice);
		}

		con2.setChoices(choiceList);

		conlist.add(con2);

		adcard.setBody(conlist);

		ActionSet action = new ActionSet();
		action.setType("Action.Submit");
		action.setTitle("OK");

		actList.add(action);

		adcard.setActions(actList);

		// ============= create department Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;

		/*
		 * List<Department_23> departmentList = departmentImpl.findAll();
		 * System.out.println(departmentList);
		 */

	}
	
	
}
