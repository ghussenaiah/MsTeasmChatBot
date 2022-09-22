


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
@Table(name = "supportdepartment_311")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportDepartment_311 {

	@Id
	@Column(nullable = false, name = "id")
	@JsonProperty("id")
	private String id;


	@Column(name = "deptname")
	@JsonProperty("deptName")
	private String deptName;
	@Column(name = "deptcode")
	@JsonProperty("deptCode")
	private String deptCode;

	@JsonIgnore
	public String getId() {
	        return id;
	}

	@JsonIgnore
	public void setId(String id) {
	        this.id = id;
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
	public String getDeptCode() {
	        return deptCode;
	}
	
	 @JsonIgnore
     public void setDeptCode(String deptCode) {
             this.deptCode = deptCode;
     }

    

     @JsonIgnore
     public static SupportDepartment_311 loadDefaultValues(String offset) {
             SupportDepartment_311 entity = new SupportDepartment_311();
             entity.setDeptName("");
             entity.setDeptCode("");
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
             SupportDepartment_311 entity = (SupportDepartment_311) object;
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
             String logResult = "SupportDepartment_311[id = " + id + ", deptName = " + deptName + ", deptCode = " + deptCode
                             + ", ]";
             return logResult;
     }


}







