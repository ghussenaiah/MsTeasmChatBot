package com.microsoft.teams.app.utility;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;

import org.springframework.stereotype.Service;



@Service
public class Utility {
	
	
	 //1  Before live deploy for jar application we have to add https and /botapp context app in application.properties file
	
	 //2  Authentication also need to change
	
	 /*   Bot Related Tasks
	 
	 
	     based on function type need to show feed back form or not 
	 
	  //  username/password ===>    sf_helpdesk@srinivasa.co / Jhills@45
	  * 
	  * 
	  *    sf_helpdesk@srinivasa.co teamsId ==> 5f92b236-28ec-474f-bae4-f9cab9275230
	  *    Help Desk  Appcatlog Id ===========> 4bfef638-9b33-4a3d-8793-b125636d1e0a
	  *    
	  *    ramamohan.rachamsetty@srinivasa.co =>21dbd337-e1f9-4d49-9f72-d5a23ff417bb
	  *    vasu.chitturi@srinivasa.co         =>6e2cd812-7f9f-4cc0-8572-5c48b1b1b4bb
	  *    
	  *    Venkata R Subrahmanyam Batchu      =>dc4b37af-3ca2-4049-989b-069589d57e72
	  *    
	  *    Sudhiranjan                        =>9975030e-1d48-438c-b92d-9f8cef2a6fca
	  *    
	  *    In Srinivas Teams 
	  *    
	  *    Husenaiah.gandham@srinivasa.com   => f6cce08a-5068-4a39-8976-7659bd02d6f7

	  *    Srikanth Deverasetty              => f09df50d-688e-4458-89c5-be35cc1075f2
	  *    
	  *    Ankith                            => 225e6ff0-0cfd-4b0e-9533-4c8082dff228
	 
	 Validations 
	 
	 throwing issues from 
	 1 Ticket #525381 is CLOSED adpative card need to replace with removed done button
	 2 if multiple clicks close and escalte button then need to process one by one
	 3 if members added manually then we need to stop sendin the welcome message // Hello All New chat is created  
	 3 while adding help desk to chat (api fails)
	 4 while creating team (api fails)
	 5 document file iteration when it is uploaded to chat
	 6 
	 
	 */
	
	
	/*Notes
	 * 
	 * Azure portal manifest file need to check 
	 * 
	 * it should not be "signInAudience": "AzureADandPersonalMicrosoftAccount",
	 * New App Register Done => NewHelpdeskApp
	 * Client Id  : 3afa6722-1ab9-4bde-9824-fb727a612eb2 
	 * 
	 * 
	 * 
	 * MicrosoftAppId=096d823c-fe93-4fd5-b30e-be4f5b4ee461                    (it will be bot Id)
	   MicrosoftAppPassword=TO18Q~s3RQK9_Glwg7JLd5.aY6UKxaCLa5RIRaTE          ( it will be secret key) while creating for bot 
	 */
	
	
	/* Improvements asked on Sep 09 2022
	 * 
	 * 
	 * 1 All adaptive cards will be width full screen  ==============================>  done
	 * 
	 * 2 online description should be 200     validation need to be added
	 * 
	 * 3 description should be 500 character  validation need to be added
	 * 
	 * 4 pin hello all message in new chat   ms teams people working on it 
	 * 
	 * 5 open new chat to Go To Ticket           ======================================> done
	 * 
	 * 6 Hai sir need to remove from escalation message   =============================> done
	 * 
	 * 7 when text is wrapping then date should come at top level it should not go down and it should be left side 
	 * 
	 * 8 Description should be below of Issue
	 * 
	 * 9 oneline and description should not be editable again  ========================> done 
	 * 
	 * 10 filter conditions need to be updated for view in runtime for it department only it it people will see it issues 
	 * 
	 * 11 Show feedback form only eligible support types               =>>>>>>>>>>> pending 
	 * 
	 * 12 quality rate order need to display in reverse order         ================> done
	 * 
	 * 
	 * /* Improvements asked on Sep 14 202
	 * 
	 * 1 pls need to remove from lable dont use shortforms                  => done
	 * 
	 * 2 issue description should come with full display after ticket creation  => done
	 * 
	 * 3 icon description changes srinivasformshelpdesk               
	 * 
	 * 4 loading symbol aftrer click afrer every click 
	 * 
	 * 5 Ticklet tle : one                                                  => done
	 * 
	 * 6 Enter text here E caps                                             => done
	 * 
	 * 7 Deatiled Description                                               => done
	 * 
	 * 8 department need to remove from open chat                           => done
	 *  
	 * 9 Hello all need to remve                                            => done
	 * 
	 * 10 Desctipon > issue description need to remove                      => done
	 * 
	 * 11 leave option need to remove 
	 * 
	 * 12 helpdesk need to remove should not apper 
	 * 
	 * 13 default rating 5 should come                                      => done
	 * 
	 * 14 pls -> Please                                                     => done
	 * 
	 * 15 E cap leter                                                       => done
	 * 
	 * 16 Servive rated                                                     => done
	 * 
	 * 17 remarks                                                           => done
	 * 
	 * 18 removal of chats unpi and loading should come for   tech issue 
	 * 
	 * 19 <close> =>  <escalate>
	 * 
	 *
	 * 20 Thanks no need to come for display                                => done
	 * 
	 * 21 background color no need                                          => done
	 * 
	 * 22 helpdesk chat need to remove to clear aftrer clicking on go to chat need to confirmation
	 * 
	 * 
	 * New Schema Changes 
	 * 
	 * 
	 * Escalation Map Table with supportId and EmployeeId 
	 * 
	 * 
	 * Fetching Employees from Support Deportment  
	 * 
	 * Fetching Function Support from Support 
	 * 
	 * Create Ticket 
	 * 
	 * Escalate to User  
	 * 
	 */
	
	
	
	
	
