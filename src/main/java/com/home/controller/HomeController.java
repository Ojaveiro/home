package com.home.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.model.Conta;
import com.home.model.Lancamento;
import com.home.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	private HomeService service;

	@ModelAttribute("contas")
	public List<Conta> allContas() {
		return service.getAllContas();
	}

	@RequestMapping("/")
	public String findByMes(final Lancamento lancamento, final ModelMap model) {
		List<Lancamento> lancamentos = service.findAllLancamento();
		model.addAttribute("list", lancamentos);
		model.addAttribute("total", lancamentos.size());
		model.addAttribute("valorTotal",
				lancamentos.stream().filter(Objects::nonNull).mapToDouble(Lancamento::getValor).sum());
		return "index";
	}

	@RequestMapping("/lancamento/{id}/remove")
	public String remove(@PathVariable("id") String itemid) {
		service.remove(itemid);
		return "redirect:/";
	}

	@RequestMapping("/conta")
	public String addConta(@RequestParam String conta, final Lancamento lancamento, final ModelMap model)
			throws ParseException {

		Conta objConta = new Conta(conta);

		service.saveConta(objConta);

		return "redirect:/";
	}

	@RequestMapping("/conta/{id}/remove")
	public String removeConta(@PathVariable("id") String id, final Lancamento lancamento, final ModelMap model)
			throws ParseException {

		service.removeConta(id);

		return "redirect:/";
	}
	

	@RequestMapping("/lancamento")
	public String filtro(@RequestParam String conta, @RequestParam String data, final Lancamento lancamento,
			final ModelMap model) throws ParseException {
		Conta objConta = service.getContaById(conta);

		Date date = null;
		if (!data.isEmpty()) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.parse(data);
		}

		List<Lancamento> lancamentos = service.findByFilter(objConta, date);
		model.addAttribute("list", lancamentos);
		model.addAttribute("total", lancamentos.size());
		model.addAttribute("valorTotal",
				lancamentos.stream().filter(Objects::nonNull).mapToDouble(Lancamento::getValor).sum());

		return "index";
	}

	@RequestMapping(value = "/", params = { "save" })
	public String save(final Lancamento lancamento, final BindingResult bindingResult, final ModelMap model) {
		lancamento.setMes(lancamento.getData().getMonth() + 1);
		this.service.addLancamento(lancamento);
		model.clear();
		return "redirect:/";
	}
}
