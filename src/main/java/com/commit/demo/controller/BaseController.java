package com.commit.demo.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.commit.demo.dto.StatusDTO;
import com.commit.demo.exception.ProjBusinessException;

//@CrossOrigin(origins = "*")
public abstract class BaseController {

	public StatusDTO handleBusinessException(ProjBusinessException projBusinessException) {
		StatusDTO status = projBusinessException.getStatus();
		return status;
	}

	
	
}

