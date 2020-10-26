package com.commit.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.commit.demo.exception.OutputStatusEnum;

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(name = "QuoteDTO")
@XmlRootElement
public class QuoteDTO extends BaseDTO{

	public QuoteDTO() {
		this.setStatus(new StatusDTO(OutputStatusEnum.SUCCESS, "", ""));
	}

	private Long id;
	private String name;
	private double price;
	

	private  List<ItemDTO> itemList = new ArrayList<>();

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

	public List<ItemDTO> getItemList() {
		return itemList;
	}

	public void addToItemList(ItemDTO itemDto) {
		 itemList.add(itemDto);
	}

	public void setItemList(List<ItemDTO> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteDTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", price=");
		builder.append(price);
		builder.append(", itemList=");
		builder.append(itemList);
		builder.append("]");
		return builder.toString();
	}

}