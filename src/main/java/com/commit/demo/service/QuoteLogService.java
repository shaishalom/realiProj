package com.commit.demo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commit.demo.dto.QuoteLogDTO;
import com.commit.demo.dto.StatusDTO;
import com.commit.demo.exception.OutputStatusEnum;
import com.commit.demo.exception.QuoteOperationEnum;
import com.commit.demo.model.QuoteEntity;
import com.commit.demo.model.QuoteLogEntity;
import com.commit.demo.repository.QuoteLogRepository;

@Service
public class QuoteLogService {

	@Autowired
	QuoteLogRepository quoteLogRepository;

	@Autowired
	protected ModelMapper modelMapper;

	@Autowired
	Logger logger;
	
	public List<QuoteLogDTO>  mapEntityToDTOList(List<QuoteLogEntity> quoteLogEntityList) {
		List<QuoteLogDTO> logList = quoteLogEntityList.stream().map(logEntity -> {
			QuoteLogDTO logDTO = mapEntityToDTO(logEntity);
			return logDTO;
		}).collect(Collectors.toList());
		
		return logList;
	}

	public QuoteLogDTO  mapEntityToDTO(QuoteLogEntity entity) {
		QuoteLogDTO logDTO = modelMapper.map(entity, QuoteLogDTO.class);
		return logDTO;
		
	}


	public QuoteLogEntity storeQuoteInLog(QuoteEntity quoteEntity,QuoteOperationEnum operation) {
		
		StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.SUCCESS,null);
		QuoteLogEntity quoteLogEntityOutput = storeQuoteInLog(quoteEntity,operation,statusDTO);
		return quoteLogEntityOutput;
	}

	public QuoteLogEntity storeQuoteInLog(QuoteEntity quoteEntity,QuoteOperationEnum operation,StatusDTO statusDTO) {
		
		QuoteLogEntity quoteLogEntity = new QuoteLogEntity();
		quoteLogEntity.setOperation(operation);
		if (quoteEntity != null) {
			quoteLogEntity.setQuoteId(quoteEntity.getId());
		}
		quoteLogEntity.setCreatedDate(new Date());
		if (statusDTO !=null) {
			quoteLogEntity.setErrorCode(statusDTO.getOutputStatusEnum().getCode());
			quoteLogEntity.setMessage(statusDTO.getExceptionReason());
		}
		
		QuoteLogEntity quoteLogEntityOutput = quoteLogRepository.save(quoteLogEntity);
		
		logger.info("store in quote log, in " + quoteLogEntityOutput.getCreatedDate() +" ,id created:" + quoteLogEntityOutput.getId() );
		return quoteLogEntityOutput;
	}

	public List<QuoteLogDTO> getAllQuotesLog() {
		List<QuoteLogEntity> entityList = quoteLogRepository.findAll();
		logger.info("found total logs:"+entityList.size());
		
		List<QuoteLogDTO> list  = mapEntityToDTOList(entityList);
		return list;
	}

}