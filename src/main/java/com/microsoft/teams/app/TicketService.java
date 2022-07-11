package com.microsoft.teams.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.teams.app.entity.ActionData;
import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.Actionfallback;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Department_23;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.TicketRepo;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.microsoft.teams.app.service.impl.SupportImpl;
import com.microsoft.teams.app.service.impl.TicketImpl;
import com.microsoft.teams.app.utility.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 
import com.microsoft.teams.app.repository.DepartmentRepo;

@Component
public class TicketService {
	
	@Autowired
	SupportImpl supportImpl;
	
	@Autowired
	TicketImpl ticketImpl;
	
	@Autowired
	Utility utility;
	
	@Autowired
	TicketRepo ticketRepo;
	
	@Autowired
	AutoGenerationRepo autoGenerationRepo;
	
	@Autowired
	DepartmentImpl departmentImpl;
	
	@Autowired
	EscalateTicketQualityService ecalateTicketQualityService;
	
	/*
	 * @Autowired AuthenticationService authService;
	 */
	
	
	
	public String createTicketAdaptiveCard(String supportTypeId, LinkedHashMap botResponseMap,
			ConcurrentHashMap<String, Ticket_296> ticket,TurnContext turnContext) {

		// ============= create ticket Json structure done =======================
		
		String Ticket="{\n"
				+ "  \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
				+ "  \"type\": \"AdaptiveCard\",\n"
				+ "  \"version\": \"1.0\",\n"
				+ "  \"body\": [\n"
				+ "  {\n"
				+ "      \"type\": \"TextBlock\",\n"
				+ "      \"text\": \"One line description of issue (Ticket Title)\",\n"
				+ "          \"weight\": \"bolder\",\n"
				+ "          \"size\": \"medium\"\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"type\": \"Input.Text\",\n"
				+ "      \"id\": \"IssueTitle\",\n"
				+ "      \"placeholder\": \"enter text here\",\n"
				+ "      \"maxLength\": 500\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"type\": \"TextBlock\",\n"
				+ "      \"text\": \"Detailed description of issue\",\n"
				+ "          \"weight\": \"bolder\",\n"
				+ "          \"size\": \"medium\"\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"type\": \"Input.Text\",\n"
				+ "      \"id\": \"IssueDescription\",\n"
				+ "      \"placeholder\": \"enter text here\",\n"
				+ "      \"maxLength\": 500,\n"
				+ "      \"isMultiline\": true\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"actions\": [\n"
				+ "    {\n"
				+ "      \"type\": \"Action.Submit\",\n"
				+ "      \"title\": \"OK\"\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}\n"
				+ "";
		
		
		Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());
		
		if (tkt != null) {
			tkt.setSupportId(supportTypeId);
		} 


		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		List<ActionSet> actList = new ArrayList<>();
		
		MsTeams mst=new MsTeams();
		mst.setWidth("full");
		
		adcard.setMsTeams(mst);

		String json = null;

		Container con = new Container();
		con.setType("TextBlock");
		con.setText("One line description of issue (Ticket Title)");
		con.setWeight("bolder");
		con.setSize("medium");
		
		Container con2 = new Container();
		con2.setType("Input.Text");
		con2.setId("IssueTitle");
		con2.setPlaceholder("enter text here");
		//con2.setMaxLength("500");
		
		
		Container con3 = new Container();
		con3.setType("TextBlock");
		con3.setText("Detailed description of issue");
		con3.setWeight("bolder");
		con3.setSize("medium");
		
		Container con4 = new Container();
		con4.setType("Input.Text");
		con4.setId("IssueDescription");
		con4.setPlaceholder("enter text here");
		//con4.setMaxLength("1000");
		con4.setIsMultiline(true);
		
		conlist.add(con);
		conlist.add(con2);
		conlist.add(con3);
		conlist.add(con4);

		adcard.setBody(conlist);

		ActionSet action = new ActionSet();
		action.setType("Action.Submit");
		action.setTitle("OK");

		actList.add(action);

		adcard.setActions(actList);
		
		

		// ============= create ticket Json structure done =======================

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

