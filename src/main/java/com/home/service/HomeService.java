package com.home.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.home.model.Conta;
import com.home.model.Lancamento;
import com.home.repository.ContaRepository;
import com.home.repository.LancamentoRepository;

@Service
public class HomeService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private ContaRepository contaRepository;

	@PostConstruct
	public void Init() {
		List<Conta> contas = new ArrayList<>();
		if (contaRepository.findAll().size() < 3) {

			contas.add(new Conta("Aluguel"));
			contas.add(new Conta("Energia"));
			contas.add(new Conta("Escola"));

			contaRepository.save(contas);
		}
	}

	public void remove(String id) {
		lancamentoRepository.delete(id);
	}

	public List<Lancamento> findAllLancamento() {
		return lancamentoRepository.findAll(new Sort(Sort.Direction.DESC, "data"));
	}

	public List<Lancamento> findByFilter(Conta conta, Date data) {
		Query query = new Query();
		
		if (conta != null)
			query.addCriteria(Criteria.where("conta").is(conta));

		if (data != null)
			query.addCriteria(Criteria.where("data").is(data));

		query.with(new Sort(Sort.Direction.DESC, "data"));
		return mongoTemplate.find(query, Lancamento.class);
	}

	public List<Lancamento> findByMes(int mes) {
		return lancamentoRepository.findByMes(mes);
	}

	public Lancamento addLancamento(Lancamento lancamento) {
		return lancamentoRepository.save(lancamento);
	}

	public void removeLancamento(Lancamento lancamento) {
		lancamentoRepository.delete(lancamento);
	}
	
	public void saveConta(Conta conta) {
		contaRepository.save(conta);
	}
	
	public void removeConta(String id) {
		contaRepository.delete(id);
	}

	public List<Conta> getAllContas() {
		return contaRepository.findAll();
	}

	public Conta getContaById(String id) {
		return contaRepository.findOne(id);
	}
}
