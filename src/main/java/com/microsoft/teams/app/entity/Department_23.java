package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "department_23")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department_23 {

	@Id
	@Column(nullable = false, name = "id")
	@JsonProperty("id")
	private String id;

	@Column(nullable = false, name = "deptcode")
	@JsonProperty("deptCode")
	private String deptCode;

	@Column(nullable = false, name = "deptname")
	@JsonProperty("deptName")
	private String deptName;

	@JsonIgnore
	public String getId() {
		return id;
	}

	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public String getDeptCode() {
		return deptCode;
	}

	@JsonIgnore
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@JsonIgnore
	public String getDeptName() {
		return deptName;
	}

	@JsonIgnore
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@JsonIgnore
	public static Department_23 loadDefaultValues(String offset) {
		Department_23 entity = new Department_23();
		entity.setDeptCode("");
		entity.setDeptName("");
		return entity;
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
		Department_23 entity = (Department_23) object;
		if (entity == null)
			return false;
		if ((entity.getId() == null && this.getId() == null)) {
			return true;
		}
		if ((entity.getId() != null && this.getId() != null && entity.getId().equals(this.getId()))) {
			return true;
		} else
			return false;
	}

	@Override
	public String toString() {
		String logResult = "Department_23[id = " + id + ", deptCode = " + deptCode + ", deptName = " + deptName + ", ]";
		return logResult;
	}
}