	public String createTicket(LinkedHashMap botResponseMap, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {

		Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());

		Date dt = new Date();
		String json = null;

		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// String check = dateFormat.format(dt);

		// status 27 for open and 28 for submitted

		AutoGenarationCode lastNumberObj = autoGenerationRepo.getLastTicketNumber("ChatHistory_299");
		int tktNumber = lastNumberObj.getAutoCodeNo();

		Long trasactionNumber = utility.nextId();

		if (tkt != null) {
			tkt.setTicketTitle((String) (botResponseMap).get("IssueTitle"));
			tkt.setDescription((String) (botResponseMap).get("IssueDescription"));
			tkt.setStatuscycleId("27");
			tkt.setCreateDateTime(dt);
			tkt.setUpdateDateTime(dt);
			tkt.setTicketNumber(String.valueOf(lastNumberObj.getAutoCodeNo()));
			tkt.setId(String.valueOf(lastNumberObj.getAutoCodeNo()));
			tkt.setEmployeeTeamsId(turnContext.getActivity().getFrom().getId());
			tkt.setTransactionentityId(trasactionNumber);
			tkt.setChatGroupId(null);
			tkt.setNextRoles(null);
			//tkt.setPriorityId("sfarm_cloud_env_1");// low priority
			tkt.setPriorityId(null);
			tkt.setStatus(null);
			tkt.setTicketQualityRate("5");
			tkt.setTicketQualityComments(null);
			tkt.setUpdatedBy(turnContext.getActivity().getFrom().getName());
			tkt.setCreatedBy(turnContext.getActivity().getFrom().getName());

			lastNumberObj.setAutoCodeNo(lastNumberObj.getAutoCodeNo() + 1);
			autoGenerationRepo.save(lastNumberObj);

			ticketRepo.save(tkt);
			
			
			
			//List<Ticket_296> tktlist = ticketRepo.findAll();
			//System.out.println(tktlist);

			Optional<Department_23> dep = departmentImpl.findById(tkt.getDepartmentId());
			
			String chatUrl=ecalateTicketQualityService.creatchatwithTeamMembers(tkt, turnContext,dep.get().getDeptName());

			AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
			List<Container> conlist = new ArrayList<>();
			List<ActionSet> actList = new ArrayList<>();

			Container con = new Container();
			con.setType("Container");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Ticket #" + String.valueOf(tktNumber) + " is raised for your support.");
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setWrap(true);

			ArrayList<Item> item = new ArrayList<>();
			item.add(it1);
			con.setItems(item);

			conlist.add(con);

			Container con2 = new Container();
			con2.setType("Container");

			Item it2 = new Item();
			it2.setType("TextBlock");
			it2.setText("Team members from " + dep.get().getDeptName() + "  department are added to this chat.");
			it2.setWeight("bolder");
			it2.setSize("medium");
			it2.setWrap(true);

			ArrayList<Item> itemList2 = new ArrayList<>();
			itemList2.add(it2);
			con2.setItems(itemList2);

			conlist.add(con2);

			/*
			 * Container con3 = new Container(); con3.setType("TextBlock");
			 * con3.setText("They will revert back to you soon."); con3.setWeight("bolder");
			 * con3.setSize("medium");
			 * 
			 * conlist.add(con3);
			 */

			adcard.setBody(conlist);
			
			ActionSet action = new ActionSet();
			action.setType("Action.OpenUrl");
			action.setTitle("Open New Chat");
			action.setUrl(chatUrl);

			/*
			 * ActionSet action = new ActionSet(); action.setType("Action.Execute");
			 * action.setTitle("CLOSE TICKET"); action.setVerb("personalDetailsFormSubmit");
			 * action.setId("ReplyYes");
			 * 
			 * ActionData ad = new ActionData(); ad.setKey1(true); ad.setKey2("okay");
			 * 
			 * Actionfallback af = new Actionfallback(); af.setType("Action.Submit");
			 * af.setTitle("CLOSE TICKET");
			 * 
			 * action.setData(ad); action.setFallback(af);
			 * 
			 * ActionSet action2 = new ActionSet(); action2.setType("Action.Execute");
			 * action2.setTitle("ESCALATE"); action2.setVerb("personalDetailsFormSubmit");
			 * action2.setId("ReplyNo");
			 * 
			 * ActionData ad2 = new ActionData(); ad2.setKey1(true); ad2.setKey2("okay");
			 * 
			 * Actionfallback af2 = new Actionfallback(); af2.setType("Action.Submit");
			 * af2.setTitle("ESCALATE");
			 * 
			 * action2.setData(ad2); action2.setFallback(af2);
			 * 
			 * actList.add(action); actList.add(action2);
			 */
			actList.add(action);
			adcard.setActions(actList);

			// ============= create ticket Json structure done =======================

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				json = ow.writeValueAsString(adcard);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return json;

	}
		
	
	
	
}

