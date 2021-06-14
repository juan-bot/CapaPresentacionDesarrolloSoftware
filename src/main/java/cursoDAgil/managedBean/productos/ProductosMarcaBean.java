package cursoDAgil.managedBean.productos;

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

import cursoDAgil.bd.domain.Productos;
import cursoDAgil.service.marcas.MarcasService;
import cursoDAgil.service.producto.ProductoService;

@Named
@ViewScoped
public class ProductosMarcaBean implements Serializable {
	private static final long serialVersionUID = -5044132846828937456L;

	@Inject
	ProductoService productoService;

	@Inject
	MarcasService marcasService;

	private List<Productos> listaProductos;
	private Productos producto;

	@PostConstruct
	public void init() {
		if (listaProductos == null) {
			listaProductos = new ArrayList<Productos>();
		}
		if (producto == null) {
			producto = new Productos();
		}
		setlistaProducto(productoService.listarTodosProductos());
	}

	public void registrar() {

		Productos prod = getProducto();

		System.out.println("\nNUEVO PRODUCTO");
		imprimeProducto();

		producto.setMarcaId(producto.getMarca().getIdMarca());
		productoService.nuevoProducto(getProducto());

		setProducto(new Productos());

		setlistaProducto(productoService.listarTodosProductos());

		getlistaProducto();

		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Registro exitoso!"));
	}

	public void buscar() {
		Map<String, Integer> mapProductos = new HashMap<>();
		mapProductos.put("idProducto", producto.getIdProducto());

		setProducto(new Productos());

		try {
			setProducto(productoService.listarProductosPorId(mapProductos));

			Map<String, Integer> mapMarca = new HashMap<>();
			mapMarca.put("idMarca", producto.getMarcaId());
			producto.setMarca(marcasService.listarMarcaPorId(mapMarca));

			System.out.println("\nBUSCAR POR ID");
			imprimeProducto();

		} catch (Exception e) {
			System.out.println("\n\n\n\nBUSCAR POR ID - No se encontró el producto!");
			System.out.println("Error al buscar producto por Id en (buscar() [productosMarcaBean.java]): " + e);
		}

		if (producto == null) {
			setProducto(new Productos());
			FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("No se encontró el producto!"));
		}

	}

	public void onRowEdit(RowEditEvent event) {
		Productos prod = ((Productos) event.getObject());
		prod.setMarcaId(producto.getMarca().getIdMarca());

		productoService.actualizaProducto(prod);
		setlistaProducto(productoService.listarTodosProductos());

		System.out.println("\nACTUALIZAR PRODUCTO");
		setProducto(prod);
		imprimeProducto();

		FacesMessage msg = new FacesMessage("Producto editado", prod.getIdProducto().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edicion cancelada",
				((Productos) event.getObject()).getIdProducto().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		System.out.println("verifica: " + newValue);
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Modificado",
					"Antes: " + oldValue + ", Ahora: " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	// setters y getters
	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public List<Productos> getlistaProducto() {
		// setProducto(new Productos());
		return listaProductos;
	}

	public void setlistaProducto(List<Productos> listaProducto) {
		this.listaProductos = listaProducto;
	}

	public void imprimeProducto() {
		getProducto();
		System.out.println("\tID: " + producto.getIdProducto());
		System.out.println("\tNombre: " + producto.getNombre());
		System.out.println("\tPrecio: " + producto.getPrecio().toString());
		System.out.println("\tPrecioVta: " + producto.getPrecioVta().toString());
		System.out.println("\tCantidad: " + producto.getCantidad().toString());
		System.out.println("\tMarca: " + producto.getMarca().getNombreMarca());
	}

	public void eliminar(){		
		System.out.println("\nELIMINAR PRODUCTO: "+ getProducto().getNombre());
		Map<String,Integer> mapProductos= new HashMap<>();
		mapProductos.put("idProducto", Integer.parseInt (getProducto().getNombre()));
		
		try{
			//imprimeProducto();
			productoService.eliminaProducto(mapProductos);
			setlistaProducto(productoService.listarTodosProductos());
			getlistaProducto();
			FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Producto eliminado!"));
		}catch(Exception e){
			System.out.println("\n\n\n\nEliminar POR ID - No se pudo eliminar el producto!");
			System.out.println("Error al eliminar producto por Id en (eliminar() [productosMarcaBean.java]): " + e);
		}
		setProducto(new Productos());
	}
}
