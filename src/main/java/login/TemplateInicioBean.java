package login;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "templateInicioBean")
@RequestScoped
public class TemplateInicioBean implements Serializable {
    //

    private HttpSession session;
    private int idUsuario;
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
    private String nombreDeUsuario;
    private String nombreDeUnidad;
    //
    private List<UsrRecursoEntity> listaRecursos;
    private MenuModel model;
    //
    private String username;
    private String password;
    //
    private Date fecha;

    @PostConstruct
    public void ini() {
        logger.info("TemplateInicioBean.init()");
        //
        username = "";
        password = "";
        //
        idUsuario = 0;
        listaRecursos = new ArrayList<UsrRecursoEntity>();
        nombreDeUsuario = "";
        nombreDeUnidad = "";
        //
        fecha = new Date();
        //
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            idUsuario = (Integer) session.getAttribute("idUsuario");
            BigDecimal bi = BigDecimal.valueOf(idUsuario);
            logger.info("Buscando usuario" + bi);
            UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
            nombreDeUsuario = usuario.getUsuario();
            PerPersonaEntity persona;
            idPersona=null;
            idEmpleador=(String)session.getAttribute("idEmpleador");
            if(idEmpleador!=null){
                nombreDeUnidad=iPersonaService.buscarPorId(idEmpleador).getNombreRazonSocial();
            }
            else{
                nombreDeUnidad="N/A";
            }
            logger.info("usuario ok");
            cargar();
        } catch (Exception e) {
//            e.printStackTrace();
            model = new DefaultMenuModel();
            DefaultMenuItem item = new DefaultMenuItem("Salir");
            item.setIcon("ui-icon-search");
            item.setCommand("#{templateInicioBean.logout}");
            model.addElement(item);
            nombreDeUsuario = "Invitado";
        }

    }

    public void cargar() {
        logger.info("cargar");
        model = new DefaultMenuModel();
        try {
            BigDecimal bi = BigDecimal.valueOf(idUsuario);
            listaRecursos = iRecursoService.buscarPorUsuario(bi);
            logger.info("" + listaRecursos.size());
        } catch (Exception e) {
        }
        crearMenuRecurso();
        DefaultMenuItem item = new DefaultMenuItem("Salir");
        item.setIcon("ui-icon-search");
        item.setCommand("#{templateInicioBean.logout}");
        model.addElement(item);
    }

    public void crearMenuRecurso() {

        for (UsrRecursoEntity recurso : listaRecursos) {
            if (recurso.getIdRecursoPadre() == null) {
                DefaultSubMenu subMenu = crearMenuHijos(recurso);
                model.addElement(subMenu);
            }
        }
    }

    public DefaultSubMenu crearMenuHijos(UsrRecursoEntity recurso) {
        logger.info("crearMenuHijos()");
        DefaultSubMenu subMenu = new DefaultSubMenu(recurso.getEtiqueta());
        for (UsrRecursoEntity recursoHijo : listaRecursos) {
            if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().equals(recurso.getIdRecurso())) {
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

    public boolean tieneHijos(UsrRecursoEntity recurso) {
        for (UsrRecursoEntity recursoHijo : listaRecursos) {
            if (recurso.getIdRecurso() != recursoHijo.getIdRecurso()) {
                if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().equals(recurso.getIdRecurso())) {
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
            ctx.redirect(ctxPath + "/faces/pages/inicio.xhtml");
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
            int idUsuario = iUsuarioService.login(username, password);
            logger.info("usuario aceptado");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("idUsuario", idUsuario);
            //
            ini();
            //
            BigDecimal bi = BigDecimal.valueOf(idUsuario);
            UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
            session.setAttribute("idPersona", usuario.getIdPersona());
            if (usuario.getEsInterno() == BigInteger.ONE) {
                session.setAttribute("idEmpleador", null);
                return "irEmpleadorBusqueda";
            } else {
                session.setAttribute("idEmpleador", usuario.getIdPersona());
                return "irBienvenida";
            }
            //return "login";
        } catch (RuntimeException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello "));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(e.getMessage(), "Hello "));
        }
        password = "";
        return "";
    }

    public String irInicioPublico() {
        //si no existe la session
        return "irInicio";
    }

    public String irInicioPrivado() {
        //si existe la session
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        session.setAttribute("idPersona", usuario.getIdPersona());
        if (usuario.getEsInterno() == BigInteger.ONE) {
            session.setAttribute("idEmpleador", null);
            return "irEmpleadorBusqueda";
        } else {
            session.setAttribute("idEmpleador", usuario.getIdPersona());
            return "irBienvenida";
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getNombreDeUnidad() {
        return nombreDeUnidad;
    }

    public void setNombreDeUnidad(String nombreDeUnidad) {
        this.nombreDeUnidad = nombreDeUnidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }
}
