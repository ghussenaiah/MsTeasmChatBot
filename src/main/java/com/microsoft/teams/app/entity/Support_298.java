package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class Support_298 {

	
	@Column(nullable = false, name = "id")
	@Id
	@JsonProperty("id")
	private String id;

	@Column(name = "supporttype")
	@JsonProperty("supportType")
	private String supportType;

	@Column(name = "departmentid")
	@JsonProperty("departmentid")
	private String departmentid;

	// private Department_23 Department_23;

}
