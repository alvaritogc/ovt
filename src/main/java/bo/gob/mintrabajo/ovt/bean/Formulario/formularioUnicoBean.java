package bo.gob.mintrabajo.ovt.bean.Formulario;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: gmercado Date: 10/8/13 Time: 2:17 PM To
 * change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class formularioUnicoBean {

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
    private PerPersonaEntity perPersonaEntity;
    private DocPlanillaEntity docPlanillaEntity;
    private Integer temporal = 0;
    private boolean temporalBoolean = true;
    private PerPersonaEntity persona;

    @PostConstruct
    public void ini() {
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal temp = BigDecimal.valueOf(idUsuario);
        UsrUsuarioEntity usuario = iUsuarioService.findById(temp);
        perPersonaEntity = iPersonaService.buscarPorId(usuario.getIdPersona());
        docPlanillaEntity = new DocPlanillaEntity();

        logger.info("buscando persona");
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
    }

    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public List<ParEntidadEntity> obtenerEntidad() {
        List<ParEntidadEntity> tmpLista;
        try {
            tmpLista = iEntidadService.getEntidadLista();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tmpLista;
    }

    public void guardarPlanilla() {
        System.out.println("Ingresando a guardar Planilla ");
        try {
            Integer idDocumento_session = (Integer) session.getAttribute("idDocumento_session");
            docPlanillaEntity.setIdDocumento(idDocumento_session);
            docPlanillaEntity.setTipoPlanilla("P0");
            docPlanillaEntity.setIdEntidadBanco(2);
            docPlanillaEntity.setMontoOperacion(new BigDecimal("1000.51"));
            docPlanillaEntity.setFechaOperacion(new Timestamp(new Date().getTime()));
            iPlanillaService.guardar(docPlanillaEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falla", "No se guardo el formulario"));
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
}
