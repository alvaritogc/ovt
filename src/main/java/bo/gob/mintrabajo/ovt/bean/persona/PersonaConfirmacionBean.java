package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/30/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PersonaConfirmacionBean {

    private String loginParameter;
    private String passwordParameter;
    private String passwordConfirm;

    public PersonaConfirmacionBean(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        passwordParameter = Util.decrypt((String) params.get("codeUnic"));
        setLoginParameter((String) params.get("codeNam"));
        setPasswordParameter(passwordParameter);

        System.out.println(" Password ---- " + passwordParameter + " -------------- login " + (String) params.get("codeNam"));
    }

    public void confirmaRegistro(){

        if(passwordParameter.equals(passwordConfirm)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Confirmado", " El registro se confirmó correctamente"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "El password no es correcto o expiró el tiempo para confirmar la cuenta"));
        }
    }



    public static void main (String args[]){
        PersonaConfirmacionBean p = new PersonaConfirmacionBean();
        p.confirmaRegistro();
    }



























    // ***** Getter y Setters *****//

    public String getLoginParameter() {
        return loginParameter;
    }

    public void setLoginParameter(String loginParameter) {
        this.loginParameter = loginParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
