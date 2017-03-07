package com.home.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.home.model.Lancamento;

public interface LancamentoRepository extends MongoRepository<Lancamento, String>, QueryByExampleExecutor<Lancamento> {

	@Query(value = "{ 'mes' : ?0 }")
	List<Lancamento> findByMes(int mes);
}
