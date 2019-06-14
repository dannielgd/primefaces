package com.algaworks.pedidovenda.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@Named
@RequestScoped
public class GraficoPedidosCriadosBean {
	
	private LineChartModel model;
	
	public void preRender() {
		this.model = new LineChartModel();
		adicionarSerie("Todos os Pedidos");
		adicionarSerie("Meus Pedidos");
		
		this.model.setTitle("Pedidos Criados");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Valores");
		
		
	}

	private void adicionarSerie(String rotulo) {
		ChartSeries series = new ChartSeries(rotulo);
		
		series.set("1", Math.random() * 1000);
		series.set("2", Math.random() * 1000);
		series.set("3", Math.random() * 1000);
		series.set("4", Math.random() * 1000);
		series.set("5", Math.random() * 1000);
		
		this.model.addSeries(series);
		
	}

	public LineChartModel getModel() {
		return model;
	}
	
	
}
