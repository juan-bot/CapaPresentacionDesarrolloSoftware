package cursoDAgil.managedBean.direccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import cursoDAgil.bd.domain.Direccion;
import cursoDAgil.service.direccion.DireccionService;

@Named
@ViewScoped
public class direccionBean {
	@Inject
	DireccionService direccionService;
	private List<Direccion> listaDireccion;
	private Direccion direccion;

	@PostConstruct
	public void init() {
		if (listaDireccion == null)
			listaDireccion = new ArrayList<Direccion>();
		if (direccion == null)
			direccion = new Direccion();
		// se invoca el metodo del servicio para obtener los clientes

		// con su dirección
		setlistaDireccion(direccionService.obtenerDirecciones());
		// setlistaCliente(clienteService.findAllClientes());
	}

	public void eliminar() {
		
		Map<String,Integer> mapDireccion= new HashMap<>();
		mapDireccion.put("idDireccion", Integer.parseInt (getDireccion().getCalle()));
		
		try{
			System.out.println("\nELIMINAR DIRECCION");
			
			direccionService.eliminarDireccion(mapDireccion);
			setlistaDireccion(direccionService.obtenerDirecciones());
			getlistaDireccion();
			
			FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Dirección eliminada!"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("No se pudo eliminar la dirección!"));
			System.out.println("Error al eliminar direccion por Id en (eliminar() [direccionBean.java]): " + e);
		}
		
		setDireccion(new Direccion());
    }

	public void registrar() {
		// invocar al servicio
		direccionService.nuevaDireccionCliente(getDireccion());

		// limpia los valores del objeto
		setDireccion(new Direccion());

		// se actualiza los valores de la tabla
		setlistaDireccion(direccionService.obtenerDirecciones());

		// setlistaCliente(clienteService.findAllClientes());
		getlistaDireccion();

		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Registro exitoso!"));
	}

	public void buscar() {
		Map<String, Integer> mapDireccion = new HashMap<>();
		mapDireccion.put("idDireccion", direccion.getIdDireccion());
		setDireccion(new Direccion());

		try {
			setDireccion(direccionService.obtenerDireccionPorId(mapDireccion));
			System.out.println("\n BUSCAR POR ID");
			System.out.println("\tID: " + direccion.getIdDireccion());
			System.out.println("\tCalle: " + direccion.getCalle());
			System.out.println("\tNúmero: " + direccion.getNumero().toString());
			System.out.println("\tColonia: " + direccion.getColonia());
			System.out.println("\tCiudad: " + direccion.getCiudad());
			System.out.println("\tPais: " + direccion.getPais());
			System.out.println("\tC.P: " + direccion.getCodigoPostal());

		} catch (Exception e) {
			System.out.println("Error al buscar direccion por Id en (buscar() [direccionBean.java]): " + e);
		}
	}

	public void onRowEdit(RowEditEvent event) {
		Direccion dir = ((Direccion) event.getObject());
		// dir.setDireccion(direccion.getDireccion());
		direccionService.editarDireccion(dir);
		FacesMessage msg = new FacesMessage("Direccion editada", dir.getIdDireccion().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edicion cancelada",
				((Direccion) event.getObject()).getIdDireccion().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		System.out.println("verifica: " + newValue);
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Direccion modificada",
					"Antes: " + oldValue + ", Ahora: " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	// setters y getters
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public List<Direccion> getlistaDireccion() {
		return listaDireccion;
	}

	public void setlistaDireccion(List<Direccion> listaDireccion) {
		this.listaDireccion = listaDireccion;
	}
}
