package cursoDAgil.managedBean.cliente;

import java.io.Serializable;
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

import cursoDAgil.bd.domain.Cliente;
import cursoDAgil.service.cliente.ClienteService;

@Named
@ViewScoped
public class ClienteDireccion2Bean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044132846828937456L;
	
	@Inject
	ClienteService clienteService;
	
	private List<Cliente> listaCliente;
	private Cliente cliente;
	private Cliente clienteQuery;
	
	public Cliente getClienteQuery() {
		return clienteQuery;
	}

	public void setClienteQuery(Cliente clienteQuery) {
		this.clienteQuery = clienteQuery;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void doClienteQuery (){
		clienteQuery = new Cliente();
		Map<String, Integer> map = new HashMap<>();
		map.put("id", cliente.getId());
		setClienteQuery(clienteService.obtenerClientePorId(map));
	}

	@PostConstruct
	public void init(){
		if(listaCliente == null){
			listaCliente = new ArrayList<Cliente>();
		}
		if(cliente == null){
			cliente = new Cliente();
			cliente.setDireccion(null);
		}
		if(clienteQuery == null){
			clienteQuery = new Cliente();
		}
		
		setListaCliente(clienteService.listarTodosClientes());
	}
	
	public void delete(){
		Map<String,Integer> map = new HashMap<>();
		map.put("id", cliente.getId());
		if(clienteService.eliminarCliente(map) == 1){
			FacesMessage msg = new FacesMessage("Cliente eliminado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			setCliente(new Cliente());
			setListaCliente(clienteService.listarTodosClientes());
			getListaCliente();
		}
		else{
			FacesMessage msg = new FacesMessage("Marca no eliminada, revisa las dependencias");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			setCliente(new Cliente());
		}
	}
	
	public void registrar(){
		cliente.setId(clienteService.numberOfRows() + 1);
		clienteService.nuevoCliente(getCliente());
		setCliente(new Cliente());
		setListaCliente(clienteService.listarTodosClientes());
		getListaCliente();
		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Registro exitoso!"));
	}
	
	public void onRowEdit(RowEditEvent event){
		Cliente cli = (Cliente) event.getObject();
		cli.setDireccion(cliente.getDireccion());
		clienteService.editarCliente(cli);
		FacesMessage msg = new FacesMessage("Cliente editado", cli.getId().toString());
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
	
	public Integer numberOfRows(){
		
		return 0;
	}
}
