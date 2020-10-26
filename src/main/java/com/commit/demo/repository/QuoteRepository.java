package com.commit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commit.demo.model.QuoteEntity;
 
@Repository
public interface QuoteRepository
        extends JpaRepository<QuoteEntity, Long> {
 
	QuoteEntity findQuoteByName(String name);
	
}
