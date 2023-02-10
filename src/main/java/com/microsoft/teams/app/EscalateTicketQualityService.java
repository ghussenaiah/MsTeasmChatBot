package com.microsoft.teams.app;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonPrimitive;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.graph.models.AadUserConversationMember;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.Chat;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageMention;
import com.microsoft.graph.models.ChatMessageMentionedIdentitySet;
import com.microsoft.graph.models.Identity;
import com.microsoft.graph.models.ItemBody;
//import com.microsoft.graph.models.PinnedChatMessageInfo;
import com.microsoft.graph.models.ChatType;
import com.microsoft.graph.models.ConversationMember;
import com.microsoft.graph.models.TeamsAppInstallation;
import com.microsoft.graph.requests.ConversationMemberCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.serializer.OffsetDateTimeSerializer;
import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.Choices;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.EscalationMap_312;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.SupportDepartment_311;
import com.microsoft.teams.app.entity.Support_298;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.EscalationMapDao;
import com.microsoft.teams.app.repository.TicketRepo;
import com.microsoft.teams.app.repository.UserRepository;
import com.microsoft.teams.app.entity.User;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.microsoft.teams.app.service.impl.EscalationMapImpl;
import com.microsoft.teams.app.service.impl.SupportImpl;
import com.microsoft.teams.app.service.impl.TicketImpl;
import com.microsoft.teams.app.utility.Utility;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;

@Slf4j
@Component
public class EscalateTicketQualityService {

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	TicketImpl ticketImpl;

	@Autowired
	CommonUtility commonUtility;
	
	@Autowired
	AutoGenerationRepo autoGenerationRepo;

	@Autowired
	DepartmentImpl departmentImpl;

	@Autowired
	SupportImpl supportImpl;

	@Autowired
	TicketService ticketservice;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	EscalationMapImpl escImpl;
	
	@Autowired
	Utility utility;
	
	
   
	public volatile LinkedHashMap<String, LinkedList<ConversationMember>> ticketMembers  = new LinkedHashMap<String, LinkedList<ConversationMember>>();

	/*
	 * @Autowired AuthenticationService authService;
	 */

