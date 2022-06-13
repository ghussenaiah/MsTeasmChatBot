package com.microsoft.teams.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class AdaptiveCardsRequest {

	private String $schema;
	private String type;
	private String version;
	private List<Container> body;
	private List<ActionSet> actions;

  public AdaptiveCardsRequest() {
		this.set$schema("http://adaptivecards.io/schemas/adaptive-card.json");
		this.type = "AdaptiveCard";
		this.version = "1.0";
	}

	public String get$schema() {
		return $schema;
	}

	public void set$schema(String $schema) {
		this.$schema = $schema;
	}


}
