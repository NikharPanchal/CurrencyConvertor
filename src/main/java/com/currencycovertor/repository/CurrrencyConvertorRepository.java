package com.currencycovertor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.currencycovertor.Entity.CurrencyEntity;

@Repository
public interface CurrrencyConvertorRepository extends MongoRepository<CurrencyEntity, String>{

}
