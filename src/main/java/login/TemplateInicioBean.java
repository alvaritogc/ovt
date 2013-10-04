/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
//
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
//
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
//import org.primefaces.component.menuitem.MenuItem;
//import org.primefaces.component.submenu.Submenu;
//import org.primefaces.model.DefaultMenuModel;
//import org.primefaces.model.MenuModel;

import org.primefaces.model.menu.DefaultMenuItem;  
import org.primefaces.model.menu.DefaultMenuModel;  
import org.primefaces.model.menu.DefaultSubMenu;  
import org.primefaces.model.menu.MenuModel;  
  

@ManagedBean(name = "templateInicioBean")
@SessionScoped
public class TemplateInicioBean implements Serializable{

    //private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private BigInteger idUsuario;
    private List<UsrRecursoEntity> listaRecursos;
    private MenuModel model;

    @PostConstruct
    public void ini() {
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("TemplateInicioBean.init()");
        System.out.println("================================");
        System.out.println("================================");

        //idUsuario = (BigInteger) session.getAttribute("idUsuario");
        System.out.println("idUsuario: " + idUsuario);
        cargar();
        crearMenu();
    }

    public void cargar() {
    }

    public List<UsrRecursoEntity> obtenerRecursos(String valor, int cantidad) {
        List<UsrRecursoEntity> listaRecursos = new ArrayList<UsrRecursoEntity>();
        for (int i = 1; i <= cantidad; i++) {
            UsrRecursoEntity recurso = new UsrRecursoEntity();
            recurso.getDescripcion();//descripcion
            recurso.setEjecutable(valor+i);//#{logginBEAN.algo
            recurso.setEsVerificable(BigInteger.ZERO);//
            recurso.setEstado(valor+i);
            recurso.setEtiqueta(valor+i);
            recurso.setFechaBitacora(new Timestamp(2013, 2, 2, 2, 2, 2, 2));
            recurso.setIdRecurso(1);
            recurso.setOrden(0);
            recurso.setRegistroBitacora(valor+i);
            recurso.setTipoPlataforma(valor+i);
            recurso.setTipoRecurso(valor+i);
        }
        return listaRecursos;
    }

    

    public void crearMenu() {
        model = new DefaultMenuModel();
        //First submenu  
//        Submenu submenu = new Submenu();
//        submenu.setLabel("Dynamic Submenu 1");
//
//        MenuItem item = new MenuItem();
//        item.setValue("Dynamic Menuitem 1.1");
//        item.setUrl("#");
//        submenu.getChildren().add(item);
//        
//        item = new MenuItem();
//        item.setValue("Salir");
//        submenu.getChildren().add(item);
//
//        model.addSubmenu(submenu);
//
//
//        submenu = new Submenu();
//        submenu.setLabel("Dynamic Submenu 2");
//
//        item = new MenuItem();
//        item.setValue("Dynamic Menuitem 2.1");
//        item.setUrl("#");
//        submenu.getChildren().add(item);
//
//        item = new MenuItem();
//        item.setValue("Dynamic Menuitem 2.2");
//        item.setUrl("#");
//        submenu.getChildren().add(item);
//
//        Submenu submenu1 = new Submenu();
//        submenu1.setLabel("Dynamic Submenu 2.1");
//
//        item = new MenuItem();
//        item.setValue("Dynamic Menuitem 2.1.1");
//        item.setUrl("#");
//        submenu1.getChildren().add(item);
//
//        submenu.getChildren().add(submenu1);
//        model.addSubmenu(submenu);
        
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
//        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
//        ((HttpSession) ctx.getSession(false)).invalidate();

        // Redirección de nuevo con el contexto de JSF,
        // si se usa una HttpServletResponse fallará.
        // Sin embargo, como ya está fuera del ciclo de vida
        // de JSF se debe usar la ruta completa 
        /*
        try {
            ctx.redirect(ctxPath + "/faces/index.xhtml");
        } catch (IOException e) {
            System.out.println("Error en cerrar sesion");
            e.printStackTrace();
        }
        return "";
        * */
        return "/faces/index.xhtml";
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
}
