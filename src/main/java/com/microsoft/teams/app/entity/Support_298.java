package com.microsoft.teams.app.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.ForeignKey;

import lombok.Data;


@Entity
public class Support_298 {

	
	  @Id
      @Column(nullable = false, name = "id")
      @JsonProperty("id")
      private String id;
      @Column(name = "supporttype")
      @JsonProperty("supportType")
      private String supportType;
      @Column(name = "fbrequired")
      @JsonProperty("fbrequired")
      private String fbrequired;
      
      @Column(name = "supportdepartmentid")
      @JsonProperty("supportdepartmentid")
      private String supportdepartmentid;
      
      //@ManyToOne(fetch = FetchType.EAGER)
      //@JoinColumn(referencedColumnName = "id", name = "supportdepartmentid", updatable = false, foreignKey = @ForeignKey(name = "FK_Support_298SupportDepartment_311"), insertable = false)
      //@JsonProperty("SupportDepartment_311")
    
   //   private SupportDepartment_311 SupportDepartment_311;
      
      
      @JsonSerialize(as = java.util.LinkedHashSet.class)
      @JsonDeserialize(as = java.util.LinkedHashSet.class)
      @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "id", name = "support_298id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "userid"), name = "support_298__user")
      @JsonProperty("User")
      private Set<User> User = new LinkedHashSet<>();

     
      public String getId() {
              return id;
      }

    
      public void setId(String id) {
              this.id = id;
      }


      public String getSupportType() {
              return supportType;
      }
      
      
      public Set<User> getUser() {
              return User;
      }

    
      public void setUser(Set<User> User) {
              this.User = User;
      }

      
   


	// private Department_23 Department_23;

}
