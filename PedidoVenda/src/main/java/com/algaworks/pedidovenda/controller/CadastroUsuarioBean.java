package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private Usuario usuario;
	private Grupo grupo;
	private List<Grupo> grupos;
	
	private boolean editandoGrupo;
	
	private Grupo grupoSelecionado;
	
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void inicializar() {
		if(usuario != null) {
			grupos = usuario.getGrupos();
		} else {
			limpar();
		}
	}
	
	public void salvar() {
		usuario.setGrupos(grupos);
		cadastroUsuarioService.salvar(usuario);
		limpar();
		System.out.println(isEditando());
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
	}
	
	public void salvarGrupo() {
		
		if(editandoGrupo) {
			grupos.set(grupos.indexOf(grupo),grupo);
			grupo = new Grupo();
		} else {
			grupos.add(grupo);
			grupo = new Grupo();
		}
	}
	
	public void excluirGrupo() {
		grupos.remove(grupoSelecionado);
	}
	
	public void editarGrupo() {
		editandoGrupo = true;
	}
	
	private void limpar() {
		usuario = new Usuario();
		grupo = new Grupo();
		editandoGrupo = false;
		grupos = new ArrayList<>();
	}
	
	@NotBlank
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}
	
	public boolean isEditando() {
		return this.usuario.getId() != null;
	}
	
	public boolean isEditandoiGrupo() {
		return editandoGrupo;
	}
}
