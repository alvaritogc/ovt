package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.bean.TemplateInicioBean;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
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

    @ManagedProperty(value="#{dominioService}")
    public IDominioService iDominioService;

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;


    private static final Logger logger = LoggerFactory.getLogger(PersonaConfirmacionBean.class);

    public PersonaConfirmacionBean() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String passwordCode = params.get("codeUnic").toString();
        String passwordCodeFinal = "";
        if (passwordCode != null) {
            for (int x = 0; x < passwordCode.length(); x++) {
                if (passwordCode.charAt(x) != ' ') {
                    passwordCodeFinal += passwordCode.charAt(x);
                } else {
                    passwordCodeFinal += '+';
                }
            }
            passwordParameter = Util.decrypt(passwordCodeFinal);
            setLoginParameter((String) params.get("codeNam"));
            setPasswordParameter(passwordParameter);

            logger.info(" Password ---- " + passwordParameter);
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Operación Inválida"));
        }
    }

    public String confirmaRegistro() {
        logger.info("Ingresando a la clase " + getClass() + " metodo confirmaRegistro()");
        String passwordEncripted = Util.encriptaMD5(getPasswordConfirm());
        if (passwordParameter.equals(passwordEncripted)) {
            try {
                logger.info("Comparando passwords " + Util.encriptaMD5(getPasswordConfirm()));

                Long idUsuario = iUsuarioService.loginConfirmacion(getLoginParameter(), passwordEncripted);
                logger.info("usuario aceptado");
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("idUsuario", idUsuario);
                UsrUsuario usuario = iUsuarioService.findById(idUsuario);

                if(usuario.getEstadoUsuario().equals("A")) {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "La cuenta ya esta confirmada, ingrese al sistema por el login principal del portal"));
                  return null;
                }

                String ipCliente = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("X-FORWARDED-FOR");
                if(ipCliente == null){
                    ipCliente = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
                }
                String bitacoraSession = usuario.getUsuario() + "|" + ipCliente;
                session.setAttribute("bitacoraSession", bitacoraSession);

                long fechaBitacoraLong = usuario.getFechaBitacora().getTime();
                long fechaActualLong = new Date().getTime();
                long diferenciaLong = fechaActualLong - fechaBitacoraLong;

                int timer = Integer.parseInt(iParametrizacion.obtenerParametro(ID_PARAMETRO_TIMER, VALOR_TIEMPO_VALIDO).getDescripcion());

                cambiaEstadoUsuario(usuario.getIdUsuario());

                if(TemplateInicioBean.mapaDominio.size() == 0){
                    cargarDominio();
                }

                if ((TimeUnit.MINUTES.toMillis(timer) > diferenciaLong)) {
                    session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());
                    if (usuario.getEsInterno() == 1) {
                        session.setAttribute("idEmpleador", null);
                        UsernamePasswordToken token = new UsernamePasswordToken(usuario.getUsuario(), passwordEncripted);
                        Subject subject = SecurityUtils.getSubject();
                        token.setRememberMe(true);
                        subject.login(token);

                        token.clear();
                        return "irEmpleadorBusqueda";

                    } else {
                        session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                        UsernamePasswordToken token = new UsernamePasswordToken(usuario.getUsuario(), passwordEncripted);
                        Subject subject = SecurityUtils.getSubject();
                        token.setRememberMe(true);
                        subject.login(token);

                        token.clear();
                        return "irEscritorio";
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidado", "El tiempo para registrarse expiró, intente registrarse de nuevo"));
                    logger.info("Eliminando .... " + idUsuario);
                    iPersonaService.eliminarRegistro(usuario.getIdPersona().getIdPersona(), usuario);
                    return null;
                }
            } catch (RuntimeException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Controle que el password ingresado sea el mismo del registro inicial"));
                e.printStackTrace();
                return null;
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidado", "El password no es correcto"));
            return null;
        }
    }

    public Cache<ParDominioPK, ParDominio> cargarDominio() {
        List<ParDominio> todosDominioLista = iDominioService.obtenerDominioLista();
        logger.info("El numero de dominios en base de datos " + todosDominioLista.size() + ", tamaño de objetos guardados en cache " + TemplateInicioBean.mapaDominio.size());
        if(todosDominioLista.size() != TemplateInicioBean.mapaDominio.size()){

            for (ParDominio d : todosDominioLista) {
                ParDominio parDominio = TemplateInicioBean.mapaDominio.getIfPresent(d.getParDominioPK());

                if (parDominio == null) {
                    logger.debug("No existe en la cache este objeto es agregado a cache " + d.getParDominioPK().getIdDominio() + " " + d.getParDominioPK().getValor());
                    TemplateInicioBean.mapaDominio.put(d.getParDominioPK(), d);
                }else{
                    logger.info("Este dominio existe en la cache pasamos al siguiente " + parDominio.getParDominioPK().getIdDominio() + " " + d.getParDominioPK().getValor());
                    continue;
                }
            }
        }else{
            logger.info("El mapa esta cargado con la misma cantidad de datos en cache ");
        }
        return TemplateInicioBean.mapaDominio;
    }

    public void cambiaEstadoUsuario(Long idUsuario){
        iPersonaService.cambiarEstadoUsuario(idUsuario);
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

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }
}
