package com.commit.demo.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "item")
@Table(name="item")
public class ItemEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id",unique=true, nullable = false)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    
   
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id")
    private QuoteEntity quote;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ItemEntity [id=" + id + ", name=" + name + "]";
	}

	public QuoteEntity getQuote() {
		return quote;
	}

	public void setQuote(QuoteEntity quote) {
		this.quote = quote;
	}
    
    

}