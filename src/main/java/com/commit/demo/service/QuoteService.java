package com.commit.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.commit.demo.dto.StatusDTO;
import com.commit.demo.dto.ItemDTO;
import com.commit.demo.dto.QuoteDTO;
import com.commit.demo.exception.ProjBusinessException;
import com.commit.demo.exception.QuoteOperationEnum;
import com.commit.demo.exception.RecordNotFoundException;
import com.commit.demo.exception.OutputStatusEnum;
import com.commit.demo.model.ItemEntity;
import com.commit.demo.model.QuoteEntity;
import com.commit.demo.repository.QuoteLogRepository;
import com.commit.demo.repository.QuoteRepository;

@Service
public class QuoteService {

	@Autowired
	QuoteRepository quoteRepository;


	@Autowired
	QuoteLogRepository quoteLogRepository;

	@Autowired
	QuoteLogService quoteLogService;

	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	Logger logger;


	PropertyMap<QuoteDTO, QuoteEntity> skipModifiedFieldsMap = new PropertyMap<QuoteDTO, QuoteEntity>() {
		protected void configure() {
			skip().setId(null);
		}
	};

	public QuoteEntity mapDTOToEntity(QuoteDTO quoteDTO, QuoteEntity quatEntity) {
		quatEntity.setName(quoteDTO.getName());
		quatEntity.setPrice(quoteDTO.getPrice());

		// loop on all child (merge them)
		List<ItemEntity> itemList = quoteDTO.getItemList().stream().map(itemDTO -> {

			ItemEntity itemEntity = quatEntity.getItemList().stream()
					.filter(itemE -> itemE.getId().equals(itemDTO.getId())).findFirst().orElse(null);
			if (itemEntity != null) {
				itemEntity.setName(itemDTO.getName());
			} else {
				itemEntity = modelMapper.map(itemDTO, ItemEntity.class);
				itemEntity.setId(null);
				itemEntity.setQuote(quatEntity);
				quatEntity.getItemList().add(itemEntity);
			}
			return itemEntity;
		}).collect(Collectors.toList());
		return quatEntity;
	}

	public QuoteEntity mapDTOToEntity(QuoteDTO quoteDTO) {
		QuoteEntity entity = (QuoteEntity) modelMapper.map(quoteDTO, QuoteEntity.class);

		return entity;
	}

	public QuoteDTO mapEntityToDTO(QuoteEntity quoteEntity) {
		QuoteDTO quoteDto = (QuoteDTO) modelMapper.map(quoteEntity, QuoteDTO.class);
		List<ItemDTO> itemList = quoteEntity.getItemList().stream().map(item -> {
			ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
			return itemDTO;
		}).collect(Collectors.toList());
		quoteDto.setItemList(itemList);
		return quoteDto;
	}

	public List<QuoteDTO> getAllQuotes() {
		List<QuoteEntity> entityList = quoteRepository.findAll();

		if (entityList.size() > 0) {
			List<QuoteDTO> dtoList = entityList.stream().map(rec -> {
				QuoteDTO quoteDTO = mapEntityToDTO(rec);
				return quoteDTO;
				// QuoteDTOmapEntityToDTO(rec)).collect(Collectors.toList());
			}).collect(Collectors.toList());
			return dtoList;
		} else {
			return new ArrayList<QuoteDTO>();
		}
	}

	public QuoteDTO getQuoteById(Long id) throws ProjBusinessException {
		Optional<QuoteEntity> quote = quoteRepository.findById(id);

		if (quote.isPresent()) {
			QuoteEntity quoteEntity = quote.get();
			quoteEntity.getItemList();
			return mapEntityToDTO(quoteEntity);
		} else {
			
			StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.NO_RECORDS_FOUND,"No quote record exist for given id:" + id,"");
			logger.error(statusDTO.toString());
			throw new ProjBusinessException(statusDTO);
		}
	}

	public QuoteDTO createOrUpdateQuote(QuoteDTO dto) throws RecordNotFoundException, ProjBusinessException {
		
		validate(dto);
		QuoteOperationEnum operationEnum;
		QuoteEntity quoteTargetEntity = null;
		if (dto.getId()!=null) {
			Optional<QuoteEntity> quoteOptional = quoteRepository.findById(dto.getId());
			if (quoteOptional.isPresent()) {
				quoteTargetEntity = quoteOptional.get();
			}
		}	
		if (quoteTargetEntity !=null) { //make update
			//quoteTargetEntity = quoteOptional.get();
			mapDTOToEntity(dto, quoteTargetEntity);
			operationEnum = QuoteOperationEnum.UPDATE;
		} else { //make insert
			
			QuoteEntity quateEntityByName = quoteRepository.findQuoteByName(dto.getName());
			operationEnum = QuoteOperationEnum.CREATE;
			if (quateEntityByName!=null) //already exist by name
			{
				StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.QUATE_NAME_ALREADY_EXIST, "Quote Name already exists:"+dto.getName());
				quoteLogService.storeQuoteInLog(quoteTargetEntity,operationEnum,statusDTO);
				
				logger.error(statusDTO.toString());
				throw new ProjBusinessException(statusDTO);
			}
			quoteTargetEntity = mapDTOToEntity(dto);
			
		}

		quoteTargetEntity = quoteRepository.save(quoteTargetEntity);
		
		quoteLogService.storeQuoteInLog(quoteTargetEntity,operationEnum);
		QuoteDTO quoteTargetDTO = mapEntityToDTO(quoteTargetEntity);

		return quoteTargetDTO;

	}

	private boolean validate(QuoteDTO dto) throws ProjBusinessException{
		// TODO Auto-generated method stub
		if (dto.getPrice()<0 ) {
			
			StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.INVALID_INPUT,"price is less then 0:" + dto.getPrice(),"");
			logger.error(statusDTO.toString());
			throw new ProjBusinessException(statusDTO);
		}
		if (StringUtils.isEmpty(dto.getName() )) {
			StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.INVALID_INPUT,"name of quote is missing:" + dto.getName(),"");
			logger.error(statusDTO.toString());
			throw new ProjBusinessException(statusDTO);
		}
		return true;
		
	}

	public QuoteDTO deleteQuoteById(Long id) throws ProjBusinessException {
		Optional<QuoteEntity> quote = quoteRepository.findById(id);

		QuoteDTO deletedQuoteDTO = new QuoteDTO();
		if (quote.isPresent()) {
			quoteRepository.deleteById(id);
			quoteLogService.storeQuoteInLog(quote.get(),QuoteOperationEnum.DELETE);

			deletedQuoteDTO.setId(id);
		} else {
			StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.NO_RECORDS_FOUND,"No quote record exist for given id:" + id,"");
			logger.error(statusDTO.toString());
			throw new ProjBusinessException(statusDTO);
		}
		return deletedQuoteDTO;
	}
}