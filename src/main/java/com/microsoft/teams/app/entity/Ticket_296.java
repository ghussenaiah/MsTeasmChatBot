package com.microsoft.teams.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.Lob;


import lombok.Data;

@Data
@Entity
public class Ticket_296 implements Serializable {

        @Column(name = "transactionentityid")
        @JsonProperty("transactionentityid")
        private Long transactionentityId;

        @Column(nullable = false, name = "id")
        @JsonProperty("id")
        @Id
        private String id;

        @Column(name = "status")
        @JsonProperty("status")
        private String status;

        @Column(name = "clstktreplyid")
        @JsonProperty("clstktreplyId")
        private String clstktreplyId;

        @Column(name = "timediff")
        @JsonProperty("timediff")
        private String timediff;

        @Column(name = "nextroles")
        @JsonProperty("nextRoles")
        private String nextRoles;

        @Column(nullable = false, name = "ticketnumber")
        @JsonProperty("ticketNumber")
        private String ticketNumber;

        @Column(nullable = false, name = "tickettitle")
        @JsonProperty("ticketTitle")
        private String ticketTitle;

        @Column(name = "description")
        @JsonProperty("description")
        private String description;

        @Column(nullable = false, name = "employeeteamsid")
        @JsonProperty("employeeTeamsId")
        private String employeeTeamsId;

        @Column(name = "ticketqualityrate")
        @JsonProperty("ticketQualityRate")
        private String ticketQualityRate;

        @Column(name = "ticketqualitycomments")
        // @Lob
        // @Lob
        // @Type(type = "org.hibernate.type.TextType")
        @JsonProperty("ticketQualityComments")
        private String ticketQualityComments;

        @Column(name = "chatgroupid")
        @JsonProperty("chatGroupId")
        private String chatGroupId;

        @Column(name = "priorityid")
        @JsonProperty("priorityId")
        private String priorityId;

        @Column(name = "statuscycleid")
        @JsonProperty("statuscycleId")
        private String statuscycleId;

       
        
        @Column(name = "supportdepartmentid")
        @JsonProperty("supportDepartmentId")
        private String supportDepartmentId;


        @Column(name = "supportid")
        @JsonProperty("supportId")
        private String supportId;
        
        @Column(name = "welmsg")
        @JsonProperty("welmsg")
        private String welmsg;
        
        @Column(name = "issuedetails")
        @Lob
        @JsonProperty("issuedetails")
        private String issuedetails;
        
        @JsonIgnore
        public String getWelmsg() {
                return welmsg;
        }

        @JsonIgnore
        public void setWelmsg(String welmsg) {
                this.welmsg = welmsg;
        }
        
        @JsonIgnore
        public String getIssuedetails() {
                return issuedetails;
        }

        @JsonIgnore
        public void setIssuedetails(String issuedetails) {
                this.issuedetails = issuedetails;
        }





        /*
         * @ManyToOne(fetch = FetchType.EAGER)
         * 
         * @JoinColumn(referencedColumnName = "id", name = "priorityid", updatable =
         * false, foreignKey = @ForeignKey(name = "FK_Ticket_296Priority_297"),
         * insertable = false)
         * 
         * @JsonProperty("Priority_297") private Priority_297 Priority_297;
         * 
         * @ManyToOne(fetch = FetchType.EAGER)
         * 
         * @JoinColumn(referencedColumnName = "id", name = "statuscycleid", updatable =
         * false, foreignKey = @ForeignKey(name = "FK_Ticket_296StatusCycle_14"),
         * insertable = false)
         * 
         * @JsonProperty("StatusCycle_14") private Statuscycle_14 Statuscycle_14;
         * 
         * 
         * @ManyToOne(fetch = FetchType.EAGER)
         * 
         * @JoinColumn(referencedColumnName = "id", name = "departmentid", updatable =
         * false, foreignKey = @ForeignKey(name = "FK_Ticket_296Department_23"),
         * insertable = false)
         * 
         * @JsonProperty("Department_23") private Department_23 Department_23;
         * 
         * @ManyToOne(fetch = FetchType.EAGER)
         * 
         * @JoinColumn(referencedColumnName = "id", name = "supportid", updatable =
         * false, foreignKey = @ForeignKey(name = "FK_Ticket_296Support_298"),
         * insertable = false)
         * 
         * @JsonProperty("Support_298") private Support_298 Support_298;
         */

        @JsonProperty("ChatHistory_299")

        @JsonSerialize(as = java.util.LinkedHashSet.class)

        @JsonDeserialize(as = java.util.LinkedHashSet.class)

        @Fetch(FetchMode.SELECT)

        @OneToMany(fetch = FetchType.EAGER) // @Cascade(CascadeType.ALL)

