package cursoDAgil.managedBean.ventas;

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

import cursoDAgil.bd.domain.Cliente;
import cursoDAgil.bd.domain.DetalleVentas;
import cursoDAgil.bd.domain.Ganancias;
import cursoDAgil.bd.domain.Productos;
import cursoDAgil.bd.domain.Ventas;
import cursoDAgil.service.cliente.ClienteService;
import cursoDAgil.service.detalleventas.DetalleVentasService;
import cursoDAgil.service.ganancias.GananciaService;
import cursoDAgil.service.producto.ProductoService;
import cursoDAgil.service.ventas.VentasService;

@Named
@ViewScoped
public class VentasDetalleVentas2Bean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8358412927555057997L;
	
	@Inject
	VentasService ventasService;
	@Inject
	DetalleVentasService detalleVentasService;
	@Inject
	ClienteService clienteService;
	@Inject
	ProductoService productoService;
	@Inject
	GananciaService gananciaService;
	
	private List <Ventas> listaVentas;
	private Ventas venta;
	private Integer numberOfRows;
	private List <Cliente> listaClientes;
	private Cliente cliente;
	private List <DetalleVentas> listaDetalleVentas;
	private DetalleVentas detalleVentas;
	private Productos producto;
	private List <Productos> listaProductos;
	private Ganancias ganancia;
	private List <Ventas> listaQuery;
	private List <Ventas> listaQueryId;
	private List <DetalleVentas> listaQueryDetalleId;
	
	public List<DetalleVentas> getListaQueryDetalleId() {
		return listaQueryDetalleId;
	}

	public void setListaQueryDetalleId(List<DetalleVentas> listaQueryDetalleId) {
		this.listaQueryDetalleId = listaQueryDetalleId;
	}

	public List<Ventas> getListaQueryId() {
		return listaQueryId;
	}

	public void setListaQueryId(List<Ventas> listaQueryId) {
		this.listaQueryId = listaQueryId;
	}

	public List<Ventas> getListaQuery() {
		return listaQuery;
	}

	public void setListaQuery(List<Ventas> listaQuery) {
		this.listaQuery = listaQuery;
	}

	public Ganancias getGanancia(){
		return this.ganancia;
	}
	
	public void setGanancia(Ganancias ganancia){
		this.ganancia = ganancia;
	}
	
	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public List<Productos> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Productos> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public void setListaClientes(List <Cliente> listaClientes){
		this.listaClientes = listaClientes;
	}
	
	public List<Cliente> getListaClientes(){
		return this.listaClientes;
	}
	
	public DetalleVentas getDetalleVentas(){
		return this.detalleVentas;
	}
	
	public void setDetalleVentas(DetalleVentas detalleVentas){
		this.detalleVentas = detalleVentas;
	}
	
	public List<DetalleVentas> getListaDetalleVentas(){
		return this.listaDetalleVentas;
	}
	
	public void setListaDetalleVentas(List<DetalleVentas> listaDetalleVentas){
		this.listaDetalleVentas = listaDetalleVentas;
	}
	
	public Cliente getCliente(){
		return this.cliente;
	}
	
	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}

	public Integer getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(Integer numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public List<Ventas> getListaVentas() {
		return listaVentas;
	}
	
	public void setListaVentas(List<Ventas> listaVentas) {
		this.listaVentas = listaVentas;
	}
	
	public Ventas getVenta() {
		return venta;
	}
	
	public void setVenta(Ventas venta) {
		this.venta = venta;
	}
	
	public void registrar(){
		
		Integer auxVentaId = ventasService.numberOfRows() + 1;
		venta.setIdVenta(auxVentaId);
		venta.setClienteId(cliente.getId());
		ventasService.nuevaVenta(getVenta());
		
		detalleVentas.setVentaId(auxVentaId);
		detalleVentas.setProductoId(producto.getIdProducto());
		detalleVentasService.nuevoDetalleVenta(getDetalleVentas());
		
		Map <String, Integer> map = new HashMap<>();
		map.put("idProducto", producto.getIdProducto());
		producto = productoService.listarProductosPorId(map);
		producto.setCantidad(producto.getCantidad() - detalleVentas.getCantidad());
		productoService.actualizaProducto(producto);
		
		ganancia.setIdGanancia(gananciaService.numberOfRows() + 1);
		ganancia.setVentaId(auxVentaId);
		ganancia.setTotalGanancia( (producto.getPrecioVta() - producto.getPrecio()) * detalleVentas.getCantidad() );
		gananciaService.nuevaGanancia(ganancia);
		
		setProducto( new Productos());
		setVenta( new Ventas());
		setCliente( new Cliente());
		setGanancia( new Ganancias());
		
		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Se registro la nueva venta"));

		setListaVentas(ventasService.listarVentasTodo());
		setListaDetalleVentas(detalleVentasService.listarTodasDetalleVentas());
		getListaVentas();
		getListaDetalleVentas();		
	}
	
	public void ventaPorId(){
		Map<String,Integer> map = new HashMap<>();
		map.put("idVenta", venta.getIdVenta());
		setListaQueryId(ventasService.ListarVentaPorId(map));
		getListaQueryId();
		setVenta(new Ventas());
	}
	
	public void detallePorId(){
		Map<String, Integer> map = new HashMap<>();
		map.put("ventaId", detalleVentas.getVentaId());
		setListaQueryDetalleId(detalleVentasService.DetalleVentaPorId(map));
		getListaQueryDetalleId();
		setDetalleVentas(new DetalleVentas());
	}
	
	public void update(){
		setListaVentas(ventasService.listarVentasTodo());
		setListaDetalleVentas(detalleVentasService.listarTodasDetalleVentas());
		getListaVentas();
		getListaDetalleVentas();
	}
	
	@PostConstruct
	public void init(){
		if(venta == null){
			venta = new Ventas();
		}
		if(listaVentas == null){
			listaVentas = new ArrayList<Ventas>();		
		}
		if(cliente == null){
			cliente = new Cliente();
		}
		if(listaClientes == null){
			listaClientes = new ArrayList<Cliente>();
		}
		if(detalleVentas == null){
			detalleVentas = new DetalleVentas();
		}
		if(listaDetalleVentas == null){
			listaDetalleVentas = new ArrayList<DetalleVentas>();
		}
		if(producto == null){
			producto = new Productos();
		}
		if(listaProductos == null){
			listaProductos = new ArrayList<Productos>();
		}
		if(ganancia == null){
			ganancia = new Ganancias();
		}
		if(listaQuery == null){
			listaQuery = new ArrayList<Ventas>();
		}
		if(listaQueryId == null){
			listaQueryId = new ArrayList<Ventas>();
		}
		if(listaQueryDetalleId == null){
			listaQueryDetalleId = new ArrayList<DetalleVentas>();
		}
		setNumberOfRows(ventasService.numberOfRows());
		setListaVentas(ventasService.listarVentasTodo());
		setListaDetalleVentas(detalleVentasService.listarTodasDetalleVentas());
		setListaClientes(clienteService.listarTodosClientes());
		setListaProductos(productoService.listarTodosProductos());
		setListaQuery(ventasService.listarVentasPorCliente());
	}
}
