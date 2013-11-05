package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.bean.TemplateInicioBean;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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
    //TemplateInicioBean templateInicioBean;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;


    private static final Logger logger = LoggerFactory.getLogger(PersonaConfirmacionBean.class);

    public PersonaConfirmacionBean(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        passwordParameter = Util.decrypt((String) params.get("codeUnic"));
        setLoginParameter((String) params.get("codeNam"));
        setPasswordParameter(passwordParameter);

        logger.info(" Password ---- " + passwordParameter + " -------------- login " + (String) params.get("codeNam"));
    }

    public String confirmaRegistro() {
        logger.info("login()");
        if (passwordParameter.equals(passwordConfirm)) {
            try {
                logger.info("iUsuarioService.login(" + getLoginParameter() + "," + getPasswordParameter() + ")");
                Long idUsuario = iUsuarioService.login(getLoginParameter(), getPasswordParameter());
                logger.info("usuario aceptado");
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("idUsuario", idUsuario);
                UsrUsuario usuario = iUsuarioService.findById(idUsuario);
                session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());
                if (usuario.getEsInterno() == 1) {
                    session.setAttribute("idEmpleador", null);
                    return "irEmpleadorBusqueda";
                } else {
                    session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                    return "irEscritorio";
                }

            } catch (RuntimeException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Controle que el password ingresado sea el mismo del registro inicial"));
                return null;
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El password no es correcto o expiró el tiempo para confirmar la cuenta"));
            return null;
        }
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

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }
}
