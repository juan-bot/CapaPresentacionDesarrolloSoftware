package cursoDAgil.managedBean.marcas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cursoDAgil.bd.domain.Marcas;
import cursoDAgil.service.marcas.MarcasService;
@Named
@ViewScoped
public class marcasTBean implements Serializable{

	private static final long serialVersionUID = 4550549561362528239L;

	@Inject
	MarcasService marcasService;
	
	private List<Marcas> listaMarcas;
	private Marcas marca;

	@PostConstruct
	public void init() {
		if (listaMarcas== null)
			listaMarcas= new ArrayList<Marcas>();
		if (marca== null) 
			marca= new Marcas();

		// se invoca el metodo del servicio para obtener los clientes con su dirección
		setListaMarcas(marcasService.listarTodasMarcas());
		
	}
	public List<Marcas> getListaMarcas(){
		return listaMarcas;
	}
	
	public void setListaMarcas(List<Marcas> listaMarcas) {
		this.listaMarcas= listaMarcas;
	}
	public Marcas getMarca(){
		return marca;
	}
	public void setMarca(Marcas marca){
		this.marca=marca;
	}
}
