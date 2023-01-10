package com.microsoft.teams.app.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "transactionentity")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@EntityListeners(AuditingEntityListener.class)

public class TransactionEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6509861805406296156L;

	@Id
	@Column(name = "transactionentityid")
	private Long transactionEntityId;

	/*@Column(name = "STATUS")
	private String status;*/

	@Column(name = "statusremark")
	private String statusRemark;

	@Column(name = "entityname")
	private String entityName;

	@Column(name = "entitytype")
	private String entityType;

	@Column(name = "createdby")
	@CreatedBy
	private String createdBy;
	
	@Column(name="updatedby")
	@LastModifiedBy
	private String updatedBy;
	

	@Column(name = "createdatetime")
	private Date createDateTime;
	@Column(name = "updatedatetime")
	private Date updateDateTime;

	@Column(name = "activitylog")
	private Long activitylog;
	
	@Transient
	private Double _errorCode = 0D;
	
	@Transient
	private String _errorMsg;
	
	
	public Long getActivitylog() {
		return activitylog;
	}

	public void setActivitylog(Long activitylog) {
		this.activitylog = activitylog;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public TransactionEntity() {
	}

	public Long getTransactionEntityId() {
		return transactionEntityId;
	}

	public void setTransactionEntityId(Long transactionEntityId) {
		this.transactionEntityId = transactionEntityId;
	}

	/*public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Double get_errorCode() {
		return _errorCode;
	}

	public void set_errorCode(Double _errorCode) {
		this._errorCode = _errorCode;
	}

	public String get_errorMsg() {
		return _errorMsg;
	}

	public void set_errorMsg(String _errorMsg) {
		this._errorMsg = _errorMsg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionEntityId == null) ? 0 : transactionEntityId.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionEntity other = (TransactionEntity) obj;
		if (transactionEntityId == null) {
			if (other.transactionEntityId != null)
				return false;
		} else if (!transactionEntityId.equals(other.transactionEntityId))
			return false;
		return true;
	}
}