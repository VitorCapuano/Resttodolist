package br.senai.sp.todolist.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.todolist.dao.ItemDAO;
import br.senai.sp.todolist.model.ItemTarefa;

@RestController
public class ItemRestController {

	@Autowired
	private ItemDAO dao;

	@RequestMapping(value = "/lista/{id}/item", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemTarefa> addItem(@PathVariable("id") long idTarefa,
			@RequestBody ItemTarefa item) {
		try {
			dao.inserirItem(idTarefa, item);
			return ResponseEntity.created(new URI("/item/" + item.getId())).body(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ItemTarefa buscarItemTarefa(@PathVariable("id") long idItemTarefa) {
		return dao.buscarItemTarefa(idItemTarefa);
	}
	
	// se der erro 403 adicionar no modelo ItemTarefa(@ManyToOne(optional = false)) no atributo Tarefa tarefa
	@RequestMapping(value="/item/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirItem(@PathVariable("id") Long idItemTarefa){
		dao.excluirItem(idItemTarefa);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/item/{id}", method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeito(@PathVariable("id") long idItemTarefa,
			@RequestBody ItemTarefa item) {
		try {
			dao.marcarFeito(idItemTarefa, item.isFeito());
			HttpHeaders responseHeaders = new HttpHeaders();
			URI location = new URI("/item/" + idItemTarefa);
			responseHeaders.setLocation(location);
			ResponseEntity<Void> response = new ResponseEntity<Void>(responseHeaders,
					HttpStatus.OK);
			return response;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

}
