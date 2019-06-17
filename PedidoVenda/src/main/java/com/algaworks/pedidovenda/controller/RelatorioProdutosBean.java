package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal valorInicio;
	private BigDecimal valorFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("valor_inicio", this.valorInicio);
		parametros.put("valor_fim", this.valorFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_produtos.jasper",
				this.response, parametros, "Produtos.pdf");
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public BigDecimal getValorInicio() {
		return valorInicio;
	}

	public void setValorInicio(BigDecimal valorInicio) {
		this.valorInicio = valorInicio;
	}

	@NotNull
	public BigDecimal getValorFim() {
		return valorFim;
	}

	public void setValorFim(BigDecimal valorFim) {
		this.valorFim = valorFim;
	}
	
}