package bo.gob.mintrabajo.ovt.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
//
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ViewScoped
public class UsuarioBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    //
    private List<UsrUsuarioEntity> listaUsuarios;
    
    @PostConstruct
    public void ini() {
        logger.info("UsuarioBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        cargar();
    }
    public void cargar(){
        cargarListaUsuarios();
    }
    
    public void cargarListaUsuarios(){
        listaUsuarios=new ArrayList<UsrUsuarioEntity>();
        listaUsuarios=iUsuarioService.getAllUsuarios();
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public List<UsrUsuarioEntity> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsrUsuarioEntity> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
}
