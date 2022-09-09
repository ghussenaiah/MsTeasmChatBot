package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "autogenarationcode")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoGenarationCode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8392594118966858070L;

	@Column(name = "id")
	@Id
	private Long id;

	@Column(name = "entityid")
	@JsonProperty("entityid")
	private String entityId;

	@NotNull
	@Column(name = "coloumnid")
	@JsonProperty("coloumnId")
	private String coloumnId;

	@Column(name = "autocodeno")
	@JsonProperty("autoCodeNo")
	private int autoCodeNo;

	@Column(name = "type")
	@JsonProperty("type")
	private String type;

	@JsonIgnore
	public String getType() {
		return type;
	}

	@JsonIgnore
	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public String getEntityId() {
		return entityId;
	}

	@JsonIgnore
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@JsonIgnore
	public String getColoumnId() {
		return coloumnId;
	}

	@JsonIgnore
	public void setColoumnId(String coloumnId) {
		this.coloumnId = coloumnId;
	}

	@JsonIgnore
	public int getAutoCodeNo() {
		return autoCodeNo;
	}

	@JsonIgnore
	public void setAutoCodeNo(int autoCodeNo) {
		this.autoCodeNo = autoCodeNo;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (this.getId() != null)
			hashCode = hashCode + 37 + this.getId().hashCode();
		else
			hashCode = hashCode + 37;
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		AutoGenarationCode entity = (AutoGenarationCode) object;
		if (entity == null)
			return false;
		if ((entity.getId() == null && this.getId() == null)) {
			return true;
		}
		if ((entity.getId() != null && this.getId() != null && entity.getId()
				.equals(this.getId()))) {
			return true;
		} else
			return false;
	}
}