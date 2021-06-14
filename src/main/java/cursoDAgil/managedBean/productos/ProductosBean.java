package cursoDAgil.managedBean.productos;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import cursoDAgil.bd.domain.Productos;
import cursoDAgil.service.producto.ProductoService;

@Named
@ViewScoped
public class ProductosBean {
	@Inject
	ProductoService productoService;
	
	private List<Productos> ListaProductos;

	public List<Productos> getProductosList() {
		if (ListaProductos == null)
			setProductosList(productoService.listarTodosProductos());
		return ListaProductos;
	}

	public void setProductosList(List<Productos> productoList) {
		this.ListaProductos = productoList;
	}

	public void onRowEdit(RowEditEvent event) {
		Productos producto= ((Productos) event.getObject());
		System.out.println("datos Producto: " + producto.getIdProducto());
		productoService.actualizaProducto(producto);
		FacesMessage msg = new FacesMessage("Producto editado", producto.getIdProducto().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edicion cancelada", ((Productos) event.getObject()).getIdProducto().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		System.out.println("verifica: " + newValue);
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente modificado",
					"Antes: " + oldValue + ", Ahora: " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
