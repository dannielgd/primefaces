package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter<Cliente> {
	
	//@Inject
	private Clientes clientes;
	
	public ClienteConverter() {
		clientes = CDIServiceLocator.getBean(Clientes.class);
	}

	@Override
	public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente retorno = null;
		
		if(StringUtils.isNotEmpty(value)) {
			Long id = Long.valueOf(value);
			retorno = clientes.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Cliente value) {
		if(value != null ) {
			Cliente cliente = (Cliente) value;
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		return "";
	}
}
