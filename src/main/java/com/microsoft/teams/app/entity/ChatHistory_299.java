package com.microsoft.teams.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id", name = "pk_chathistory_299id"), name = "chathistory_299")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatHistory_299 implements Serializable {

       
        @Column(nullable = false, name = "id")
        @Id
        @JsonProperty("id")
        private String id;
        @Column(name = "senderid")
        @JsonProperty("senderId")
        private String senderId;
        @Column(name = "receiverid")
        @JsonProperty("receiverId")
        private String receiverId;
      
        @Column(nullable = false, name = "message")
        // @Lob
        @JsonProperty("message")
        private String message;
        @Column(name = "teamsmessageid")
        @JsonProperty("teamsmessageId")
        private String teamsmessageId;

        @JsonIgnore
        public String getId() {
                return id;
        }

        @JsonIgnore
        public void setId(String id) {
                this.id = id;
        }

        @JsonIgnore
        public String getSenderId() {
                return senderId;
        }
        @JsonIgnore
        public void setSenderId(String senderId) {
                this.senderId = senderId;
        }

        @JsonIgnore
        public String getReceiverId() {
                return receiverId;
        }

        @JsonIgnore
        public void setReceiverId(String receiverId) {
                this.receiverId = receiverId;
        }

        @JsonIgnore
        public String getMessage() {
                return message;
        }

        @JsonIgnore
        public void setMessage(String message) {
                this.message = message;
        }

        @JsonIgnore
        public String getTeamsmessageId() {
                return teamsmessageId;
        }

        @JsonIgnore
        public void setTeamsmessageId(String teamsmessageId) {
                this.teamsmessageId = teamsmessageId;
        }

        @JsonIgnore
        public static ChatHistory_299 loadDefaultValues(String offset) {
                ChatHistory_299 entity = new ChatHistory_299();
                entity.setSenderId("");
                entity.setReceiverId("");
                entity.setMessage("");
                entity.setTeamsmessageId("");
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
                ChatHistory_299 entity = (ChatHistory_299) object;
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
                String logResult = "ChatHistory_299[id = " + id + ", senderId = " + senderId + ", receiverId = " + receiverId
                                + ", message = " + message + ", teamsmessageId = " + teamsmessageId + ", ]";
                return logResult;
        }
}
                                    
               