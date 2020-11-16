package com.reali.demo.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.reali.demo.dto.BaseInputDTO;
import com.reali.demo.dto.BaseOutputDTO;
import com.reali.demo.dto.StatusDTO;
import com.reali.demo.exception.OutputStatusEnum;
import com.reali.demo.exception.ProjBusinessException;
import com.thoughtworks.xstream.XStream;


@Component
public abstract class BaseServiceImpl {
	@Autowired
	private Logger	logger;

	@Autowired
	private XStream	xStream;

	public BaseOutputDTO executeBusiness(BaseInputDTO baseInputDTO) {
		BaseOutputDTO baseOutputDTO = initResponse();
		StopWatch clock = new StopWatch();
		try {
			clock.start();
			logger.info("Start of service.  " + baseInputDTO.getClass()
					.getSimpleName() + " " + xStream.toXML(baseInputDTO));
			Boolean isSuccess = validateInput(baseInputDTO, baseOutputDTO);

			if (isSuccess) {
				baseOutputDTO = executeService(baseInputDTO, baseOutputDTO);
				createSuccessResponse(baseInputDTO, baseOutputDTO);
			}
		} catch (Exception e) {
			if (e instanceof ProjBusinessException) {
				
				baseOutputDTO.setStatus(handleBusinessException((ProjBusinessException) e));
				logger.debug(e.toString(), e);
			}

			else {
				logger.error(e.toString());
				createErrorResponse(baseInputDTO, baseOutputDTO, OutputStatusEnum.UNEXPECTED);
			}

			logger.debug(baseOutputDTO.getResultStatus().getFreeText(), e);
		} finally {
			clock.stop();
			logger.info("End of service.  " + baseInputDTO.getClass().getSimpleName() 
					+ " lasted: (ms)" + clock.getLastTaskTimeMillis());

			if (clock.getLastTaskTimeMillis() > 800) {
				logger.warn("Slow execution of service.  " + baseInputDTO.getClass()
						.getSimpleName() + " lasted: (ms)" + clock.getLastTaskTimeMillis());
			}
		}

		logger.debug("End of Service of service. Output is: "+ xStream.toXML(baseOutputDTO));
		return baseOutputDTO;
	}


	/**
	 * create error response
	 * @param baseBoREQ
	 * @param baseSRQRES
	 * @param status
	 */
	public void createErrorResponse(BaseInputDTO baseBoREQ, BaseOutputDTO baseSRQRES, OutputStatusEnum status) {

		baseSRQRES.setStatus(new StatusDTO(status, status.getDescription(), status.getDescription()));

	}

	/**
	 * create Success response
	 * @param baseBoREQ
	 * @param baseSRQRES
	 */
	public void createSuccessResponse(BaseInputDTO baseBoREQ, BaseOutputDTO baseSRQRES) {

		baseSRQRES.setStatus(new StatusDTO(OutputStatusEnum.SUCCESS, "", ""));
	}


	public abstract BaseOutputDTO executeService(BaseInputDTO baseBoREQ, BaseOutputDTO baseSRQRES) throws ProjBusinessException;

	public abstract Boolean validateInput(BaseInputDTO baseBoREQ, BaseOutputDTO baseSRQRES) throws ProjBusinessException;

	public abstract BaseOutputDTO initResponse();

	
	public StatusDTO handleBusinessException(ProjBusinessException projBusinessException) {
		StatusDTO status = projBusinessException.getStatus();
		return status;
	}

}