	private static final String ESCALATE_ISSUE_STATUS = "sfarm_cloud_env_11";
	
	
	
	private static final int TOTAL_BITS = 64;
	private static final int EPOCH_BITS = 42;
	private static final int NODE_ID_BITS = 10;
	private static final int SEQUENCE_BITS = 12;

	private static final int maxNodeId = (int)(Math.pow(2, NODE_ID_BITS) - 1);
	private static final int maxSequence = (int)(Math.pow(2, SEQUENCE_BITS) - 1);

	// Custom Epoch (January 1, 2015 Midnight UTC = 2015-01-01T00:00:00Z)
	private static final long CUSTOM_EPOCH = 1420070400000L;

	private final int nodeId;

	private volatile long lastTimestamp = -1L;
	private volatile long sequence = 0L;

	// Create SequenceGenerator with a nodeId
	public Utility(int nodeId) {
		if (nodeId < 0 || nodeId > maxNodeId) {
			throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, maxNodeId));
		}
		this.nodeId = nodeId;
	}

	// Let SequenceGenerator generate a nodeId
	public Utility() {
		this.nodeId = createNodeId();
	}

	public synchronized long nextId() {
		long currentTimestamp = timestamp();

		if (currentTimestamp < lastTimestamp) {
			throw new IllegalStateException("Invalid System Clock!");
		}

		if (currentTimestamp == lastTimestamp) {
			sequence = (sequence + 1) & maxSequence;
			if (sequence == 0) {
				// Sequence Exhausted, wait till next millisecond.
				currentTimestamp = waitNextMillis(currentTimestamp);
			}
		} else {
			// reset sequence to start with zero for the next millisecond
			sequence = 0;
		}

		lastTimestamp = currentTimestamp;

		long id = currentTimestamp << (TOTAL_BITS - EPOCH_BITS);
		id |= (nodeId << (TOTAL_BITS - EPOCH_BITS - NODE_ID_BITS));
		id |= sequence;
		return id;
	}


	// Get current timestamp in milliseconds, adjust for the custom epoch.
	private static long timestamp() {
		return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
	}

	// Block and wait till next millisecond
	private long waitNextMillis(long currentTimestamp) {
		while (currentTimestamp == lastTimestamp) {
			currentTimestamp = timestamp();
		}
		return currentTimestamp;
	}

	private int createNodeId() {
		int nodeId;
		try {
			StringBuilder sb = new StringBuilder();
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				byte[] mac = networkInterface.getHardwareAddress();
				if (mac != null) {
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X", mac[i]));
					}
				}
			}
			nodeId = sb.toString().hashCode();
		} catch (Exception ex) {
			nodeId = (new SecureRandom().nextInt());
		}
		nodeId = nodeId & maxNodeId;
		return nodeId;
	}
	
	 /*
	        server.ssl.key-store=classpath:keystore.p12
			server.ssl.key-store-password=kagami
			server.ssl.keyStoreType = PKCS12
			server.ssl.keyAlias = tomcat
			server.servlet.session.cookie.secure=true
			server.ssl.enabled=true
			server.http2.enabled=true
			
	 */		


}





