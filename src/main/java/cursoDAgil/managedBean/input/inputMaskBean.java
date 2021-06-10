package cursoDAgil.managedBean.input;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class inputMaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4472342451549784365L;
	private String date;
	private String phone;
	private String key;

	// método que muestra las mascaras en un growl personalizado
	public void showMask() {
		System.out.println("si entraaaaaaaaaaaaaaaaaaaaaaaaaaa");
		try{
			FacesContext.getCurrentInstance().addMessage("growlMensaje", new FacesMessage(FacesMessage.SEVERITY_INFO, "Mascara Primefaces!!", "Date: " + getDate() + " Phone: " + getPhone() + " key: " + getKey()));
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

	// setters y getters
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		System.out.println("set");
		this.phone = phone;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		System.out.println("set");
		this.key = key;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		System.out.println("set");
		this.date = date;
	}
}
