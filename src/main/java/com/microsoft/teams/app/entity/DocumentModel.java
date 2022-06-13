package com.microsoft.teams.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="document_model")
@Getter
@Setter
public class DocumentModel {
	@Id
	@GenericGenerator(name = "longTime", strategy = "com.microsoft.teams.app.entity.SequenceGenerator")
	@GeneratedValue(generator = "longTime")
    @Column(name = "id")
    private long id;
	
    @Column(name = "filename")
	private String filename;
    
    @Column(name = "filetype")
    private String filetype;
    

   // @Column(name="fileContent")
	@Lob
    private byte[] fileContent;
	
    @Column(name="event_name")
	private String eventName;
	
    @Column(name="user_id")
	private String userId;
	
	@Column(name="docId",unique = true)
	private String docId;
	
	@Column(name="entityid")
	private String entityId;
	
	@Column(name="transactionentityId")
	private String transactionEntityId;
	
	@Column
	private Date createDateTime;
	
	public DocumentModel(){}
	
	public DocumentModel( String filename,String filetype, byte[] fileContent,String docId){
		this.docId=docId;
		this.filename = filename;
		this.filetype=filetype;
		this.fileContent = fileContent;
	}
	
	public DocumentModel( String filename,String filetype, byte[] fileContent,String docId,String userId,String eventName, String entityId, String transactionEntityId){
		this.docId=docId;
		this.filename = filename;
		this.filetype=filetype;
		this.fileContent = fileContent;
		this.userId = userId;
		this.eventName = eventName;
		this.entityId = entityId;
		this.transactionEntityId = transactionEntityId;
	}
}
	