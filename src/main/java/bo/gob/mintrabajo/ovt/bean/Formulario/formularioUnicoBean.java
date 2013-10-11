package bo.gob.mintrabajo.ovt.bean.Formulario;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: gmercado Date: 10/8/13 Time: 2:17 PM To
 * change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class formularioUnicoBean implements Serializable{

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Integer idUsuario;
    private String idPersona;
    private static final Logger logger = LoggerFactory.getLogger(formularioUnicoBean.class);
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;

    private List<ParObligacionCalendarioEntity> parObligacionCalendarioLista;
    private List<ParEntidadEntity> parEntidadEntityLista;
    private PerPersonaEntity perPersonaEntity;
    private DocPlanillaEntity docPlanillaEntity;
    private boolean esRectificatorio=false;
    private Date fechaOperacionAux;
    private Long numeroOrden;

    private Integer temporal = 0;
    private boolean temporalBoolean;
    private PerPersonaEntity persona;
    private Date fechaTemp = new Date();

    @PostConstruct
    public void ini() {
        idPersona = (String) session.getAttribute("idEmpleador");
        logger.info("buscando persona:"+idPersona);
        persona = iPersonaService.buscarPorId(idPersona);
        //
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal temp = BigDecimal.valueOf(idUsuario);
        UsrUsuarioEntity usuario = iUsuarioService.findById(temp);
        perPersonaEntity = iPersonaService.buscarPorId(idPersona);
        docPlanillaEntity = new DocPlanillaEntity();

        
        obtenerPeriodoLista();
        obtenerEntidad();
    }

    public void obtenerPeriodoLista(){
        parObligacionCalendarioLista = new ArrayList<ParObligacionCalendarioEntity>();
        parObligacionCalendarioLista = iObligacionCalendarioService.obtenerObligacionCalendario();
    }


    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public void obtenerEntidad() {
            parEntidadEntityLista = iEntidadService.getEntidadLista();
    }

    public String guardarPlanilla() {
        System.out.println("Ingresando a guardar Planilla ");
        try {
//            DocDocumentoEntity documento_session = (DocDocumentoEntity) session.getAttribute("documento_session");
//            docPlanillaEntity.setIdDocumento(documento_session.getIdDocumento());
//            docPlanillaEntity.setTipoPlanilla("DDJJ");
//            docPlanillaEntity.setIdEntidadBanco(2);
//            docPlanillaEntity.setFechaOperacion(new Timestamp(fechaOperacionAux.getTime()));
//            iPlanillaService.guardar(docPlanillaEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
            docPlanillaEntity = new DocPlanillaEntity();
            return "irListadoBienvenida";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falla", "No se guardo el formulario"));
            return null;
        }
    }

    //** Getters and Setters **//
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

    public PerPersonaEntity getPerPersonaEntity() {
        return perPersonaEntity;
    }

    public void setPerPersonaEntity(PerPersonaEntity perPersonaEntity) {
        this.perPersonaEntity = perPersonaEntity;
    }

    public DocPlanillaEntity getDocPlanillaEntity() {
        return docPlanillaEntity;
    }

    public void setDocPlanillaEntity(DocPlanillaEntity docPlanillaEntity) {
        this.docPlanillaEntity = docPlanillaEntity;
    }

    public IEntidadService getiEntidadService() {
        return iEntidadService;
    }

    public void setiEntidadService(IEntidadService iEntidadService) {
        this.iEntidadService = iEntidadService;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    public Integer getTemporal() {
        return temporal;
    }

    public void setTemporal(Integer temporal) {
        this.temporal = temporal;
    }

    public boolean isTemporalBoolean() {
        return temporalBoolean;
    }

    public void setTemporalBoolean(boolean temporalBoolean) {
        this.temporalBoolean = temporalBoolean;
    }

    public PerPersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PerPersonaEntity persona) {
        this.persona = persona;
    }

    public Date getFechaTemp() {
        return fechaTemp;
    }

    public void setFechaTemp(Date fechaTemp) {
        this.fechaTemp = fechaTemp;
    }

    public boolean isEsRectificatorio() {
        return esRectificatorio;
    }

    public void setEsRectificatorio(boolean esRectificatorio) {
        this.esRectificatorio = esRectificatorio;
    }

    public Date getFechaOperacionAux() {
        return fechaOperacionAux;
    }

    public void setFechaOperacionAux(Date fechaOperacionAux) {
        this.fechaOperacionAux = fechaOperacionAux;
    }

    public IObligacionCalendarioService getiObligacionCalendarioService() {
        return iObligacionCalendarioService;
    }

    public void setiObligacionCalendarioService(IObligacionCalendarioService iObligacionCalendarioService) {
        this.iObligacionCalendarioService = iObligacionCalendarioService;
    }

    public List<ParObligacionCalendarioEntity> getParObligacionCalendarioLista() {
        return parObligacionCalendarioLista;
    }

    public void setParObligacionCalendarioLista(List<ParObligacionCalendarioEntity> parObligacionCalendarioLista) {
        this.parObligacionCalendarioLista = parObligacionCalendarioLista;
    }

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public List<ParEntidadEntity> getParEntidadEntityLista() {
        return parEntidadEntityLista;
    }

    public void setParEntidadEntityLista(List<ParEntidadEntity> parEntidadEntityLista) {
        this.parEntidadEntityLista = parEntidadEntityLista;
    }
}