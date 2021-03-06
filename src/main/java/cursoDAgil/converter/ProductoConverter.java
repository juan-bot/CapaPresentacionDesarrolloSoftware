package cursoDAgil.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Productos;
import cursoDAgil.service.producto.ProductoService;

@Named
public class ProductoConverter implements Converter{

	@Inject
	ProductoService productoService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && (value.trim().length() > 0)){
			try{
				Map<String, Integer> map = new HashMap<>();
				map.put("idProducto", Integer.parseInt(value));
				return productoService.listarProductosPorId(map);
			}
			catch(NumberFormatException e){
				return null;
			}
		}
		else{
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(((value != null) && ((Productos) value).getIdProducto() != null )){
			return ((Productos) value).getIdProducto().toString();
		}
		else{
			return null;
		}
	}
}
