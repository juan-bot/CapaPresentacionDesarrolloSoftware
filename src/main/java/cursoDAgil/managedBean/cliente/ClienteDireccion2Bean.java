package cursoDAgil.managedBean.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import cursoDAgil.bd.domain.Cliente;
import cursoDAgil.service.cliente.ClienteService;

@Named
@ViewScoped
public class ClienteDireccion2Bean implements Serializable {

	private static final long serialVersionUID = -5044132846828937456L;

	@Inject
	ClienteService clienteService;
	
	private List<Cliente> listaCliente;
	private Cliente cliente;

	@PostConstruct
	public void init() {
		if (listaCliente == null)
			listaCliente = new ArrayList<Cliente>();
		if (cliente == null) {
			cliente = new Cliente();
			cliente.setDireccion(null);
		}
		// se invoca el metodo del servicio para obtener los clientes

		// con su dirección
		setlistaCliente(clienteService.listarTodosClientes());
		// setlistaCliente(clienteService.findAllClientes());
	}

	// metodo que registra nuevo cliente
	public void registrar() {
		// invocar al servicio
		clienteService.nuevoCliente(getCliente());
		
		// limpia los valores del objeto
		setCliente(new Cliente());
		
		// se actualiza los valores de la tabla
		setlistaCliente(clienteService.listarTodosClientes());
		
		// setlistaCliente(clienteService.findAllClientes());
		getlistaCliente();
		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Registro exitoso!"));

	}

	public void onRowEdit(RowEditEvent event) {
		Cliente cli = ((Cliente) event.getObject());
		cli.setDireccion(cliente.getDireccion());
		clienteService.editarCliente(cli);
		FacesMessage msg = new FacesMessage("Cliente editado",cli.getId().toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edicion cancelada",((Cliente) event.getObject()).getId().toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		System.out.println("verifica: " + newValue);
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new

			FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente modificado", "Antes: " + oldValue + ", Ahora: " +newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	// setters y getters
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getlistaCliente() {
		return listaCliente;
	}

	public void setlistaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}
