package bo.gob.mintrabajo.ovt.bean.declaracion;

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
    //
    private UsrUsuario usuario;
    private boolean esFuncionario;
    //
    private VperPersona vperPersona;
    private DocDocumento documento;
    private DocGenerico docGenerico;
    //
    private boolean entero03;
    private boolean entero04;
    private boolean entero05;

    @PostConstruct
    public void ini() {
        logger.info("BajaRoeBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
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
        docGenerico.setCadena05("");
        docGenerico.setCadena06("");
        if (esFuncionario) {
            docGenerico.setCadena06(usuario.getUsuario());
        }
        cargarDocumento();
    }

    public void cargarDocumento() {
        documento = new DocDocumento();
        //
        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(idEmpleador, 0L)));
        documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("ROE012", (short) 1)));//trimestral
        //documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1011", (short) 1)));//sin movimiento
        //documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1012", (short) 1)));//rectificatoria
        //
        documento.setFechaDocumento(new Date());
        documento.setCodEstado(iDocumentoEstadoService.buscarPorId("110"));
        documento.setFechaReferenca(new Date());
        documento.setTipoMedioRegistro("DDJJ");
        documento.setFechaBitacora(new Date());
        documento.setRegistroBitacora(idUsuario.toString());
    }

    public String guardar() {
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("Guardar");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("docGenerico : " + docGenerico.getCadena01());
        if (docGenerico.getCadena01().trim().equals("")
                && docGenerico.getCadena02().trim().equals("")
                && docGenerico.getCadena03().trim().equals("")
                && docGenerico.getCadena04().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año para Indicar si la suspención es temporal o definitiva."));
            return "";
        }
        if (docGenerico.getCadena01().trim().equals("") && docGenerico.getCadena02().trim().equals("")) {
            if (!((!docGenerico.getCadena03().trim().equals("")) && (!docGenerico.getCadena04().trim().equals("")))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año de la Suspención definitiva."));
                return "";
            }
        }
        if (docGenerico.getCadena02().trim().equals("") && docGenerico.getCadena03().trim().equals("")) {
            if (!((!docGenerico.getCadena01().trim().equals("")) && (!docGenerico.getCadena02().trim().equals("")))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Mes y Año de la Suspención temporal."));
                return "";
            }
        }
        if (docGenerico.getEntero01()==null || docGenerico.getEntero01() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Número de trabajadores."));
            return "";
        }
        if (docGenerico.getCadena05().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el nombre del empleador y/o Representante legal."));
            return "";
        }
        if (docGenerico.getEntero02()==null || docGenerico.getEntero02() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el Número de documento de identidad."));
            return "";
        }
        //
        if (esFuncionario) {
            System.out.println("Es funcionario");
            docGenerico.setEntero03(entero03 ? 1 : 0);
            docGenerico.setEntero04(entero04 ? 1 : 0);
            docGenerico.setEntero05(entero05 ? 1 : 0);
//            System.out.println("Es funcionario");
//            if(entero03){
//                System.out.println("1");
//                docGenerico.setEntero03(1);
//            }
//            else{
//                System.out.println("0");
//                docGenerico.setEntero03(0);
//            }
//            if(entero04){
//                System.out.println("1");
//                docGenerico.setEntero04(1);
//            }
//            else{
//                System.out.println("0");
//                docGenerico.setEntero04(0);
//            }
//            if(entero05){
//                System.out.println("1");
//                docGenerico.setEntero05(1);
//            }
//            else{
//                System.out.println("0");
//                docGenerico.setEntero05(0);
//            }
        }
        //
        documento = iDocumentoService.guardarBajaRoe(documento, docGenerico);
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
}