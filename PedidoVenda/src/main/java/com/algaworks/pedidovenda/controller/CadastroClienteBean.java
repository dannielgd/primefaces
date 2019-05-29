package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private Endereco endereco;
	private List<Integer> enderecos;
	
	@Inject
	private CadastroClienteService cadastroClienteService;
	
	public CadastroClienteBean() {
		if (cliente == null) {
			limpar();
		}
	}
	
	public void salvar() {
		
		try {
			cadastroClienteService.salvar(cliente);
			limpar();
			
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	private void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
		enderecos = new ArrayList<>();
	}
	
	public void salvarEndereco() {
		System.out.println("Salvou Endereço!");
	}
	
	public List<Integer> getEnderecos() {
		return enderecos;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public TipoPessoa[] getTiposCliente() {
		return TipoPessoa.values();
	}
	
	public boolean isEditando() {
		return cliente.getId() != null;
	}
}
