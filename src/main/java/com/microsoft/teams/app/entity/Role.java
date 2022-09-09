package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "role")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable {

        @Column(name = "currentroleid")
        @JsonProperty("currentRoleId")
        private String currentRoleId;
        @Column(name = "reportstoroleid")
        @JsonProperty("reportsToRoleId")
        private String reportsToRoleId;
       
        @Column(nullable = false, name = "roleid")
        @JsonProperty("roleId")
        private String roleId;
    
        @Column(nullable = false, name = "rolename")
        @JsonProperty("roleName")
        private String roleName;
     
        @Column(nullable = false, name = "id")
        @Id
        @JsonProperty("id")
        private String id;
        
   

        @JsonIgnore
        public String getCurrentRoleId() {
                return currentRoleId;
        }

        @JsonIgnore
        public void setCurrentRoleId(String currentRoleId) {
                this.currentRoleId = currentRoleId;
        }

        @JsonIgnore
        public String getReportsToRoleId() {
                return reportsToRoleId;
        }

        @JsonIgnore
        public void setReportsToRoleId(String reportsToRoleId) {
                this.reportsToRoleId = reportsToRoleId;
        }

        @JsonIgnore
        public String getRoleId() {
                return roleId;
        }

        @JsonIgnore
        public void setRoleId(String roleId) {
                this.roleId = roleId;
        }
        @JsonIgnore
        public String getRoleName() {
                return roleName;
        }

        @JsonIgnore
        public void setRoleName(String roleName) {
                this.roleName = roleName;
        }

        @JsonIgnore
        public String getId() {
                return id;
        }

        @JsonIgnore
        public void setId(String id) {
                this.id = id;
        }

       
        @JsonIgnore
        public static Role loadDefaultValues(String offset) {
                Role entity = new Role();
                entity.setCurrentRoleId("");
                entity.setRoleId("");
                entity.setRoleName("");
            
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
                Role entity = (Role) object;
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
                String logResult = "Role[currentRoleId = " + currentRoleId + ", reportsToRoleId = " + reportsToRoleId
                                + ", roleId = " + roleId + ", roleName = " + roleName + ", id = " + id + "]";
                return logResult;
        }
}



        