        @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "id", name = "ticket_296id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id", unique = true, name = "chathistory_299id"), name = "ticket_296__chathistory_299")
        private Set<ChatHistory_299> ChatHistory_299 = new LinkedHashSet<>();

        /*
         * @JsonProperty("AddressInformation_34")
         * 
         * @JsonSerialize(as = java.util.LinkedHashSet.class)
         * 
         * @JsonDeserialize(as = java.util.LinkedHashSet.class)
         * 
         * @Fetch(FetchMode.SELECT)
         * 
         * @OneToMany(fetch = FetchType.EAGER)
         * 
         * //@Cascade(CascadeType.ALL)
         * 
         * @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "id", name =
         * "employee_11id"), inverseJoinColumns = @JoinColumn(referencedColumnName =
         * "id", unique = true, name = "addressinformation_34id"), name =
         * "employee_11__addressinformation_34") private Set<AddressInformation_34>
         * AddressInformation_34 = new LinkedHashSet<>();
         */

        @Column(name = "createdby")
        @CreatedBy
        private String createdBy;

        @Column(name = "updatedby")
        @LastModifiedBy
        private String updatedBy;

        @Column(name = "createdatetime")
        private Date createDateTime;

        @Column(name = "updatedatetime")
        private Date updateDateTime;

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

        @JsonIgnore
        public String getTicketNumber() {
                return ticketNumber;
        }

        @JsonIgnore
        public void setTicketNumber(String ticketNumber) {
                this.ticketNumber = ticketNumber;
        }

        @JsonIgnore
        public String getTicketTitle() {
                return ticketTitle;
        }

        @JsonIgnore
        public void setTicketTitle(String ticketTitle) {
                this.ticketTitle = ticketTitle;
        }

        @JsonIgnore
        public String getDescription() {
                return description;
        }

        @JsonIgnore
        public void setDescription(String description) {
                this.description = description;
        }

        @JsonIgnore
        public String getEmployeeTeamsId() {
                return employeeTeamsId;
        }

        @JsonIgnore
        public void setEmployeeTeamsId(String employeeTeamsId) {
                this.employeeTeamsId = employeeTeamsId;
        }

        @JsonIgnore
        public String getTicketQualityRate() {
                return ticketQualityRate;
        }

        @JsonIgnore
        public void setTicketQualityRate(String ticketQualityRate) {
                this.ticketQualityRate = ticketQualityRate;
        }

        @JsonIgnore
        public String getTicketQualityComments() {
                return ticketQualityComments;
        }

        @JsonIgnore
        public void setTicketQualityComments(String ticketQualityComments) {
                this.ticketQualityComments = ticketQualityComments;
        }

        @JsonIgnore
        public String getChatGroupId() {
                return chatGroupId;
        }

        @JsonIgnore
        public void setChatGroupId(String chatGroupId) {
                this.chatGroupId = chatGroupId;
        }

        @JsonIgnore
        public String getPriorityId() {
                return priorityId;
        }

        @JsonIgnore
        public void setPriorityId(String priorityId) {
                this.priorityId = priorityId;
        }

        @JsonIgnore
        public String getStatuscycleId() {
                return statuscycleId;
        }

        @JsonIgnore
        public void setStatuscycleId(String statuscycleId) {
                this.statuscycleId = statuscycleId;
        }

        @JsonIgnore
        public String getSupportDepartmentId() {
                return supportDepartmentId;
        }

        @JsonIgnore
        public void setSupportDepartmentId(String supportDepartmentId) {
                this.supportDepartmentId = supportDepartmentId;
        }


        @JsonIgnore
        public String getSupportId() {
                return supportId;
        }

        @JsonIgnore
        public void setSupportId(String supportId) {
                this.supportId = supportId;
        }

        /*
         * @JsonIgnore public Priority_297 getPriority_297() { return Priority_297; }
         * 
         * @JsonIgnore public void setPriority_297(Priority_297 Priority_297) {
         * this.Priority_297 = Priority_297; }
         * 
         * @JsonIgnore public Statuscycle_14 getStatusCycle_14() { return
         * Statuscycle_14; }
         * 
         * @JsonIgnore public void setStatusCycle_14(Statuscycle_14 Statuscycle_14) {
         * this.Statuscycle_14 = Statuscycle_14; }
         * 
         * @JsonIgnore public Department_23 getDepartment_23() { return Department_23; }
         * 
         * @JsonIgnore public void setDepartment_23(Department_23 Department_23) {
         * this.Department_23 = Department_23; }
         * 
         * @JsonIgnore public Support_298 getSupport_298() { return Support_298; }
         * 
         * @JsonIgnore public void setSupport_298(Support_298 Support_298) {
         * this.Support_298 = Support_298; }
         */

        /*
         * @JsonIgnore public Set<ChatHistory_299> getChatHistory_299() { return
         * ChatHistory_299; }
         * 
         * @JsonIgnore public void setChatHistory_299(Set<ChatHistory_299>
         * ChatHistory_299) { this.ChatHistory_299 = ChatHistory_299; }
         */
        /*
         * public Ticket_296() { super.setEntityType("Ticket_296"); }
         */

        @JsonIgnore
        public static Ticket_296 loadDefaultValues(String offset) {
                Ticket_296 entity = new Ticket_296();
                entity.setStatus("");
                entity.setNextRoles("");
                entity.setTicketNumber("");
                entity.setTicketTitle("");
                entity.setDescription("");
                entity.setEmployeeTeamsId("");
                entity.setTicketQualityRate("");
                entity.setTicketQualityComments("");
                entity.setChatGroupId("");
                entity.setWelmsg("");
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
                Ticket_296 entity = (Ticket_296) object;
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

        /*
         * @Override public String toString() { String logResult = "Ticket_296[id = " +
         * id + ", status = " + status + ", nextRoles = " + nextRoles +
         * ", ticketNumber = " + ticketNumber + ", ticketTitle = " + ticketTitle +
         * ", description = " + description + ", employeeTeamsId = " + employeeTeamsId +
         * ", ticketQualityRate = " + ticketQualityRate + ", ticketQualityComments = " +
         * ticketQualityComments + ", chatGroupId = " + chatGroupId + ", priorityId = "
         * + priorityId + ", statusCycleId = " + statusCycleId + ", departmentId = " +
         * departmentId + ", supportId = " + supportId + ", ]"; if (ChatHistory_299 !=
         * null) { for (ChatHistory_299 value : ChatHistory_299) { if (value != null) {
         * logResult = logResult + value.toString(); } } } return logResult; }
         */

}
