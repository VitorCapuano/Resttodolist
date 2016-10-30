package br.senai.sp.todolist.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100)
	private String titulo;
	@OneToMany(mappedBy = "tarefa", fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemTarefa> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<ItemTarefa> getItens() {
		return itens;
	}

	public void setItens(List<ItemTarefa> itens) {
		this.itens = itens;
	}

}
