package com.currencycovertor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.currencycovertor.Entity.CurrencyRates;

public interface CurrencyRatesRepository extends MongoRepository<CurrencyRates, String>{

	@Query(value="{currency_symbol : ?0}" , fields = "{currency_symbol : 1}")
	CurrencyRates findByCurrencySymbol(String currencySymbol);

	@Query(value="{currency_symbol : ?0}" , fields = "{rate : 1}")
	CurrencyRates findByRateByCurrency(String currency);

}
