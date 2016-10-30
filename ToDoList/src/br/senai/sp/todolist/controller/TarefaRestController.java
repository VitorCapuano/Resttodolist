package br.senai.sp.todolist.controller;

import java.net.URI;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.todolist.dao.TarefaDAO;
import br.senai.sp.todolist.model.ItemTarefa;
import br.senai.sp.todolist.model.Tarefa;

@RestController
public class TarefaRestController {

	@Autowired
	private TarefaDAO dao;

	@RequestMapping(value = "/lista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody Tarefa tarefa) {
		try {
			for (ItemTarefa item : tarefa.getItens()) {
				item.setTarefa(tarefa);
			}
			dao.inserir(tarefa);
			return ResponseEntity.created(new URI("/lista/" + tarefa.getId())).body(tarefa);
		} catch (ConstraintViolationException e) {
			String mensagem = "";
			for (ConstraintViolation<?> constrain : e.getConstraintViolations()) {
				mensagem += constrain.getMessage() + "\n";
			}
			return ResponseEntity.badRequest().body(mensagem);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Tarefa> listar() {
		return dao.lista();
	}

	@RequestMapping(value = "/lista/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Tarefa buscarTarefa(@PathVariable("id") long idTarefa) {
		return dao.buscarTarefa(idTarefa);
	}

	@RequestMapping(value="lista/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void>excluir(@PathVariable("id") long idTarefa){
		dao.excluir(idTarefa);
		return ResponseEntity.noContent().build();
	}

}
