package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import com.itextpdf.text.Document;
import org.slf4j.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class InicioBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private String idPersona;
    private String idEmpleador;
    private static final Logger logger = LoggerFactory.getLogger(InicioBean.class);
    //
    private List<UsrUsuario> listaUsuarios;
    
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value = "#{moduloService}")
    private IModuloService iModuloService;

    @PostConstruct
    public void ini() {
        listaUsuarios=iUsuarioService.getAllUsuarios();
    }
    
    public void guardarDominio(){
        System.out.println("========MODULO=============");
        System.out.println("============================");
        System.out.println("============================");
        UsrModulo modulo=iModuloService.findById("PAR");
        System.out.println("=======DOMINIO==============");
        System.out.println("============================");
        ParDominio dominio=new ParDominio();
        dominio.setDescripcion("Activo");
        dominio.setObservacion("Activo");
        dominio.setIdModulo(modulo);
        iDominioService.save(dominio);
        
    }

    public List<UsrUsuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsrUsuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public IModuloService getiModuloService() {
        return iModuloService;
    }

    public void setiModuloService(IModuloService iModuloService) {
        this.iModuloService = iModuloService;
    }
}
