package com.commit.demo.exception;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.commit.demo.dto.StatusDTO;
import com.commit.demo.utils.StringUtils;

@SuppressWarnings("serial")
public class ProjBusinessException extends Exception
{
	private StatusDTO status;
	
	
	public ProjBusinessException(StatusDTO status)
	{
		super();
		this.status = status;
		
	}

	public ProjBusinessException(StatusDTO status, String message)
	{
		super(message);
		this.status = status;
		}
	
	public ProjBusinessException(StatusDTO status, Throwable throwable)
	{
		super(throwable);
		this.status = status;
	}
	public ProjBusinessException(  Throwable throwable)
	{
		super(throwable);
	}

	public StatusDTO getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "ProjBusinessException [status=" + status + ", message:"+getMessage() + "] " ;
	}
	
}

