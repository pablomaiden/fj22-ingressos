package br.com.caelum.ingresso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;

@Controller
public class SessaoController {
	
	@Autowired
	FilmeDao filmeDao;
	
	@Autowired
	SalaDao salaDao;
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId){		
		try {			
			ModelAndView modelView = new ModelAndView("sessao/sessao");
			
			//Acessando a camada de dados
			Sala sala          = salaDao.findOne(salaId);
			List<Filme> filmes = filmeDao.findAll();
			//===fim===
			
			//Entregando para a camada de visão renderizar
			modelView.addObject("sala",sala);
			modelView.addObject("filmes", filmes);
			//===fim===
			
			return modelView;
			
		}catch (Exception e) {
			e.printStackTrace();						
		}		
		return null;		
	}
	
	
	

}
