package com.commit.demo.dto;

import java.util.Date;

import javax.persistence.Column;

import com.commit.demo.exception.QuoteOperationEnum;

public class QuoteLogDTO {

	private Long id;
	private Long quoteId;
	private Date createdDate;
	QuoteOperationEnum operation;
	
	private Integer errorCode;
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
		builder.append("QuoteLogDTO [id=");
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