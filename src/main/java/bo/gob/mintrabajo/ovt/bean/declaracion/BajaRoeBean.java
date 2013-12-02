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
public class BajaRoeBean {
    //

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String bitacoraSession;
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
    private PerUnidadPK perUnidadPK;
    //
    private boolean entero03;
    private boolean entero04;
    private boolean entero05;
    //
    private List<ParDominio> listaDominioMeses;
    private List<ParCalendario> listaCalendarioAnios;
    private List<String> listaGestiones;

    @PostConstruct
    public void ini() {
        logger.info("BajaRoeBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        bitacoraSession = (String) session.getAttribute("bitacoraSession");
        usuario = iUsuarioService.findById(idUsuario);
        esFuncionario = usuario.getEsInterno() == 1 ? true : false;
        cargar();
    }

    public void cargar() {
        vperPersona = iVperPersonaService.cargaVistaPersona(idEmpleador);

        docGenerico = new DocGenerico();
        docGenerico.setCadena01("");
        docGenerico.setCadena02("");
        docGenerico.setCadena03("");
        docGenerico.setCadena04("");
        docGenerico.setCadena05(vperPersona.getRlNombre());
        docGenerico.setCadena06(vperPersona.getRlNroIdentidad());
        if (esFuncionario) {
            PerPersona persona = iPersonaService.obtenerPersonaPorUsuario(usuario);
            //docGenerico.setCadena07(usuario.getUsuario());
            docGenerico.setCadena07("" + persona.getNombreRazonSocial()
                    + " " + (persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "")
                    + " " + (persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "")
            );
        }
        cargarDocumento();
        cargarFechas();
    }

    public void cargarDocumento() {
        perUnidadPK = new PerUnidadPK(idEmpleador, 0L);
        //documento = new DocDocumento();
        //
        //documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(idEmpleador, 0L)));
        //
//        DocDefinicionPK docDefinicionPK=new DocDefinicionPK();
//        docDefinicionPK.setCodDocumento("ROE012");
//        docDefinicionPK.setVersion((short)1);
//        docDefinicion=iDefinicionService.buscaPorId(docDefinicionPK);
        //docDefinicion = iDefinicionService.buscarActivoPorParametro(Dominios.PAR_DOCUMENTO_ROE_BAJA);
    }

    public void cargarFechas() {
//        listaDominioMeses=iDominioService.obtenerItemsDominio("TPERIODO");
        ParDominioPK parDominioPK = new ParDominioPK();
        parDominioPK.setIdDominio("TCALENDARIO");
        parDominioPK.setValor("MES");
        listaDominioMeses = iDominioService.obtenerDominioPorDominioPadreOrderByValor(parDominioPK);
        listaCalendarioAnios = iCalendarioService.listaCalendario();
        listaGestiones = iCalendarioService.listaGestiones();
    }

    public String guardar() {
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("Guardar");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("docGenerico : " + docGenerico.getCadena01());
        if ((docGenerico.getCadena01() == null || docGenerico.getCadena01().trim().equals(""))
                && (docGenerico.getCadena02() == null || docGenerico.getCadena02().trim().equals(""))
                && (docGenerico.getCadena03() == null || docGenerico.getCadena03().trim().equals(""))
                && (docGenerico.getCadena04() == null || docGenerico.getCadena04().trim().equals(""))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año para Indicar si la suspención es temporal o definitiva."));
            return "";
        }
        if ((docGenerico.getCadena01() == null || docGenerico.getCadena01().trim().equals(""))
                && (docGenerico.getCadena02() == null || docGenerico.getCadena02().trim().equals(""))) {
            if (!((!(docGenerico.getCadena03() == null || docGenerico.getCadena03().trim().equals("")))
                    && (!(docGenerico.getCadena04() == null || docGenerico.getCadena04().trim().equals(""))))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año de la Suspención definitiva."));
                return "";
            }
        }
        if ((docGenerico.getCadena02() == null || docGenerico.getCadena02().trim().equals(""))
                && (docGenerico.getCadena03() == null || docGenerico.getCadena03().trim().equals(""))) {
            if (!((!(docGenerico.getCadena01() == null || docGenerico.getCadena01().trim().equals("")))
                    && (!(docGenerico.getCadena02() == null || docGenerico.getCadena02().trim().equals(""))))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año de la Suspención temporal."));
                return "";
            }
        }
        if (docGenerico.getEntero01() == null || docGenerico.getEntero01() < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Número de trabajadores."));
            return "";
        }
        //
        if (esFuncionario) {
            System.out.println("Es funcionario");
            docGenerico.setEntero03(entero03 ? 1 : 0);
            docGenerico.setEntero04(entero04 ? 1 : 0);
            docGenerico.setEntero05(entero05 ? 1 : 0);
        }
        //
        //documento = iDocumentoService.guardarBajaRoe(documento, docGenerico, idUsuario.toString());
        documento = iDocumentoService.guardarBajaRoe(perUnidadPK, docGenerico, idUsuario.toString());
        //RequestContext context = RequestContext.getCurrentInstance();
        //context.execute("dlgConfirmacion.show()");
        return "irEscritorio";
    }

    public void vaciarSuspencionTemporal() {
        docGenerico.setCadena01("");
        docGenerico.setCadena02("");
    }

    public void vaciarSuspencionDefinitiva() {
        docGenerico.setCadena03("");
        docGenerico.setCadena04("");
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

    public boolean isEntero03() {
        return entero03;
    }

    public void setEntero03(boolean entero03) {
        this.entero03 = entero03;
    }

    public boolean isEntero04() {
        return entero04;
    }

    public void setEntero04(boolean entero04) {
        this.entero04 = entero04;
    }

    public boolean isEntero05() {
        return entero05;
    }

    public void setEntero05(boolean entero05) {
        this.entero05 = entero05;
    }

    public List<ParDominio> getListaDominioMeses() {
        return listaDominioMeses;
    }

    public void setListaDominioMeses(List<ParDominio> listaDominioMeses) {
        this.listaDominioMeses = listaDominioMeses;
    }

    public List<ParCalendario> getListaCalendarioAnios() {
        return listaCalendarioAnios;
    }

    public void setListaCalendarioAnios(List<ParCalendario> listaCalendarioAnios) {
        this.listaCalendarioAnios = listaCalendarioAnios;
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

    public List<String> getListaGestiones() {
        return listaGestiones;
    }

    public void setListaGestiones(List<String> listaGestiones) {
        this.listaGestiones = listaGestiones;
    }

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }
}