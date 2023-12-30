package com.currencycovertor.Entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRatesResponse {
	@JsonProperty("valid")
	private boolean valid;

	@JsonProperty("updated")
	private long updated;

	@JsonProperty("base")
	private String base;

	@JsonProperty("rates")
	private Map<String, Double> rates;

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
}