	public String ticketStatusUpdate(String status, Ticket_296 tkt,
			TurnContext turnContext) throws ParseException {
		
		String json = null;
		// tkt = ticket.get(turnContext.getActivity().getFrom().getId());
		// String msgId=turnContext.getActivity().getReplyToId();

		String ChatId = turnContext.getActivity().getConversation().getId();

		if (tkt == null) {
			tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}
		// "CLOSE TICKET".equalsIgnoreCase(status)
		 

		if ("closeticket".equalsIgnoreCase(status)) { // close the ticket

			tkt.setStatuscycleId("sfarm_cloud_env_10");
			tkt.setUpdateDateTime(new Date());

			tkt.setTimediff(commonUtility.CalculateDateDifference(tkt.getCreateDateTime(), tkt.getUpdateDateTime()));

			ticketRepo.save(tkt);

			AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
			List<Container> conlist = new ArrayList<>();
			List<ActionSet> actList = new ArrayList<>();

			Container con = new Container();
			con.setType("Container");
			MsTeams mst=new MsTeams();
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
			it2.setText("Please rate the quality service rendered");
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
			con3.setValue("5");  // for default selection

			ArrayList<Choices> choiceList = new ArrayList<>();
			
			Choices choice005 = new Choices();
			choice005.setTitle("5. OUTSTANDING-Issue resolved in less than expected time.");
			choice005.setValue("5");
			choiceList.add(choice005);
			

			Choices choice004 = new Choices();
			choice004.setTitle("4. EXCELLENT-Issue resolved within expected time.");
			choice004.setValue("4");
			choiceList.add(choice004);
			
			Choices choice003 = new Choices();
			choice003.setTitle("3. GOOD-Issue resolved in reasonable time.");
			choice003.setValue("3");
			choiceList.add(choice003);
			

			Choices choice002 = new Choices();
			choice002.setTitle("2. NEEDS IMPROVEMENT-Delay in Issue resolution.");
			choice002.setValue("2");
			choiceList.add(choice002);
			

			Choices choice001 = new Choices();
			choice001.setTitle("1. POOR-Significant delay in Issue resolution.");
			choice001.setValue("1");
			choiceList.add(choice001);


			con3.setChoices(choiceList);

			/// work in progresss =============================================

			conlist.add(con3);

			Container con5 = new Container();
			con5.setType("Container");

			Item it5 = new Item();
			it5.setType("TextBlock");
			it5.setText("Default rating of 5 will be posted if feedback is not provided");
			it5.setWeight("bolder");
			it5.setSize("medium");
			it5.setWrap(true);

			ArrayList<Item> item5 = new ArrayList<>();
			item5.add(it5);
			con5.setItems(item5);

			conlist.add(con5);

			Container con6 = new Container();
			con6.setType("TextBlock");
			con6.setText("Please provide remarks");
			con6.setWeight("bolder");
			con6.setSize("medium");

			conlist.add(con6);

			Container con7 = new Container();
			con7.setType("Input.Text");
			con7.setId("Remarks");
			con7.setIsRequired(true);
			con7.setErrorMessage("Pls enter remarks");
			con7.setPlaceholder("Enter text here");
			con7.setMaxLength("700");
			con7.setIsMultiline(true);

			conlist.add(con7);

			adcard.setBody(conlist);

			ActionSet action = new ActionSet();
			action.setType("Action.Submit");
			action.setTitle("Done");

			actList.add(action);

			adcard.setActions(actList);
			
			adcard.setMsTeams(mst);

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				json = ow.writeValueAsString(adcard);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			return json;

		} else if ("escalate".equalsIgnoreCase(status) && tkt.getEmployeeTeamsId().equalsIgnoreCase(turnContext.getActivity().getFrom().getAadObjectId())) {

			final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();
			
			tkt.setStatuscycleId("sfarm_cloud_env_11");
			
			ticketRepo.save(tkt);
			
			
			EscalationMap_312 escMap=escImpl.findAllBySupportId(tkt.getSupportId());
			
			User appuser=escMap.getUser();
			
			String url="https://graph.microsoft.com/v1.0/users/".concat(appuser.getTeamsId());
			
			AadUserConversationMember conversationMember = new AadUserConversationMember();
			conversationMember.additionalDataManager().put("user@odata.bind",
					new JsonPrimitive(url));
			conversationMember.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
			try {
				conversationMember.visibleHistoryStartDateTime = OffsetDateTimeSerializer
						.deserialize("0001-01-01T00:00:00Z");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LinkedList<String> rolesList = new LinkedList<String>();
			rolesList.add("owner");
			conversationMember.roles = rolesList;

			graphClient.chats(ChatId).members().buildRequest()
					.post(conversationMember);


			ChatMessage chatMessage = new ChatMessage();
			ItemBody body = new ItemBody();
			body.contentType = BodyType.HTML;
			body.content = "<at id=\"0\">"+appuser.getUserName()+"</at> Issue has been escalated !!";
			chatMessage.body = body;
			LinkedList<ChatMessageMention> mentionsList = new LinkedList<ChatMessageMention>();
			ChatMessageMention mentions = new ChatMessageMention();
			mentions.id = 0;
			mentions.mentionText = appuser.getUserName();
			ChatMessageMentionedIdentitySet mentioned = new ChatMessageMentionedIdentitySet();
			Identity user = new Identity();
			user.displayName = appuser.getUserName();
			user.id =appuser.getTeamsId();
			
			mentioned.user = user;
			mentions.mentioned = mentioned;
			mentionsList.add(mentions);
			chatMessage.mentions = mentionsList;
			graphClient.chats(ChatId).messages().buildRequest().post(chatMessage);
			
			
			
			/*
			 * ChatMessage chatMessage = new ChatMessage(); ItemBody body = new ItemBody();
			 * body.contentType = BodyType.HTML; body.content =
			 * "<at id=\"0\">Srikanth Devarasetty</at> Hello sir, Issue has been escalated !!"
			 * ; chatMessage.body = body; LinkedList<ChatMessageMention> mentionsList = new
			 * LinkedList<ChatMessageMention>(); ChatMessageMention mentions = new
			 * ChatMessageMention(); mentions.id = 0; mentions.mentionText =
			 * "Srikanth Devarasetty"; ChatMessageMentionedIdentitySet mentioned = new
			 * ChatMessageMentionedIdentitySet(); Identity user = new Identity();
			 * user.displayName = "Srikanth Devarasetty"; user.id =
			 * "be35539f-1ca1-442b-b609-e3c6a4af7104";
			 * 
			 * mentioned.user = user; mentions.mentioned = mentioned;
			 * mentionsList.add(mentions); chatMessage.mentions = mentionsList;
			 */

			
			
			

			
		

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

			e.printStackTrace();
		}
	}


	public void updateTiecktMembersList(String suppirtId,
			TurnContext turnContext) {
		
		final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();
		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
		String UserteamsId = turnContext.getActivity().getFrom().getAadObjectId();
		String chatId = turnContext.getActivity().getConversation().getId();
		
		LinkedList<ConversationMember> UserList=ticketMembers.get(chatId);
		if(UserList!=null && UserList.size()>0) {
			ticketMembers.remove(chatId);
		}
		
		
		Support_298 sup = supportImpl.findAll(suppirtId);
		
		Set<User> userList=sup.getUser();
		
		Set<String> addedMembers=new HashSet<String>();
		
		for (User user:userList) {

			if (user != null && user.getTeamsId() != null) {
			  
					AadUserConversationMember member = new AadUserConversationMember();
					LinkedList<String> rolesList = new LinkedList<String>();
					rolesList.add("owner");
					member.roles = rolesList; //
					
					member.additionalDataManager().put("user@odata.bind",
							new JsonPrimitive("https://graph.microsoft.com/v1.0/users('" + user.getTeamsId() + "')"));
					member.additionalDataManager().put("@odata.type",
							new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
					//graphClient.chats(chatId).members().buildRequest().post(member);
					membersList.add(member);
					addedMembers.add(user.getTeamsId());
					
				}
		}
		
		AadUserConversationMember members1 = new AadUserConversationMember();
		LinkedList<String> rolesList1 = new LinkedList<String>();
		rolesList1.add("owner");
		members1.roles = rolesList1;
		members1.additionalDataManager().put("user@odata.bind",
				new JsonPrimitive("https://graph.microsoft.com/v1.0/users('5f92b236-28ec-474f-bae4-f9cab9275230')"));
		members1.additionalDataManager().put("@odata.type",
				new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		
		membersList.add(members1);

	
		  if (!addedMembers.contains(UserteamsId) && !UserteamsId.equalsIgnoreCase("5f92b236-28ec-474f-bae4-f9cab9275230") ) {
				  
				com.microsoft.graph.models.User user = graphClient.users(UserteamsId).buildRequest().select("userType").get();
				AadUserConversationMember member = new AadUserConversationMember();
				LinkedList<String> rolesList = new LinkedList<String>();
				 if(user.userType.equalsIgnoreCase("Guest")) {
					 rolesList.add("guest");
				 }else {
					 rolesList.add("owner");
				 }
				 
				 member.roles = rolesList;
				 member.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('"+UserteamsId+"')"));
				 member.additionalDataManager().put("@odata.type",
						new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
				 membersList.add(member);
				//graphClient.chats(chatId).members().buildRequest().post(member);
			
		}
		 
		  ticketMembers.put(chatId, membersList);
		  
	}

	public String creatchatwithTeamMembers(Ticket_296 tkt, TurnContext turnContext,
			GraphServiceClient<Request> graphClient,AutoGenarationCode lastNumberObj,String UserteamsId,String UserteamsName,String issueTtle,String issueDescription) {

	
		//
		// String json = null;
		String chaturl = null;
		// MyNewApp

		//List<User> userList = userRepo.findAllByDepartmentId(tkt.getSupportDepartmentId());
		
		//Set<String> addedMembers=new HashSet<String>();
		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
		
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
		
		String chatId = turnContext.getActivity().getConversation().getId();

		Chat chat = new Chat();
		chat.chatType = ChatType.GROUP;
		chat.topic = "Ticket #".concat(tkt.getTicketNumber() + " " + tkt.getTicketTitle());
		
		// adding sfhelp user
		
		
		 
		 
				
		
		
		/*
		 * AadUserConversationMember members2 = new AadUserConversationMember();
		 * LinkedList<String> rolesList2 = new LinkedList<String>();
		 * rolesList2.add("guest"); members2.roles = rolesList2;
		 * members2.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('f6cce08a-5068-4a39-8976-7659bd02d6f7')"
		 * )); members2.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * 
		 * membersList.add(members2);
		 * addedMembers.add("f6cce08a-5068-4a39-8976-7659bd02d6f7");
		 */ // husen
		
		/*
		 * AadUserConversationMember members4 = new AadUserConversationMember();
		 * LinkedList<String> rolesList4 = new LinkedList<String>();
		 * rolesList4.add("guest"); members4.roles = rolesList4;
		 * members4.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('225e6ff0-0cfd-4b0e-9533-4c8082dff228')"
		 * )); members4.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * 
		 * membersList.add(members4);
		 * addedMembers.add("225e6ff0-0cfd-4b0e-9533-4c8082dff228");
		 */// ankith
		
		
		
		  
		  
		/*
		 * AadUserConversationMember members3 = new AadUserConversationMember();
		 * LinkedList<String> rolesList3 = new LinkedList<String>();
		 * rolesList3.add("owner"); members3.roles = rolesList3;
		 * members3.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('dc4b37af-3ca2-4049-989b-069589d57e72')"
		 * )); members3.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * 
		 * membersList.add(members3);
		 * addedMembers.add("dc4b37af-3ca2-4049-989b-069589d57e72");
		 */ //subramanyam
		  
		
		  
	
		 
		
		//turnContext.getActivity().getFrom().getAadObjectId()
		
		
		/*
		 * AadUserConversationMember members1 = new AadUserConversationMember();
		 * LinkedList<String> rolesList1 = new LinkedList<String>();
		 * rolesList1.add("owner"); members1.roles = rolesList1;
		 * members1.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('2d35cd72-8402-46e3-9782-a0e3cc79f675')"
		 * )); members1.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * membersList.add(members1);
		 */

		
		/*
		 * AadUserConversationMember members2 = new AadUserConversationMember();
		 * LinkedList<String> rolesList2 = new LinkedList<String>();
		 * rolesList2.add("owner"); members2.roles = rolesList2;
		 * members2.additionalDataManager().put("user@odata.bind", new JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/users('be35539f-1ca1-442b-b609-e3c6a4af7104')"
		 * )); members2.additionalDataManager().put("@odata.type", new
		 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		 * membersList.add(members2);
		 */
		 
		
		LinkedList<ConversationMember> UserList=ticketMembers.get(chatId);
		
		



		//membersList.add(members1);
	
		
		if (UserList != null && UserList.size() > 0) {
			membersList.addAll(UserList);
			//membersList.add(members1);
		}


		ConversationMemberCollectionResponse conversationMemberCollectionResponse = new ConversationMemberCollectionResponse();
		conversationMemberCollectionResponse.value = membersList;
		ConversationMemberCollectionPage conversationMemberCollectionPage = new ConversationMemberCollectionPage(
				conversationMemberCollectionResponse, null);
		chat.members = conversationMemberCollectionPage;

		try {

			// graphClient.applications().buildRequest().post(application);
			Chat cli = graphClient.chats().buildRequest().post(chat);
			
			//graphClient.chats("19:2da4c29f6d7041eca70b638b43d45437@thread.v2")
		
			
			

			/*
			 * Chat chat1 = new Chat(); chat1.topic = "Group chat title update";
			 * 
			 * graphClient.chats("19:1c5b01696d2e4a179c292bc9cf04e63b@thread.v2").
			 * buildRequest().
			 */

			chaturl = cli.webUrl;
			
			tkt.setChatGroupId(cli.id);
			tkt.setChatweburl(chaturl);
			
			if (chaturl != null) {
				
				ticketMembers.remove(chatId);

				Thread newThread = new Thread(() -> {
					
				

					createTicketAsyncCall(cli.id, tkt, lastNumberObj, UserteamsId, UserteamsName, issueTtle,
							issueDescription, graphClient);
					AddChatBotToTeamsApp(cli.id, graphClient);

					
				});
				newThread.start();
			}
		

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
			
			membersList.clear();
		
		

		} catch (Exception e) {
			// logger.debug("" + e.getMessage());
		}

		return chaturl;

	}
	
	public void createTicketAsyncCall(String chatId,Ticket_296 tkt,AutoGenarationCode lastNumberObj,String UserteamsId,String UserteamsName,String issueTtle,String issueDescription,GraphServiceClient<Request> graphClient)
	{
		
	
		
		Long trasactionNumber = utility.nextId();
		Date dt = new Date();
		
		if (tkt != null) {
			
			//tkt.setDescription((String) (botResponseMap).get("IssueDescription"));
			tkt.setIssuedetails(issueDescription);
			tkt.setStatuscycleId("27");
			tkt.setCreateDateTime(dt);
			tkt.setUpdateDateTime(dt);
		
			tkt.setId(String.valueOf(lastNumberObj.getAutoCodeNo()));
			tkt.setEmployeeTeamsId(UserteamsId);
			tkt.setTransactionentityId(trasactionNumber);
			tkt.setChatGroupId(chatId);
			tkt.setNextRoles(null);
			// tkt.setPriorityId("sfarm_cloud_env_1");// low priority
			tkt.setPriorityId(null);
			tkt.setStatus(null);
			tkt.setTicketQualityRate("5");
			tkt.setTicketQualityComments(null);
			tkt.setUpdatedBy(UserteamsName);
			tkt.setCreatedBy(UserteamsName);

			lastNumberObj.setAutoCodeNo(lastNumberObj.getAutoCodeNo() + 1);
			autoGenerationRepo.save(lastNumberObj);
		

			ticketRepo.save(tkt);
			
			/*
			 * Support_298 sup = supportImpl.findAll(tkt.getSupportId());
			 * 
			 * Set<User> userList=sup.getUser();
			 * 
			 * Set<String> addedMembers=new HashSet<String>();
			 * 
			 * for (User user:userList) {
			 * 
			 * if (user != null && user.getTeamsId() != null) {
			 * 
			 * AadUserConversationMember member = new AadUserConversationMember();
			 * LinkedList<String> rolesList = new LinkedList<String>();
			 * rolesList.add("owner"); member.roles = rolesList; //
			 * addedMembers.add(user.getTeamsId());
			 * member.additionalDataManager().put("user@odata.bind", new
			 * JsonPrimitive("https://graph.microsoft.com/v1.0/users('" + user.getTeamsId()
			 * + "')")); member.additionalDataManager().put("@odata.type", new
			 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
			 * graphClient.chats(chatId).members().buildRequest().post(member);
			 * 
			 * } }
			 * 
			 * if (!addedMembers.contains(UserteamsId)) {
			 * 
			 * com.microsoft.graph.models.User user =
			 * graphClient.users(UserteamsId).buildRequest().select("userType").get();
			 * AadUserConversationMember member = new AadUserConversationMember();
			 * LinkedList<String> rolesList = new LinkedList<String>();
			 * if(user.userType.equalsIgnoreCase("Guest")) { rolesList.add("guest"); }else {
			 * rolesList.add("owner"); }
			 * 
			 * member.roles = rolesList;
			 * member.additionalDataManager().put("user@odata.bind", new
			 * JsonPrimitive("https://graph.microsoft.com/v1.0/users('"+UserteamsId+"')"));
			 * member.additionalDataManager().put("@odata.type", new
			 * JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
			 * graphClient.chats(chatId).members().buildRequest().post(member);
			 * 
			 * }
			 */
			  
		

			  
		}

	}


	public void AddChatBotToTeamsApp(String chatId, GraphServiceClient<Request> graphClient) {
		TeamsAppInstallation teamsAppInstallation = new TeamsAppInstallation();
		teamsAppInstallation.additionalDataManager().put("teamsApp@odata.bind", new JsonPrimitive(
				"https://graph.microsoft.com/v1.0/appCatalogs/teamsApps/7395a58b-c80d-4513-a72f-aed2e0bae73d"));
		
		
		// old bot id e4540487-96d6-46c2-96aa-ecd2be55c0fd

		/*
		 * teamsAppInstallation.additionalDataManager().put("teamsApp@odata.bind", new
		 * JsonPrimitive(
		 * "https://graph.microsoft.com/v1.0/appCatalogs/teamsApps/fb162ead-38f7-4473-85db-5389af86b5f8"
		 * ));
		 */
		graphClient.chats(chatId).installedApps().buildRequest().post(teamsAppInstallation);

	}

	public String ticketQualityRateUpdate(LinkedHashMap botResponseMap, ConcurrentHashMap<String, Ticket_296> ticket,
			TurnContext turnContext) {

		Ticket_296 tkt = null;

		String ChatId = turnContext.getActivity().getConversation().getId();
		if (tkt == null) {
			tkt = ticketRepo.findAllByChatGroupId(ChatId);
		}
		
		if (tkt.getEmployeeTeamsId().equalsIgnoreCase(turnContext.getActivity().getFrom().getAadObjectId())) {

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
			
			
			final Timer timer = new Timer();

			final TimerTask task = new TimerTask() {

			    @Override
			    public void run() {
			    	removeMemberFromChat(ChatId,turnContext);
			        timer.cancel(); // stop timer after execution
			    }
			};

			timer.schedule(task, 5000);
			
		
		}
		
		

		return createThanksAdaptiveCard();

	}
	
	
	public void removeMemberFromChat(String chatId, TurnContext turnContext) {

		String userteamsId = turnContext.getActivity().getFrom().getAadObjectId();

		final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();
		ConversationMemberCollectionPage members = graphClient.me().chats(chatId).members().buildRequest().get();
		List<ConversationMember> listUser = members.getCurrentPage();

		for (ConversationMember itrUser : listUser) {
			AadUserConversationMember deleteUser = (com.microsoft.graph.models.AadUserConversationMember) itrUser;
			// System.out.println(deleteUser.userId);
			if (!deleteUser.userId.equalsIgnoreCase("5f92b236-28ec-474f-bae4-f9cab9275230")
					&& !deleteUser.userId.equalsIgnoreCase(userteamsId)) {
				log.info("deleting the user from chatid and userid {}{}", chatId, deleteUser.id);
				graphClient.chats(chatId).members(deleteUser.id).buildRequest().delete();

			}
		}

	}

	public String AdaptiveCardForPreviousSelection(String SelectedId, String requestType,
			LinkedHashMap botResponseMap,TurnContext turnContext) throws IOException {

		// ============= Thanks Json structure done =======================

		String json = null;
		AdaptiveCardsRequest adcard = null;
		if (requestType == "Department") {

			Optional<SupportDepartment_311> dep = departmentImpl.findById(SelectedId);
			adcard = new AdaptiveCardsRequest();

			Container con = new Container();
			con.setType("Container");
			

			MsTeams mst=new MsTeams();
			mst.setWidth("full");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Selected Department Name : " + dep.get().getDeptName());
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setColor("accent");

			ArrayList<Item> item = new ArrayList<>();
			ArrayList<Container> conlist = new ArrayList<>();

			item.add(it1);
			con.setItems(item);

			conlist.add(con);
			adcard.setMsTeams(mst);
			adcard.setBody(conlist);

		} else if (requestType == "Functional") {

			Optional<Support_298> sup = supportImpl.findById(SelectedId);
			adcard = new AdaptiveCardsRequest();

			Container con = new Container();
			con.setType("Container");
			
			MsTeams mst=new MsTeams();
			mst.setWidth("full");

			Item it1 = new Item();
			it1.setType("TextBlock");
			it1.setText("Selected Support Type : " + sup.get().getSupportType());
			it1.setWeight("bolder");
			it1.setSize("medium");
			it1.setColor("accent");

			ArrayList<Item> item = new ArrayList<>();
			ArrayList<Container> conlist = new ArrayList<>();

			item.add(it1);
			con.setItems(item);

			conlist.add(con);
			adcard.setBody(conlist);
			adcard.setMsTeams(mst);
		} else if (requestType == "Ticket") {

			if ((botResponseMap).get("IssueTitle") != null && (botResponseMap).get("IssueDescription") != null) {
				return ticketservice.TicketAdaptiveCard((String) (botResponseMap).get("IssueTitle"),
						(String) (botResponseMap).get("IssueDescription"));
			}
		}
		else if (requestType == "StatusUpdate") {

			adcard = new AdaptiveCardsRequest();
			return ticketservice.feedbackUpdatedForm(botResponseMap, turnContext);
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
		con.setStyle("good");
		MsTeams mst=new MsTeams();
		mst.setWidth("full");
		con.setBleed(true);

		Item it1 = new Item();
		it1.setType("TextBlock");
		it1.setText("Your remarks saved");
		it1.setWeight("bolder");
		it1.setSize("medium");
		it1.setColor("good");

		ArrayList<Item> item = new ArrayList<>();
		ArrayList<Container> conlist = new ArrayList<>();

		item.add(it1);
		con.setItems(item);

		conlist.add(con);
		adcard.setBody(conlist);
		adcard.setMsTeams(mst);

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
