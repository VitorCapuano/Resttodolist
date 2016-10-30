package br.senai.sp.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.model.Tarefa;

@Repository
public class TarefaDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Tarefa tarefa){
		manager.persist(tarefa);
	}
	
	public List<Tarefa>lista(){
		TypedQuery<Tarefa> query = manager.createQuery("select t from Tarefa t", Tarefa.class);
		return query.getResultList();
	}
	
	public Tarefa buscarTarefa(Long idTarefa){
		return manager.find(Tarefa.class, idTarefa);
	}
	
	@Transactional
	public void excluir(Long idTarefa){
		Tarefa tarefa = manager.find(Tarefa.class, idTarefa);
		manager.remove(tarefa);
	}

}
