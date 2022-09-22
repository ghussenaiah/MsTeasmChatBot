package com.microsoft.teams.app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;

import com.microsoft.teams.app.service.impl.SupportImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class SupportService {

	@Autowired
	SupportImpl supportImpl;

	/*
	 * @Autowired AuthenticationService authService;
	 */

	public String createSupportAdaptiveCard(String departmentId, LinkedHashMap botResponseMap,
			ConcurrentHashMap<String, Ticket_296> ticket, TurnContext turnContext) {

		// ============= create department Json structure done =======================

		Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());

		if (tkt != null) {
			tkt.setSupportDepartmentId(departmentId);
		} else {
			Ticket_296 t = new Ticket_296();
			t.setSupportDepartmentId(departmentId);
			ticket.put(turnContext.getActivity().getFrom().getId(), t);
		}

		List<Support_298> supportList = supportImpl.findAllBySupportdepartmentId(departmentId);
		System.out.println(supportList);

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
		it1.setText("Please select the type of support");
		it1.setWeight("bolder");
		it1.setSize("medium");

		ArrayList<Item> item = new ArrayList<>();
		item.add(it1);
		con.setItems(item);

		conlist.add(con);

		Container con2 = new Container();
		con2.setType("Input.ChoiceSet");
		con2.setId("SupportType");
		con2.setIsMultiSelect(false);
		con2.setStyle("expanded");
		con2.setValue("0");

		ArrayList<Choices> choiceList = new ArrayList<>();
		for (Support_298 supp : supportList) {

			Choices choice = new Choices();
			choice.setTitle(supp.getSupportType());

			choice.setValue(supp.getId());
			choiceList.add(choice);
		}

		con2.setChoices(choiceList);

		conlist.add(con2);

		adcard.setBody(conlist);

		ActionSet action = new ActionSet();
		action.setType("Action.Submit");
		action.setTitle("OK");

		actList.add(action);
		adcard.setMsTeams(mst);

		adcard.setActions(actList);

		// ============= create department Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;

		/*
		 * List<Department_23> departmentList = departmentImpl.findAll();
		 * System.out.println(departmentList);
		 */

	}

}
