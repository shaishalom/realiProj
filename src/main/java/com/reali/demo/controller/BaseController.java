package com.reali.demo.controller;

import com.reali.demo.dto.StatusDTO;
import com.reali.demo.exception.ProjBusinessException;

public abstract class BaseController {

	public StatusDTO handleBusinessException(ProjBusinessException projBusinessException) {
		StatusDTO status = projBusinessException.getStatus();
		return status;
	}

	
	
}

