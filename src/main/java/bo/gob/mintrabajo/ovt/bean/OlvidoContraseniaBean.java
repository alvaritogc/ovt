package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IPerUsuarioService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//package bo.gob.mintrabajo.ovt.Util;

@ManagedBean(name = "olvidoContraseniaBean")
@ViewScoped
public class OlvidoContraseniaBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(OlvidoContraseniaBean.class);

    private HttpSession session;
    private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private HttpServletRequest httpServletRequest = ((HttpServletRequest) externalContext.getRequest());

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;


    private String contrasenia;

    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }

    private String nuevaContrasenia="";

    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }

    private String confirmarContrasenia="";

    //private String email=getParam("codeNam");
    private String email="";



    @PostConstruct
    public void ini() {
        logger.info("=====>>>>  OLVIDO CONTRASENIA");
        email=getParam("codeNam");
        contrasenia=getParam("codeUnic");
        logger.info("=====>>>>  PARAMETRO PASADO POR GET correo: "+email);
        logger.info("=====>>>>  PARAMETRO PASADO POR GET password: "+contrasenia);
/*        contrasenia=Util.decrypt(contrasenia);
        logger.info("=====>>>>  CONTRASENIA DESCENCRIPTADA "+contrasenia);*/
    }

    public String verificarContrasenia()throws  IOException{
        logger.info("=====>>>>  INICIO VERIFICAR CONTRASENIA");
        System.out.println("=====>>>>  email: "+email);
        System.out.println("=====>>>>  contrasenia: "+contrasenia);
        System.out.println("=====>>>>  nuevaContrasenia: "+nuevaContrasenia);
        System.out.println("=====>>>>  confirmarContrasenia: "+confirmarContrasenia);

        nuevaContrasenia=Util.encriptaMD5(nuevaContrasenia);
        confirmarContrasenia=Util.encriptaMD5(confirmarContrasenia);
        logger.info("====>>>> verificarContrasenia: email:" + email + " contrasenia: " + contrasenia + " nuevaContrasenia " + nuevaContrasenia + " confirmarContrasenia: " + confirmarContrasenia);
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        final int LONGITUD_MINIMA=7;
        if(nuevaContrasenia.length()<LONGITUD_MINIMA){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La longitud minima de la contrasenia es "+LONGITUD_MINIMA+". Intente nuevamente"));
            ini();
            return "";
        }

        if(email==null && email.trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Correo electronico es obligatorio."));
            ini();
            return "";
        }else{
            if(!validarEmail(email)){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL formato del correo electronico es incorrecto."));
                ini();
                return "";
            }
        }

        if(contrasenia==null && contrasenia.equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Contraseña es obligatorio."));
            ini();
            return "";
        }

        System.out.println("=====>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
         System.out.println("=====>>>>  email: "+email);
        System.out.println("=====>>>>  contrasenia: "+contrasenia);
        System.out.println("=====>>>>  nuevaContrasenia: "+nuevaContrasenia);
        System.out.println("=====>>>>  confirmarContrasenia: "+confirmarContrasenia);
        String mensaje= iUsuarioService.cambiarContrasenia(email,contrasenia,nuevaContrasenia,confirmarContrasenia);

        if(mensaje.equalsIgnoreCase("OK")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se cambio la contrasenia."));
            //ini();
            ctx.redirect(ctxPath + "/faces/pages/inicio.xhtml");
            return "";
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",mensaje));
            ini();
            return "";
        }

    }

    public boolean validarEmail(String email){
        Pattern patron = Pattern.compile("^[\\w-\\.]+\\@[\\w\\.-]+\\.[a-z]{2,4}$");
        Matcher encajador = patron.matcher(email);
        if (encajador.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtiene el parametro pasado por GET
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        name = httpServletRequest.getParameter(name);
        if (name == null) {
            return null;
        }
        else
            return  name;
       /* Pattern p = Pattern.compile("\\d+$");
        Matcher m = p.matcher(name);
        if (m.matches()) {
            return Long.parseLong(name, 10);
        }
        return null;*/
    }

    public String logout() {
        logger.info("logout()");
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        // Usar el contexto de JSF para invalidar la sesión,
        // NO EL DE SERVLETS (nada de HttpServletRequest)
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        ((HttpSession) ctx.getSession(false)).invalidate();

        // Redirección de nuevo con el contexto de JSF,
        // si se usa una HttpServletResponse fallará.
        // Sin embargo, como ya está fuera del ciclo de vida
        // de JSF se debe usar la ruta completa 
        try {
            //ctx.redirect(ctxPath + "/faces/index.xhtml");
            ctx.redirect(ctxPath + "/faces/pages/inicio.xhtml");
        } catch (IOException e) {
            System.out.println("Error en cerrar sesion");
            e.printStackTrace();
        }
        return "";
    }


    /*
    ****************************************
    *
    *           GETTER Y SETTER
    *
    * **************************************
     */

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


}

