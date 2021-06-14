package cursoDAgil.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Marcas;
import cursoDAgil.service.marcas.MarcasService;

@Named
public class MarcasConverter implements Converter{
	@Inject
	MarcasService marcasService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value){
		Map<String,Integer> mapMarcas = new HashMap<>();
		mapMarcas.put("idMarca", Integer.parseInt(value));
		
		if(value!=null && (value.trim().length()>0)){
			return marcasService.listarMarcaPorId(mapMarcas);
		}else{
			return null;
		}
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value){
		if(((value!=null)&&((Marcas) value).getIdMarca()!=null)){
			return ((Marcas) value).getIdMarca().toString();
		}else{
			return null;
		}
	}
}
