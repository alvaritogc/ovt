package bo.gob.mintrabajo.ovt.bean.auditoria;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.entities.VdocLogImpresion;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 12/2/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AuditoriaImpresionBean {

    @ManagedProperty(value = "#{logImpresionService}")
    private ILogImpresionService iLogImpresionService;

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    private static final Logger log = LoggerFactory.getLogger(AuditoriaImpresionBean.class);
    private List<VdocLogImpresion> docLogImpresionLista;
    private List<VdocLogImpresion> docLogImpresionFiltro;
    private String tipoImpresion;
    private String nroIdentificacion;
    private String codDocumento;
    private Date fechaInicio;
    private Date fechaFinal;

    @PostConstruct
    public void ini() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String param = params.get("code").toString();
        if (param.equals("1RXN0YSBlcyBsYSBub3RhIGRlIGVzdGEgcOFnaW5h"))
            tipoImpresion = "DOWN";
        else if (param.equals("R3N0YS4lcyBsY8hub3RhIxdlIGVzW244FnRTGh2"))
            tipoImpresion = "IMPR";
    }


    public void buscarLogImpresion() {
        try {
            if (nroIdentificacion.length() > 0 || codDocumento.length() > 1 || fechaInicio != null || fechaFinal != null) {
                docLogImpresionLista = iLogImpresionService.filtrarLogImpresion(nroIdentificacion, codDocumento, fechaInicio, fechaFinal, tipoImpresion);
                log.info("Nro de resultados " + docLogImpresionLista.size());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Debe seleccionar almenos un criterio de búsqueda"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Ocurrió una falla de base de datos, comuniquese con el administrador"));
        }
    }

    /// **** getters && setters **** ///
    public ILogImpresionService getiLogImpresionService() {
        return iLogImpresionService;
    }

    public void setiLogImpresionService(ILogImpresionService iLogImpresionService) {
        this.iLogImpresionService = iLogImpresionService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public List<VdocLogImpresion> getDocLogImpresionLista() {
        return docLogImpresionLista;
    }

    public void setDocLogImpresionLista(List<VdocLogImpresion> docLogImpresionLista) {
        this.docLogImpresionLista = docLogImpresionLista;
    }

    public List<VdocLogImpresion> getDocLogImpresionFiltro() {
        return docLogImpresionFiltro;
    }

    public void setDocLogImpresionFiltro(List<VdocLogImpresion> docLogImpresionFiltro) {
        this.docLogImpresionFiltro = docLogImpresionFiltro;
    }
}
