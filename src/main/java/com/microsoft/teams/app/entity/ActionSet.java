package com.microsoft.teams.app.entity;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ActionSet {

	private String type;
	private String title;
	private String verb;
	private String id;
	private String userIds;
	private ActionData data;
	private Actionfallback fallback;
	private String method;
	private String url;
	private String extraData;

	/*
	 * {\n" + " \"type\": \"Action.Execute\",\n" +
	 * "      \"title\": \"CLOSE TICKET\",\n" +
	 * "      \"verb\": \"personalDetailsFormSubmit\",\n" +
	 * "      \"id\": \"surveyReplyYes\",\n" + "      \"userIds\": \"\" ,\n" +
	 * "      \"data\":{\n" + "        \"key1\": true,\n" +
	 * "        \"key2\":\"okay\"\n" + "      },\n" + "       \"fallback\": {\n" +
	 * "        \"type\": \"Action.Submit\",\n" +
	 * "        \"title\": \"CLOSE TICKET\"\n" + "      }  \n" + "    },\n"
	 */

}
