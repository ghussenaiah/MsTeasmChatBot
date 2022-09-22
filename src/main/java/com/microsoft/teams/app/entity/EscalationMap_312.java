package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.ForeignKey;

@Data
@Entity
@Table(name = "escalationmap_312")
@JsonIgnoreProperties(ignoreUnknown = true)

public class EscalationMap_312 {


     @Column(nullable = false, name = "id")
     @JsonProperty("id")
     @Id
     private String id;
     @Column(name = "status")
     @JsonProperty("status")
     private String status;
     @Column(name = "nextroles")
     @JsonProperty("nextRoles")
     private String nextRoles;
     @Column(name = "supportid")
     @JsonProperty("supportid")
     private String supportid;
     @Column(name = "userid")
     @JsonProperty("userId")
     private String userId;
		/*
		 * @ManyToOne(fetch = FetchType.EAGER)
		 * 
		 * @JoinColumn(referencedColumnName = "id", name = "supportid", updatable =
		 * false, foreignKey = @ForeignKey(name = "FK_EscalationMap_312Support_298"),
		 * insertable = false)
		 * 
		 * @JsonProperty("Support_298")
		 */
   
     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(referencedColumnName = "id", name = "userid", updatable = false, foreignKey = @ForeignKey(name = "FK_EscalationMap_312User"), insertable = false)
     @JsonProperty("User")
     private User User;

     @JsonIgnore
     public String getId() {
             return id;
     }

     @JsonIgnore
     public void setId(String id) {
             this.id = id;
     }

     @JsonIgnore
     public String getStatus() {
             return status;
     }

     @JsonIgnore
     public void setStatus(String status) {
             this.status = status;
     }

     @JsonIgnore
     public String getNextRoles() {
             return nextRoles;
     }

     @JsonIgnore
     public void setNextRoles(String nextRoles) {
             this.nextRoles = nextRoles;
     }

		/*
		 * @JsonIgnore public String getSupportId() { return supportid; }
		 * 
		 * @JsonIgnore public void setSupportId(String supportId) { this.supportid =
		 * supportId; }
		 */

     @JsonIgnore
     public String getUserId() {
             return userId;
     }

     @JsonIgnore
     public void setUserId(String userId) {
             this.userId = userId;
     }

		/*
		 * @JsonIgnore public Support_298 getSupport_298() { return Support_298; }
		 * 
		 * @JsonIgnore public void setSupport_298(Support_298 Support_298) {
		 * this.Support_298 = Support_298; }
		 */

     @JsonIgnore
     public User getUser() {
             return User;
     }
     
     @JsonIgnore
     public void setUser(User User) {
             this.User = User;
     }

 
     @JsonIgnore
     public static EscalationMap_312 loadDefaultValues(String offset) {
             EscalationMap_312 entity = new EscalationMap_312();
             entity.setStatus("");
             entity.setNextRoles("");
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
             EscalationMap_312 entity = (EscalationMap_312) object;
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
             String logResult = "EscalationMap_312[id = " + id + ", status = " + status + ", nextRoles = " + nextRoles
                             + ", supportId = " + supportid + ", userId = " + userId + ", ]";
             return logResult;
     }


}
