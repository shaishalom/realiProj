package com.commit.demo.dto;

public class ItemDTO {

    private Long id;
    private String name;

	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", name=" + name + "]";
	}

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


	public ItemDTO() {
	}

	public ItemDTO(String name) {
		super();
		this.name = name;
	}
    
    

}