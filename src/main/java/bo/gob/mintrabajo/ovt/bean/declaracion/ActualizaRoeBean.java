package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;

@ManagedBean
@ViewScoped
public class ActualizaRoeBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value = "#{calendarioService}")
    private ICalendarioService iCalendarioService;
    //
    private UsrUsuario usuario;
    private boolean esFuncionario;
    //
    private VperPersona vperPersona;
    private DocDocumento documento;
    private DocGenerico docGenerico;
    private DocDefinicion docDefinicion;
    //
    private String bitacoraSession;
    //
    private boolean mostrarFormulario;
    private String mostrarFormularioMensaje;

    @PostConstruct
    public void ini() {
        logger.info("BajaRoeBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        bitacoraSession = (String) session.getAttribute("bitacoraSession");
        usuario = iUsuarioService.findById(idUsuario);
        mostrarFormulario = iDocumentoService.existeRoe(idEmpleador);
        if (mostrarFormulario) {
            mostrarFormularioMensaje = "";
        } else {
            if (idEmpleador == null) {
                mostrarFormularioMensaje = "No se encontro al empleador";
            } else {
                mostrarFormularioMensaje = "No se encontro un documento ROE activo para este empleador";
            }
        }
        cargar();
    }

    public void cargar() {
        vperPersona = iVperPersonaService.cargaVistaPersona(idEmpleador);
        documento = new DocDocumento();
        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(idEmpleador, 0L)));
        //
        docGenerico = new DocGenerico();
        docGenerico.setCadena08(vperPersona.getRlNombre());
        docGenerico.setCadena09(vperPersona.getRlNroIdentidad());
        docDefinicion = iDefinicionService.buscarActivoPorParametro(Dominios.PAR_DOCUMENTO_ROE_MODIFICACION);
    }

    public String guardar() {
        logger.info("Guardando actualizacion sin movimiento");
        documento = iDocumentoService.guardarActualizaRoe(documento, docGenerico, bitacoraSession);
        return "irEscritorio";
    }

    public String irEscritorio() {
        return "irEscritorio";
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

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public VperPersona getVperPersona() {
        return vperPersona;
    }

    public void setVperPersona(VperPersona vperPersona) {
        this.vperPersona = vperPersona;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }

    public DocGenerico getDocGenerico() {
        return docGenerico;
    }

    public void setDocGenerico(DocGenerico docGenerico) {
        this.docGenerico = docGenerico;
    }

    public DocDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumento documento) {
        this.documento = documento;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public boolean isEsFuncionario() {
        return esFuncionario;
    }

    public void setEsFuncionario(boolean esFuncionario) {
        this.esFuncionario = esFuncionario;
    }


    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public ICalendarioService getiCalendarioService() {
        return iCalendarioService;
    }

    public void setiCalendarioService(ICalendarioService iCalendarioService) {
        this.iCalendarioService = iCalendarioService;
    }


    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

    public boolean isMostrarFormulario() {
        return mostrarFormulario;
    }

    public void setMostrarFormulario(boolean mostrarFormulario) {
        this.mostrarFormulario = mostrarFormulario;
    }

    public String getMostrarFormularioMensaje() {
        return mostrarFormularioMensaje;
    }

    public void setMostrarFormularioMensaje(String mostrarFormularioMensaje) {
        this.mostrarFormularioMensaje = mostrarFormularioMensaje;
    }
}