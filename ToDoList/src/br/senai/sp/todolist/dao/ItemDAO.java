package br.senai.sp.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.model.ItemTarefa;
import br.senai.sp.todolist.model.Tarefa;

@Repository
public class ItemDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserirItem(Long idTarefa, ItemTarefa item){
		item.setTarefa(manager.find(Tarefa.class, idTarefa));
		manager.persist(item);
	}
	
	public ItemTarefa buscarItemTarefa(Long idItemTarefa) {
		return manager.find(ItemTarefa.class, idItemTarefa);
	}
	
	@Transactional
	public void excluirItem(Long idItemTarefa){
		ItemTarefa item = manager.find(ItemTarefa.class, idItemTarefa);
		Tarefa tarefa = item.getTarefa();
		tarefa.getItens().remove(item);
		manager.merge(tarefa);
	}
	
	
	@Transactional
	public void marcarFeito(Long idItemTarefa, boolean valor) {
		ItemTarefa item = manager.find(ItemTarefa.class, idItemTarefa);
		item.setFeito(valor);
		manager.merge(item);
	}


}
