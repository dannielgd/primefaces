package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		Usuario usr = manager.merge(usuario); // salva ou atualiza no bd
		return guardarGrupo(usr);
	}

	public Usuario guardarGrupo(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) {

		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario); // marca para exclusão
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído!");
		}
	}

	public List<Usuario> filtrados(UsuarioFilter filtro) {
		Session session = (Session) manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Usuario.class);

		// where nome like '%joao%'
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Usuario porId(Long id) {
		Usuario usuario = manager.find(Usuario.class, id);
		return usuario;

	}

	public List<Usuario> vendedores() {
		// filtrar apenas vendedores por grupo específico.
		return this.manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}

		return usuario;
	}

}
