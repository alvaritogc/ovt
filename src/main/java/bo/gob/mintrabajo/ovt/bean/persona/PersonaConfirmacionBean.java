package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.*;

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

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{parametrizacionService}")
    private IParametrizacionService iParametrizacion;


    private static final Logger logger = LoggerFactory.getLogger(PersonaConfirmacionBean.class);

    public PersonaConfirmacionBean(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String passwordCode = params.get("codeUnic").toString();
        String passwordCodeFinal = "";
        for (int x=0; x < passwordCode.length(); x++) {
            if (passwordCode.charAt(x) != ' '){
                passwordCodeFinal += passwordCode.charAt(x);
            }else{
                passwordCodeFinal += '+';
            }
        }
        passwordParameter = Util.decrypt(passwordCodeFinal);
        setLoginParameter((String) params.get("codeNam"));
        setPasswordParameter(passwordParameter);

        logger.info(" Password ---- " + passwordParameter );
    }

    public String confirmaRegistro() {
        logger.info("Ingresando a la clase " + getClass() + " metodo confirmaRegistro()");
        if (passwordParameter.equals(passwordConfirm)) {
            try {

                Long idUsuario = iUsuarioService.login(getLoginParameter(), getPasswordParameter());
                logger.info("usuario aceptado");
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("idUsuario", idUsuario);
                UsrUsuario usuario = iUsuarioService.findById(idUsuario);

                long fechaBitacoraLong = usuario.getFechaBitacora().getTime();
                long fechaActualLong = new Date().getTime();
                long diferenciaLong = fechaActualLong - fechaBitacoraLong;

                int timer = Integer.parseInt(iParametrizacion.obtenerParametro(ID_PARAMETRO_TIMER, VALOR_TIEMPO_VALIDO).getDescripcion());

                if ((TimeUnit.MINUTES.toMillis(timer) > diferenciaLong)) {

                    session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());
                    if (usuario.getEsInterno() == 1) {
                        session.setAttribute("idEmpleador", null);
                        return "irEmpleadorBusqueda";
                    } else {
                        session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                        return "irEscritorio";
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidado", "El tiempo para registrarse expiró, intente registrarse de nuevo"));
                    return null;
                }
            } catch (RuntimeException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Controle que el password ingresado sea el mismo del registro inicial"));
                return null;
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidado", "El password no es correcto"));
            return null;
        }
    }

    public void decryptar(){
        String temporal;
        temporal = Util.decrypt("T5ntQkyy68COSFokfMetEgRQj/MUA9/A6mlZ2SJoYR+w==");
        System.out.println(temporal);
    }

    public static void main (String arg[]){
        PersonaConfirmacionBean p = new PersonaConfirmacionBean();
        p.decryptar();
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

    public IParametrizacionService getiParametrizacion() {
        return iParametrizacion;
    }

    public void setiParametrizacion(IParametrizacionService iParametrizacion) {
        this.iParametrizacion = iParametrizacion;
    }
}
