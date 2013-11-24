package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
public class edicionRoeBean {
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
    @ManagedProperty(value = "#{docGenericoService}")
    private IDocGenericoService iDocGenericoService;
    //
    private Long idDocumento;
    private UsrUsuario usuario;
    private boolean esFuncionario;
    //
    private VperPersona vperPersona;
    //private DocDocumento documento;
    private DocGenerico docGenerico;
    //
    private String bancoDeposito;
    private int nroComprobanteDeposito;
    private Date fechaDeposito;
    private BigDecimal montoDeposito;
    private DocDefinicion docDefinicion;

    @PostConstruct
    public void ini() {
        logger.info("BajaRoeBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        usuario = iUsuarioService.findById(idUsuario);
        esFuncionario = usuario.getEsInterno() == 1 ? true : false;
        cargarGenericoSession();
    }
    
    public void cargarGenericoSession(){
        idDocumento=(Long) session.getAttribute("idDocumento");
        
        docGenerico=iDocGenericoService.buscarPorDocumento(idDocumento);
        docDefinicion=iDefinicionService.buscaPorId(docGenerico.getIdDocumento().getDocDefinicion().getDocDefinicionPK());
        //
        vperPersona = iVperPersonaService.cargaVistaPersona(docGenerico.getIdDocumento().getPerUnidad().getPerPersona().getIdPersona());
        bancoDeposito=docGenerico.getCadena01();
        nroComprobanteDeposito=Integer.valueOf(docGenerico.getCadena02());
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        try {
            fechaDeposito=sdf.parse(docGenerico.getCadena03());
        } catch (Exception e) {
        }
        montoDeposito=new BigDecimal(docGenerico.getCadena04());
    }

    public String guardar() {
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("Guardar");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("docGenerico : " + docGenerico.getCadena01());
        if(bancoDeposito==null || bancoDeposito.equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo Banco."));
            return "";
        }
        if(nroComprobanteDeposito==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo nroComprobanteDeposito."));
            return "";
        }
        if(fechaDeposito==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo fechaDeposito."));
            return "";
        }
        if(montoDeposito==null || montoDeposito.equals(BigDecimal.ZERO)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo montoDeposito."));
            return "";
        }
        docGenerico.setCadena01(bancoDeposito);
        docGenerico.setCadena02(""+nroComprobanteDeposito);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        docGenerico.setCadena03(sdf.format(fechaDeposito));
        docGenerico.setCadena04(montoDeposito.toString());
        //
        //documento = iDocumentoService.guardarImpresionRoe(documento, docGenerico,idUsuario.toString());
        docGenerico=iDocGenericoService.modificar(docGenerico, idDocumento);
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

//    public DocDocumento getDocumento() {
//        return documento;
//    }
//
//    public void setDocumento(DocDocumento documento) {
//        this.documento = documento;
//    }

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

    public String getBancoDeposito() {
        return bancoDeposito;
    }

    public void setBancoDeposito(String bancoDeposito) {
        this.bancoDeposito = bancoDeposito;
    }

    public int getNroComprobanteDeposito() {
        return nroComprobanteDeposito;
    }

    public void setNroComprobanteDeposito(int nroComprobanteDeposito) {
        this.nroComprobanteDeposito = nroComprobanteDeposito;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public BigDecimal getMontoDeposito() {
        return montoDeposito;
    }

    public void setMontoDeposito(BigDecimal montoDeposito) {
        this.montoDeposito = montoDeposito;
    }

    public IDocGenericoService getiDocGenericoService() {
        return iDocGenericoService;
    }

    public void setiDocGenericoService(IDocGenericoService iDocGenericoService) {
        this.iDocGenericoService = iDocGenericoService;
    }

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

   
}