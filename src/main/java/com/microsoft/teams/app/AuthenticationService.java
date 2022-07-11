package com.microsoft.teams.app;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;


public class AuthenticationService {
  
	private static GraphServiceClient<Request> graphClient = null;

	public static GraphServiceClient<Request> getInstance() {
		if (graphClient == null) {
			final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
					.clientId("bccaebb1-43bd-49fa-aa3e-6e8b48037100").username("admin@kgmerp.onmicrosoft.com")
					.password("Kgm@123$").build();
			final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
					usernamePasswordCredential);

			graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider)
					.buildClient();
		}
		return graphClient;
	}
	
	
	@Scheduled(cron = "*/50 * * * *")
	public void currentTime() throws Exception {
		graphClient = null;
	}
	
}
