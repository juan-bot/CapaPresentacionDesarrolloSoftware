package cursoDAgil.managedBean.ganancias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import cursoDAgil.bd.domain.Ganancias;
import cursoDAgil.service.ganancias.GananciaService;

@Named
@ViewScoped
public class GananciasBean {
	@Inject
	GananciaService gananciaService;
	private List<Ganancias> gananciaList;
	private List<Ganancias> gananciaFechaList;
	private String text="";
	
	public List<Ganancias> getGananciasList() {
		if (gananciaList == null)
			setGananciasList(gananciaService.obtenerGanancias());
		return gananciaList;

	}

	public List<Ganancias> getGananciaList() {
		return gananciaList;

	}
	public List<Ganancias> getGananciaFechaList() {
		return gananciaFechaList;

	}
	public List<Ganancias> getGananciasFecha(){
		Map<String,String> mapGanancias= new HashMap<>();
		mapGanancias.put("fecha", text);
		setGananciasFechaList(gananciaService.obtenerGananciasPorFecha(mapGanancias));
		return gananciaFechaList;
	}
	public void setGananciasList(List<Ganancias> gananciaList) {
		this.gananciaList = gananciaList;
	}
	public void setGananciasFechaList(List<Ganancias> gananciaFechaList) {
		this.gananciaFechaList = gananciaFechaList;
	}
	public void setText(String text){
		this.text=text;
	}
	public String getText(){
		return text;
	}


}
