package login;

import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
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
@SessionScoped
public class TemplateInicioBean implements Serializable {
    //

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private static final Logger logger = LoggerFactory.getLogger(TemplateInicioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
    //
    private List<UsrRecursoEntity> listaRecursos;
    private MenuModel model;

    @PostConstruct
    public void ini() {
        logger.info("TemplateInicioBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        logger.info("usuario ok");
        cargar();
    }

    public void cargar() {
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
        item.setCommand("#{templateInicioBean.logout()}");
        model.addElement(item);

    }

    public void crearMenuRecurso() {
        logger.info("crearMenuRecurso()");
        for (UsrRecursoEntity recurso : listaRecursos) {
            logger.info("====");
            logger.info("id:" + recurso.getIdRecurso());
            logger.info("rec:" + recurso.getEtiqueta());
            logger.info("padre:" + recurso.getIdRecursoPadre());
            logger.info("====");
            System.out.println("");
            if (recurso.getIdRecursoPadre() == null) {
                DefaultSubMenu subMenu = crearMenuHijos(recurso);
                model.addElement(subMenu);
            }
        }
    }

    public DefaultSubMenu crearMenuHijos(UsrRecursoEntity recurso) {
        logger.info("crearMenuHijos()");
        DefaultSubMenu subMenu = new DefaultSubMenu(recurso.getEtiqueta());
        System.out.println("Creando hijos para " + recurso.getEtiqueta());
        for (UsrRecursoEntity recursoHijo : listaRecursos) {
//            System.out.println("recurso Hijo " + recursoHijo.getEtiqueta());
//            if (recurso.getIdRecurso() != recursoHijo.getIdRecurso()) {
//                if (tieneHijos(recursoHijo)) {
//                    System.out.println("Tiene Hijos");
//                    DefaultSubMenu subMenuHijo = crearMenuHijos(recursoHijo);
//                    subMenu.addElement(subMenuHijo);
//                } else {
//                    System.out.println("creando item");
//                    DefaultMenuItem item = new DefaultMenuItem(recursoHijo.getEtiqueta());
//                    item.setUrl("" + recursoHijo.getEjecutable());
//                    subMenu.addElement(item);
//                }
//            }
            System.out.println("========================================");
            System.out.println("========================================");
            System.out.println("========================================");
            System.out.println("Padre"+recurso.getEtiqueta());
            System.out.println("Hijo"+recursoHijo.getEtiqueta());
            if(recursoHijo.getIdRecursoPadre()!=null && recursoHijo.getIdRecursoPadre().equals(recurso.getIdRecurso())){
                System.out.println("es su hijo");
                if(tieneHijos(recursoHijo)){
                    System.out.println("tiene sub hijos");
                    DefaultSubMenu subMenuHijo = crearMenuHijos(recursoHijo);
                    subMenu.addElement(subMenuHijo);
                }
                else{
                    System.out.println("no tiene sub hijos adicionado");
                    DefaultMenuItem item = new DefaultMenuItem(recursoHijo.getEtiqueta());
                    item.setUrl("" + recursoHijo.getEjecutable());
                    subMenu.addElement(item);
                }
            }
            System.out.println("========================================");
            System.out.println("========================================");
            System.out.println("========================================");
        }
        return subMenu;
    }

    public boolean tieneHijos(UsrRecursoEntity recurso) {
        for (UsrRecursoEntity recursoHijo : listaRecursos) {
            if(recurso.getIdRecurso()!=recursoHijo.getIdRecurso()){
                if (recursoHijo.getIdRecursoPadre()!=null && recursoHijo.getIdRecursoPadre().equals( recurso.getIdRecurso())) {
                System.out.println("yyyy");
                System.out.println("" + recurso.getEtiqueta() + " padre de : " + recursoHijo.getEtiqueta());
                System.out.println("yyyy");
                return true;
            }
            }
            
        }
        System.out.println("xxxxx");
        return false;
    }

    public String logout() {
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
            ctx.redirect(ctxPath + "/faces/index.xhtml");
        } catch (IOException e) {
            System.out.println("Error en cerrar sesion");
            e.printStackTrace();
        }
        return "";
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
}
