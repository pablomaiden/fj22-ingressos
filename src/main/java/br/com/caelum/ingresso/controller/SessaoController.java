package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {

	@Autowired
	FilmeDao filmeDao;

	@Autowired
	SalaDao salaDao;

	@Autowired
	SessaoDao sessaoDao;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		try {
			ModelAndView modelView = new ModelAndView("sessao/sessao");

			// Acessando a camada de dados
			Sala sala = salaDao.findOne(salaId);
			List<Filme> filmes = filmeDao.findAll();
			// ===fim===

			// Entregando para a camada de visão renderizar
			modelView.addObject("sala", sala);
			modelView.addObject("filmes", filmes);
			// ===fim===
			return modelView;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView save(@Valid SessaoForm form, BindingResult result) {
		
		if (result.hasErrors())
		    return form(form.getSalaId(),form);

		Sessao sessao = form.toSessao(salaDao, filmeDao);
		sessaoDao.save(sessao);
		return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
	}
	
	@GetMapping("/admin/sala/{id}/sessoes")
    public ModelAndView listaSessoes(@PathVariable("id") Integer id) {

    	Sala sala            = salaDao.findOne(id);
        List<Sessao> sessaos = sessaoDao.buscaSessoesDaSala(sala);

        ModelAndView view = new ModelAndView("sessao/lista");
        view.addObject("sala",sala);
        view.addObject("sessoes",sessaos);

        return view;
    }
	
	@DeleteMapping("/admin/sessao/{id}")
	@Transactional
	public void delete(@PathVariable("id") Integer id){			
		sessaoDao.delete(id);		 		
	}

}
