package com.microsoft.teams.app;


import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microsoft.bot.builder.InvokeResponse;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.teams.TeamsActivityHandler;
import com.microsoft.bot.connector.Async;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.schema.ActionTypes;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.AdaptiveCardInvokeResponse;
import com.microsoft.bot.schema.AdaptiveCardInvokeValue;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.CardAction;
import com.microsoft.bot.schema.CardImage;
import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.HeroCard;
import com.microsoft.bot.schema.ResourceResponse;
import com.microsoft.bot.schema.Serialization;
import com.microsoft.bot.schema.ThumbnailCard;
import com.microsoft.bot.schema.teams.AppBasedLinkQuery;
import com.microsoft.bot.schema.teams.MessagingExtensionAttachment;
import com.microsoft.bot.schema.teams.MessagingExtensionResponse;
import com.microsoft.bot.schema.teams.MessagingExtensionResult;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.CustomRequestBuilder;
import com.microsoft.graph.httpcore.HttpClients;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.ResponseType;
import com.microsoft.graph.models.Site;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.ChatMessageCollectionPage;
import com.microsoft.graph.requests.DriveCollectionPage;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.DriveItemRequestBuilder;
import com.microsoft.graph.requests.DriveRecentCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.SiteCollectionPage;
import com.microsoft.graph.requests.UserRequestBuilder;
import com.microsoft.teams.app.entity.ActionData;
import com.microsoft.teams.app.entity.ActionSet;
import com.microsoft.teams.app.entity.Actionfallback;
import com.microsoft.teams.app.entity.AdaptiveCardsRequest;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.ChatHistory_299;
import com.microsoft.teams.app.entity.Container;
import com.microsoft.teams.app.entity.Department_23;
import com.microsoft.teams.app.entity.Item;
import com.microsoft.teams.app.entity.MsTeams;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.TicketRepo;
import com.microsoft.teams.app.service.impl.DepartmentImpl;
import com.microsoft.teams.app.service.impl.SupportImpl;

import okhttp3.Request;

import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

/*spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
 * 
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/newtest?useSSL=false
spring.datasource.username=root
spring.datasource.password=WANAparthy@544
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
*/

public class EchoBot extends TeamsActivityHandler {
	
	
	private Logger logger = LoggerFactory.getLogger(BotController.class);
	
	@Autowired
	DepartmentImpl departmentImpl;
	
	@Autowired
	SupportImpl supportImpl;
	
	@Autowired
	SupportService supportService;
	
	@Autowired
	DepartmentService depService;
	
	@Autowired 
	TicketService ticketService;
	
	@Autowired 
	CommonUtility commonUtility;
	
	@Autowired 
	EscalateTicketQualityService ticketQualityService;
	
	@Autowired
	TicketRepo ticketRepo;
	
	@Autowired
	AutoGenerationRepo autoGenerationRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	@Autowired
	FetchnSave_Retry_Mechanism fetchnSave_Retry_Mechanism;
	
	
	
	public ConcurrentHashMap<String, Ticket_296> ticket = new ConcurrentHashMap<>();
	/*
	 * @PostConstruct public void test() {
	 * 
	 * List<Department_23> departmentList = departmentImpl.findAll();
	 * System.out.println(departmentList);
	 * 
	 * }
	 */
	 
	 

	    // user method
	
		protected CompletableFuture<Void> checkonMessageActivity(TurnContext turnContext) {

			processTurnContext(turnContext);

			// return turnContext.sendActivity(activity).th
			return CompletableFuture.completedFuture(null);
			// return turnContext.sendActivity(activity).thenApply(sendResult -> null);

			// return turnContext.sendActivity(MessageFactory.text("Echo: " +
			// turnContext.getActivity().getText())).thenApply(sendResult -> null);

		}
	
