package cursoDAgil.managedBean.input;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "pwdBean")
@ViewScoped
public class PasswordBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6783286197571111531L;
	private String password = "";
	private String pwd1 = "";
	private String pwd2 = "";

	public void showPassword() {
		System.out.println("entra ?");
		FacesContext.getCurrentInstance().addMessage("growlMensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Password Primefaces!!!", "Password correcto: " + getPassword()));
	}

	public void compararPassword() {
		if (getPwd1().equals(getPwd2()))
			FacesContext.getCurrentInstance().addMessage("growlMensaje2", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Password Primefaces!!!", "los passwords son los mismos: " + getPwd1()));

		else
			FacesContext.getCurrentInstance().addMessage("growlMensaje2", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Password Primefaces!!!", "los passwords son diferentes: " + getPwd1() + " != " + getPwd2()));
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}
}
