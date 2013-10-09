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
public class BienvenidaBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String idUnidad;
    private static final Logger logger = LoggerFactory.getLogger(BienvenidaBean.class);
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
    private String textoBenvenida;
    //
    private PerPersonaEntity persona;
    private List<PerUnidadEntity> listaUnidades;
    //
    private List<DocDocumentoEntity> listaDocumentos;

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        logger.info("usuario ok");
        logger.info("buscando persona");
        idPersona=(String) session.getAttribute("idEmpleador");
        persona=iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
//        try{
//            idPersona=(String) session.getAttribute("idEmpleador");
//            persona=iPersonaService.buscarPorId(idPersona);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            idPersona=null;
//            idPersona=(String) session.getAttribute("idEmpleador");
//            persona=iPersonaService.buscarPorId(usuario.getIdPersona());
//        }
        //persona=iPersonaService.buscarPorId(usuario.getIdPersona());
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        textoBenvenida="Bienvenido  OVT";
        listaUnidades=iUnidadService.listarPorPersona(persona.getIdPersona());
        cargarDocumentos();
    }
    public void cargarDocumentos(){
        try{
            listaDocumentos=iDocumentoService.listarPorPersona(persona.getIdPersona());
            if(listaDocumentos==null){
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
    }
    
    public String irRealizarCambioDeEstados(){
        return "irInicio";
    }
    public String irImprimirDocumento(){
        return "irInicio";
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public String getTextoBenvenida() {
        return textoBenvenida;
    }

    public void setTextoBenvenida(String textoBenvenida) {
        this.textoBenvenida = textoBenvenida;
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

    public List<PerUnidadEntity> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<PerUnidadEntity> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<DocDocumentoEntity> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocDocumentoEntity> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }
}
