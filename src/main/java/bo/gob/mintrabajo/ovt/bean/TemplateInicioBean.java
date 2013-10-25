package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
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
import org.primefaces.model.menu.DefaultSubMenu;

@ManagedBean(name = "templateInicioBean")
@ViewScoped
public class TemplateInicioBean implements Serializable {
    //
    private HttpSession session;
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private static final Logger logger = LoggerFactory.getLogger(TemplateInicioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    //
    private UsrUsuario usuario;
    private PerPersona persona;
    private PerPersona empleador;
    //
    //
    private List<UsrRecurso> listaRecursos;
    private MenuModel model;
    //
    private String username;
    private String password;

    @PostConstruct
    public void ini() {
        logger.info("TemplateInicioBean.init()");
        //
        username = "";
        password = "";
        //
        idUsuario = null;
        idPersona = null;
        idEmpleador = null;
        listaRecursos = new ArrayList<UsrRecurso>();
        usuario=null;
        persona=null;
        empleador=null;
        //
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            idUsuario = (Long) session.getAttribute("idUsuario");
            //
            logger.info("Buscando usuario" + idUsuario);
            usuario = iUsuarioService.findById(idUsuario);
            //
            idPersona=(String) session.getAttribute("idPersona");
            persona=iPersonaService.findById(idPersona);
            
            idEmpleador=(String)session.getAttribute("idEmpleador");
            if(idEmpleador!=null){
                empleador=iPersonaService.findById(idEmpleador);
            }
            logger.info("usuario ok");
            cargar();
        } catch (Exception e) {
//            e.printStackTrace();
            model = new DefaultMenuModel();
            DefaultMenuItem item = new DefaultMenuItem("Salir");
            item.setIcon("ui-icon-arrowthickstop-1-e");
            item.setCommand("#{templateInicioBean.logout}");
            model.addElement(item);
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

    public void crearMenuRecurso() {
        for (UsrRecurso recurso : listaRecursos) {
            if (recurso.getIdRecursoPadre() == null) {
                DefaultSubMenu subMenu = crearMenuHijos(recurso);
                model.addElement(subMenu);
            }
        }
    }

    public DefaultSubMenu crearMenuHijos(UsrRecurso recurso) {
        DefaultSubMenu subMenu = new DefaultSubMenu(recurso.getEtiqueta());
        for (UsrRecurso recursoHijo : listaRecursos) {
            if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().getIdRecurso().equals(recurso.getIdRecurso())) {
                if (tieneHijos(recursoHijo)) {
                    DefaultSubMenu subMenuHijo = crearMenuHijos(recursoHijo);
                    subMenu.addElement(subMenuHijo);
                } else {
                    DefaultMenuItem item = new DefaultMenuItem(recursoHijo.getEtiqueta());
                    item.setUrl("/faces" + recursoHijo.getEjecutable());
                    subMenu.addElement(item);
                }
            }
            
        }
        return subMenu;
    }

    public boolean tieneHijos(UsrRecurso recurso) {
        for (UsrRecurso recursoHijo : listaRecursos) {
            if (recurso.getIdRecurso() != recursoHijo.getIdRecurso()) {
                if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().getIdRecurso().equals(recurso.getIdRecurso())) {
                    return true;
                }
            }
        }
        return false;
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
            //ctx.redirect(ctxPath + "/faces/pages/inicio.xhtml");
            ctx.redirect(ctxPath + "/");
        } catch (IOException e) {
            System.out.println("Error en cerrar sesion");
            e.printStackTrace();
        }
        return "";
    }

    public String login() {
        logger.info("login()");
        try {
            logger.info("iUsuarioService.login(" + username + "," + password + ")");
            Long idUsuario = iUsuarioService.login(username, password);
            logger.info("usuario aceptado");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("idUsuario", idUsuario);
            UsrUsuario usuario=iUsuarioService.findById(idUsuario);
            session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());
            if(usuario.getEsInterno()==1)
            {
                session.setAttribute("idEmpleador", null);
                ini();
                return "irEmpleadorBusqueda";
            }
            else{
                session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                ini();
                return "irBienvenida";
            }
            //
        } catch (RuntimeException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), "Hello "));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), "Hello "));
        }
        password = "";
        return "";
    }

     public String irInicioPublico() {
        //si no existe la session
        return "irInicio";
    }

    public String irInicioPrivado() {
        if(idPersona!=null && idEmpleador!=null && idPersona.equals(idEmpleador))
        {
            return "irBienvenida";
        }
        else{
            return "irEmpleadorBusqueda";
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public UsrUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public PerPersona getEmpleador() {
        return empleador;
    }

    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
