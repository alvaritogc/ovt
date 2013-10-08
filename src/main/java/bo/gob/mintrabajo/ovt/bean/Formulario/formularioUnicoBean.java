package bo.gob.mintrabajo.ovt.bean.Formulario;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class formularioUnicoBean {

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Integer idUsuario;
    private static final Logger logger = LoggerFactory.getLogger(formularioUnicoBean.class);

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;

    private PerPersonaEntity perPersonaEntity;
    private DocPlanillaEntity docPlanillaEntity;

    @PostConstruct
    public void ini() {
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal temp = BigDecimal.valueOf(idUsuario);
        UsrUsuarioEntity usuario = iUsuarioService.findById(temp);
        perPersonaEntity = iPersonaService.buscarPorId(usuario.getIdPersona());
        docPlanillaEntity = new DocPlanillaEntity();
    }

    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public List<ParEntidadEntity> obtenerEntidad(){

        List<ParEntidadEntity> tmpLista = iEntidadService.getEntidadLista();
        return tmpLista;
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
}
