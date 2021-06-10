package cursoDAgil.managedBean.direccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Direccion;
import cursoDAgil.service.direccion.DireccionService;

@Named
@ViewScoped
public class direccionTBean implements Serializable{

	private static final long serialVersionUID = -1630203213498049352L;
	
	@Inject
	DireccionService direccionService;
	private List<Direccion> listaDirecciones;
	private Direccion direccion;

	@PostConstruct
	public void init() {
		if (listaDirecciones == null)
			listaDirecciones = new ArrayList<Direccion>();
		if (direccion == null) 
			direccion = new Direccion();

		// se invoca el metodo del servicio para obtener los clientes con su dirección
		setListaDireccion(direccionService.obtenerDirecciones());
		// setlistaCliente(clienteService.findAllClientes());
	}
	public List<Direccion> getListaDireccion(){
		return listaDirecciones;
	}
	
	public void setListaDireccion(List<Direccion> listaDirecciones) {
		this.listaDirecciones = listaDirecciones;
	}
	public Direccion getDireccion(){
		return direccion;
	}
	public void setDireccion(Direccion direccion){
		this.direccion=direccion;
	}
}
