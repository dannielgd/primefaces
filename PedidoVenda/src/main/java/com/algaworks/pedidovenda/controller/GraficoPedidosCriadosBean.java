package com.algaworks.pedidovenda.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.security.UsuarioLogado;
import com.algaworks.pedidovenda.security.UsuarioSistema;
import com.algaworks.pedidovenda.util.UtilData;

@Named
@RequestScoped
public class GraficoPedidosCriadosBean {
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private Pedidos pedidos;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject @UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	private LineChartModel model;
	private PieChartModel modelPizza;
	
	public void preRender() {
		this.model = new LineChartModel();
		this.modelPizza = new PieChartModel();
		
		adicionarSerie("Todos os Pedidos", null);
		adicionarSerie("Meus Pedidos", usuarioLogado.getUsuario());
		
		criarGraficoPizza();
		
		this.model.setTitle("Pedidos Criados");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		Axis yAxis = model.getAxis(AxisType.Y);
		
		
	}

	private void adicionarSerie(String rotulo, Usuario criadoPor) {
		
		Map<Date,BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(15, criadoPor);
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for(Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
		
	}
	
	private void criarGraficoPizza() {
		
		this.model.setTitle("Pedidos Criados por Usuário");
		this.model.setLegendPosition("w");
		this.model.setAnimate(true);
		
		List<Usuario> vendedores = this.usuarios.vendedores();
		
		
		
		for(Usuario usuario : vendedores) {
			
			Map<Date,BigDecimal> valoresPorData = this.pedidos
					.valoresTotaisPorData(UtilData.getIntefvaloDeDias(usuario.getDataCriacao(), new Date()), usuario);
			
			this.modelPizza.set(usuario.getNome(), somarValoresVendidos(valoresPorData));
		}
		
	}
	
	private BigDecimal somarValoresVendidos(Map<Date,BigDecimal> valoresPorData) {
		BigDecimal soma = BigDecimal.ZERO;
		
		for(Date data : valoresPorData.keySet()) {
			soma = soma.add(valoresPorData.get(data));
		}
		return soma;
	}

	public LineChartModel getModel() {
		return model;
	}

	public PieChartModel getModelPizza() {
		return modelPizza;
	}
	
}
