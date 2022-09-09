package com.microsoft.teams.app;

import org.springframework.scheduling.annotation.Scheduled;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

public class AuthenticationService {

	private static GraphServiceClient<Request> graphClient = null;

	/*
	 * public static GraphServiceClient<Request> getInstance() {
	 * if (graphClient == null) {
	 * final ClientSecretCredential clientSecretCredential = new
	 * ClientSecretCredentialBuilder().clientId(
	 * "096d823c-fe93-4fd5-b30e-be4f5b4ee461")
	 * .clientSecret("").tenantId("").build();
	 * 
	 * final TokenCredentialAuthProvider tokenCredentialAuthProvider = new
	 * TokenCredentialAuthProvider(
	 * clientSecretCredential);
	 * 
	 * graphClient = GraphServiceClient.builder().authenticationProvider(
	 * tokenCredentialAuthProvider)
	 * .buildClient();
	 * }
	 * return graphClient;
	 * }
	 */

	public static GraphServiceClient<Request> getInstance() {
		if (graphClient == null) {
			final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()

					.clientId("096d823c-fe93-4fd5-b30e-be4f5b4ee461").username(
							"sf_helpdesk@srinivasa.co")
					.password("Jhills@45").build();
			final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
					usernamePasswordCredential);

			graphClient = GraphServiceClient.builder().authenticationProvider(
					tokenCredentialAuthProvider).buildClient();
		}
		return graphClient;
	}

	/*
	 * working code for username and pasword
	 * public static GraphServiceClient<Request> getInstance() { if (graphClient ==
	 * null) { final UsernamePasswordCredential usernamePasswordCredential = new
	 * UsernamePasswordCredentialBuilder()
	 * .clientId("bccaebb1-43bd-49fa-aa3e-6e8b48037100").username(
	 * "admin@kgmerp.onmicrosoft.com") .password("Kgm@123$").build(); final
	 * TokenCredentialAuthProvider tokenCredentialAuthProvider = new
	 * TokenCredentialAuthProvider( usernamePasswordCredential);
	 * 
	 * graphClient = GraphServiceClient.builder().authenticationProvider(
	 * tokenCredentialAuthProvider) .buildClient(); } return graphClient; }
	 */

	@Scheduled(cron = "*/50 * * * *")
	public void currentTime() throws Exception {
		graphClient = null;
	}

}
