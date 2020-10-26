package com.commit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commit.demo.model.QuoteLogEntity;
 
@Repository
public interface QuoteLogRepository
        extends JpaRepository<QuoteLogEntity, Long> {
 
	
}
