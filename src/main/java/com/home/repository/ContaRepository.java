package com.home.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.model.Conta;

public interface ContaRepository extends MongoRepository<Conta, String>{

}
