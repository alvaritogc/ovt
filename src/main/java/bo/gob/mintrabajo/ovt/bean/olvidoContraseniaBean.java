package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.IPerUsuarioService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUsuario;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
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

    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{perUsuarioService}")
    private IPerUsuarioService iPerUsuarioService;


    @PostConstruct
    public void ini() {
        logger.info("TemplateInicioBean.init()");
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
        model = new DefaultMenuModel();
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
        model.addElement(item);
    }


    public boolean verificarContrasenia(){

         if (nuevaContrasenia.equals(confirmarContrasenia)){
             return  true;
         }else {
             return  false;
         }

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
        try {
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
        }
        return "irInicio";
    }



    public void olvidoContrasenia(){
        System.out.println("=============>>>>> OLVIDO SU CONTRASENIA contrasenia:"+contrasenia+" confirmarContrasenia "+confirmarContrasenia);
        PerUsuario perUsuario=iPerUsuarioService.obtenerPorNITyEmail(nit,email);
       if(perUsuario==null) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR ", "No existe un usuario cuyo Nro. de identificacion (NIT)  y correo electronico sean: "+nit+", "+email));
       }

           //System.out.println("======>>>>> NO EXISTE USUARIO");
       else
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO ", " Verifique su correo electronico"));

    }

