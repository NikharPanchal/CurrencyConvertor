package com.currencycovertor.service;

public interface CurrencyConvertorService {

	void fetchAllCurrency();

	void syncRates();

	double convertCurrency(String from, String to, double rate);

}
