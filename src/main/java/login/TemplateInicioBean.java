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
        crearMenu();
        try {
            BigDecimal bi = BigDecimal.valueOf(idUsuario);

            listaRecursos = iRecursoService.buscarPorUsuario(bi);
            logger.info("" + listaRecursos.size());
        } catch (Exception e) {
        }
        crearMenuRecurso();
    }

    public void crearMenuRecurso() {
        logger.info("0");
//        for (UsrRecursoEntity recurso : listaRecursos) {
//            DefaultSubMenu secondSubmenu = new DefaultSubMenu(recurso.getEtiqueta());
//            DefaultMenuItem item = new DefaultMenuItem(recurso.getEtiqueta());
//            item.setUrl("" + recurso.getEjecutable());
//            item.setIcon("ui-icon-disk");
//            secondSubmenu.addElement(item);
//            model.addElement(secondSubmenu);
//        }
        for (UsrRecursoEntity recurso : listaRecursos){
            logger.info("1");
            if(recurso.getIdRecursoPadre()==null || recurso.getIdRecursoPadre()==0){
                DefaultSubMenu subMenu=crearMenuHijos(recurso);
                model.addElement(subMenu);
            }
        }
    }

    public boolean tieneHijos(int idRecurso) {
        for (UsrRecursoEntity recurso : listaRecursos) {
            if (recurso.getIdRecursoPadre() == idRecurso) {
                return true;
            }
        }
        return false;
    }

    public DefaultSubMenu crearMenuHijos(UsrRecursoEntity recurso) {
        logger.info("2");
        DefaultSubMenu subMenu = new DefaultSubMenu(recurso.getEtiqueta());
        
        for (UsrRecursoEntity recursoHijo : listaRecursos) {
            logger.info("3");
            DefaultMenuItem item = new DefaultMenuItem(recursoHijo.getEtiqueta());
            item.setUrl("" + recursoHijo.getEjecutable());
            item.setIcon("ui-icon-disk");
            subMenu.addElement(item);
        }
        return subMenu;
    }

    public void crearMenu() {
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");

        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);

        model.addElement(firstSubmenu);

        //Second submenu  
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");

        item = new DefaultMenuItem("Save");
        item.setIcon("ui-icon-disk");
        item.setCommand("#{menuBean.save}");
        //item.setUpdate("messages");  
        secondSubmenu.addElement(item);

        item = new DefaultMenuItem("Delete");
        item.setIcon("ui-icon-close");
        item.setCommand("#{menuBean.delete}");
        item.setAjax(false);
        secondSubmenu.addElement(item);

        item = new DefaultMenuItem("Redirect");
        item.setIcon("ui-icon-search");
        item.setCommand("#{menuBean.redirect}");
        secondSubmenu.addElement(item);

        item = new DefaultMenuItem("Salir");
        item.setIcon("ui-icon-search");
        item.setCommand("#{templateInicioBean.logout()}");
        secondSubmenu.addElement(item);

        model.addElement(secondSubmenu);
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