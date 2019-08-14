package com.atb.ninja.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="fluxdata")
public class FluxData {

	
	@Id
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public FluxData() {

	}

	public FluxData(String data) {
		super();
		this.data = data;
	}
	
	
	

}
