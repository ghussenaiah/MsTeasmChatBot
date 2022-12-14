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
public class Container {

	/*
	 * "type": "TextBlock", "size": "Medium", "weight": "Bolder", "text": "${title}"
	 */

	private String id;
	private String type;
	private String style;
	
	private String gridStyle;
	private Boolean firstRowAsHeaders;
	
	private Boolean isMultiSelect;
	private Boolean isMultiline;
	private Boolean bleed;
	private String size;
	private String value;
	private String weight;
	private String placeholder;
	private String maxLength;
	private String text;
	private List<Column> columns;
	private List<Rows> rows;
	private List<Choices> choices;
	private List<Item> items;
	private Boolean wrap;
	private String url;
	private String altText;
	private String color;
	private Boolean isRequired;
	private String errorMessage;
	private int maxLines;
	
	

	

}
