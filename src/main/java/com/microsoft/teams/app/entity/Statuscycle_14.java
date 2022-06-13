package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class Statuscycle_14 {


	@Column(nullable = false, name = "id")
	@Id
	@JsonProperty("id")
	private String id;

	private String statusName;

	private String processListId;

	private String colorId;
}
