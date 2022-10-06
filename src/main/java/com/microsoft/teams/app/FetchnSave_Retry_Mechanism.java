package com.microsoft.teams.app;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;

import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.requests.ChatMessageCollectionPage;

import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.DriveItemCollectionRequestBuilder;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import com.microsoft.teams.app.entity.AutoGenarationCode;
import com.microsoft.teams.app.entity.ChatHistory_299;
import com.microsoft.teams.app.entity.Ticket_296;
import com.microsoft.teams.app.repository.AutoGenerationRepo;
import com.microsoft.teams.app.repository.TicketRepo;

import okhttp3.Request;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.Instant;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import com.microsoft.teams.app.entity.DocumentModel;
import com.microsoft.teams.app.service.impl.DocumentServiceModel;
import org.apache.commons.lang3.StringUtils;
import com.microsoft.teams.app.repository.UserRepository;
import com.microsoft.teams.app.entity.User;

@Component
public class FetchnSave_Retry_Mechanism {

	private static final Logger log = LoggerFactory.getLogger(FetchnSave_Retry_Mechanism.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	JdbcTemplate jdbcTemplate;

	// @Autowired
	// AuthenticationService authService;

	@Autowired
	AutoGenerationRepo autoGenerationRepo;

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	DocumentServiceModel documentservicemodel;

	@Autowired
	UserRepository userRepo;

	
	/*
	 * @PostConstruct public void testcodeHere() {
	 * 
	 * // List<User> userList = userRepo.findAllByDepartmentId("sfarm_cloud_env_9");
	 * 
	 * List<User> userList = userRepo.findAll("sfarm_cloud_env_9");
	 * 
	 * //userRepo.findAllByDepartmentIdAndRoleName(null)
	 * 
	 * for (int i = 0; i < userList.size(); i++) {
	 * 
	 * System.out.println(userList.get(0).getTeamsId());
	 * 
	 * } }
	 */
	 
	// * * * * * every one minute
	// */1 * * * * * every second
	// * * * * * *

	// @Scheduled(cron = "*/1 * * * * *")

	// @Scheduled(cron = "0 * * ? * *")
	//@Scheduled(cron = "*/1 * * * * *")
	
	
	//
	//@PostConstruct
	@Scheduled(cron = "*/1 * * * * *")
	public void currentTime() throws Exception {
		// log.info("Current Time = {}", dateFormat.format(new Date()));
		updateTeamsMsgToDatabase();
		/*
		 * System.out.println("statement started"); Thread newThread = new Thread(() ->
		 * { factorial(); }); newThread.start();
		 * System.out.println("statement reached");
		 */
		// updateTicketQualityAfter5Hours();
	}
	
	
	public synchronized void updateTicketQualityAfter5Hours() throws Exception {

		List<Ticket_296> tkts = ticketRepo.findAllByStatuscycleId("27");
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

		URL resource = getClass().getClassLoader().getResource("srinivaslogo.png");

		System.out.println(resource);
		resource.getFile();
		int chatNumber=-1;
		int holdforUpdate=-1;

		AutoGenarationCode lastNumberObj = autoGenerationRepo.getLastTicketNumber("ChatHistory_299");
		if(lastNumberObj!=null) {
			chatNumber = lastNumberObj.getAutoCodeNo();
			holdforUpdate = chatNumber;
		}
	

		List<Ticket_296> tkts = ticketRepo.findAllByStatuscycleId("27");

		// Ticket_296 tkt =
		// ticketRepo.findAllByChatGroupId("19:1d13e45f1c94414cb73baee6c68e27d0@thread.v2");
		System.out.println(tkts);

		log.info("Current Time = {}", dateFormat.format(new Date()));

		// String
		// graphUrl="https://graph.microsoft.com/v1.0/users/admin@kgmip.onmicrosoft.com/contacts";
		// URL graphUrl = new URL("https://graph.microsoft.com/v1.0/me/");

		// String token = tokenCredentialAuthProvider.getAuthorizationTokenAsync(new
		// URL("https://graph.microsoft.com/v1.0/me/")).get();

		// System.out.println(token);

		final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();

		System.out.println(graphClient.getServiceRoot());

		try {

			for (Ticket_296 tkt : tkts) {

				// URL url = getClass().getResource("/drawIcon.png");

				// graphClient.groups(tkt.getChatGroupId()).addFavorite().buildRequest().post();

				// ChatMessageCollectionPage messages =
				// graphClient.chats("19:1d13e45f1c94414cb73baee6c68e27d0@thread.v2").messages().buildRequest().top(2).get();

				if (tkt.getChatGroupId() != null) {
					ChatMessageCollectionPage messages = graphClient.chats(tkt.getChatGroupId()).messages()
							.buildRequest().top(50).get();// 50 is the maximum limit for graph api
					OffsetDateTime erplastmsgupdate = tkt.getUpdateDateTime().toInstant().atOffset(ZoneOffset.UTC);

					List<ChatMessage> mm = messages.getCurrentPage();

					Instant instant = null;
					Boolean lastmessageTime = true;

					try {
						for (ChatMessage message : mm) {

							if (message.createdDateTime.compareTo(erplastmsgupdate) > 0) {

								ChatHistory_299 ch = new ChatHistory_299();
								System.out.println("Id: " + message.id);
								System.out.println("CreatedTime: " + message.createdDateTime);
								DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

								if (message.body.content != null && message.attachments.size() == 0
										&& message.from != null) {
									String textdata = message.body.content;
									String fromuser = message.from.user.displayName;
									String messgeId = message.id;
									if (lastmessageTime) {
										instant = message.createdDateTime.toInstant();
										lastmessageTime = false;
									}

									String date = message.createdDateTime.format(customFormatter);
									// Date messageDate = (Date) customFormatter.parse(date);
									String actualmessage = textdata.replaceAll("<[^>]*>", "");
									System.out.println(actualmessage);

									System.out.println();
									// System.out.println(erplastmsgupdate.compareTo(message.createdDateTime));
									// messageDate.after();
									ch.setId(String.valueOf(chatNumber));
									ch.setMessage(actualmessage);
									ch.setReceiverId(actualmessage);
									ch.setSenderId(actualmessage);
									ch.setTeamsmessageId(actualmessage);

									// tkt.getUpdateDateTime().

									String chatId = String.valueOf(chatNumber);

									chatNumber = chatNumber + 1;

									updateRecordToDatabase(chatId, actualmessage, fromuser, messgeId, date, tkt.getId(),
											null, chatNumber, lastNumberObj);

								} else {

									List<ChatMessageAttachment> chatMsgAttachList = message.attachments;

									for (ChatMessageAttachment cmA : chatMsgAttachList) {

										if (!cmA.contentType
												.equalsIgnoreCase("application/vnd.microsoft.card.adaptive")) {

											Map<String, String> fileMap = DownloadFileUsingUrl(graphClient, cmA);
											System.out.println(cmA);
											String messgeId = message.id;
											String chatId = String.valueOf(chatNumber);
											String fromuser = message.from.user.displayName;
											if (lastmessageTime) {
												instant = message.createdDateTime.toInstant();
												lastmessageTime = false;
											}

											String date = message.createdDateTime.format(customFormatter);
											chatNumber = chatNumber + 1;
											updateRecordToDatabase(chatId, fileMap.get("fileName"), fromuser, messgeId,
													date, tkt.getId(), fileMap.get("fileId"), chatNumber,
													lastNumberObj);

										}

									}

								}

							}

							// tkt.getChatHistory_299().add(ch);

						}
					} catch (Exception e) {

						log.error("error while reading the chat message ,", e.fillInStackTrace());
						if (instant != null) {
							tkt.setUpdateDateTime(Date.from(instant));
							ticketRepo.save(tkt);
						}
					}
					if (instant != null) {
						tkt.setUpdateDateTime(Date.from(instant));
						ticketRepo.save(tkt);
					}

				}
			}
		} catch (Exception e) {
			log.error("error while reading the tickets for chat reading ,", e.fillInStackTrace());
			if (holdforUpdate < chatNumber) {
				lastNumberObj.setAutoCodeNo(chatNumber);
				autoGenerationRepo.save(lastNumberObj);
			}
		}
		if (holdforUpdate < chatNumber) {
			lastNumberObj.setAutoCodeNo(chatNumber);
			autoGenerationRepo.save(lastNumberObj);
		}

		// System.out.println(mm);

	}

	public void updateRecordToDatabase(String chatId, String actualmessage, String fromuser, String messgeId,
			String date, String tktId, String fileId, int chatNumber, AutoGenarationCode lastNumberObj) {
		try {
			if (fileId != null) {

				jdbcTemplate.update(
						"insert into chathistory_299 (id, message, senderid, teamsmessageid,createtime,file) VALUES ("
								+ chatId + ",'" + actualmessage + "','" + fromuser + "','" + messgeId + "','" + date
								+ "','" + fileId + "')");
			} else {

				jdbcTemplate.update(
						"insert into chathistory_299 (id, message, senderid, teamsmessageid,createtime) VALUES ("
								+ chatId + ",'" + actualmessage + "','" + fromuser + "','" + messgeId + "','" + date
								+ "')");
			}

			jdbcTemplate.update("insert into ticket_296__chathistory_299 (ticket_296id, chathistory_299id) VALUES ("
					+ tktId + "," + chatId + ")");
		} catch (Exception e) {

			lastNumberObj.setAutoCodeNo(chatNumber);
			autoGenerationRepo.save(lastNumberObj);

			log.info("Exception while the chat data = {}", e.fillInStackTrace());
		}
	}

	public Map<String, String> DownloadFileUsingUrl(GraphServiceClient<Request> graphClient,
			ChatMessageAttachment chatMsgAttachment)
			throws Exception {

		// pending in this method is
		
		
		
		Map<String, String> fileMap = new HashMap<>();

		// drive id
		String fileId = chatMsgAttachment.id;
		String ImportedfileId = null;
		// String fileextension=chatMsgAttachment.name;
		String fileExtension = chatMsgAttachment.name.substring(chatMsgAttachment.name.lastIndexOf('.') + 1);

		Drive drive = graphClient.me().drive().buildRequest().get();

		System.out.println(drive.id);
		// folder id
		DriveItemCollectionPage dicp = graphClient.me().drives(drive.id).root().children().buildRequest().get();
		//dicp.getCount();
		//dicp.getNextPage().
		

		System.out.println(dicp);
		
		//dicp.getCurrentPage().get(0);
		//dicp.getNextPage()
		
		

		DriveItem folderId = dicp.getCurrentPage().get(0);

		
		System.out.println(folderId);

		DriveItemCollectionPage filesList = graphClient.me().drives(drive.id).items(folderId.id).children()
				.buildRequest().get();

		List<DriveItem> driveItem = filesList.getCurrentPage();

		DriveItem requiredDriveItem = driveItem.stream()
				.filter(e -> fileId.equalsIgnoreCase(StringUtils.substringBetween(e.eTag, "{", "}"))).findAny()
				.orElse(null);

		if (requiredDriveItem != null) {

			String linkUrl = requiredDriveItem.additionalDataManager().get("@microsoft.graph.downloadUrl").toString()
					.replaceAll("^\"|\"$", "");

			URL crunchifyRobotsURL = new URL(linkUrl);

			InputStream is = null;

			try {
				is = crunchifyRobotsURL.openStream();
				byte[] imageBytes = IOUtils.toByteArray(is);

				ImportedfileId = uploadDocument(imageBytes, fileExtension, chatMsgAttachment.name);
				fileMap.put("fileName", chatMsgAttachment.name);
				fileMap.put("fileId", ImportedfileId);
				fileMap.put("fileext", fileExtension);
			} catch (IOException e) {
				System.err.printf("Failed while reading bytes from %s: %s", crunchifyRobotsURL.toExternalForm(),
						e.getMessage());
				e.printStackTrace();
				// Perform any other exception handling that's appropriate.
			} finally {
				if (is != null) {
					is.close();
				}
			}
			/*
			 * Below is working code ReadableByteChannel crunchifyByteChannel =
			 * Channels.newChannel(crunchifyRobotsURL.openStream());
			 * 
			 * FileOutputStream crunchifyOutputStream = new
			 * FileOutputStream("/home/husen/Downloads/filedownload.pdf");
			 * 
			 * crunchifyOutputStream.getChannel().transferFrom(crunchifyByteChannel, 0,
			 * Long.MAX_VALUE);
			 * 
			 * crunchifyOutputStream.close();
			 * 
			 * crunchifyByteChannel.close();
			 */
		}
		return fileMap;

	}

	public String uploadDocument(byte[] fileContent, String filetype, String fileName) throws Exception {
		long timeStamp = new Date().getTime();
		String docId = String.valueOf(timeStamp);

		if (fileContent != null) {
			DocumentModel docmodel = new DocumentModel(fileName, filetype, fileContent, docId);
			documentservicemodel.save(docmodel);
			log.info("file uploaded with name" + fileName);

			// File file = new File("");

			// FileInputStream fl = new FileInputStream(file);

			// Now creating byte array of same length as file
			// byte[] arr = new byte[(int) file.length()];

			// fl.read(arr);

			// lastly closing an instance of file input stream
			// to avoid memory leakage
			// fl.close();
		}
		return docId;

	}

}