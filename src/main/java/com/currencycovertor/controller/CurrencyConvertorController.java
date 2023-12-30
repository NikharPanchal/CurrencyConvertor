package com.currencycovertor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currencycovertor.service.CurrencyConvertorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v1")
@RestController
@Api(tags = "Currency Converter API")
public class CurrencyConvertorController {
	
	@Autowired
	private CurrencyConvertorService convertorService;
	
    private final Logger log = LoggerFactory.getLogger(CurrencyConvertorController.class);
	
//    @ApiOperation(value = "Get Currency Info", notes = "Get information about a specific currency")
//	@GetMapping("/fetch-currency")
//	public void FetchAllCurrency() {
//		log.info("In Controller FetchAllCurrency :: ");
//		convertorService.fetchAllCurrency();
//		log.info("Out Controller FetchAllCurrency :: ");
//	}
    
    @ApiOperation(value = "Get Rate Info", notes = "Get information about a specific Rates")
	@GetMapping("/sync-rates")
	public void updateRates() {
		log.info("In Controller syncRates :: ");
		convertorService.syncRates();
		log.info("Out Controller syncRates :: ");
	}
	
    @ApiOperation(value ="Convert from One Currency to another Currency")
    @PostMapping("/convert")
	public double convertCurrency(@RequestParam("from") String from, @RequestParam("to") String to,
			@RequestParam("rate") double rate) {
		log.info("Request - Convert from :: {} to {} value :: {}", from, to, rate);
		double result = convertorService.convertCurrency(from, to, rate);
		return result;
	}
}
