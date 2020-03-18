package com.algaworks;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {

	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

	static {
		LISTA_CONTATOS.add(new Contato("1", "Maria", "+55 34 3214-5678", "15/06/1980"));
		LISTA_CONTATOS.add(new Contato("2", "João", "+55 34 1234-5678", "05/02/1985"));
		LISTA_CONTATOS.add(new Contato("3", "Normandes", "+55 11 3236-2336", "11/04/1979"));
		LISTA_CONTATOS.add(new Contato("4", "Thiago", "+55 11 3238-6110", "01/03/1986"));
		LISTA_CONTATOS.add(new Contato("5", "Alexandre", "+55 34 3211-1458", "23/07/1983"));
		LISTA_CONTATOS.add(new Contato("6", "Ricardo", "+55 34 3217-9854", "30/09/1989"));
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("listar");

		modelAndView.addObject("contatos", LISTA_CONTATOS);

		return modelAndView;
	}

	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView modelAndView = new ModelAndView("formulario");

		modelAndView.addObject("contato", new Contato());

		return modelAndView;
	}
	
	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();
		
		contato.setId(id);
		
		LISTA_CONTATOS.add(contato);
		
		return "redirect:/contatos";
	}
	
	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		Contato contato = procurarContato(id);
		
		modelAndView.addObject("contato", contato);
		
		return modelAndView;
	}
	
	@PutMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		Integer indice = procurarIndiceContato(contato.getId());
		
		Contato contatoVelho = LISTA_CONTATOS.get(indice);
		
		remover(contatoVelho.getId());
		
		LISTA_CONTATOS.add(indice, contato);
		
		return "redirect:/contatos";
	}
	
	
	@DeleteMapping("/contatos/{id}")
	public String remover(@PathVariable String id) {
		Contato contato = procurarContato(id);
		
		LISTA_CONTATOS.remove(contato);
	
		return "redirect:/contatos";
	}
	// -------------------------------------- Métodos auxiliares
	
		private Contato procurarContato(String id) {
			Integer indice = procurarIndiceContato(id);
			
			if (indice != null) {
				return LISTA_CONTATOS.get(indice);
			}
			
			return null;
		}
		
		private Integer procurarIndiceContato(String id) {
			for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
				Contato contato = LISTA_CONTATOS.get(i);
				
				if (contato.getId().equals(id)) {
					return i;
				}
			}
			
			return null;
		}
		
	
	
}
