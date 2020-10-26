package com.commit.demo.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.commit.demo.exception.QuoteOperationEnum;

@Entity (name="quoteLog")
@Table(name="quoteLog")
public class QuoteLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id",unique=true, nullable = false)
	private Long id;


	@Column(name = "quote_id")
	private Long quoteId;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	
	@Column(name = "quote_operation")
	QuoteOperationEnum operation;

	@Column(name = "error_code")
	private Integer errorCode;

	@Column(name = "message")
	private String message;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getQuoteId() {
		return quoteId;
	}


	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public QuoteOperationEnum getOperation() {
		return operation;
	}


	public void setOperation(QuoteOperationEnum operation) {
		this.operation = operation;
	}




	public Integer getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteLogEntity [id=");
		builder.append(id);
		builder.append(", quoteId=");
		builder.append(quoteId);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
	

    
}