package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	Pedido pedido;
	public void enviarPedido() throws EmailException {
		/*
		 * MailMessage message = mailer.novaMensagem();
		 * 
		 * message.to(this.pedido.getCliente().getEmail()) .subject("Pedido " +
		 * this.pedido.getId()) .bodyHtml("<strong>Valor Total:</strong> " +
		 * this.pedido.getValorTotal()) .send();
		 * 
		 * FacesUtil.addInfoMessage("Pedido enviado por email com sucesso!");
		 */
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("danniel.pedidovenda@gmail.com", "PedidoVenda123"));
		email.setSSLOnConnect(true);
		email.setFrom("danniel.pedidovenda@gmail.com");
		email.setSubject("Envio de teste - Pedido : " + this.pedido.getId());
		email.setMsg("Caso esteja vendo essa mensagem é porque o envio funcionou :) - Valor Total: " + this.pedido.getValorTotal());
		email.addTo("dannielcriacoes@gmail.com");
		email.send();
	
		System.out.println("Fim!");
	}
}
