package com.algaworks.pedidovenda.model;

public enum TipoPessoa {
	
	FISICA("Física"), JURIDICA("Jurídica");
	
	private final String descricao;
	
	TipoPessoa (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
