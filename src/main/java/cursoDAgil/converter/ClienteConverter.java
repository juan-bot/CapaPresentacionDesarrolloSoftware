package cursoDAgil.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Cliente;
import cursoDAgil.service.cliente.ClienteService;

@Named
public class ClienteConverter implements Converter{
	
	@Inject
	ClienteService clienteService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && (value.trim().length() > 0)){
			Map<String, Integer> map = new HashMap<>();
			map.put("id", Integer.parseInt(value));
			return clienteService.obtenerClientePorId(map);
			
		}
		else{
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(((value != null) && ((Cliente) value).getId() != null )){
			return ((Cliente) value).getId().toString();
		}
		else{
			return null;
		}
	}

}
