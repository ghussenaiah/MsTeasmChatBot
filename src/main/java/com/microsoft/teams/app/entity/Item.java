package com.microsoft.teams.app.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class Item {

	
	
	/*
	 * [ { "type": "Column", "items": [ { "type": "Image", "style": "Person", "url":
	 * "${creator.profileImage}", "size": "Small" } ], "width": "auto" }, { "type":
	 * "Column", "items": [ { "type": "TextBlock", "weight": "Bolder", "text":
	 * "${creator.name}", "wrap": true }, { "type": "TextBlock", "spacing": "None",
	 * "text": "Created {{DATE(${createdUtc},SHORT)}}", "isSubtle": true, "wrap":
	 * true } ], "width": "stretch"
	 */
     
	
	private String type;
	private String style;
	private String url;
	private String size;
	private String weight;
	private String text;
	private Boolean wrap;
	private String spacing;
	private Boolean isSubtle;
	private String id;
	private Boolean isMultiSelect;
	private String value;
	private String color;    // vaue could be Attention
	private String horizontalAlignment;  // value could be Right
	
	private List<Choices> choices;
	
}
