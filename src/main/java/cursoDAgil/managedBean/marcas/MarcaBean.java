package cursoDAgil.managedBean.marcas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import cursoDAgil.bd.domain.Marcas;
import cursoDAgil.service.marcas.MarcasService;
import javafx.scene.control.TableColumn.CellEditEvent;

@Named
@ViewScoped
public class MarcaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	MarcasService marcasService;

	private List<Marcas> listaMarcas;
	private Marcas marcas;

	@PostConstruct
	public void init() {
		if (listaMarcas == null)
			listaMarcas = new ArrayList<Marcas>();
		if (marcas == null) {
			marcas = new Marcas();
		}

		setlistaMarcas(marcasService.listarTodasMarcas());
	}

	public void registrar() {
		marcas.setIdMarca(marcasService.numberOfRows() + 1);
		marcasService.nuevaMarca(getMarcas());
		setMarcas(new Marcas());
		setlistaMarcas(marcasService.listarTodasMarcas());
		getlistaMarcas();
		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage("Regustro exitoso!"));
	}

	public void onRowEdit(RowEditEvent event) {
		Marcas mar = ((Marcas) event.getObject());
		marcasService.actualizaMarca(mar);
		FacesMessage msg = new FacesMessage("Marca editada", mar.getIdMarca().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada", ((Marcas) event.getObject()).getIdMarca().toString());
	}

	public void OnCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verfica:" + newValue);
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca modificada",
					"Antes" + oldValue + ", Ahora:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public Marcas getMarcas() {
		return marcas;
	}

	public void setMarcas(Marcas marcas) {
		this.marcas = marcas;
	}

	public List<Marcas> getlistaMarcas() {
		return listaMarcas;
	}

	public void setlistaMarcas(List<Marcas> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}
}
