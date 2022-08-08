package com.microsoft.teams.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperatorExtensionsKt;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonPrimitive;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.AadUserConversationMember;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.Chat;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageMention;
import com.microsoft.graph.models.ChatMessageMentionedIdentitySet;
import com.microsoft.graph.models.ChatSendActivityNotificationParameterSet;
import com.microsoft.graph.models.ChatType;
import com.microsoft.graph.models.ConversationMember;
import com.microsoft.graph.models.Identity;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.TeamsAppInstallation;
import com.microsoft.graph.requests.ChatMessageCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Column;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Department_23;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.TicketRepo;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.microsoft.teams.app.service.impl.SupportImpl;
import com.microsoft.teams.app.service.impl.TicketImpl;

import okhttp3.Request;


@Component
public class EscalateTicketQualityService {
	
	
	@Autowired
	TicketRepo ticketRepo;
	
	@Autowired
	TicketImpl ticketImpl;
	
	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	DepartmentImpl departmentImpl;
	
	@Autowired
	SupportImpl supportImpl;
	
	@Autowired
	TicketService ticketservice;
	
	/*
	 * @Autowired AuthenticationService authService;
	 */
	
	
	public String ticketStatusUpdate(String status, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {
		
		
		String json = null;
		
		Ticket_296 tkt = null;
		//tkt = ticket.get(turnContext.getActivity().getFrom().getId());
		//String msgId=turnContext.getActivity().getReplyToId();
		
		String ChatId = turnContext.getActivity().getConversation().getId();

		
		
		if(tkt==null) {
			 tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}
		
		if ("CLOSE TICKET".equalsIgnoreCase(status)) { // close the ticket
			
			
			
			tkt.setStatuscycleId("sfarm_cloud_env_10");
			tkt.setUpdateDateTime(new Date());
			
			tkt.setTimediff(commonUtility.CalculateDateDifference(tkt.getCreateDateTime(), tkt.getUpdateDateTime()));
			
			ticketRepo.save(tkt);
			
			AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
			List<Container> conlist = new ArrayList<>();
			List<ActionSet> actList = new ArrayList<>();

		

			Container con = new Container();
			con.setType("Container");
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
			it2.setText("Please rate the quality service rendered");
			it2.setWeight("bolder");
			it2.setSize("medium");
			ArrayList<Item> item2 = new ArrayList<>();
			item2.add(it2);
			con2.setItems(item2);
			conlist.add(con2);
			
			
	///   work in progresss =============================================
			
			Container con3 = new Container();
			con3.setType("Input.ChoiceSet");
			con3.setId("QualityRate");
			con3.setIsMultiSelect(false);
			con3.setStyle("expanded");
			con3.setValue("0");
			
			ArrayList<Choices> choiceList = new ArrayList<>();
			
			Choices choice001 = new Choices();
			choice001.setTitle("1");
			choice001.setValue("1");
			choiceList.add(choice001);
			
			Choices choice002 = new Choices();
			choice002.setTitle("2");
			choice002.setValue("2");
			choiceList.add(choice002);
			
			Choices choice003 = new Choices();
			choice003.setTitle("3");
			choice003.setValue("3");
			choiceList.add(choice003);
			
			
			Choices choice004 = new Choices();
			choice004.setTitle("4");
			choice004.setValue("4");
			choiceList.add(choice004);
			
			Choices choice005 = new Choices();
			choice005.setTitle("5");
			choice005.setValue("5");
			choiceList.add(choice005);
			
			con3.setChoices(choiceList);
			
			
			/*
			 * Container con3 = new Container(); con3.setType("ColumnSet");
			 * 
			 * ArrayList<Column> columnsList = new ArrayList<>();
			 * 
			 * // 1 radio button
			 * 
			 * ArrayList<Item> itemList3 = new ArrayList<>();
			 * 
			 * Column col = new Column(); col.setType("Column");
			 * 
			 * Item item3=new Item(); item3.setType("Input.ChoiceSet");
			 * item3.setId("firstColumn"); item3.setStyle("expanded");
			 * item3.setIsMultiSelect(false); item3.setValue("0");
			 * 
			 * List<Choices> choices=new ArrayList<>(); Choices choice=new Choices();
			 * choice.setTitle("1"); choice.setValue("1"); choices.add(choice);
			 * 
			 * item3.setChoices(choices); itemList3.add(item3);
			 * 
			 * col.setItems(itemList3);
			 * 
			 * columnsList.add(col); con3.setColumns(columnsList);
			 * 
			 * 
			 * 
			 * // 2 radio button
			 * 
			 * ArrayList<Item> itemList4 = new ArrayList<>();
			 * 
			 * Column col4 = new Column(); col4.setType("Column");
			 * 
			 * Item item4=new Item(); item4.setType("Input.ChoiceSet");
			 * item4.setId("secondColumn"); item4.setStyle("expanded");
			 * item4.setIsMultiSelect(false); item4.setValue("0");
			 * 
			 * List<Choices> choices4=new ArrayList<>(); Choices choice4=new Choices();
			 * choice4.setTitle("2"); choice4.setValue("2"); choices4.add(choice4);
			 * 
			 * item4.setChoices(choices4); itemList4.add(item4);
			 * 
			 * col4.setItems(itemList4);
			 * 
			 * columnsList.add(col4);
			 * 
			 * con3.setColumns(columnsList);
			 */
			
			///   work in progresss =============================================
			
			conlist.add(con3);
			
			
			Container con5 = new Container();
			con5.setType("Container");

			Item it5 = new Item();
			it5.setType("TextBlock");
			it5.setText("Default rating of 5 will be posted if feedback is not provided with in 1 hour");
			it5.setWeight("bolder");
			it5.setSize("medium");
			it5.setWrap(true);

			ArrayList<Item> item5 = new ArrayList<>();
			item5.add(it5);
			con5.setItems(item5);
			
			conlist.add(con5);
			
			
			Container con6 = new Container();
			con6.setType("TextBlock");
			con6.setText("Pls provide remarks");
			con6.setWeight("bolder");
			con6.setSize("medium");
	
			conlist.add(con6);
			
			Container con7 = new Container();
			con7.setType("Input.Text");
			con7.setId("Remarks");
			con7.setPlaceholder("enter text here");
			con7.setMaxLength("500");
			con7.setIsMultiline(true);
			
			conlist.add(con7);

			adcard.setBody(conlist);

			ActionSet action = new ActionSet();
			action.setType("Action.Submit");
			action.setTitle("Done");

			actList.add(action);

			adcard.setActions(actList);
			
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				json = ow.writeValueAsString(adcard);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return json;
			
			

		} else if ("ESCALATE".equalsIgnoreCase(status)) {
			
			final GraphServiceClient<Request> graphClient=	AuthenticationService.getInstance();
			
			ChatMessage chatMessage = new ChatMessage();
			ItemBody body = new ItemBody();
			body.contentType = BodyType.HTML;
			body.content = "<at id=\"0\">Srikanth Devarasetty</at> Hello sir, Issue has been escalated !!";
			chatMessage.body = body;
			LinkedList<ChatMessageMention> mentionsList = new LinkedList<ChatMessageMention>();
			ChatMessageMention mentions = new ChatMessageMention();
			mentions.id = 0;
			mentions.mentionText = "Srikanth Devarasetty";
			ChatMessageMentionedIdentitySet mentioned = new ChatMessageMentionedIdentitySet();
			Identity user = new Identity();
			user.displayName = "Srikanth Devarasetty";
			user.id = "be35539f-1ca1-442b-b609-e3c6a4af7104";
		//	user.userIdentityType = TeamworkUserIdentityType.AAD_USER;
			mentioned.user = user;
			mentions.mentioned = mentioned;
			mentionsList.add(mentions);
			chatMessage.mentions = mentionsList;

			graphClient.chats(ChatId).messages().buildRequest().post(chatMessage);

			// create chat

			// json=creatchatwithTeamMembers(tkt, turnContext);

		}
		return json;
	}
	
	
	
	public void updateCloseTicketMessageId(String messageId, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {
		try {
			Ticket_296 tkt = null;
			tkt = ticket.get(turnContext.getActivity().getFrom().getId());
			tkt.setClstktreplyId(messageId);
			ticketRepo.save(tkt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String creatchatwithTeamMembers(Ticket_296 tkt,TurnContext turnContext,String deptName,GraphServiceClient<Request> graphClient) {

		// 
		//String json = null;
		String chaturl=null;
		// MyNewApp

		/*
		 * final ClientSecretCredential usernamePasswordCredential = new
		 * ClientSecretCredentialBuilder()
		 * .clientId("71a88e6e-1337-450e-a65a-e36d27b19781") .clientSecret()
		 * .tenantId(tenant) .build();
		 * 
		 * String a[] = new String[] { "A", "B", "C", "D" };
		 * 
		 * // Getting the list view of Array List<String> scopes = Arrays.asList(a);
		 * 
		 * final TokenCredentialAuthProvider tokenCredentialAuthProvider = new
		 * TokenCredentialAuthProvider(scopes, clientSecretCredential);
		 */

		


		/*
		 * final UsernamePasswordCredential usernamePasswordCredential = new
		 * UsernamePasswordCredentialBuilder()
		 * .clientId("bccaebb1-43bd-49fa-aa3e-6e8b48037100").username(
		 * "admin@kgmerp.onmicrosoft.com") .password("Kgm@123$").build();
		 */
     	
     
		/*
		 * final TokenCredentialAuthProvider tokenCredentialAuthProvider = new
		 * TokenCredentialAuthProvider( usernamePasswordCredential);
		 */
		
		
		System.out.println("creating new chat group");

		/*
		 * final GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
		 * .authenticationProvider(tokenCredentialAuthProvider).buildClient();
		 * System.out.println(graphClient.getServiceRoot());
		 */

		Chat chat = new Chat();
		chat.chatType = ChatType.GROUP;
		chat.topic = "Ticket #".concat(tkt.getTicketNumber()+" "+tkt.getTicketTitle());
			
		
		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
		
		// 42160820-8445-4775-830b-c6fe29603480
		
		AadUserConversationMember members = new AadUserConversationMember();
		LinkedList<String> rolesList = new LinkedList<String>();
		rolesList.add("owner");
		members.roles = rolesList;
		members.additionalDataManager().put("user@odata.bind",
				new JsonPrimitive("https://graph.microsoft.com/v1.0/users('42160820-8445-4775-830b-c6fe29603480')"));
		members.additionalDataManager().put("@odata.type",
				new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		membersList.add(members);
		
		
		
		
		AadUserConversationMember members1 = new AadUserConversationMember();
		LinkedList<String> rolesList1 = new LinkedList<String>();
		rolesList1.add("owner");
		members1.roles = rolesList1;
		members1.additionalDataManager().put("user@odata.bind",
				new JsonPrimitive("https://graph.microsoft.com/v1.0/users('2d35cd72-8402-46e3-9782-a0e3cc79f675')"));
		members1.additionalDataManager().put("@odata.type",
				new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		membersList.add(members1);
		
		
		

		AadUserConversationMember members2 = new AadUserConversationMember();
		LinkedList<String> rolesList2 = new LinkedList<String>();
		rolesList2.add("owner");
		members2.roles = rolesList2;
		members2.additionalDataManager().put("user@odata.bind",
				new JsonPrimitive("https://graph.microsoft.com/v1.0/users('be35539f-1ca1-442b-b609-e3c6a4af7104')"));
		members2.additionalDataManager().put("@odata.type",
				new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		membersList.add(members2);
		
		
		
		
		
		/*
		 * AadUserConversationMember members3 = new AadUserConversationMember();
		 * LinkedList<String> rolesList3 = new LinkedList<String>();
		 * rolesList3.add("owner"); members3.roles = rolesList3;
		 * members3.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('74acad78-c073-4d6c-b2ba-f9ab67ee0500')"
		 * )); members3.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * membersList.add(members3);
		 */
		
		
		

		ConversationMemberCollectionResponse conversationMemberCollectionResponse = new ConversationMemberCollectionResponse();
		conversationMemberCollectionResponse.value = membersList;
		ConversationMemberCollectionPage conversationMemberCollectionPage = new ConversationMemberCollectionPage(
				conversationMemberCollectionResponse, null);
		chat.members = conversationMemberCollectionPage;

		try {

			// graphClient.applications().buildRequest().post(application);
			Chat cli = graphClient.chats().buildRequest().post(chat);
			
			/*
			 * Chat chat1 = new Chat(); chat1.topic = "Group chat title update";
			 * 
			 * graphClient.chats("19:1c5b01696d2e4a179c292bc9cf04e63b@thread.v2").
			 * buildRequest().
			 */
			
			chaturl =cli.webUrl;
			tkt.setChatGroupId(cli.id);
			
			ticketRepo.save(tkt);
			
			System.out.println(cli);
			
			AddChatBotToTeamsApp(cli.id,graphClient);
			
			/*
			 * //==== working code for send message to group chat======
			 * 
			 * ChatMessage chatMessage = new ChatMessage(); ItemBody body = new ItemBody();
			 * body.content =
			 * "<br/><strong>Hello All, New chat group created with ticket #".concat(tkt.
			 * getTicketNumber())
			 * +" !! for discussion </strong><br/><strong>Issue Details : "+tkt.
			 * getDescription()+"</strong><br/><strong>All "
			 * +deptName+" people were added to this Chat Group</strong>";
			 * body.contentType=BodyType.HTML; chatMessage.body = body;
			 * graphClient.chats(cli.id).messages().buildRequest().post(chatMessage);
			 */
			
		
			System.out.println(cli.id);
			System.out.println(cli.tenantId);
			System.out.println(cli.topic);
			System.out.println(cli.webUrl);
			System.out.println(cli.oDataType);

		} catch (Exception e) {
			// logger.debug("" + e.getMessage());
		}
		
		return chaturl;

	}
	
	public void AddChatBotToTeamsApp(String chatId, GraphServiceClient<Request> graphClient) {
		TeamsAppInstallation teamsAppInstallation = new TeamsAppInstallation();
		teamsAppInstallation.additionalDataManager().put("teamsApp@odata.bind", new JsonPrimitive(
				"https://graph.microsoft.com/v1.0/appCatalogs/teamsApps/fb162ead-38f7-4473-85db-5389af86b5f8"));

		graphClient.chats(chatId).installedApps().buildRequest().post(teamsAppInstallation);
	}
	
	
	public String ticketQualityRateUpdate(LinkedHashMap botResponseMap, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {

		Ticket_296 tkt = null;
		
	    String ChatId = turnContext.getActivity().getConversation().getId();
	    if(tkt==null) {
			 tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}

		if (((botResponseMap).get("Remarks")) != null) {
			tkt.setTicketQualityComments((String) ((botResponseMap).get("Remarks")));
		}

		if (((botResponseMap).get("QualityRate")) != null) {
			tkt.setTicketQualityRate((String) ((botResponseMap).get("QualityRate")));

		} else if (((botResponseMap).get("secondColumn")) != null) {
			tkt.setTicketQualityRate("2");

		} else if (((botResponseMap).get("thirdColumn")) != null) {
			tkt.setTicketQualityRate("3");

		} else if (((botResponseMap).get("fourthColumn")) != null) {
			tkt.setTicketQualityRate("4");

		} else if (((botResponseMap).get("fifthColumn")) != null) {
			tkt.setTicketQualityRate("5");

		}

		ticketRepo.save(tkt);

		return createThanksAdaptiveCard();

	}
	
	
	
	public String AdaptiveCardForPreviousSelection(String SelectedId, String requestType,
			LinkedHashMap botResponseMap) {

		// ============= Thanks Json structure done =======================

		String json = null;
		AdaptiveCardsRequest adcard = null;
		if (requestType == "Department") {

			Optional<Department_23> dep = departmentImpl.findById(SelectedId);
			adcard = new AdaptiveCardsRequest();

			Container con = new Container();
			con.setType("Container");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Selected Department Name : " + dep.get().getDeptName());
			it1.setWeight("bolder");
			it1.setSize("medium");

			ArrayList<Item> item = new ArrayList<>();
			ArrayList<Container> conlist = new ArrayList<>();

			item.add(it1);
			con.setItems(item);

			conlist.add(con);
			adcard.setBody(conlist);

		} else if (requestType == "Functional") {

			Optional<Support_298> sup = supportImpl.findById(SelectedId);
			adcard = new AdaptiveCardsRequest();

			Container con = new Container();
			con.setType("Container");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Selected Support Type : " + sup.get().getSupportType());
			it1.setWeight("bolder");
			it1.setSize("medium");

			ArrayList<Item> item = new ArrayList<>();
			ArrayList<Container> conlist = new ArrayList<>();

			item.add(it1);
			con.setItems(item);

			conlist.add(con);
			adcard.setBody(conlist);
		} else if (requestType == "Ticket") {

			if ((botResponseMap).get("IssueTitle") != null && (botResponseMap).get("IssueDescription") != null) {
				return ticketservice.TicketAdaptiveCard((String) (botResponseMap).get("IssueTitle"),
						(String) (botResponseMap).get("IssueDescription"));
			}

			// dep.get().getDeptName()

			// final GraphServiceClient<Request> graphClient =
			// AuthenticationService.getInstance();

			// ============= Thanks Json structure done =======================
		}

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(adcard);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String createThanksAdaptiveCard() {

		// ============= Thanks Json structure done =======================
		AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
		String json = null;
		Container con = new Container();
		con.setType("Container");

		Item it1 = new Item();
		it1.setType("TextBlock");
		it1.setText("Thank you for your valuable remarks !!!");
		it1.setWeight("bolder");
		it1.setSize("medium");

		ArrayList<Item> item = new ArrayList<>();
		ArrayList<Container> conlist = new ArrayList<>();

		item.add(it1);
		con.setItems(item);

		conlist.add(con);
		adcard.setBody(conlist);

		// ============= Thanks Json structure done =======================

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
