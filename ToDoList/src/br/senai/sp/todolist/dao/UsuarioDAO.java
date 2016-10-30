package br.senai.sp.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.model.Usuario;

@Repository
public class UsuarioDAO {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void inserir(Usuario usuario) {
		manager.persist(usuario);
	}

	public List<Usuario> listar() {
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u", Usuario.class);
		return query.getResultList();
	}

	public Usuario logar(Usuario usuario) {
		TypedQuery<Usuario> query = manager
				.createQuery("select u from Usuario u where u.login = :login and"
						+ " u.senha = :senha", Usuario.class);
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		try {
			return usuario = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
