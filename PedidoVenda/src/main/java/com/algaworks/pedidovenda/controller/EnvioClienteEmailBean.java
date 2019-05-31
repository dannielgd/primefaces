package com.algaworks.pedidovenda.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioClienteEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@ClienteEdicao
	Cliente cliente;
	
	public void enviarCliente() throws IOException {
		
		MailMessage message = mailer.novaMensagem();
		
		String filePath = getClass().getClassLoader().getResource("emails/cliente.template").getFile();
		
		message.to(this.cliente.getEmail()).subject("Cliente " + this.cliente.getNome())
		.bodyHtml(new VelocityTemplate(new File(filePath)))
		.put("cliente", this.cliente)
		.send();
		
		FacesUtil.addInfoMessage("Dados do Cliente enviados por email com sucesso!");
		 
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