		// user method 
		private void processTurnContext(TurnContext turnContext) {

			List<Activity> activityList = new ArrayList<>();
			logger.info("getChannelData()=> " + turnContext.getActivity().getChannelData().toString());
			logger.info("getCallerId()=> " + turnContext.getActivity().getCallerId());
			logger.info("getSummary()=> " + turnContext.getActivity().getSummary());
			logger.info("getConversationId()=> " + turnContext.getActivity().getConversation().getId());
			logger.info("getConversationType()=> " + turnContext.getActivity().getConversation().getConversationType());
			logger.info("getConversationName()=> " + turnContext.getActivity().getConversation().getName());
			// turnContext.getActivity().getConversation().setIsGroup(true);
			logger.info("getFrom()=> " + turnContext.getActivity().getFrom().toString());
			logger.info("getChannelId()=> " + turnContext.getActivity().getChannelId());
			logger.info("getId()=> " + turnContext.getActivity().getId());
			logger.info("getReplyToId()=> " + turnContext.getActivity().getReplyToId());
			logger.info("getTopicName()=> " + turnContext.getActivity().getTopicName());
			logger.info("getText()=> " + turnContext.getActivity().getText());
			logger.info("getLabel()=> " + turnContext.getActivity().getLabel());
			logger.info("getAction()=> " + turnContext.getActivity().getAction());
			logger.info("getDeliveryMode()=> " + turnContext.getActivity().getDeliveryMode());
			logger.info("getImportance()=> " + turnContext.getActivity().getImportance());
			logger.info("getName()=> " + turnContext.getActivity().getName());
			logger.info("getText()=> " + turnContext.getActivity().getText());
			logger.info("getServiceUrl()=> " + turnContext.getActivity().getServiceUrl());
			logger.info("getType()=> " + turnContext.getActivity().getType());
			logger.info("getValueType()=> " + turnContext.getActivity().getValueType());
			logger.info("teamsGetTeamId()=> " + turnContext.getActivity().teamsGetTeamId());
			logger.info("getConversation().getName())=> " + turnContext.getActivity().getConversation().getName());
			logger.info("getRecipientId=> " + turnContext.getActivity().getRecipient().getId());
			logger.info("getFromId=> " + turnContext.getActivity().getFrom().getId());

			logger.info("getActivityId=> " + turnContext.getActivity().getId());
			logger.info("getActivityName=> " + turnContext.getActivity().getName());

			logger.info("getActivity().getConversation().getAadObjectId()=> "
					+ turnContext.getActivity().getConversation().getAadObjectId());

			Attachment cardAttachment = null;

			if (turnContext.getActivity().getValue() != null) {
				LinkedHashMap botResponseMap = (LinkedHashMap) turnContext.getActivity().getValue();
				// String triggerClicked = (String) ((Map)
				// botResponseMap).get("ActionResponse");

				if (((botResponseMap).get("Department")) != null) {
					cardAttachment = new Attachment();
					try {
						// supportService
						cardAttachment.setContent(Serialization.jsonToTree(supportService.createSupportAdaptiveCard(
								((String) (botResponseMap).get("Department")), botResponseMap, ticket, turnContext)));
						// cardAttachment.setContent(Serialization.jsonToTree(supportTyp));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (((botResponseMap).get("SupportType")) != null) {
					cardAttachment = new Attachment();

					try {
						// cardAttachment.setContent(Serialization.jsonToTree(Ticket));
						cardAttachment.setContent(Serialization.jsonToTree(ticketService.createTicketAdaptiveCard(
								((String) (botResponseMap).get("SupportType")), botResponseMap, ticket, turnContext)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (((botResponseMap).get("IssueTitle")) != null) {
					cardAttachment = new Attachment();
					try {
						cardAttachment.setContent(Serialization
								.jsonToTree(ticketService.createTicket(botResponseMap, ticket, turnContext)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (((botResponseMap).get("Remarks")) != null) {
					cardAttachment = new Attachment();
					try {
						cardAttachment.setContent(Serialization.jsonToTree(
								ticketQualityService.ticketQualityRateUpdate(botResponseMap, ticket, turnContext)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				else {

					cardAttachment = new Attachment();
					commonUtility.removeContextData(ticket, turnContext);
					callShowImage(turnContext);
					try {

						// cardAttachment.setContent(Serialization.jsonToTree(Department));
						cardAttachment.setContent(Serialization.jsonToTree(depService.createDepartmentAdaptiveCard()));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else {

				/*
				 * List<Department_23> departmentList=departmentImpl.findAll();
				 * System.out.println(departmentList.get(0));
				 */

				cardAttachment = new Attachment();
				commonUtility.removeContextData(ticket, turnContext);
				callShowImage(turnContext);
				try {
					// cardAttachment.setContent(Serialization.jsonToTree(Department));
					cardAttachment.setContent(Serialization.jsonToTree(depService.createDepartmentAdaptiveCard()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// String adaptiveCardString="{\"$schema\":
			// \"http://adaptivecards.io/schemas/adaptive-card.json\",
			// \"type\":\"AdaptiveCard\",\"version\": \"1.0\",\"body\": [{\"type\":
			// \"TextBlock\",\"text\": \"PublishAdaptiveCard schema\"}],\"actions\": []}";

			cardAttachment.setContentType("application/vnd.microsoft.card.adaptive");

			// 16547 63426 661
			Activity activity = MessageFactory.attachment(cardAttachment);

			// activity.setReplyToId(activity.getId());
			/*
			 * Activity activity = Activity.clone(turnContext.getActivity());
			 * activity.setAttachment(cardAttachment);
			 * activity.setId(turnContext.getActivity().getReplyToId());
			 * 
			 * // activity.setReplyToId(turnContext.getActivity().getId());
			 * 
			 * logger.info("before retrun getReplyToId()=> " + activity.getReplyToId()); //
			 * activity.setId(turnContext.getActivity().getReplyToId()); //
			 * turnContext.updateActivity(activity);
			 * 
			 * logger.info(turnContext.getActivity().getChannelData().toString());
			 */

			// ResourceResponse rp = turnContext.sendActivity(activity).join();
			// activity.setId(rp.getId());

			CompletableFuture<ResourceResponse> resourceresponse = turnContext.sendActivity(activity);
			try {
				ResourceResponse rr = resourceresponse.get();
				//ticketQualityService.updateCloseTicketMessageId(rr.getId(), ticket, turnContext);

			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

        // user method
		private void callShowImage(TurnContext turnContext) {
			Attachment showImage = new Attachment();

			try {
				showImage.setContent(Serialization.jsonToTree(depService.ShowHelpDeskImage()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showImage.setContentType("application/vnd.microsoft.card.adaptive");
			Activity imgAct = MessageFactory.attachment(showImage);
			turnContext.sendActivity(imgAct);

		}


	protected CompletableFuture<Void> onMessageReactionActivity(TurnContext turnContext) {
	        CompletableFuture<Void> task = null;
	        System.out.println("helo");

	        if (turnContext.getActivity().getReactionsAdded() != null) {
	            task = onReactionsAdded(turnContext.getActivity().getReactionsAdded(), turnContext);
	        }

	        if (turnContext.getActivity().getReactionsRemoved() != null) {
	            if (task != null) {
	                task.thenApply(
	                    result -> onReactionsRemoved(
	                        turnContext.getActivity().getReactionsRemoved(), turnContext
	                    )
	                );
	            } else {
	                task = onReactionsRemoved(
	                    turnContext.getActivity().getReactionsRemoved(), turnContext
	                );
	            }
	        }

	        return task == null ? CompletableFuture.completedFuture(null) : task;
	    }
	

		@Override
		protected CompletableFuture<InvokeResponse> onInvokeActivity(TurnContext turnContext) {
			
			System.out.println("helo");
			
			logger.info("getChannelData()=> " + turnContext.getActivity().getChannelData().toString());
			logger.info("getCallerId()=> " + turnContext.getActivity().getCallerId());
			logger.info("getSummary()=> " + turnContext.getActivity().getSummary());
			logger.info("getConversationId()=> " + turnContext.getActivity().getConversation().getId());
			logger.info("getConversationType()=> " + turnContext.getActivity().getConversation().getConversationType());
			logger.info("getConversationName()=> " + turnContext.getActivity().getConversation().getName());
			// turnContext.getActivity().getConversation().setIsGroup(true);
			logger.info("getFrom()=> " + turnContext.getActivity().getFrom().toString());
			logger.info("getChannelId()=> " + turnContext.getActivity().getChannelId());
			logger.info("getId()=> " + turnContext.getActivity().getId());
			logger.info("getReplyToId()=> " + turnContext.getActivity().getReplyToId());
			logger.info("getTopicName()=> " + turnContext.getActivity().getTopicName());
			logger.info("getText()=> " + turnContext.getActivity().getText());
			logger.info("getLabel()=> " + turnContext.getActivity().getLabel());
			logger.info("getAction()=> " + turnContext.getActivity().getAction());
			logger.info("getDeliveryMode()=> " + turnContext.getActivity().getDeliveryMode());
			logger.info("getImportance()=> " + turnContext.getActivity().getImportance());
			logger.info("getName()=> " + turnContext.getActivity().getName());
			logger.info("getText()=> " + turnContext.getActivity().getText());
			logger.info("getServiceUrl()=> " + turnContext.getActivity().getServiceUrl());
			logger.info("getType()=> " + turnContext.getActivity().getType());
			logger.info("getValueType()=> " + turnContext.getActivity().getValueType());
			logger.info("teamsGetTeamId()=> " + turnContext.getActivity().teamsGetTeamId());
			logger.info("getConversation().getName())=> " + turnContext.getActivity().getConversation().getName());
			logger.info("getRecipientId=> " + turnContext.getActivity().getRecipient().getId());
			logger.info("getFromId=> " + turnContext.getActivity().getFrom().getId());
			
			logger.info("getActivityId=> " + turnContext.getActivity().getId());
			logger.info("getActivityName=> " + turnContext.getActivity().getName());
			
			logger.info("getActivity().getConversation().getAadObjectId()=> " + turnContext.getActivity().getConversation().getAadObjectId());

			Attachment cardAttachment = null;
			if (turnContext.getActivity().getValue() != null) {
				LinkedHashMap botResponseMap = (LinkedHashMap) turnContext.getActivity().getValue();
				LinkedHashMap actionObj = (LinkedHashMap) botResponseMap.get("action");
				String triggerClicked = (String) ((Map) actionObj).get("title");
				cardAttachment = new Attachment();
				try {
					cardAttachment.setContent(Serialization
							.jsonToTree(ticketQualityService.ticketStatusUpdate(triggerClicked, ticket, turnContext)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			cardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
			Activity activity = MessageFactory.attachment(cardAttachment);
			// activity.setId(turnContext.getActivity().getReplyToId());
			// turnContext.updateActivity(activity);

			logger.info(turnContext.getActivity().getChannelData().toString());
			
		

			return turnContext.sendActivity(activity).thenApply(sendResult -> null);
			// String triggerClicked = (String) ((Map)
			// botResponseMap).get("ActionResponse");
			// return CompletableFuture.completedFuture(null);
		}
	 
	

		@Override
		protected CompletableFuture<MessagingExtensionResponse> onTeamsAppBasedLinkQuery(TurnContext turnContextt,
				AppBasedLinkQuery query) {
			System.out.println("helo");
			ThumbnailCard card = new ThumbnailCard();
			card.setTitle("CodeProject");
			card.setText(query.getUrl());

			final String logoLink = "https://codeproject.freetls.fastly.net/App_Themes/CodeProject/Img/logo250x135.gif";
			CardImage cardImage = new CardImage(logoLink);
			card.setImages(Collections.singletonList(cardImage));

			// Create attachments
			MessagingExtensionAttachment attachments = new MessagingExtensionAttachment();
			attachments.setContentType(HeroCard.CONTENTTYPE);
			attachments.setContent(card);

			// Result
			MessagingExtensionResult result = new MessagingExtensionResult();
			result.setAttachmentLayout("list");
			result.setType("result");
			result.setAttachments(Collections.singletonList(attachments));

			// MessagingExtensionResponse
			return CompletableFuture.completedFuture(new MessagingExtensionResponse(result));

		}

	@Override
	protected CompletableFuture<Void> onMembersAdded(List<ChannelAccount> membersAdded, TurnContext turnContext) {

		System.out.println("user getAadObjectId => " + membersAdded.get(0).getAadObjectId());
		System.out.println("user getId => " + membersAdded.get(0).getId());

		return membersAdded.stream()
				.filter(member -> !StringUtils.equals(member.getId(), turnContext.getActivity().getRecipient().getId()))
				.map(channel -> turnContext.sendActivity(MessageFactory.text("Hello and welcome!")))
				.collect(CompletableFutures.toFutureList()).thenApply(resourceResponses -> null);
	}
	// user method
	private Attachment createAdaptiveCardAttachment() throws URISyntaxException, IOException {

		String filePath = "./src/main/resources/card.json";
		try {
			// Read JSON
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
			String adaptiveCardJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			// Replace placeholders with the actual values
			adaptiveCardJson = StringUtils.replace(adaptiveCardJson, "<USER_ID>", "Akash");
			adaptiveCardJson = StringUtils.replace(adaptiveCardJson, "<ID>", "Akash");
			adaptiveCardJson = StringUtils.replace(adaptiveCardJson, "<TITLE>", "Akash");
			adaptiveCardJson = StringUtils.replace(adaptiveCardJson, "<COMPLETED>", "Akash");
			// Create attachment
			Attachment attachment = new Attachment();
			attachment.setContentType("application/vnd.microsoft.card.adaptive");
			attachment.setContent(Serialization.jsonToTree(adaptiveCardJson));
			return attachment;
		} catch (Exception e) {
			e.printStackTrace();
			return new Attachment();
		}
	}
	
	
	@Override
	protected CompletableFuture<Void> onConversationUpdateActivity(TurnContext turnContext) {
		System.out.println("onConversationUpdateActivity");
		return CompletableFuture.completedFuture(null);

	}
		
	
	// if we turn on then invoked when we call onMessageActivity 
	/*
	 * @Override public CompletableFuture<Void> onTurn(TurnContext turnContext) {
	 * System.out.println("onTurn"); return CompletableFuture.completedFuture(null);
	 * 
	 * }
	 */
	@Override
	protected CompletableFuture<Void> onMembersRemoved(List<ChannelAccount> membersRemoved, TurnContext turnContext) {
		System.out.println("onMembersRemoved");
		return CompletableFuture.completedFuture(null);
	}
	  

	  
	
	  
	@Override
	protected CompletableFuture<Void> onEventActivity(TurnContext turnContext) {
		System.out.println("onEventActivity");
		return CompletableFuture.completedFuture(null);
	}
	  

	  
	@Override
	protected CompletableFuture<Void> onSignInInvoke(TurnContext turnContext) {
		System.out.println("onSignInInvoke");
		return CompletableFuture.completedFuture(null);
	}

	protected static InvokeResponse createInvokeResponse(Object body) {
		System.out.println("createInvokeResponse");
		return new InvokeResponse(HttpURLConnection.HTTP_OK, body);
	}

	@Override
	protected CompletableFuture<Void> onTokenResponseEvent(TurnContext turnContext) {
		System.out.println("onTokenResponseEvent");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onEvent(TurnContext turnContext) {
		System.out.println("onEvent");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onInstallationUpdate(TurnContext turnContext) {
		System.out.println("onInstallationUpdate");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onCommandActivity(TurnContext turnContext) {
		System.out.println("onCommandActivity");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onCommandResultActivity(TurnContext turnContext) {
		System.out.println("onCommandResultActivity");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onInstallationUpdateAdd(TurnContext turnContext) {
		System.out.println("onInstallationUpdateAdd");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onInstallationUpdateRemove(TurnContext turnContext) {
		System.out.println("onInstallationUpdateRemove");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<AdaptiveCardInvokeResponse> onAdaptiveCardInvoke(TurnContext turnContext,
			AdaptiveCardInvokeValue invokeValue) {
		System.out.println("onAdaptiveCardInvoke");
		return Async.completeExceptionally(new InvokeResponseException(HttpURLConnection.HTTP_NOT_IMPLEMENTED));
	}

	@Override
	protected CompletableFuture<Void> onEndOfConversationActivity(TurnContext turnContext) {
		System.out.println("onEndOfConversationActivity");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onTypingActivity(TurnContext turnContext) {
		System.out.println("onTypingActivity");
		return CompletableFuture.completedFuture(null);
	}

	@Override
	protected CompletableFuture<Void> onUnrecognizedActivityType(TurnContext turnContext) {
		System.out.println("onUnrecognizedActivityType");
		return CompletableFuture.completedFuture(null);
	}
	  
	private AdaptiveCardInvokeValue getAdaptiveCardInvokeValue(Activity activity) throws InvokeResponseException {
		System.out.println("getAdaptiveCardInvokeValue");
		if (activity.getValue() == null) {
			AdaptiveCardInvokeResponse response = createAdaptiveCardInvokeErrorResponse(
					HttpURLConnection.HTTP_BAD_REQUEST, "BadRequest", "Missing value property");
			throw new InvokeResponseException(HttpURLConnection.HTTP_BAD_REQUEST, response);
		}

		Object obj = activity.getValue();
		JsonNode node = null;
		if (obj instanceof JsonNode) {
			node = (JsonNode) obj;
		} else {
			AdaptiveCardInvokeResponse response = createAdaptiveCardInvokeErrorResponse(
					HttpURLConnection.HTTP_BAD_REQUEST, "BadRequest", "Value property instanceof not properly formed");
			throw new InvokeResponseException(HttpURLConnection.HTTP_BAD_REQUEST, response);
		}

		AdaptiveCardInvokeValue invokeValue = Serialization.treeToValue(node, AdaptiveCardInvokeValue.class);
		if (invokeValue == null) {
			AdaptiveCardInvokeResponse response = createAdaptiveCardInvokeErrorResponse(
					HttpURLConnection.HTTP_BAD_REQUEST, "BadRequest", "Value property instanceof not properly formed");
			throw new InvokeResponseException(HttpURLConnection.HTTP_BAD_REQUEST, response);
		}

		if (invokeValue.getAction() == null) {
			AdaptiveCardInvokeResponse response = createAdaptiveCardInvokeErrorResponse(
					HttpURLConnection.HTTP_BAD_REQUEST, "BadRequest", "Missing action property");
			throw new InvokeResponseException(HttpURLConnection.HTTP_BAD_REQUEST, response);
		}

		if (!invokeValue.getAction().getType().equals("Action.Execute")) {
			AdaptiveCardInvokeResponse response = createAdaptiveCardInvokeErrorResponse(
					HttpURLConnection.HTTP_BAD_REQUEST, "NotSupported",
					String.format("The action '%s'is not supported.", invokeValue.getAction().getType()));
			throw new InvokeResponseException(HttpURLConnection.HTTP_BAD_REQUEST, response);
		}

		return invokeValue;
	}
	  
	private AdaptiveCardInvokeResponse createAdaptiveCardInvokeErrorResponse(Integer statusCode, String code,
			String message) {
		System.out.println("getAdaptiveCardInvokeValue");
		AdaptiveCardInvokeResponse adaptiveCardInvokeResponse = new AdaptiveCardInvokeResponse();
		adaptiveCardInvokeResponse.setStatusCode(statusCode);
		adaptiveCardInvokeResponse.setType("application/vnd.getmicrosoft().error");
		com.microsoft.bot.schema.Error error = new com.microsoft.bot.schema.Error();
		error.setCode(code);
		error.setMessage(message);
		adaptiveCardInvokeResponse.setValue(error);
		return adaptiveCardInvokeResponse;
	}
	 		
		  // @PostConstruct 
				public void test() throws Exception {
			    	
			    	
			    	//fetchnSave_Retry_Mechanism.updateTeamsMsgToDatabase();
			    	
			    	
			    	String clientId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			    	String[] scope = {"files.readwrite.all", "offline_access"};
			    	String redirectURL = "http://localhost:8080/";
			    	String clientSecret = "xxxxxxxxxxxxxxxxxxxxxxx";
			
			    
			    	
			     //   URL url = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Employee%20Declaration%20_Income%20tax_Form.xlsx");
			     //   HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();		
			      //  String accessToken="eyJ0eXAiOiJKV1QiLCJub25jZSI6IjB6TXdKYVFCV3ZLRzdWZHpYZUV6Y20xLWZaT213aGhlYjJhSUlybmpwLTQiLCJhbGciOiJSUzI1NiIsIng1dCI6ImpTMVhvMU9XRGpfNTJ2YndHTmd2UU8yVnpNYyIsImtpZCI6ImpTMVhvMU9XRGpfNTJ2YndHTmd2UU8yVnpNYyJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC85MzYyYzI2NC1mNGNmLTQ1ZDMtOGU2MC1lZjg4Y2ViNzM2MDMvIiwiaWF0IjoxNjU1MDUyMzY5LCJuYmYiOjE2NTUwNTIzNjksImV4cCI6MTY1NTA1NjcwMiwiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhUQUFBQXU0WnQwZGtnbjd5OUx4b0FFRWZZbC80S09YZ0xaZit0VEFIb3p0aUd6R0E9IiwiYW1yIjpbInB3ZCJdLCJhcHBfZGlzcGxheW5hbWUiOiJHcmFwaCBFeHBsb3JlciIsImFwcGlkIjoiZGU4YmM4YjUtZDlmOS00OGIxLWE4YWQtYjc0OGRhNzI1MDY0IiwiYXBwaWRhY3IiOiIwIiwiZmFtaWx5X25hbWUiOiJJbmRpYSIsImdpdmVuX25hbWUiOiJLYWdhbWkiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIyNy42LjEzMy4yMTciLCJuYW1lIjoiS2FnYW1pIEluZGlhIiwib2lkIjoiMTNlZDIxODAtNmIxYi00Y2ZkLTk1ZmYtZjMyZmU1MTcyYTQ2IiwicGxhdGYiOiI4IiwicHVpZCI6IjEwMDMyMDAxNTJCQjU1NjgiLCJyaCI6IjAuQVhBQVpNSmlrOF8wMDBXT1lPLUl6cmMyQXdNQUFBQUFBQUFBd0FBQUFBQUFBQUJ3QUVNLiIsInNjcCI6IkFwcGxpY2F0aW9uLlJlYWQuQWxsIEFwcGxpY2F0aW9uLlJlYWRXcml0ZS5BbGwgQ2hhbm5lbE1lc3NhZ2UuUmVhZC5BbGwgQ2hhdC5DcmVhdGUgQ2hhdC5SZWFkV3JpdGUgRGlyZWN0b3J5LlJlYWQuQWxsIERpcmVjdG9yeS5SZWFkV3JpdGUuQWxsIEdyb3VwLlJlYWQuQWxsIEdyb3VwLlJlYWRXcml0ZS5BbGwgTWFpbC5SZWFkIE1haWwuUmVhZEJhc2ljIE1haWwuUmVhZFdyaXRlIG9wZW5pZCBQZW9wbGUuUmVhZCBQZW9wbGUuUmVhZC5BbGwgcHJvZmlsZSBUZWFtLkNyZWF0ZSBUZWFtLlJlYWRCYXNpYy5BbGwgVGVhbU1lbWJlci5SZWFkV3JpdGUuQWxsIFRlYW1zQXBwSW5zdGFsbGF0aW9uLlJlYWRGb3JDaGF0IFRlYW1zQXBwSW5zdGFsbGF0aW9uLlJlYWRGb3JUZWFtIFRlYW1zQXBwSW5zdGFsbGF0aW9uLlJlYWRGb3JVc2VyIFRlYW1zQXBwSW5zdGFsbGF0aW9uLlJlYWRXcml0ZUZvckNoYXQgVGVhbXNBcHBJbnN0YWxsYXRpb24uUmVhZFdyaXRlRm9yVGVhbSBUZWFtc0FwcEluc3RhbGxhdGlvbi5SZWFkV3JpdGVTZWxmRm9yQ2hhdCBUZWFtU2V0dGluZ3MuUmVhZC5BbGwgVGVhbVNldHRpbmdzLlJlYWRXcml0ZS5BbGwgVXNlci5SZWFkIFVzZXIuUmVhZC5BbGwgVXNlci5SZWFkQmFzaWMuQWxsIFVzZXIuUmVhZFdyaXRlLkFsbCBlbWFpbCIsInNpZ25pbl9zdGF0ZSI6WyJrbXNpIl0sInN1YiI6ImFsT0xLSlpaMVh4Z0Fib2Y2ZDF3TzN4X2toTTNyZ2swQWJTb2VydFl2YVkiLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiQVMiLCJ0aWQiOiI5MzYyYzI2NC1mNGNmLTQ1ZDMtOGU2MC1lZjg4Y2ViNzM2MDMiLCJ1bmlxdWVfbmFtZSI6ImFkbWluQGtnbWlwLm9ubWljcm9zb2Z0LmNvbSIsInVwbiI6ImFkbWluQGtnbWlwLm9ubWljcm9zb2Z0LmNvbSIsInV0aSI6IjAzdWxueUdQY2s2QVBqNUN4Mmk3QUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbIjYyZTkwMzk0LTY5ZjUtNDIzNy05MTkwLTAxMjE3NzE0NWUxMCIsImI3OWZiZjRkLTNlZjktNDY4OS04MTQzLTc2YjE5NGU4NTUwOSJdLCJ4bXNfc3QiOnsic3ViIjoibkdmai1BNkpDS011czhxTlZCalZRSlg1TUwxWmRsV1I3NnlBMmJGbHhkcyJ9LCJ4bXNfdGNkdCI6MTYyMzg2MzU0OX0.r1vynk2zAW0So5OuvbraADogtt1I_cJBRGBeAsmywTN9LI5BwODDCXNKxVHbQ2r33MIkx6iMC23NmKWyExznFWY7SGPP4-Jb41vwXp9dbu4W-Ph9Qxf69vZmk6mHPh8gzTTxHnh2CFNopvI3UxXV2UyJVqnpnBGQE2PerTjWWlGq3qFKyPyrZNwR1gnlS7n8BVC8j7nKNWkpVi_p2AppE-6RMqIO7GFQNtlIkDDOz8FPX09MtMfc7t88OhkleAJyfdr691ctZRLRIcwcO7hDcCqYlLCEHgIA1HEofREBXR9eeifVAfqD7bbiQcURiqpHqN5uqA03Z13T4KqJlyJuMw";
			      //  httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
			     //   int responseCode = httpConn.getResponseCode();
			    //	System.out.println(responseCode);
			    	
			      //  fetchnSave_Retry_Mechanism.updateTeamsMsgToDatabase();
			    	BufferedInputStream bis = null;
			    	BufferedOutputStream bos = null;
			    	
			     	final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
							.clientId("bccaebb1-43bd-49fa-aa3e-6e8b48037100").username("admin@kgmerp.onmicrosoft.com")
							.password("Kgm@123$").build();
					final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
							usernamePasswordCredential);
			    	
			    	
			    	/*final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
							.clientId("dc3cba5c-d9f7-4a84-b6f6-f51d07a20480").username("admin@kgmip.onmicrosoft.com")
							.password("Kgm@123$").build();
					final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
							usernamePasswordCredential);*/
					System.out.println("hello world");
					
					
					
					 //String accessToken = tokenCredentialAuthProvider.getAuthorizationTokenAsync(new URL("https://graph.microsoft.com/v1.0/me/")).get();
					
					//  System.out.println(accessToken);

					final GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
							.authenticationProvider(tokenCredentialAuthProvider).buildClient();
					
					User user = graphClient.me().buildRequest().get();
					System.out.println(user);
					
					DriveItemCollectionPage children = graphClient.me().drive().root().children()
							.buildRequest()
							.get();
					
					List<DriveItem> di=children.getCurrentPage();
					
					System.out.println(di.get(0));
					
				//	DriveItem drive=graphClient.shares("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png").driveItem().buildRequest().get();
					
					
				//	System.out.println(drive);
					 
					
				//	URL url1 = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png");
				//	URL url1 = new URL("https://www.w3schools.com/html/pic_trulli.jpg");
					//URL url1 = new URL("https://kgmip-my.sharepoint.com/:x:/g/personal/husenaiah_g_kgmip_onmicrosoft_com/EZgbvc5oTghIglTxP5X2fFABvInffzpjRYxC0AdtEA1YPw?e=dtSAtP");
				
					
					 URL url1=new URL("https://m365x214355-my.sharepoint.com/personal/meganb_m365x214355_onmicrosoft_com/_layouts/15/download.aspx?UniqueId=dd092d3e-427f-45ea-8daf-e25e7f77530c&Translate=false&tempauth=eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvbTM2NXgyMTQzNTUtbXkuc2hhcmVwb2ludC5jb21AZGNkMjE5ZGQtYmM2OC00YjliLWJmMGItNGEzM2E3OTZiZTM1IiwiaXNzIjoiMDAwMDAwMDMtMDAwMC0wZmYxLWNlMDAtMDAwMDAwMDAwMDAwIiwibmJmIjoiMTY1NTI2NjEyNyIsImV4cCI6IjE2NTUyNjk3MjciLCJlbmRwb2ludHVybCI6IjlacHYvK25wK3ExWnFXdnlMM2RZNGVVaHdtMlllaHJvRTlBSmFjOC84R2c9IiwiZW5kcG9pbnR1cmxMZW5ndGgiOiIxNjkiLCJpc2xvb3BiYWNrIjoiVHJ1ZSIsImNpZCI6Ik5tUTVOR1U0WWpNdE5EWmxNQzAwTnpGaExUZzRNREl0TVRjeE5tWXdNV0k0TnpSaSIsInZlciI6Imhhc2hlZHByb29mdG9rZW4iLCJzaXRlaWQiOiJaRGd5TXpFeVpqa3RZakl6WWkwMFkySmpMVGsxWkRVdE0yVXdaRGswWlRZNFl6RmwiLCJhcHBfZGlzcGxheW5hbWUiOiJhcGlzYW5kYm94cHJveHkiLCJnaXZlbl9uYW1lIjoiTWVnYW4iLCJmYW1pbHlfbmFtZSI6IkJvd2VuIiwiYXBwaWQiOiIwNWIxMGEyZC02MmRiLTQyMGMtODYyNi01NWYzYTVlNzg2NWIiLCJ0aWQiOiJkY2QyMTlkZC1iYzY4LTRiOWItYmYwYi00YTMzYTc5NmJlMzUiLCJ1cG4iOiJtZWdhbmJAbTM2NXgyMTQzNTUub25taWNyb3NvZnQuY29tIiwicHVpZCI6IjEwMDNCRkZEQTM4MTMxQUYiLCJjYWNoZWtleSI6IjBoLmZ8bWVtYmVyc2hpcHwxMDAzYmZmZGEzODEzMWFmQGxpdmUuY29tIiwic2NwIjoibXlmaWxlcy5yZWFkIGdyb3VwLnJlYWQgYWxsc2l0ZXMucmVhZCBhbGxwcm9maWxlcy5yZWFkIGFsbHByb2ZpbGVzLnJlYWQiLCJ0dCI6IjIiLCJ1c2VQZXJzaXN0ZW50Q29va2llIjpudWxsLCJpcGFkZHIiOiIyMC4xOTAuMTMyLjQwIn0.ZTBLZHFQbTdudGdGTlJydm8yNWxsVFE0VGN4c2hwRkxHY05jRDM5a3Bmdz0&ApiVersion=2.0");
					 HttpURLConnection httpConn = (HttpURLConnection) url1.openConnection();
			          httpConn.setDoOutput(true);
			          httpConn.setRequestProperty("api-version", "1.5");
			          httpConn.setDoInput(true);	
			          //httpConn.setRequestMethod("GET");			
			          //httpConn.setRequestProperty("Accept", "application/json;odata=minimalmetadata");
			          //httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			    	  //urlConn.setReadTimeout(5000);
			    	  //urlConn.setConnectTimeout(5000);
			    	  //con.setRequestProperty("Cookie", "foo=bar"); 
			          //httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
			           
			          System.out.println("content type =>"+httpConn.getContentType()+"");
			          
			          System.out.println("content type =>"+httpConn.getResponseCode()+"");
			          
			          
			          InputStream is = httpConn.getInputStream();
			          
			          
			          byte[] bytedata=is.readAllBytes();
			          
			          
			    	    System.out.println(bytedata.length);
			    	     
						File outputFile = new File("/home/husenaiah/Downloads/2022/hello_amchecking.xlsx");
			    	    
						try (FileOutputStream outputStream1 = new FileOutputStream(outputFile);) {

							outputStream1.write(bytedata); // Write the bytes and you're done.

						} catch (Exception e) {
							e.printStackTrace();
						}
					
					
					
					//  + "/$value"
					
					String baseUrl="https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Employee%20Declaration%20_Income%20tax_Form.xlsx";
					
					

					 InputStream stream12 = graphClient.customRequest(baseUrl, InputStream.class)
				    			.buildRequest()
				    			.get();
					
					
					//final InputStream result = graphClient.me().messages("id").attachments().buildRequest();
					final CustomRequestBuilder downloadRequestBuilder = new CustomRequestBuilder<>(baseUrl, graphClient, new ArrayList(), InputStream.class);
					final InputStream stream = (InputStream) downloadRequestBuilder.buildRequest().get();
					
				com.microsoft.graph.models.Attachment ath= graphClient.me().messages("1654879856327").attachments("e68f4cd2-dc1f-47b9-9f4b-29d7c86ee8d5").buildRequest().get();
				System.out.println(ath);
			    	
			    	
			    // https://135.181.202.86:12002/kagami-generated_Srinivasa_Live/dms/downloadDocument?docId=1654509082354
			    	 //  URL url = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png");
			    	   // URL url = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Employee%20Declaration%20_Income%20tax_Form1212.xlsx");
			    //	URL url = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft Teams Chat Files/Screenshot from 2022-06-05 22-28-36.png");
			    	  //  URL url = new URL("https://www.w3schools.com/css/img_5terre.jpg");
			    	    

			    	//BufferedReader reader = new BufferedReader(new InputStreamReader((, null));
			    	
			    	//BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL("")).openConnection()).getInputStream(),Charset.forName("UTF-8")));
			    	
			    	
			    	
			    	
			    	  // URL url1 = new URL("https://www.w3schools.com/css/img_5terre.jpg");

			    
			          
			 
			                // Setting the request method and
			                // properties.
			           
			 
			              
							/*
							 * InputStream ip = con.getInputStream();
							 * 
							 * BufferedReader br1 = new BufferedReader( new
							 * InputStreamReader(ip,Charset.forName("UTF-8")));
							 * 
							 * System.out.println(br1);
							 * 
							 * 
							 * // Printing the response code // and response message from server.
							 * System.out.println("Response Code:" + con.getResponseCode());
							 * System.out.println( "Response Message:" + con.getResponseMessage());
							 */
			    	
			    	    
			    	    //URLConnection urlConn = url.openConnection();
			    	   // con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			    	    //urlConn.setReadTimeout(5000);
			    	    //urlConn.setConnectTimeout(5000);
			    	    //con.setRequestProperty("Cookie", "foo=bar"); 

//			    	    String contentType = con.getContentType();

//			    	    System.out.println("contentType:" + contentType);

			    	   
			    	    
			    	    
			    	  //  BufferedReader reader = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
			    	
			    	   // File outputFile = new File("/home/husenaiah/Downloads/2022/pngtesttest20055.jpg");		

			    	    
			    	   // FileUtils.copyInputStreamToFile(is, outputFile);
			    	    
			    	    
						 
			    	    
			    	 //   bis = new BufferedInputStream(is, 4 * 1024);
			    	   // bos = new BufferedOutputStream(new FileOutputStream(fileName.toString()));​
			    	//InputStream in = new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png").openStream();
			    	//Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);

			   
			    	
			    
			
					
					//InputStream stream = graphClient.me().messages("Message-ID").attachments("Attachment-ID").buildRequest().get();
						  
						
					
					
					
					 InputStream stream123 = graphClient.customRequest("/me/drive/items/cebd1b98-4e68-4808-8254-f13f95f67c50/content", InputStream.class)
				    			.buildRequest()
				    			.get();
				         System.out.println(stream);
					
					graphClient.sites().byId("kgmip.sharepoint.com,9cbf9fea-9611-4bd8-885e-43e2e5edab6b,c9a49ae3-fa6f-430b-82ed-fa748ad66c83").drive().root();
					
					System.out.println(graphClient);
					
					DriveCollectionPage drive1 = graphClient.me().drives().buildRequest().get();
					
							System.out.println(drive1.getCurrentPage());
					SiteCollectionPage sites = graphClient.sites()
							.buildRequest()
							.get();
					
					Long  ss=sites.getCount();
					System.out.println(ss);
					
					System.out.println(sites.getCurrentPage());
					
					DriveRecentCollectionPage recent = graphClient.me().drive()
							.recent()
							.buildRequest()
							.get();
					System.out.println(recent);

			    
				//	InputStream stream = graphClient.customRequest("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png", InputStream.class)
			    //			.buildRequest()
			    //			.get();
					
			
					//Drive children = graphClient.me().drive().buildRequest().get();
					//System.out.println(children);
				
					
					System.out.println(recent);
					
				
					
					DriveItemRequestBuilder dirb=graphClient.me().drive().items("cebd1b98-4e68-4808-8254-f13f95f67c50");
					
					System.out.println(dirb.createdByUser());
			    	
			    	System.out.println(stream.read());

				}
			 
				
				protected CompletableFuture<Void> onMessageActivity12(TurnContext turnContext) {
					HeroCard card = new HeroCard();
					card.setText("You can upload an image or select one of the following choices");

					// Note that some channels require different values to be used in order to get
					// buttons to display text.
					// In this code the emulator is accounted for with the 'title' parameter, but in
					// other channels you may
					// need to provide a value for other parameters like 'text' or 'displayText'.
					card.setButtons(new CardAction(ActionTypes.IM_BACK, "1. Inline Attachment", "1"),
							new CardAction(ActionTypes.IM_BACK, "2. Internet Attachment", "2"),
							new CardAction(ActionTypes.IM_BACK, "3. Uploaded Attachment", "3"));

					Activity reply = MessageFactory.attachment(card.toAttachment());
					return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);

				}
				
				@Override
				protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
					
					
					final GraphServiceClient<Request> graphClient=	AuthenticationService.getInstance();
					String json=null;
					AdaptiveCardsRequest adcard = new AdaptiveCardsRequest();
					List<Container> conlist = new ArrayList<>();
					List<ActionSet> actList = new ArrayList<>();

					Container con = new Container();
					con.setType("Container");

					Item it1 = new Item();
					it1.setType("TextBlock");
					it1.setText("Ticket # is raised for your support.");
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
					it2.setText("Team members from department are added to this chat.");
					it2.setWeight("bolder");
					it2.setSize("medium");
					it2.setWrap(true);

					ArrayList<Item> itemList2 = new ArrayList<>();
					itemList2.add(it2);
					con2.setItems(itemList2);

					conlist.add(con2);

					Container con3 = new Container();
					con3.setType("TextBlock");
					con3.setText("They will revert back to you soon.");
					con3.setWeight("bolder");
					con3.setSize("medium");

					conlist.add(con3);

					adcard.setBody(conlist);
					
					
			    // http://localhost:3978/api/redis/employee/getall
					
				//trial one 
					
				
				
				 // ActionSet action = new ActionSet(); 
				 // action.setType("Action.OpenUrl");
				 // action.setTitle("Action.OpenUrl");
				 // action.setId("http_request");
				 // action.setMethod("GET"); 
				 // action.setUrl("https://0a4d-115-246-202-106.ngrok.io/api/redis/employee/getall");
				 
				 
					
			     //trial two 		
					
					
					/*
					 * ActionSet action = new ActionSet(); action.setType("Action.Http");
					 * action.setTitle("Action.Http"); action.setId("http_request");
					 * 
					 * action.setUrl(
					 * "https://0a4d-115-246-202-106.ngrok.io/api/redis/employee/getall");
					 */
					 
					
				// trial three	
					
					
				/*
				 * ActionSet action = new ActionSet(); 
				 * action.setType("Action.Submit");
				 * action.setTitle("Call Java"); 
				 * action.setId("http_request"); 
				 * MsTeams msteams=new MsTeams();
				 * msteams.setType("signin");
				 * msteams.setValue("https://0a4d-115-246-202-106.ngrok.io/api/redis/employee/getall");
				  
				 * ActionData data=new ActionData(); 
				 * data.setMsteams(msteams);
				 * data.setExtraData("(this will be ignored)");
				   action.setData(data);
				 */
					  
				// trial four	
					  
					  
					  
				ActionSet action = new ActionSet();
				action.setType("Action.Submit");
				action.setTitle("Call Java"); // action.setId("http_request"); 
				MsTeams msteams = new MsTeams();
				msteams.setType("signin");
				msteams.setValue("https://0a4d-115-246-202-106.ngrok.io/api/redis/employee/getall");

				ActionData data = new ActionData();
				data.setMsteams(msteams);
				data.setExtraData("(this will be ignored)");
				action.setData(data);
						 
					  
					
					
					
				
					/*
					 * { "type": "Action.Submit", "title": "Click me for signin", 
					 * "data": {
					 * "msteams": { "type": "signin", "value":
					 * "https://yoursigninurl.com/signinpath?parames=values", }, 
					 * "extraData": "(this will be ignored)" } 
					 * 
					 * }
					 */
					
					
					/*
					 * { "type": "Action.Http", "title": "Say hello", "method": "GET", "url":
					 * "https://contoso.com/sayhello?name={{nameInput.value}}" }
					 */
					
					
					
					
					/*
					 * { "type": "Action.OpenUrl", "title": "Action.OpenUrl", "url":
					 * "https://adaptivecards.io" }
					 */
					
				/*
				 * "type": "Action.Http", "title": "Say hello", "method": "POST", "url":
				 * "https://contoso.com/sayhello", "body": "{{nameInput.value}}" }
				 */
					
					

				/*
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
				 */

					actList.add(action);
					//actList.add(action2);

					adcard.setActions(actList);
					Attachment cardAttachment=null;
					
					

					// ============= create ticket Json structure done =======================

					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					try {
						json = ow.writeValueAsString(adcard);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					cardAttachment = new Attachment();
					try {
						cardAttachment.setContent(Serialization.jsonToTree(json));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					json="{\n"
							+ "    \"type\": \"AdaptiveCard\",\n"
							+ "    \"body\": [\n"
							+ "        {\n"
							+ "            \"type\": \"Container\",\n"
							+ "            \"id\": \"353b659f-b668-fac0-5b7f-5d2f1bdb46ac\",\n"
							+ "            \"padding\": \"Default\",\n"
							+ "            \"items\": [\n"
							+ "                {\n"
							+ "                    \"type\": \"ActionSet\",\n"
							+ "                    \"actions\": [\n"
							+ "                        {\n"
							+ "                            \"type\": \"Action.Http\",\n"
							+ "                            \"id\": \"accept\",\n"
							+ "                            \"title\": \"Accept\",\n"
							+ "                            \"method\": \"POST\",\n"
							+ "                            \"url\": \"https://www.microsoft.com\",\n"
							+ "                            \"body\": \"{}\",\n"
							+ "                            \"isPrimary\": true,\n"
							+ "                            \"style\": \"positive\"\n"
							+ "                        },\n"
							+ "                        {\n"
							+ "                            \"type\": \"Action.ShowCard\",\n"
							+ "                            \"id\": \"e1487cbc-66b0-037e-cdc4-045fb7d8d0b8\",\n"
							+ "                            \"title\": \"Reject\",\n"
							+ "                            \"card\": {\n"
							+ "                                \"type\": \"AdaptiveCard\",\n"
							+ "                                \"body\": [\n"
							+ "                                    {\n"
							+ "                                        \"type\": \"Input.Text\",\n"
							+ "                                        \"id\": \"Comment\",\n"
							+ "                                        \"placeholder\": \"Add a comment\",\n"
							+ "                                        \"isMultiline\": true\n"
							+ "                                    },\n"
							+ "                                    {\n"
							+ "                                        \"type\": \"ActionSet\",\n"
							+ "                                        \"id\": \"1e77f639-e5a8-320f-c6de-4291227db6b3\",\n"
							+ "                                        \"actions\": [\n"
							+ "                                            {\n"
							+ "                                                \"type\": \"Action.Submit\",\n"
							+ "                                                \"id\": \"1ca3a888-ebfb-1feb-064b-928960616e52\",\n"
							+ "                                                \"title\": \"Submit\",\n"
							+ "                                                \"method\": \"POST\",\n"
							+ "                                                \"url\": \"https://dev3.kagamierp.com:3978/api/redis/employee/getall\",\n"
							+ "                                                 \"body\": \"{}\",\n"
							+ "                                            }\n"
							+ "                                        ]\n"
							+ "                                    }\n"
							+ "                                ],\n"
							+ "                                \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
							+ "                                \"fallbackText\": \"Unable to render the card\",\n"
							+ "                                \"padding\": \"None\"\n"
							+ "                            }\n"
							+ "                        }\n"
							+ "                    ],\n"
							+ "                    \"spacing\": \"None\"\n"
							+ "                }\n"
							+ "            ],\n"
							+ "            \"spacing\": \"None\",\n"
							+ "            \"separator\": true\n"
							+ "        }\n"
							+ "    ],\n"
							+ "    \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
							+ "    \"version\": \"1.0\",\n"
							+ "    \"padding\": \"None\"\n"
							+ "}";
					
					json ="{\n"
							+ "    \"@type\": \"MessageCard\",\n"
							+ "    \"@context\": \"http://schema.org/extensions\",\n"
							+ "    \"themeColor\": \"0076D7\",\n"
							+ "    \"summary\": \"Larry Bryant created a new task\",\n"
							+ "    \"sections\": [{\n"
							+ "        \"activityTitle\": \"Larry Bryant created a new task\",\n"
							+ "        \"activitySubtitle\": \"On Project Tango\",\n"
							+ "        \"activityImage\": \"https://teamsnodesample.azurewebsites.net/static/img/image5.png\",\n"
							+ "        \"facts\": [{\n"
							+ "            \"name\": \"Assigned to\",\n"
							+ "            \"value\": \"Unassigned\"\n"
							+ "        }, {\n"
							+ "            \"name\": \"Due date\",\n"
							+ "            \"value\": \"Mon May 01 2017 17:07:18 GMT-0700 (Pacific Daylight Time)\"\n"
							+ "        }, {\n"
							+ "            \"name\": \"Status\",\n"
							+ "            \"value\": \"Not started\"\n"
							+ "        }],\n"
							+ "        \"markdown\": true\n"
							+ "    }],\n"
							+ "    \"potentialAction\": [{\n"
							+ "        \"@type\": \"ActionCard\",\n"
							+ "        \"name\": \"Add a comment\",\n"
							+ "        \"inputs\": [{\n"
							+ "            \"@type\": \"TextInput\",\n"
							+ "            \"id\": \"comment\",\n"
							+ "            \"isMultiline\": false,\n"
							+ "            \"title\": \"Add a comment here for this task\"\n"
							+ "        }],\n"
							+ "        \"actions\": [{\n"
							+ "            \"@type\": \"HttpPOST\",\n"
							+ "            \"name\": \"Add comment\",\n"
							+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\n"
							+ "        }]\n"
							+ "    }, {\n"
							+ "        \"@type\": \"ActionCard\",\n"
							+ "        \"name\": \"Set due date\",\n"
							+ "        \"inputs\": [{\n"
							+ "            \"@type\": \"DateInput\",\n"
							+ "            \"id\": \"dueDate\",\n"
							+ "            \"title\": \"Enter a due date for this task\"\n"
							+ "        }],\n"
							+ "        \"actions\": [{\n"
							+ "            \"@type\": \"HttpPOST\",\n"
							+ "            \"name\": \"Save\",\n"
							+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\n"
							+ "        }]\n"
							+ "    }, {\n"
							+ "        \"@type\": \"OpenUri\",\n"
							+ "        \"name\": \"Learn More\",\n"
							+ "        \"targets\": [{\n"
							+ "            \"os\": \"default\",\n"
							+ "            \"uri\": \"https://docs.microsoft.com/outlook/actionable-messages\"\n"
							+ "        }]\n"
							+ "    }, {\n"
							+ "        \"@type\": \"ActionCard\",\n"
							+ "        \"name\": \"Change status\",\n"
							+ "        \"inputs\": [{\n"
							+ "            \"@type\": \"MultichoiceInput\",\n"
							+ "            \"id\": \"list\",\n"
							+ "            \"title\": \"Select a status\",\n"
							+ "            \"isMultiSelect\": \"false\",\n"
							+ "            \"choices\": [{\n"
							+ "                \"display\": \"In Progress\",\n"
							+ "                \"value\": \"1\"\n"
							+ "            }, {\n"
							+ "                \"display\": \"Active\",\n"
							+ "                \"value\": \"2\"\n"
							+ "            }, {\n"
							+ "                \"display\": \"Closed\",\n"
							+ "                \"value\": \"3\"\n"
							+ "            }]\n"
							+ "        }],\n"
							+ "        \"actions\": [{\n"
							+ "            \"@type\": \"HttpPOST\",\n"
							+ "            \"name\": \"Save\",\n"
							+ "            \"target\": \"https://docs.microsoft.com/outlook/actionable-messages\"\n"
							+ "        }]\n"
							+ "    }]\n"
							+ "}";
					
					json="\n"
							+ "\n"
							+ "{\n"
							+ "    \"type\": \"AdaptiveCard\",\n"
							+ "    \"body\": [\n"
							+ "        {\n"
							+ "            \"type\": \"Container\",\n"
							+ "            \"id\": \"353b659f-b668-fac0-5b7f-5d2f1bdb46ac\",\n"
							+ "            \"padding\": \"Default\",\n"
							+ "            \"items\": [\n"
							+ "                {\n"
							+ "                    \"type\": \"ActionSet\",\n"
							+ "                    \"actions\": [\n"
							+ "                        {\n"
							+ "                            \"type\": \"Action.Submit\",\n"
							+ "                            \"id\": \"accept\",\n"
							+ "                            \"title\": \"Accept\",\n"
							+ "                            \"method\": \"POST\",\n"
							+ "                            \"url\": \"https://www.microsoft.com\",\n"
							+ "                            \"body\": \"{}\",\n"
							+ "                            \"isPrimary\": true,\n"
							+ "                            \"style\": \"positive\"\n"
							+ "                        },\n"
							+ "                        {\n"
							+ "                            \"type\": \"Action.ShowCard\",\n"
							+ "                            \"id\": \"e1487cbc-66b0-037e-cdc4-045fb7d8d0b8\",\n"
							+ "                            \"title\": \"Reject\",\n"
							+ "                            \"card\": {\n"
							+ "                                \"type\": \"AdaptiveCard\",\n"
							+ "                                \"body\": [\n"
							+ "                                    {\n"
							+ "                                        \"type\": \"Input.Text\",\n"
							+ "                                        \"id\": \"Comment\",\n"
							+ "                                        \"placeholder\": \"Add a comment\",\n"
							+ "                                        \"isMultiline\": true\n"
							+ "                                    },\n"
							+ "                                    {\n"
							+ "                                        \"type\": \"ActionSet\",\n"
							+ "                                        \"id\": \"1e77f639-e5a8-320f-c6de-4291227db6b3\",\n"
							+ "                                        \"actions\": [\n"
							+ "                                            {\n"
							+ "                                                \"type\": \"Action.Submit\",\n"
							+ "                                                \"id\": \"1ca3a888-ebfb-1feb-064b-928960616e52\",\n"
							+ "                                                \"title\": \"Submit\",\n"
							+ "                                                \"method\": \"POST\",\n"
							+ "                                                \"url\": \"https://04f9-115-246-202-106.ngrok.io/api/redis/employee/post\",\n"
							+ "                                                \"body\": \"{comment : {{Comment.value}}}\"\n"
							+ "                                            }\n"
							+ "                                        ]\n"
							+ "                                    }\n"
							+ "                                ],\n"
							+ "                                \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
							+ "                                \"fallbackText\": \"Unable to render the card\",\n"
							+ "                                \"padding\": \"None\"\n"
							+ "                            }\n"
							+ "                        }\n"
							+ "                    ],\n"
							+ "                    \"spacing\": \"None\"\n"
							+ "                }\n"
							+ "            ],\n"
							+ "            \"spacing\": \"None\",\n"
							+ "            \"separator\": true\n"
							+ "        }\n"
							+ "    ],\n"
							+ "    \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\n"
							+ "    \"version\": \"1.0\",\n"
							+ "    \"padding\": \"None\"\n"
							+ "}";
					
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.subject = null;
					ItemBody body = new ItemBody();
					body.contentType = BodyType.HTML;
					body.content = "<attachment id=\"74d20c7f34aa4a7fb74e2b30004247c5\"></attachment>";
					chatMessage.body = body;
					LinkedList<ChatMessageAttachment> attachmentsList = new LinkedList<ChatMessageAttachment>();
					ChatMessageAttachment attachments = new ChatMessageAttachment();
					attachments.id = "74d20c7f34aa4a7fb74e2b30004247c5";
					
					attachments.contentType = "application/vnd.microsoft.card.adaptive";
					attachments.contentUrl = null;
					attachments.content = json;
					attachments.name = null;
					attachments.thumbnailUrl = null;
					attachmentsList.add(attachments);
					chatMessage.attachments = attachmentsList;
					graphClient.chats("19:f5835314d2ee4193bf1222771de863a9@thread.v2").messages().buildRequest().post(chatMessage);
					
				
					
					//attachments.contentType = "application/vnd.microsoft.card.thumbnail";
					//	attachments.content = "{\r\n  \"title\": \"This is an example of posting a card\",\r\n  \"subtitle\": \"<h3>This is the subtitle</h3>\",\r\n  \"text\": \"Here is some body text. <br>\\r\\nAnd a <a href=\\\"http://microsoft.com/\\\">hyperlink</a>. <br>\\r\\nAnd below that is some buttons:\",\r\n  \"buttons\": [\r\n    {\r\n      \"type\": \"messageBack\",\r\n      \"title\": \"Login to FakeBot\",\r\n      \"text\": \"login\",\r\n      \"displayText\": \"login\",\r\n      \"value\": \"login\"\r\n    }\r\n  ]\r\n}";
					//ChatMessage chatMessage = new ChatMessage();
					//ItemBody body = new ItemBody();
					//body.content = "<br/><strong>Hello All, New chat group created with ticket !! for discussion </strong><br/><strong>Issue Details : "+tkt.getDescription()+"</strong><br/><strong>All "+deptName+" people were added to this Chat Group</strong>";
					//body.contentType=BodyType.HTML;
					//chatMessage.body = body;
					
					
					//graphClient.chats("").messages().buildRequest().post(chatMessage);
					
					
					cardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
					Activity activity = MessageFactory.attachment(cardAttachment);
					turnContext.sendActivity(activity);
				//	processTurnContext(turnContext);

					// return turnContext.sendActivity(activity).th
					return CompletableFuture.completedFuture(null);
					// return turnContext.sendActivity(activity).thenApply(sendResult -> null);

					// return turnContext.sendActivity(MessageFactory.text("Echo: " +
					// turnContext.getActivity().getText())).thenApply(sendResult -> null);

				}
			
    
}
