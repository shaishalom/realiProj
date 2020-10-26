package com.commit.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity (name="quote")
@Table(name="quote")
public class QuoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id",unique=true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	double price;
	
	
//	@OneToMany(fetch = FetchType.LAZY,mappedBy="question")
//    private List<PsyOptions> productlist=new ArrayList<PsyOptions>();

	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="quote", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ItemEntity> itemList=new ArrayList<ItemEntity>();
	
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true,mappedBy = "")
//	@JoinColumn(name = "QUOTE_ID",nullable=false)
//	private List<ItemEntity> itemList =new ArrayList<>();


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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<ItemEntity> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemEntity> itemList) {
		this.itemList = itemList;
	}
	
	@Override
	public String toString() {
		return "QuoteEntity [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
    
}