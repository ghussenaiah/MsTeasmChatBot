package com.microsoft.teams.app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.ResourceResponse;
import com.microsoft.bot.schema.Serialization;
import com.microsoft.graph.models.ChatMessage;

import com.microsoft.graph.requests.ChatMessageCollectionPage;

import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.teams.app.entity.Action;
import com.microsoft.teams.app.entity.ActionData;
import com.microsoft.teams.app.entity.ActionSet;

import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.Cell;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Column;
import com.microsoft.teams.app.entity.Container;

import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.Rows;
import com.microsoft.teams.app.entity.SupportDepartment_311;

import com.microsoft.teams.app.entity.Ticket_296;

import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.SupportRepo;
import com.microsoft.teams.app.repository.TicketRepo;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.microsoft.teams.app.service.impl.SupportImpl;
import com.microsoft.teams.app.service.impl.TicketImpl;
import com.microsoft.teams.app.utility.Utility;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@Slf4j
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
	EscalateTicketQualityService ticketQualityService;

	@Autowired
	AutoGenerationRepo autoGenerationRepo;

	@Autowired
	DepartmentImpl departmentImpl;

	@Autowired
	EscalateTicketQualityService ecalateTicketQualityService;

	@Autowired
	TicketService ticketService;
	
	@Autowired
	SupportRepo supportRepo;
	
	

	/*
	 * @Autowired AuthenticationService authService;
	 */

	public String createTicketAdaptiveCard(String supportTypeId, LinkedHashMap botResponseMap,
			ConcurrentHashMap<String, Ticket_296> ticket, TurnContext turnContext) {

		// ============= create ticket Json structure done =======================

		String Ticket = "{\n" + "  \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
				+ "  \"type\": \"AdaptiveCard\",\n" + "  \"version\": \"1.0\",\n" + "  \"body\": [\n" + "  {\n"
				+ "      \"type\": \"TextBlock\",\n"
				+ "      \"text\": \"One line description of issue (Ticket Title)\",\n"
				+ "          \"weight\": \"bolder\",\n" + "          \"size\": \"medium\"\n" + "    },\n" + "    {\n"
				+ "      \"type\": \"Input.Text\",\n" + "      \"id\": \"IssueTitle\",\n"
				+ "      \"placeholder\": \"enter text here\",\n" + "      \"maxLength\": 500\n" + "    },\n"
				+ "    {\n" + "      \"type\": \"TextBlock\",\n"
				+ "      \"text\": \"Detailed description of issue\",\n" + "          \"weight\": \"bolder\",\n"
				+ "          \"size\": \"medium\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Input.Text\",\n"
				+ "      \"id\": \"IssueDescription\",\n" + "      \"placeholder\": \"enter text here\",\n"
				+ "      \"maxLength\": 500,\n" + "      \"isMultiline\": true\n" + "    }\n" + "  ],\n"
				+ "  \"actions\": [\n" + "    {\n" + "      \"type\": \"Action.Submit\",\n"
				+ "      \"title\": \"OK\"\n" + "    }\n" + "  ]\n" + "}\n" + "";

		Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());

		if (tkt != null) {
			tkt.setSupportId(supportTypeId);
		}
		
		Thread newThread = new Thread(() -> {
			ecalateTicketQualityService.updateTiecktMembersList(supportTypeId, turnContext);
		});
		newThread.start();
		
		

		return TicketAdaptiveCard("", "");

		/*
		 * AdaptiveCardsRequest adcard = new AdaptiveCardsRequest(); List<Container>
		 * conlist = new ArrayList<>(); List<ActionSet> actList = new ArrayList<>();
		 * 
		 * MsTeams mst=new MsTeams(); mst.setWidth("full");
		 * 
		 * adcard.setMsTeams(mst);
		 * 
		 * String json = null;
		 * 
		 * Container con = new Container(); con.setType("TextBlock");
		 * con.setText("One line description of issue (Ticket Title)");
		 * con.setWeight("bolder"); con.setSize("medium");
		 * 
		 * Container con2 = new Container(); con2.setType("Input.Text");
		 * con2.setId("IssueTitle"); con2.setPlaceholder("enter text here");
		 * //con2.setMaxLength("500");
		 * 
		 * 
		 * Container con3 = new Container(); con3.setType("TextBlock");
		 * con3.setText("Detailed description of issue"); con3.setWeight("bolder");
		 * con3.setSize("medium");
		 * 
		 * Container con4 = new Container(); con4.setType("Input.Text");
		 * con4.setId("IssueDescription"); con4.setPlaceholder("enter text here");
		 * //con4.setMaxLength("1000"); con4.setIsMultiline(true);
		 * 
		 * conlist.add(con); conlist.add(con2); conlist.add(con3); conlist.add(con4);
		 * 
		 * adcard.setBody(conlist);
		 * 
		 * ActionSet action = new ActionSet(); action.setType("Action.Submit");
		 * action.setTitle("OK");
		 * 
		 * actList.add(action);
		 * 
		 * adcard.setActions(actList);
		 * 
		 * 
		 * 
		 * // ============= create ticket Json structure done =======================
		 * 
		 * ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); try
		 * { json = ow.writeValueAsString(adcard); } catch (JsonProcessingException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * return json;
		 * 
		 */

	}

	public String TicketAdaptiveCard(String issueTitle, String issueDescription) {

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		List<ActionSet> actList = new ArrayList<>();

		MsTeams mst = new MsTeams();
		mst.setWidth("full");

		adcard.setMsTeams(mst);

		String json = null;

		Container con = new Container();
		con.setType("TextBlock");
		con.setText("Ticket Title: One line description of issue");
		con.setWeight("bolder");
		con.setSize("medium");
		con.setColor("accent");

		Container con2 = new Container();
		con2.setType("Input.Text");
		con2.setId("IssueTitle");
		con2.setIsRequired(true);
		con2.setErrorMessage("Please enter issue (max 200 chars)");

		if (issueTitle != null && !issueTitle.isEmpty()) {
			con2.setType("TextBlock");
			con2.setText(issueTitle);

		} else {
			con2.setPlaceholder("Enter text here");
			ActionSet action = new ActionSet();
			action.setType("Action.Submit");
			action.setTitle("OK");

			actList.add(action);

			adcard.setActions(actList);
		}

		// con2.setMaxLength("500");

		Container con3 = new Container();
		con3.setType("TextBlock");
		con3.setText("Detailed Description");
		con3.setWeight("bolder");
		con3.setSize("medium");
		con3.setColor("accent");

		Container con4 = new Container();
		con4.setType("Input.Text");
		con4.setId("IssueDescription");

		if (issueDescription != null && !issueDescription.isEmpty()) {

			// con4.setType("TextBlock");
			// con4.setText(issueDescription);
			// con4.setMaxLength("700");
			// con4.setIsMultiline(true);
			// con4.setMaxLines(8);

			con4.setType("Container");
			// con2.setStyle("good");
			con4.setBleed(true);
			Item it2 = new Item();
			it2.setType("TextBlock");
			it2.setText(issueDescription);
			it2.setWeight("bolder");
			it2.setSize("medium");
			it2.setWrap(true);
			// it2.setColor("accent");
			// it2.setColor("good");

			ArrayList<Item> itemList2 = new ArrayList<>();
			itemList2.add(it2);
			con4.setItems(itemList2);

		} else {
			con4.setPlaceholder("Enter text here");
		}
		// con4.setMaxLength("1000");
		con4.setIsMultiline(true);

		conlist.add(con);
		conlist.add(con2);
		conlist.add(con3);
		conlist.add(con4);

		adcard.setBody(conlist);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String feedbackUpdatedForm(LinkedHashMap botResponseMap, TurnContext turnContext) throws IOException {

		Ticket_296 tkt = null;
		String remarks = null;
		String qualityrate = null;
		String json = null;

		String ChatId = turnContext.getActivity().getConversation().getId();
		if (tkt == null) {
			tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}
		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		if (tkt.getEmployeeTeamsId().equalsIgnoreCase(turnContext.getActivity().getFrom().getAadObjectId())) {

			if (((botResponseMap).get("Remarks")) != null) {
				tkt.setTicketQualityComments((String) ((botResponseMap).get("Remarks")));
				remarks = (String) ((botResponseMap).get("Remarks"));
			}

			if (((botResponseMap).get("QualityRate")) != null) {
				qualityrate = (String) ((botResponseMap).get("QualityRate"));
			}

			Container con = new Container();
			con.setType("Container");
			MsTeams mst = new MsTeams();
			mst.setWidth("full");
			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Ticket #".concat(tkt.getTicketNumber().concat(" is CLOSED")));
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setWrap(true);
			it1.setColor("Attention");
			ArrayList<Item> item = new ArrayList<>();
			item.add(it1);
			con.setItems(item);
			conlist.add(con);

			Container con2 = new Container();
			con2.setType("Container");
			Item it2 = new Item();
			it2.setType("TextBlock");
			it2.setText("Service rated");
			it2.setWeight("bolder");
			it2.setSize("medium");
			ArrayList<Item> item2 = new ArrayList<>();
			item2.add(it2);
			con2.setItems(item2);
			conlist.add(con2);

			/// work in progresss =============================================

			Container con3 = new Container();
			con3.setType("Input.ChoiceSet");
			con3.setId("QualityRate");
			con3.setIsMultiSelect(false);
			con3.setStyle("expanded");
			con3.setValue(qualityrate); // for default selection

			ArrayList<Choices> choiceList = new ArrayList<>();

			if (qualityrate.equalsIgnoreCase("1")) {
				Choices choice001 = new Choices();
				choice001.setTitle("1. POOR-Significant delay in Issue resolution.");
				choice001.setValue("1");
				choiceList.add(choice001);

			} else if (qualityrate.equalsIgnoreCase("2")) {

				Choices choice002 = new Choices();
				choice002.setTitle("2. NEEDS IMPROVEMENT-Delay in Issue resolution.");
				choice002.setValue("2");
				choiceList.add(choice002);

			} else if (qualityrate.equalsIgnoreCase("3")) {

				Choices choice003 = new Choices();
				choice003.setTitle("3. GOOD-Issue resolved in reasonable time.");
				choice003.setValue("3");
				choiceList.add(choice003);

			} else if (qualityrate.equalsIgnoreCase("4")) {

				Choices choice004 = new Choices();
				choice004.setTitle("4. EXCELLENT-Issue resolved within expected time.");
				choice004.setValue("4");
				choiceList.add(choice004);

			} else if (qualityrate.equalsIgnoreCase("5")) {

				Choices choice005 = new Choices();
				choice005.setTitle("5. OUTSTANDING-Issue resolved in less than expected time.");
				choice005.setValue("5");
				choiceList.add(choice005);

			}

			con3.setChoices(choiceList);

			/// work in progresss =============================================

			conlist.add(con3);

			// Container con5 = new Container();
			// con5.setType("Container");

			// Item it5 = new Item();
			// it5.setType("TextBlock");
			// it5.setText("Default rating of 5 will be posted if feedback is not
			// provided");
			// it5.setWeight("bolder");
			// it5.setSize("medium");
			// it5.setWrap(true);

			// ArrayList<Item> item5 = new ArrayList<>();
			// item5.add(it5);

			// con5.setItems(item5);

			// conlist.add(con5);

			Container con6 = new Container();
			con6.setType("TextBlock");
			con6.setText("Remarks");
			con6.setWeight("bolder");
			con6.setSize("medium");

			conlist.add(con6);

			Container con7 = new Container();
			con7.setType("TextBlock");
			con7.setId("Remarks");
			con7.setText(remarks);
			con7.setMaxLength("700");
			con7.setIsMultiline(true);
			con7.setMaxLines(7);

			conlist.add(con7);

			adcard.setBody(conlist);
			adcard.setMsTeams(mst);

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				json = ow.writeValueAsString(adcard);

				Attachment newcardAttachment = new Attachment();
				newcardAttachment.setContent(Serialization.jsonToTree(json));
				newcardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
				Activity newactivity = MessageFactory.attachment(newcardAttachment);
				newactivity.setId(turnContext.getActivity().getReplyToId());
				CompletableFuture<ResourceResponse> resourceresponse = turnContext.updateActivity(newactivity);
				System.out.println(resourceresponse);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

		}
		/*
		 * else {
		 * 
		 * Container con = new Container(); con.setType("Container"); MsTeams mst = new
		 * MsTeams(); mst.setWidth("full"); Item it1 = new Item();
		 * it1.setType("TextBlock");
		 * it1.setText("Unauthorized user pls contact IT support");
		 * it1.setWeight("bolder"); it1.setSize("medium"); it1.setWrap(true);
		 * it1.setColor("Attention"); ArrayList<Item> item = new ArrayList<>();
		 * item.add(it1); con.setItems(item); conlist.add(con); adcard.setBody(conlist);
		 * adcard.setMsTeams(mst); }
		 */

		return json;

	}

	public String createTicket(LinkedHashMap botResponseMap, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {

		Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());

		if (tkt == null) {
			String ChatId = turnContext.getActivity().getConversation().getId();
			tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}
		
		String json = null;
		AutoGenarationCode lastNumberObj = autoGenerationRepo.getLastTicketNumber("chathistory_299");
		int tktNumber = lastNumberObj.getAutoCodeNo();

		String UserteamsId = turnContext.getActivity().getFrom().getAadObjectId();
		String UserteamsName = turnContext.getActivity().getFrom().getName();
		String issueTtle = (String) (botResponseMap).get("IssueTitle");
		String issueDescription = (String) (botResponseMap).get("IssueDescription");

		tkt.setTicketNumber(String.valueOf(lastNumberObj.getAutoCodeNo()));
		tkt.setTicketTitle(issueTtle);

		final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();
	
		
		
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// String check = dateFormat.format(dt);

		// status 27 for open and 28 for submitted

		// Optional<SupportDepartment_311> dep =
		// departmentImpl.findById(tkt.getSupportDepartmentId());

		String chatUrl = ecalateTicketQualityService.creatchatwithTeamMembers(tkt, turnContext, graphClient,
				lastNumberObj, UserteamsId, UserteamsName, issueTtle, issueDescription);

		log.info("chat url and tkt number =>{},{}", chatUrl, tktNumber);
		if (chatUrl != null && tktNumber > 0) {

			AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
			List<Container> conlist = new ArrayList<>();
			List<ActionSet> actList = new ArrayList<>();

			Container con = new Container();
			con.setType("Container");
			MsTeams mst = new MsTeams();
			mst.setWidth("full");

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
			// it2.setText("Team members from " + dep.get().getDeptName() + " department are
			// added to this chat.");
			it2.setText("Team members from selected support type were added to this chat.");
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
			adcard.setMsTeams(mst);

			ActionSet action = new ActionSet();
			action.setType("Action.OpenUrl");
			action.setTitle("Go To Ticket");
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

				e.printStackTrace();
			}
			return json;
		}
		return json;

	}

	public String ShowMyTickets(TurnContext turnContext) {

		String json = null;
		AdaptiveCardsRequest adcard =null;

		// Optional<Department_23> dep = departmentImpl.findById(tkt.getDepartmentId());
		// fetching all open tickets
		String UserteamsId = turnContext.getActivity().getFrom().getAadObjectId();

		List<Ticket_296> tkts = ticketRepo.findAll(UserteamsId);
		
		if (tkts != null && tkts.size() > 0) {
		
	    adcard = new AdaptiveCardsRequest();
		MsTeams mst=new MsTeams();
		mst.setWidth("full");
		adcard.setMsTeams(mst);
		//adcard.setVersion("1.5");
		List<Container> conlist = new ArrayList<>();

		Container con1 = new Container();

		con1.setType("Table");
		con1.setGridStyle("accent");
		con1.setFirstRowAsHeaders(true);

		List<Column> columns = new ArrayList<Column>();
		
		List<Rows> rows = new ArrayList<Rows>();
		
		// setting columns 
		Column c1 = new Column();
		c1.setWidth("3");
		Column c2 = new Column();
		c2.setWidth("3");
		Column c3 = new Column();
		c3.setWidth("5");

		columns.add(c1);
		columns.add(c2);
		columns.add(c3);

		con1.setColumns(columns);

		// setting rows
		Rows r1 = new Rows();

		r1.setType("TableRow");
		r1.setStyle("accent");

		ArrayList<Cell> cells = new ArrayList<>();

		Cell r1c1 = new Cell();

		r1c1.setType("TableCell");
		ArrayList<Item> items = new ArrayList<Item>();

		Item i1 = new Item();
		i1.setType("TextBlock");
		i1.setText("Tkt No");
		i1.setWrap(true);
		i1.setWeight("Bolder");
		//i1.setHorizontalAlignment("center");

		items.add(i1);
		r1c1.setItems(items);

		Cell r1c2 = new Cell();

		r1c2.setType("TableCell");
		ArrayList<Item> item2 = new ArrayList<Item>();

		Item i2 = new Item();
		i2.setType("TextBlock");
		i2.setText("Tkt Title");
		i2.setWrap(true);
		i2.setWeight("Bolder");
		//i2.setHorizontalAlignment("center");

		item2.add(i2);
		r1c2.setItems(item2);

		Cell r1c3 = new Cell();

		r1c3.setType("TableCell");
		ArrayList<Item> item3 = new ArrayList<Item>();

		Item i3 = new Item();
		i3.setType("TextBlock");
		i3.setText("Open Chat Act");
		i3.setWrap(true);
		i3.setWeight("Bolder");
		//i3.setHorizontalAlignment("center");

		item3.add(i3);
		r1c3.setItems(item3);

		cells.add(r1c1);
		cells.add(r1c2);
		cells.add(r1c3);

		r1.setCells(cells);

		rows.add(r1);
		
		Rows nextRow=null;
		ArrayList<Cell> nextcells=null;
		Cell nextc1 =null;
		Cell nextc2=null;
		Cell nextc3=null;
		
		
		
		
		
			for (Ticket_296 tkt : tkts) {

				nextRow = new Rows();

				nextRow.setType("TableRow");
				nextRow.setStyle("accent");

				nextcells = new ArrayList<>();

				nextc1 = new Cell();

				nextc1.setType("TableCell");
				ArrayList<Item> nextitems = new ArrayList<Item>();

				Item nexti1 = new Item();
				nexti1.setType("TextBlock");
				nexti1.setText("#".concat(tkt.getTicketNumber()));
				nexti1.setWrap(true);
				nexti1.setWeight("Bolder");
				//nexti1.setHorizontalAlignment("center");

				nextitems.add(nexti1);
				nextc1.setItems(nextitems);

				nextc2 = new Cell();

				nextc2.setType("TableCell");
				ArrayList<Item> nextitem2 = new ArrayList<Item>();

				Item nexti2 = new Item();
				nexti2.setType("TextBlock");
				nexti2.setText(tkt.getTicketTitle());
				nexti2.setWrap(true);
				nexti2.setWeight("Bolder");
				//nexti2.setHorizontalAlignment("center");

				nextitem2.add(nexti2);
				nextc2.setItems(nextitem2);
				
				
				nextc3 = new Cell();

				nextc3.setType("TableCell");
				nextc3.setStyle("accent");
				
				ArrayList<Item> nextitem3 = new ArrayList<Item>();
				
				ArrayList<Item> itemSet = new ArrayList<Item>();
				
		
				
				Item item=new Item();
				item.setType("Container");
			//	item.setHorizontalAlignment("center");
		
				
				// done =======================
				
				Item actionItem = new Item();
				actionItem.setType("ActionSet");
				//actionItem.setHorizontalAlignment("center");
				
				
				List<Action> actions=new ArrayList<>();
				
				
				Action act=new Action();
				act.setType("Action.OpenUrl");
				act.setTitle("Go To Ticket");
				act.setUrl(tkt.getChatweburl());
				//act.setWrap(true);
				//act.setData(dt);
				actions.add(act);
				
				actionItem.setActions(actions);
				
				itemSet.add(actionItem);

				
				item.setItems(itemSet);
				
				nextitem3.add(item);
				
				nextc3.setItems(nextitem3);
				
				nextcells.add(nextc1);
				nextcells.add(nextc2);
				nextcells.add(nextc3);

				nextRow.setCells(nextcells);

				rows.add(nextRow);
				
			}
			con1.setRows(rows);

			conlist.add(con1);

			adcard.setBody(conlist);
			
			
		} else {

			adcard = new AdaptiveCardsRequest();
			List<Container> conlistnoticket = new ArrayList<>();

			Container con = new Container();
			con.setType("Container");
			con.setGridStyle("accent");
			MsTeams mstwidth = new MsTeams();
			mstwidth.setWidth("full");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("No open tickets for you");
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setWrap(true);

			ArrayList<Item> item = new ArrayList<>();
			item.add(it1);
			con.setItems(item);

			conlistnoticket.add(con);

			adcard.setBody(conlistnoticket);
			adcard.setMsTeams(mstwidth);

		}

		// Rows r2=new Rows();
		// Rows r3=new Rows();
		// Rows r4=new Rows();
		// Rows r5=new Rows();
		// Rows r6=new Rows();

	

		

		// tkt.setWelmsg("Yes"); // when people added then should not send again new
		// chat group information
		// ticketRepo.save(tkt);

		// ============= create ticket Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return json;

	}
	
	
	
	
	public String HelpCommands(TurnContext turnContext) {

		String json = null;

		// Optional<Department_23> dep = departmentImpl.findById(tkt.getDepartmentId());
		// fetching all open tickets

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		MsTeams mst=new MsTeams();
		mst.setWidth("full");
		adcard.setMsTeams(mst);
		//adcard.setVersion("1.5");
		List<Container> conlist = new ArrayList<>();
		
		//title 
		
		Container headline=new Container();
		headline.setType("TextBlock");
		headline.setText("Pls use below supported chat commands ");
		headline.setWrap(true);
		headline.setStyle("heading");
		

		Container table = new Container();
		
		
		/*
		 * { "type": "TextBlock", "text": "Table with gridlines", "wrap": true, "style":
		 * "heading" },
		 */
		table.setType("Table");
		//table.setGridStyle("accent");
		//table.setFirstRowAsHeaders(true);

		List<Column> columns = new ArrayList<Column>();
		
		List<Rows> rows = new ArrayList<Rows>();
		
		// setting columns 
		Column c1 = new Column();
		c1.setWidth("3");
		Column c2 = new Column();
		c2.setWidth("7");
	

		columns.add(c1);
		columns.add(c2);
	

		table.setColumns(columns);

		// setting rows
		
		
	    Map<String,String> HelpList=new HashMap<String,String>();
	    
		HelpList.put("NEW TICKET", "Initiates steps for creation of a new ticket");
		HelpList.put("SHOW MY TICKETS", "Lists your tickets that are in OPEN status");
		HelpList.put("CLEAR", "Clears all messages in this window");
		HelpList.put("HELP", "Displays supported Chat Commands");
	   // HelpList.put("", "");
	
		for (Map.Entry<String, String> help : HelpList.entrySet()) {

			Rows r1 = new Rows();

			r1.setType("TableRow");
			r1.setStyle("accent");

			ArrayList<Cell> cells = new ArrayList<>();

			Cell r1c1 = new Cell();

			r1c1.setType("TableCell");
			r1c1.setStyle("accent");
			ArrayList<Item> items = new ArrayList<Item>();

			Item i1 = new Item();
			i1.setType("TextBlock");
			i1.setText(help.getKey());
			i1.setWrap(true);
			i1.setWeight("Bolder");
			//i1.setHorizontalAlignment("left");

			items.add(i1);
			r1c1.setItems(items);

			Cell r1c2 = new Cell();

			r1c2.setType("TableCell");
			r1c2.setStyle("accent");
			ArrayList<Item> item2 = new ArrayList<Item>();

			Item i2 = new Item();
			i2.setType("TextBlock");
			i2.setText(help.getValue());
			i2.setWrap(true);
			i2.setWeight("Bolder");
			//i2.setHorizontalAlignment("center");

			item2.add(i2);
			r1c2.setItems(item2);

			cells.add(r1c1);
			cells.add(r1c2);

			r1.setCells(cells);

			rows.add(r1);

		}
		
		table.setRows(rows);
		
		conlist.add(headline);

		conlist.add(table);

		adcard.setBody(conlist);

		// tkt.setWelmsg("Yes"); // when people added then should not send again new
		// chat group information
		// ticketRepo.save(tkt);

		// ============= create ticket Json structure done =======================
		
		HelpList.clear();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return json;

	}
	
	
	
	public void ClearChat(TurnContext turnContext) {

		// GraphServiceClient graphClient =
		// GraphServiceClient.builder().authenticationProvider( "" ).buildClient();

		final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();

		String userid = turnContext.getActivity().getFrom().getAadObjectId();
		ChatMessageCollectionPage messages = graphClient.chats("chatId").messages().buildRequest().top(50).get();// 50
																													// is
																													// max
		List<ChatMessage> mm = messages.getCurrentPage();

		for (ChatMessage message : mm) {

			graphClient.chats("19:22273db3497f4b32bue61f6e82be21c5@thread.tacv2").messages(message.id).buildRequest()
					.delete();
		}
		// graphClient.users("8f98f01d-1a73-401a-b9e9-9fd1e6f5e5ar")

	}

	public String SendChatInitialMessage(TurnContext turnContext, Ticket_296 tkt) {

		String json = null;

		// Optional<Department_23> dep = departmentImpl.findById(tkt.getDepartmentId());

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
	//	List<ActionSet> actList = new ArrayList<>();

		Container con = new Container();
		con.setType("Container");
		MsTeams mst = new MsTeams();
		mst.setWidth("full");
		// con.setStyle("good");
		con.setBleed(true);
		Item it1 = new Item();
		it1.setType("TextBlock");
		// body.content = "<br/><strong>Hello All, New chat group created with ticket
		// #".concat(tkt.getTicketNumber())+" !! for discussion
		// </strong><br/><strong>Issue Details :
		// "+tkt.getDescription()+"</strong><br/><strong>All "+deptName+" people were
		// added to this Chat Group</strong>";
		//it1.setText("New chat group created with ticket #" + (tkt.getTicketNumber()) + " for discussion !!");
		it1.setText("Ticket #" + (tkt.getTicketNumber()) + " - "+tkt.getTicketTitle());
		it1.setWeight("bolder");
		it1.setSize("medium");
		it1.setWrap(true);
		it1.setColor("accent");

		ArrayList<Item> item = new ArrayList<>();
		item.add(it1);
		con.setItems(item);

		conlist.add(con);
		
		 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int minutesToAdd = 330;// server running with utc so we are converting to IST

		Date newDate = DateUtils.addMinutes(tkt.getCreateDateTime(), minutesToAdd);

		String strDate = formatter.format(newDate);
		  
		Container con3 = new Container();
		con3.setType("Container");
		// con2.setStyle("good");
		con3.setBleed(true);

		Item it3 = new Item();
		it3.setType("TextBlock");
		it3.setText("Created DateTime : " + strDate + " Created By "+tkt.getCreatedBy());
		it3.setWeight("bolder");
		it3.setSize("medium");
		it3.setWrap(true);
		it3.setColor("accent");

		ArrayList<Item> itemList3 = new ArrayList<>();
		itemList3.add(it3);
		con3.setItems(itemList3);

		
		conlist.add(con3);
		

		Container con2 = new Container();
		con2.setType("Container");
		// con2.setStyle("good");
		con2.setBleed(true);

		Item it2 = new Item();
		it2.setType("TextBlock");
		it2.setText("Description : " + tkt.getIssuedetails() + " ");
		it2.setWeight("bolder");
		it2.setSize("medium");
		it2.setWrap(true);
		it2.setColor("accent");

		ArrayList<Item> itemList2 = new ArrayList<>();
		itemList2.add(it2);
		con2.setItems(itemList2);

		conlist.add(con2);

		/*
		 * Container con3 = new Container(); con3.setType("Container");
		 * //con2.setStyle("good"); con3.setBleed(true);
		 * 
		 * Item it3 = new Item(); it3.setType("TextBlock");
		 * it3.setText(dep.get().getDeptName() +
		 * " Department people were added to this Chat Group"); it3.setWeight("bolder");
		 * it3.setSize("medium"); it3.setWrap(true); it3.setColor("accent");
		 * 
		 * ArrayList<Item> itemList3 = new ArrayList<>(); itemList3.add(it3);
		 * con3.setItems(itemList3);
		 * 
		 * conlist.add(con3);
		 */

		/*
		 * Container con3 = new Container(); con3.setType("TextBlock");
		 * con3.setText("They will revert back to you soon."); con3.setWeight("bolder");
		 * con3.setSize("medium");
		 * 
		 * conlist.add(con3);
		 */
		adcard.setMsTeams(mst);
		adcard.setBody(conlist);
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
		 * 
		 * adcard.setActions(actList);
		 * 
		 */

		/*
		 * ActionSet action = new ActionSet(); action.setType("Action.Submit");
		 * action.setTitle("CLOSE TICKET");
		 * 
		 * ActionData ad = new ActionData(); ad.setButton("closeticket");
		 * action.setData(ad);
		 * 
		 * ActionSet action2 = new ActionSet(); action2.setType("Action.Submit");
		 * action2.setTitle("ESCALATE");
		 * 
		 * ActionData ad2 = new ActionData(); ad2.setButton("escalate");
		 * action2.setData(ad2);
		 * 
		 * actList.add(action); actList.add(action2);
		 * 
		 * adcard.setActions(actList);
		 */

		//tkt.setWelmsg("Yes"); // when people added then should not send again new chat group information
		//ticketRepo.save(tkt);

		// ============= create ticket Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return json;

	}
	
	
	public String SendChatSecondMessage(TurnContext turnContext, Ticket_296 tkt) {

		String json = null;
		Optional<SupportDepartment_311> dep = departmentImpl.findById(tkt.getSupportDepartmentId());
	    String departmentName=null;
	  //  String supportName=null;
	    
	    if(dep.isPresent())
	    {
	    	  SupportDepartment_311 depData= dep.get();
	    	  departmentName=depData.getDeptName();
	    }
	    /*
		 * Optional<Support_298>
		 * sup=Optional.of(supportImpl.findAll(tkt.getSupportId())); if(sup.isPresent())
		 * { Support_298 supData= sup.get(); supportName= supData.getSupportType(); }
		 */

		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		List<Container> conlist = new ArrayList<>();
		List<ActionSet> actList = new ArrayList<>();

		Container con = new Container();
		con.setType("Container");
		MsTeams mst = new MsTeams();
		mst.setWidth("full");
		
		con.setBleed(true);
		Item it1 = new Item();
		it1.setType("TextBlock");
		// body.content = "<br/><strong>Hello All, New chat group created with ticket
		// #".concat(tkt.getTicketNumber())+" !! for discussion
		// </strong><br/><strong>Issue Details :
		// "+tkt.getDescription()+"</strong><br/><strong>All "+deptName+" people were
		// added to this Chat Group</strong>";
		//it1.setText("New chat group created with ticket #" + (tkt.getTicketNumber()) + " for discussion !!");
		it1.setText("Support team members of "+departmentName+" are added to this chat.");
		it1.setWeight("bolder");
		it1.setSize("medium");
		it1.setWrap(true);
		it1.setColor("accent");

		ArrayList<Item> item = new ArrayList<>();
		item.add(it1);
		con.setItems(item);

		conlist.add(con);
		
		adcard.setMsTeams(mst);
		adcard.setBody(conlist);
		
		ActionSet action = new ActionSet();
		action.setType("Action.Submit");
		action.setTitle("CLOSE TICKET");

		ActionData ad = new ActionData();
		ad.setButton("closeticket");
		action.setData(ad);

		ActionSet action2 = new ActionSet();
		action2.setType("Action.Submit");
		action2.setTitle("ESCALATE");

		ActionData ad2 = new ActionData();
		ad2.setButton("escalate");
		action2.setData(ad2);

		actList.add(action);
		actList.add(action2);

		adcard.setActions(actList);

		tkt.setWelmsg("Yes"); // when people added then should not send again new chat group information
		ticketRepo.save(tkt);

		// ============= create ticket Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return json;

	}

	synchronized void IssueStatus(TurnContext turnContext, ConcurrentHashMap<String, Ticket_296> ticket) {

		log.info("On click synchronized call reached ");
		Attachment cardAttachment = null;
		Attachment updatetriggerAttachment = null;

		String ChatId = turnContext.getActivity().getConversation().getId();

		Ticket_296 tkt = ticketRepo.findAllByChatGroupId(ChatId);

		if (turnContext.getActivity().getValue() != null) {

			LinkedHashMap botResponseMap = (LinkedHashMap) turnContext.getActivity().getValue();
			String triggerClicked = (String) botResponseMap.get("button");
			// String triggerClicked = (String) ((Map) actionObj).get("title");
			// "CLOSE TICKET".equalsIgnoreCase(triggerClicked)

			log.info("On click trigger type  ========> {}", triggerClicked);

			if ("closeticket".equalsIgnoreCase(triggerClicked)
					&& !tkt.getStatuscycleId().equalsIgnoreCase("sfarm_cloud_env_10")) {

				updatetriggerAttachment = new Attachment();
				try {
					updatetriggerAttachment.setContent(
							Serialization.jsonToTree(ticketService.ButtonHide(triggerClicked, turnContext, tkt,ChatId)));
				} catch (IOException e) {

					e.printStackTrace();
				}
				updatetriggerAttachment.setContentType("application/vnd.microsoft.card.adaptive");
				Activity newactivity = MessageFactory.attachment(updatetriggerAttachment);
				newactivity.setId(turnContext.getActivity().getReplyToId());
				CompletableFuture<ResourceResponse> resourceresponse = turnContext.updateActivity(newactivity);
				System.out.println(resourceresponse);
				// "ESCALATE".equalsIgnoreCase(triggerClicked)

			} else if ("escalate".equalsIgnoreCase(triggerClicked)
					&& !tkt.getStatuscycleId().equalsIgnoreCase("sfarm_cloud_env_11")
					&& !tkt.getStatuscycleId().equalsIgnoreCase("sfarm_cloud_env_10") && tkt.getEmployeeTeamsId()
							.equalsIgnoreCase(turnContext.getActivity().getFrom().getAadObjectId())) {

				updatetriggerAttachment = new Attachment();
				try {
					updatetriggerAttachment.setContent(
							Serialization.jsonToTree(ticketService.ButtonHide(triggerClicked, turnContext, tkt,ChatId)));
				} catch (IOException e) {

					e.printStackTrace();
				}
				updatetriggerAttachment.setContentType("application/vnd.microsoft.card.adaptive");
				Activity newactivity = MessageFactory.attachment(updatetriggerAttachment);
				newactivity.setId(turnContext.getActivity().getReplyToId());
				CompletableFuture<ResourceResponse> resourceresponse = turnContext.updateActivity(newactivity);
				System.out.println(resourceresponse);

			}

			// System.out.println("statement started");
			Thread newThread = new Thread(() -> {
				try {
					ticketService.StatusDbUpdate(triggerClicked, turnContext, tkt, cardAttachment);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			newThread.start();
			// System.out.println("statement reached");

		}

	}

	public void StatusDbUpdate(String triggerClicked, TurnContext turnContext, Ticket_296 tkt,
			Attachment cardAttachment) throws ParseException {

		String feedback = ticketQualityService.ticketStatusUpdate(triggerClicked, tkt, turnContext);
		if (feedback != null) {
			cardAttachment = new Attachment();
			try {

				cardAttachment.setContent(Serialization.jsonToTree(feedback));
				cardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
				Activity activity = MessageFactory.attachment(cardAttachment);
				// activity.setId(turnContext.getActivity().getReplyToId());
				// turnContext.updateActivity(activity);

				// logger.info(turnContext.getActivity().getChannelData().toString());

				turnContext.sendActivity(activity).thenApply(sendResult -> null);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	public String ButtonHide(String triggerClicked, TurnContext turnContext, Ticket_296 tkt,String chatId) {

		
		/*  old code */
		/*
		 * AdaptiveCardsRequest adcard = new AdaptiveCardsRequest(); List<Container>
		 * conlist = new ArrayList<>(); List<ActionSet> actList = new ArrayList<>();
		 * 
		 * // Optional<Department_23> dep =
		 * departmentImpl.findById(tkt.getDepartmentId()); String json = null; Container
		 * con = new Container(); MsTeams mst = new MsTeams(); mst.setWidth("full");
		 * con.setType("Container"); // con.setStyle("good"); con.setBleed(true); Item
		 * it1 = new Item(); it1.setType("TextBlock");
		 * it1.setText("New chat group created with ticket #" + (tkt.getTicketNumber())
		 * + " for discussion !!"); it1.setWeight("bolder"); it1.setSize("medium");
		 * it1.setWrap(true); it1.setColor("accent");
		 * 
		 * ArrayList<Item> item = new ArrayList<>(); item.add(it1); con.setItems(item);
		 * conlist.add(con);
		 * 
		 * Container con2 = new Container(); con2.setType("Container"); //
		 * con2.setStyle("good"); con2.setBleed(true); Item it2 = new Item();
		 * it2.setType("TextBlock"); it2.setText("Description : " +
		 * tkt.getIssuedetails() + " "); it2.setWeight("bolder"); it2.setSize("medium");
		 * it2.setWrap(true); it2.setColor("accent"); // it2.setColor("good");
		 * 
		 * ArrayList<Item> itemList2 = new ArrayList<>(); itemList2.add(it2);
		 * con2.setItems(itemList2);
		 * 
		 * conlist.add(con2);
		 * 
		 * adcard.setBody(conlist); adcard.setMsTeams(mst);
		 */
		
		/*  old code */
		
			String json = null;
			Optional<SupportDepartment_311> dep = departmentImpl.findById(tkt.getSupportDepartmentId());
		    String departmentName=null;
		  //  String supportName=null;
		    
		    if(dep.isPresent())
		    {
		    	  SupportDepartment_311 depData= dep.get();
		    	  departmentName=depData.getDeptName();
		    }
		    /*
			 * Optional<Support_298>
			 * sup=Optional.of(supportImpl.findAll(tkt.getSupportId())); if(sup.isPresent())
			 * { Support_298 supData= sup.get(); supportName= supData.getSupportType(); }
			 */

			AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
			List<Container> conlist = new ArrayList<>();
			List<ActionSet> actList = new ArrayList<>();

			Container con = new Container();
			con.setType("Container");
			MsTeams mst = new MsTeams();
			mst.setWidth("full");
			
			con.setBleed(true);
			Item it1 = new Item();
			it1.setType("TextBlock");
			// body.content = "<br/><strong>Hello All, New chat group created with ticket
			// #".concat(tkt.getTicketNumber())+" !! for discussion
			// </strong><br/><strong>Issue Details :
			// "+tkt.getDescription()+"</strong><br/><strong>All "+deptName+" people were
			// added to this Chat Group</strong>";
			//it1.setText("New chat group created with ticket #" + (tkt.getTicketNumber()) + " for discussion !!");
			it1.setText("Support team members of "+departmentName+" are added to this chat.");
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setWrap(true);
			it1.setColor("accent");

			ArrayList<Item> item = new ArrayList<>();
			item.add(it1);
			con.setItems(item);

			conlist.add(con);
			
			adcard.setMsTeams(mst);
			adcard.setBody(conlist);
			
		/*
		 * Container con3 = new Container(); con3.setType("Container");
		 * //con2.setStyle("good"); con3.setBleed(true);
		 * 
		 * Item it3 = new Item(); it3.setType("TextBlock");
		 * it3.setText(dep.get().getDeptName() +
		 * " Department people were added to this Chat Group"); it3.setWeight("bolder");
		 * it3.setSize("medium"); it3.setWrap(true); it3.setColor("accent");
		 * 
		 * ArrayList<Item> itemList3 = new ArrayList<>(); itemList3.add(it3);
		 * con3.setItems(itemList3);
		 * 
		 * conlist.add(con3);
		 */

		// if we completely close the ticket then no need to add any trigger
		// if issue is escalated then need to add close trigger only

		

		if ("escalate".equalsIgnoreCase(triggerClicked)) {
			ActionSet action = new ActionSet();
			action.setType("Action.Submit");
			action.setTitle("CLOSE TICKET");
			ActionData ad = new ActionData();
			ad.setButton("closeticket");
			action.setData(ad);
			actList.add(action);
			adcard.setActions(actList);
		}
		// remove all users except created user
	
	
		
		

		// ============= create ticket Json structure done =======================

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}
	


}
