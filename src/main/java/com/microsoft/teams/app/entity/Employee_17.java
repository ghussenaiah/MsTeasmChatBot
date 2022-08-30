package com.microsoft.teams.app.entity;

import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;
import java.io.File;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Lob;
import org.hibernate.annotations.Cascade;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id", name = "pk_employee_17id"), name = "employee_17")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee_17 {

      
        @Column(nullable = false, name = "id")
        @JsonProperty("id")
        private String id;
        
        @Column(name = "status")
        @JsonProperty("status")
        private String status;
   
        @Column(nullable = false, name = "empcode")
        @JsonProperty("empCode")
        private String empCode;
     
        @Column(nullable = false, name = "firstname")
        @JsonProperty("firstName")
        private String firstName;
       
        @Column(name = "lastname")
        @JsonProperty("lastName")
        private String lastName;
        
        @Column(nullable = false, name = "email")
        @JsonProperty("email")
        private String email;
        
        @Column(name = "contact")
        @JsonProperty("contact")
        private String contact;
        
        @Column(name = "hdfcaccountnumber")
        @JsonProperty("hdfcAccountNumber")
        private String hdfcAccountNumber;
        
        @Column(name = "vehicleno")
        @JsonProperty("vehicleNo")
        private String vehicleNo;
        
        @Column(name = "digsign")
        @JsonProperty("digSign")
        private String digSign;

        @Column(name = "profilepic")
        @JsonProperty("profilePic")
        private String profilePic;
        
        @Column(name = "teamsid")
        @JsonProperty("teamsId")
        private String teamsId;

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
        public String getEmpCode() {
                return empCode;
        }

        @JsonIgnore
        public void setEmpCode(String empCode) {
                this.empCode = empCode;
        }

        @JsonIgnore
        public String getFirstName() {
                return firstName;
        }

        @JsonIgnore
        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        @JsonIgnore
        public String getLastName() {
                return lastName;
        }

        @JsonIgnore
        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        @JsonIgnore
        public String getEmail() {
                return email;
        }

        @JsonIgnore
        public void setEmail(String email) {
                this.email = email;
        }

        @JsonIgnore
        public String getContact() {
                return contact;
        }

        @JsonIgnore
        public void setContact(String contact) {
                this.contact = contact;
        }

        @JsonIgnore
        public String getHdfcAccountNumber() {
                return hdfcAccountNumber;
        }

        @JsonIgnore
        public void setHdfcAccountNumber(String hdfcAccountNumber) {
                this.hdfcAccountNumber = hdfcAccountNumber;
        }

        @JsonIgnore
        public String getVehicleNo() {
                return vehicleNo;
        }

        @JsonIgnore
        public void setVehicleNo(String vehicleNo) {
                this.vehicleNo = vehicleNo;
        }

        @JsonIgnore
        public String getDigSign() {
                return digSign;
        }
        @JsonIgnore
        public void setDigSign(String digSign) {
                this.digSign = digSign;
        }

        @JsonIgnore
        public String getProfilePic() {
                return profilePic;
        }

        @JsonIgnore
        public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
        }

        @JsonIgnore
        public String getTeamsId() {
                return teamsId;
        }

        @JsonIgnore
        public void setTeamsId(String teamsId) {
                this.teamsId = teamsId;
        }

        @JsonIgnore
        public static Employee_17 loadDefaultValues(String offset) {
                Employee_17 entity = new Employee_17();
                entity.setStatus("");
                entity.setEmpCode("");
                entity.setFirstName("");
                entity.setLastName("");
                entity.setEmail("");
                entity.setContact("");
                entity.setHdfcAccountNumber("");
                entity.setVehicleNo("");
                entity.setTeamsId("");
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
                Employee_17 entity = (Employee_17) object;
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
                String logResult = "Employee_17[id = " + id + ", status = " + status + ", empCode = " + empCode
                                + ", firstName = " + firstName + ", lastName = " + lastName + ", email = " + email + ", contact = "
                                + contact + ", hdfcAccountNumber = " + hdfcAccountNumber + ", vehicleNo = " + vehicleNo + ", digSign = "
                                + digSign + ", profilePic = " + profilePic + ", teamsId = " + teamsId + ", ]";
                return logResult;
        }
}



































