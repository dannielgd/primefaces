package com.algaworks.pedidovenda.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	Pedido pedido;
	public void enviarPedido() throws IOException {
		
		MailMessage message = mailer.novaMensagem();
		
		String filePath = getClass().getClassLoader().getResource("/emails/pedido.template").getFile();
		
		message.to(this.pedido.getCliente().getEmail()) .subject("Pedido " + this.pedido.getId())
		.bodyHtml(new VelocityTemplate(new File(filePath)))
		.put("pedido", this.pedido)
		.put("numberTool", new NumberTool())
		.put("locale", new Locale("pt", "BR"))
		.send();
		
		FacesUtil.addInfoMessage("Pedido enviado por email com sucesso!");
		 
//		Email email = new SimpleEmail();
//		email.setHostName("smtp.gmail.com");
//		email.setSmtpPort(465);
//		email.setAuthenticator(new DefaultAuthenticator("danniel.pedidovenda@gmail.com", "PedidoVenda123"));
//		email.setSSLOnConnect(true);
//		email.setFrom("danniel.pedidovenda@gmail.com");
//		email.setSubject("Envio de teste - Pedido : " + this.pedido.getId());
//		email.setMsg("Caso esteja vendo essa mensagem é porque o envio funcionou :) - Valor Total: " + this.pedido.getValorTotal());
//		email.addTo("dannielcriacoes@gmail.com");
//		email.send();
//	
//		System.out.println("Fim!");
	}
}
