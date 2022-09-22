package com.microsoft.teams.app;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.SupportDepartment_311;
import com.microsoft.teams.app.repository.SupportRepo;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 







@Component
public class DepartmentService {
	
	@Autowired
	DepartmentImpl departmentImpl;
	
	@Autowired
	SupportRepo supportRepo;
	
	
	
	/*
	 * @Autowired AuthenticationService authService;
	 */
	

	
	public String createDepartmentAdaptiveCard() {

		// ============= create department Json structure done =======================

		List<SupportDepartment_311> departmentList = departmentImpl.findAll();
		System.out.println(departmentList);

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		List<ActionSet> actList = new ArrayList<>();

		String json = null;

		Container con = new Container();
		con.setType("Container");
		

		MsTeams mst=new MsTeams();
		mst.setWidth("full");

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
		for (SupportDepartment_311 dep : departmentList) {

			Choices choice = new Choices();
			choice.setTitle(dep.getDeptName());

			choice.setValue(dep.getId());
			choiceList.add(choice);
		}

		con2.setChoices(choiceList);

		conlist.add(con2);

		adcard.setBody(conlist);
		
		adcard.setMsTeams(mst);

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
	
	public String ShowHelpDeskImage() {
		
		/*
		 * { "$schema": "http://adaptivecards.io/schemas/adaptive-card.json", "type":
		 * "AdaptiveCard", "version": "1.0", "body": [ { "type": "Image", "url":
		 * "https://adaptivecards.io/content/cats/1.png", "altText": "Cat" } ] }
		 */
		
		
		// ============= create department Json structure done =======================

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		
		MsTeams mst=new MsTeams();
		mst.setWidth("full");

		String json = null;

		Container con = new Container();
		con.setType("Image");
		//con.setUrl("https://www.w3schools.com/css/paris.jpg");
		// srinivasa_help_desk.png 
		// stackedcolumnchart.png
		// https://135.181.202.86:12002/kagami-generated_Srinivasa_Live/
		
	//	URL resource = getClass().getClassLoader().getResource("srinivaslogo.png");
		
	//	System.out.println(resource);
		
		//con.setUrl(resource.getFile());
	//	con.setUrl("https://ess.kagamierp.com/kagami-generated_HRMS_GROUP/assets/images/report_icons/stackedcolumnchart.png");
	//	con.setUrl("https://135.181.202.86:12002/kagami-generated_Srinivasa_Live/assets/images/report_icons/srinivaslogo.png");
		//con.setUrl("https://adaptivecards.io/content/cats/1.png");
		
	//	https://thumbs.dreamstime.com/b/beautiful-rain-forest-ang-ka-nature-trail-doi-inthanon-national-park-thailand-36703721.jpg
		
		//con.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTe4SbTvUMHFpg5yI-p1ONBNewWPA6Rp4vH_Q&usqp=CAU");
		
// https://lh3.googleusercontent.com/86_CXv2VK9Q-z9h96NShC3sfXNvXPF2pLOb638IMp1zb5MzqRRL9iPDCbXDbI4mtz0eS0Q=s170
	//	con.setUrl("https://lh3.googleusercontent.com/cebdtLR-fF_74roBaUvtIMS0rVRPksapPTIQjea6UWztw7tX1HCG52RLcFQcgG6zmhQ1FSw=s170");
		
		//con.setUrl("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiZ999Ee26EBiVgSrX0wGDCbfNGkJWG62nO8TmbonZG8nFy_EfkYOxjcNSQARu-JDNmtqsZSpMQ4Grq3ahtHnOALEMxss4T1WW__o4GF3eXOs5RUE6Su6-K4EVHQmndFPCdXwdUpFlsTjG06r-gYBKIk1tXh2lchWPiVLzO0h6QlWgGahOr4hF8ADZg/w463-h130/srinivasa_help_desk.png");
		con.setUrl("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiZ999Ee26EBiVgSrX0wGDCbfNGkJWG62nO8TmbonZG8nFy_EfkYOxjcNSQARu-JDNmtqsZSpMQ4Grq3ahtHnOALEMxss4T1WW__o4GF3eXOs5RUE6Su6-K4EVHQmndFPCdXwdUpFlsTjG06r-gYBKIk1tXh2lchWPiVLzO0h6QlWgGahOr4hF8ADZg/w916-h130/srinivasa_help_desk.png");
		//
	
	//	con.setUrl("https://135.181.202.86:12002/kagami-generated_Srinivasa_Live/assets/images/bitkemylogo.png");

		con.setAltText("Helpdesk Image");

		conlist.add(con);

		adcard.setBody(conlist);
		
		adcard.setMsTeams(mst);

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
