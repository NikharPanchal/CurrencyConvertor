package com.currencycovertor.Entity;


import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyEntity {

	@Field(name = "symbol")
	private String symbol;

	@Field(name = "country_name")
	private String name;
	
	@Field(name = "rate")
	@JsonIgnoreProperties(ignoreUnknown = true)
	private double rates;

	@Field(name = "created_dt")
	@CreatedDate
	private Date createdDt;

	@Field(name = "upadated_dt")
	@LastModifiedDate
	private Date updatedDt;

	public String getSymbol() {
        return this.symbol;
    }

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

}
