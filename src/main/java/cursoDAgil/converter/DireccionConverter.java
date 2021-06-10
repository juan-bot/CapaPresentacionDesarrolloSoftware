package cursoDAgil.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Direccion;
import cursoDAgil.service.direccion.DireccionService;

@Named
public class DireccionConverter implements Converter {
	@Inject
	DireccionService direccionService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Map<String, Integer> mapDireccion = new HashMap<>();
		mapDireccion.put("idDireccion", Integer.parseInt(value));
		if (value != null && (value.trim().length() > 0)) {
			return direccionService.obtenerDireccionPorId(mapDireccion);

		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (((value != null) && ((Direccion) value).getIdDireccion()!= null)){
			return ((Direccion) value).getIdDireccion().toString();
		} else {
			return null;
		}
	}

}
