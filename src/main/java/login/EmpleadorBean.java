package login;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
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

@ManagedBean
@ViewScoped
public class EmpleadorBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String idUnidad;
    private static final Logger logger = LoggerFactory.getLogger(EmpleadorBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    //
    //
    private PerPersonaEntity persona;
    private List<PerPersonaEntity> listaPersonas;
    private PerPersonaEntity personaABM;

    @PostConstruct
    public void ini() {
        logger.info("EmpleadorBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        cargar();
    }

    public void cargar() {
//        listaPersonas=iPersonaService.getAllPersonas();
        listaPersonas=new ArrayList<PerPersonaEntity>();
    }
    
    public String seleccionarEmpleador(){
        session.setAttribute("idEmpleador", personaABM.getIdPersona());
        return "irBienvenida";
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }   

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public PerPersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PerPersonaEntity persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<PerPersonaEntity> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<PerPersonaEntity> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public PerPersonaEntity getPersonaABM() {
        return personaABM;
    }

    public void setPersonaABM(PerPersonaEntity personaABM) {
        this.personaABM = personaABM;
    }
}
