package com.currencycovertor.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.currencycovertor.Entity.CurrencyEntity;
import com.currencycovertor.Entity.CurrencyRates;
import com.currencycovertor.Entity.CurrencyRatesResponse;
import com.currencycovertor.controller.CurrencyConvertorController;
import com.currencycovertor.repository.CurrencyRatesRepository;
import com.currencycovertor.repository.CurrrencyConvertorRepository;
import com.currencycovertor.service.CurrencyConvertorService;
import com.currencycovertor.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencyConvertorServiceImpl implements CurrencyConvertorService {

	@Value("${currencyconvertor.supportedcurrncy}")
	private String supportedCurrency;

	@Value("${currencyconvertor.allRates}")
	private String allRates;

	@Value("${hostSupportedCurrency}")
	private String suppotedCuurencyHost;
	
	@Value("${hostRates}")
	private String hostRates;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private CurrrencyConvertorRepository repo;

	@Autowired
	private CurrencyRatesRepository currencyRatesRepo;
	
	private final Logger log = LoggerFactory.getLogger(CurrencyConvertorController.class);

	@Override
	public void fetchAllCurrency() {
		try {
			String response = CommonUtils.callServerGetReq(supportedCurrency, suppotedCuurencyHost);
			if (CommonUtils.isNotBlank(response)) {
				CurrencyEntity[] currencyEntities = mapper.readValue(response, CurrencyEntity[].class);
				List<CurrencyEntity> currencyEntityList = Arrays.stream(currencyEntities).collect(Collectors.toList());
				if (CommonUtils.isNotEmpty(currencyEntityList)) {
					List<CurrencyEntity> dbCurrency = repo.findAll();
					List<String> namesAsList = dbCurrency.stream().map(CurrencyEntity::getSymbol)
							.collect(Collectors.toList());

					List<CurrencyEntity> currenciesToSave = currencyEntityList.stream()
							.filter(currencyEntity -> !namesAsList.contains(currencyEntity.getSymbol()))
							.collect(Collectors.toList());

					repo.saveAll(currenciesToSave);
					log.info("Currency Data updated succedfully..");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("General Error in fetchAllCurrency. message :: {}", e.getMessage());
		}
	}

	@Override
	public void syncRates() {
		try {
			String response = CommonUtils.callServerGetReq(allRates, hostRates);
			if (CommonUtils.isNotBlank(response)) {
				CurrencyRatesResponse ratesResponse = mapper.readValue(response, CurrencyRatesResponse.class);
				if (ratesResponse != null && ratesResponse.getRates() != null) {
					Map<String, Double> rates = ratesResponse.getRates();
					for (Map.Entry<String, Double> entry : rates.entrySet()) {
						String currencySymbol = entry.getKey();
						Double rate = entry.getValue();
						CurrencyRates existingCurrencyRate = currencyRatesRepo.findByCurrencySymbol(currencySymbol);
						if (existingCurrencyRate != null
								&& existingCurrencyRate.getCurrency_symbol().equals(currencySymbol)) {
							Query query = new Query(Criteria.where("currency_symbol").is(currencySymbol));
							Update update = new Update()
									.set("rate", rate)
									.set("updated_dt", new Date());
							mongoTemplate.updateFirst(query, update, CurrencyRates.class);
						} else {
							CurrencyRates newCurrencyRate = CurrencyRates.builder()
									.currency_symbol(currencySymbol)
									.rate(rate).build();
							currencyRatesRepo.save(newCurrencyRate);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("General Error in syncRates. message :: {}", e.getMessage());
		}
	}

	@Override
	public double convertCurrency(String from, String to, double value) {
		try {
			if (CommonUtils.isNotBlank(from) && CommonUtils.isNotBlank(to) && value > 0) {
				CurrencyRates toRate = currencyRatesRepo.findByRateByCurrency(to);
				CurrencyRates fromRate = currencyRatesRepo.findByRateByCurrency(from);
				if (toRate != null && fromRate != null) {
					double toValue = toRate.getRate();
					double fromValue = fromRate.getRate();
					if (toValue > 0 && fromValue > 0) {
						return value * (toValue / fromValue);
					}
				} else {
					log.error("Rates for provided currencies not found");
				}
			} else {
				log.error("Invalid from/to currency symbols provided.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("General Error in convertCurrency. message :: {}", e.getMessage());
		}
		return 0;
	}
}