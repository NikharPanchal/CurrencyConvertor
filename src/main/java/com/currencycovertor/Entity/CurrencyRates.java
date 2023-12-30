package com.currencycovertor.Entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRates {

	@Id
	private String Id;
	
	@Field(name="currency_symbol")
	private String currency_symbol;
	
	@Field(name="rate")
	private double rate;
	
	@CreatedDate
	private Date created_dt;
	
	@LastModifiedDate
	private Date updated_dt;
}
