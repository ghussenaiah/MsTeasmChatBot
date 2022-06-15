package com.microsoft.teams.app;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;
import com.microsoft.graph.requests.ChatMessageCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.ChatHistory_299;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.TicketRepo;

import okhttp3.Request;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import com.microsoft.teams.app.entity.DocumentModel;
import com.microsoft.teams.app.service.impl.DocumentServiceModel;

@Component
public class FetchnSave_Retry_Mechanism {

	private static final Logger log = LoggerFactory.getLogger(FetchnSave_Retry_Mechanism.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	AutoGenerationRepo autoGenerationRepo;

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	DocumentServiceModel documentservicemodel;

	FetchnSave_Retry_Mechanism() {

	}

	@Scheduled(cron = "*/1 * * * * *")
	public void currentTime() throws Exception {
		// log.info("Current Time = {}", dateFormat.format(new Date()));
		 // updateTeamsMsgToDatabase();
		//updateTicketQualityAfter5Hours();
	}

	public synchronized void updateTicketQualityAfter5Hours() throws Exception {

		List<Ticket_296> tkts = ticketRepo.findAllByStatuscycleId("sfarm_cloud_env_10");
		for (Ticket_296 tkt : tkts) {
			long difference_In_Time = new Date().getTime() - tkt.getUpdateDateTime().getTime();
			long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
			if (difference_In_Hours >= 1) {

				tkt.setTicketQualityRate("5");
				tkt.setTicketQualityComments("Posted By System After 1 Hour of Closing Ticket");
				ticketRepo.save(tkt);
			}
		}
	}

	@Transactional
	public synchronized void updateTeamsMsgToDatabase() throws Exception {

		AutoGenarationCode lastNumberObj = autoGenerationRepo.getLastTicketNumber("ChatHistory_299");
		int chatNumber = lastNumberObj.getAutoCodeNo();

		Ticket_296 tkt = ticketRepo.findAllByChatGroupId("19:1d13e45f1c94414cb73baee6c68e27d0@thread.v2");
		System.out.println(tkt);

		log.info("Current Time = {}", dateFormat.format(new Date()));

		final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
				.clientId("dc3cba5c-d9f7-4a84-b6f6-f51d07a20480").username("admin@kgmip.onmicrosoft.com")
				.password("Kgm@123$").build();
		final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
				usernamePasswordCredential);
		System.out.println("hello world");
	
		
        // String graphUrl="https://graph.microsoft.com/v1.0/users/admin@kgmip.onmicrosoft.com/contacts";
		// URL graphUrl = new URL("https://graph.microsoft.com/v1.0/me/");
		
		// String token = tokenCredentialAuthProvider.getAuthorizationTokenAsync(new URL("https://graph.microsoft.com/v1.0/me/")).get();
		
		// System.out.println(token);
	
		
		

		final GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
				.authenticationProvider(tokenCredentialAuthProvider).buildClient();
		System.out.println(graphClient.getServiceRoot());


		ChatMessageCollectionPage messages = graphClient.chats("19:1d13e45f1c94414cb73baee6c68e27d0@thread.v2").messages().buildRequest().top(2).get();
		

		// System.out.println(messages.getCurrentPage().get(0));

		List<ChatMessage> mm = messages.getCurrentPage();

		for (ChatMessage message : mm) {

			ChatHistory_299 ch = new ChatHistory_299();
			System.out.println("Id: " + message.id);
			System.out.println("CreatedTime: " + message.createdDateTime);
			DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

			if (message.body.content != null && message.attachments.size() == 0) {
				String textdata = message.body.content;
				String fromuser = message.from.user.displayName;
				String messgeId = message.id;
				String date = message.createdDateTime.format(customFormatter);
				String actualmessage = textdata.replaceAll("<[^>]*>", "");
				System.out.println(actualmessage);

				ch.setId(String.valueOf(chatNumber));
				ch.setMessage(actualmessage);
				ch.setReceiverId(actualmessage);
				ch.setSenderId(actualmessage);
				ch.setTeamsmessageId(actualmessage);

				String chatId = String.valueOf(chatNumber);

				jdbcTemplate.update(
						"insert into chathistory_299 (id, message, senderid, teamsmessageid,createtime) VALUES ("
								+ chatId + ",'" + actualmessage + "','" + fromuser + "','" + messgeId + "','" + date
								+ "')");
				jdbcTemplate.update("insert into ticket_296__chathistory_299 (ticket_296id, chathistory_299id) VALUES ("
						+ tkt.getId() + "," + chatId + ")");

				chatNumber = chatNumber + 1;

			} 
				  else {
				  
				  List<ChatMessageAttachment> chatMsgAttachList=message.attachments;
				  
				  for (ChatMessageAttachment cmA : chatMsgAttachList) {
				  
					  System.out.println(cmA);
				  //cmA.contentUrl= "https://kgmip-my.sharepoint.com/:x:/g/personal/husenaiah_g_kgmip_onmicrosoft_com/EZgbvc5oTghIglTxP5X2fFAB7lQZZJQGmQFMvTBbuVKsmw?login_hint=admin%40kgmip.onmicrosoft.com&ct=1654852547269&wdOrigin=OFFICECOM-WEB.MAIN.OTHER&cid=65b2135c-9b23-41ab-a221-ef07d83d0ecf";
				  
				  String downloadLink=
				  "https://135.181.202.86:12002/kagami-generated_Srinivasa_Live/dms/downloadDocument?docId=1654509082354";
				  
				  // https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft Teams Chat Files/Screenshot from 2022-06-05 22-28-36.png
				  //https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png
				  
				  File packageFile = new
				  File("/home/husenaiah/Downloads/avadhootTemplates/employee.png");
				  
				  
					/*
					 * try (BufferedInputStream in = new BufferedInputStream(new URL(
					 * "https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png"
					 * ).openStream()); FileOutputStream fileOutputStream = new
					 * FileOutputStream("")) { byte dataBuffer[] = new byte[1024]; int bytesRead;
					 * while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					 * fileOutputStream.write(dataBuffer, 0, bytesRead); }
					 * uploadDocument(dataBuffer, "xlsx", cmA.name); } catch (IOException e) {
					 * 
					 * log.info("Current Time = {}", e); }
					 */
				  
				  
				  
				 // FileUtils.copyURLToFile(new URL("https://kgmip-my.sharepoint.com/personal/husenaiah_g_kgmip_onmicrosoft_com/Documents/Microsoft%20Teams%20Chat%20Files/Screenshot%20from%202022-06-05%2022-28-36.png"), packageFile);
				  
				//  FileInputStream fl = new FileInputStream(packageFile);
				  
				  // Now creating byte array of same length as file 
				//  byte[] arr = new byte[(int) packageFile.length()];
				 // fl.read(arr); 
				//  fl.close();
				  
				
				  
				//  Random rd = new Random(); byte[] arrtest = new byte[7]; rd.nextBytes(arr);
				 // System.out.println(arr); 
				//  uploadDocument(arr, "xlsx", cmA.name);
				  
				
				  
				 
				  }
				  
				  }
				 
			// tkt.getChatHistory_299().add(ch);

		}

		lastNumberObj.setAutoCodeNo(chatNumber);
		autoGenerationRepo.save(lastNumberObj);

		// ticketRepo.save(tkt);

		System.out.println(mm);

	}

	public void uploadDocument(byte[] fileContent, String filetype, String fileName) throws Exception {
		long timeStamp = new Date().getTime();
		String docId = String.valueOf(timeStamp);
		log.info("file uploaded with name" + fileName);

		if (fileContent != null) {
			DocumentModel docmodel = new DocumentModel(fileName, filetype, fileContent, docId);
			documentservicemodel.save(docmodel);

			File file = new File("");

			FileInputStream fl = new FileInputStream(file);

			// Now creating byte array of same length as file
			byte[] arr = new byte[(int) file.length()];
			fl.read(arr);

			// lastly closing an instance of file input stream
			// to avoid memory leakage
			fl.close();
		}

	}

}