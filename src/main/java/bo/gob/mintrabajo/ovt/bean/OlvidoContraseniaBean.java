package bo.gob.mintrabajo.ovt.bean;

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

@ManagedBean(name = "olvidoContraseniaBean")
@ViewScoped
public class OlvidoContraseniaBean implements Serializable {
    //

    private HttpSession session;

    private String contrasenia;
    private String nuevaContrasenia;
    private String confirmarContrasenia;

    private static final Logger logger = LoggerFactory.getLogger(OlvidoContraseniaBean.class);



    private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private HttpServletRequest httpServletRequest = ((HttpServletRequest) externalContext.getRequest());

    private String email=getParam("codeNam");

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @PostConstruct
    public void ini() {
        logger.info("=====>>>>  OLVIDO CONTRASENIA");
        logger.info("=====>>>>  PARAMETRO PASADO POR GET "+email);
        //

        //
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

            cargar();
        } catch (Exception e) {
//            e.printStackTrace();

        }

    }

    public void cargar() {
        System.out.println("cargar()");
/*        model = new DefaultMenuModel();
        try {
            listaRecursos = iRecursoService.buscarPorUsuario(idUsuario);
            logger.info("nro recursos del usuario:" + listaRecursos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        crearMenuRecurso();
        DefaultMenuItem item = new DefaultMenuItem("Salir");
        item.setIcon("ui-icon-arrowthickstop-1-e");
        item.setCommand("#{templateInicioBean.logout}");
        model.addElement(item);*/
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

    public void verificarContrasenia(){

        UsrUsuario usuario= iUsuarioService.findByUsuarioAndClave(email,contrasenia,nuevaContrasenia);

/*         if (nuevaContrasenia.equals(confirmarContrasenia)){
            //Actualizar la nueva contrasenia
            // return  true;
             UsrUsuario usuario= iUsuarioService.findByUsuarioAndClave(email,contrasenia,nuevaContrasenia);
             if(usuario==null){
                 FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La contrasenia asociada a su cuenta es incorrecta."));
                 ini();
                 return ;
             }

             if(contrasenia.equals(nuevaContrasenia)){
                 FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","El valor de la nueva contrasenia asociada debe ser distinta a la anterior contrasenia."));
                 ini();
                 return ;
             }
             System.out.println("====>>> ANTIGUA CONTRASENIA "+usuario.getClave());
              usuario.setClave(nuevaContrasenia);
             System.out.println("====>>> NUEVA CONTRASENIA "+usuario.getClave());
             //usuario=iUsuarioService.save(usuario);
             System.out.println("====>>> NUEVA CONTRASENIA EXITO");
             FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Se cambio la contrasenia."));
             ini();
             return ;

         }else {
                FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","El valor del campo Nueva contrasenia debe ser igual al campo Confirmar contrasenia."));
                 ini();
                 return ;
         }*/

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



    public String irInicio() {
   /*     try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
            usuario = iUsuarioService.findById(idUsuario);
            if(usuario.getEsInterno()==1){
                return "irEmpleadorBusqueda";
            }
            else{
                return "irEscritorio";
            }
        } catch (Exception e) {
            System.out.println("No se encontro la session");
        }*/
        return "irInicio";
    }



    public void olvidoContrasenia(){
/*        System.out.println("=============>>>>> OLVIDO SU CONTRASENIA contrasenia:"+contrasenia+" confirmarContrasenia "+confirmarContrasenia);
        PerUsuario perUsuario=iPerUsuarioService.obtenerPorNITyEmail(nit,email);
       if(perUsuario==null) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR ", "No existe un usuario cuyo Nro. de identificacion (NIT)  y correo electronico sean: "+nit+", "+email));
       }

           //System.out.println("======>>>>> NO EXISTE USUARIO");
       else
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO ", " Verifique su correo electronico"));*/

    }

    /*
    ****************************************
    *
    *           GETTER Y SETTER
    *
    * **************************************
     */



    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }

    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }
}

