package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.bean.EscritorioBean;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class ImpresionRoeBean {
    //

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
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
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    //
    private Long idDocumento;
    private DocDefinicionPK docDefinicionPK;
    private PerUnidadPK perUnidadPK;
    private UsrUsuario usuario;
    private boolean esFuncionario;
    //
    private VperPersona vperPersona;
    private DocDocumento documento;
    private DocGenerico docGenerico;
    private DocDefinicion docDefinicion;
    //
    private String bancoDeposito;
    private int nroComprobanteDeposito;
    private Date fechaDeposito;
    private BigDecimal montoDeposito;
    private List<ParEntidad> listaEntidades;
    private String parametroDocDefinicion;
//    private boolean cambiarNroUnidad;
    private String bitacoraSession;

    @PostConstruct
    public void ini() {
        logger.info("BajaRoeBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        bitacoraSession=(String)session.getAttribute("bitacoraSession");
        cargar();
        cargarEntidades();

//        try {
//            docDefinicion = iDefinicionService.buscaPorId((DocDefinicionPK) session.getAttribute("docDefinicionPK"));
//        } catch (Exception e) {
//            DocDefinicionPK docDefinicionPK = new DocDefinicionPK();
//            docDefinicionPK.setCodDocumento("ROE013");
//            docDefinicionPK.setVersion((short) 1);
//            docDefinicion = iDefinicionService.buscaPorId(docDefinicionPK);
//        }
//
//        usuario = iUsuarioService.findById(idUsuario);
//        esFuncionario = usuario.getEsInterno() == 1 ? true : false;
//        cargar1();
//        cargarEntidades();
    }

    public void cargar() {
        //session.setAttribute("parametroDocDefinicion", Dominios.PAR_DOCUMENTO_ROE_INSCRIPCION);
        //session.setAttribute("parametroDocDefinicion", Dominios.PAR_DOCUMENTO_ROE_INSCRIPCION);
        parametroDocDefinicion=(String) session.getAttribute("parametroDocDefinicion");
//        cambiarNroUnidad=false;
//        System.out.println("============================================");
//        System.out.println("============================================");
//        System.out.println("============================================");
//        System.out.println("parametroDocDefinicion: "+parametroDocDefinicion);
//        System.out.println("============================================");
//        System.out.println("============================================");
//        System.out.println("============================================");
        //docDefinicionPK = (DocDefinicionPK) session.getAttribute("docDefinicionPK");
//        if (parametroDocDefinicion != null) {
//            docDefinicion=iDefinicionService.buscarActivoPorParametro(parametroDocDefinicion);
//            
//            vperPersona = iVperPersonaService.cargaVistaPersona(idEmpleador);
//            bancoDeposito = "";
//            nroComprobanteDeposito = 0;
//            fechaDeposito = null;
//            montoDeposito = BigDecimal.ZERO;
//            //
//            docGenerico = new DocGenerico();
//            docGenerico.setCadena05(vperPersona.getRlNombre());
//            docGenerico.setCadena06(vperPersona.getRlNroIdentidad());
//            //
//            perUnidadPK = new PerUnidadPK(idEmpleador, 0L);
//            //
//            cambiarNroUnidad=true;
//            
//        } else {
            idDocumento = (Long) session.getAttribute("idDocumento");
            if (idDocumento != null) {
                docGenerico = iDocGenericoService.buscarPorDocumento(idDocumento);
                //docDefinicionPK = docGenerico.getIdDocumento().getDocDefinicion().getDocDefinicionPK();
                //-----doc definicion----
                docDefinicion=iDefinicionService.buscarActivoPorCodDocumento(docGenerico.getIdDocumento().getDocDefinicion().getDocDefinicionPK().getCodDocumento());
                //-----------------------
                docGenerico = iDocGenericoService.buscarPorDocumento(idDocumento);
                //
                vperPersona = iVperPersonaService.cargaVistaPersona(docGenerico.getIdDocumento().getPerUnidad().getPerPersona().getIdPersona());
                bancoDeposito = docGenerico.getCadena01();
                nroComprobanteDeposito = Integer.valueOf(docGenerico.getCadena02() != null ? docGenerico.getCadena02() : "0");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fechaDeposito = sdf.parse(docGenerico.getCadena03());
                } catch (Exception e) {
                }
                montoDeposito = new BigDecimal(docGenerico.getCadena04() != null ? docGenerico.getCadena04() : "0");
            } else {
                docDefinicion=iDefinicionService.buscarActivoPorParametro(Dominios.PAR_DOCUMENTO_ROE_IMPRESION);
//                docDefinicionPK = new DocDefinicionPK();
//                docDefinicionPK.setCodDocumento("ROE013");
//                docDefinicionPK.setVersion((short) 1);
                //
                vperPersona = iVperPersonaService.cargaVistaPersona(idEmpleador);
                bancoDeposito = "";
                nroComprobanteDeposito = 0;
                fechaDeposito = null;
                montoDeposito = BigDecimal.ZERO;
                //
                docGenerico = new DocGenerico();
                docGenerico.setCadena05(vperPersona.getRlNombre());
                docGenerico.setCadena06(vperPersona.getRlNroIdentidad());
                //
                perUnidadPK = new PerUnidadPK(idEmpleador, 0L);
            }

//        }
        //docDefinicion = iDefinicionService.buscaPorId(docDefinicionPK);
//        System.out.println("============================================");
//        System.out.println("docDefinicion: "+docDefinicion.getNombre());
//        System.out.println("============================================");
    }

//    public void cargar1() {
//        vperPersona = iVperPersonaService.cargaVistaPersona(idEmpleador);
//        bancoDeposito = "";
//        nroComprobanteDeposito = 0;
//        fechaDeposito = null;
//        montoDeposito = BigDecimal.ZERO;
//        docGenerico = new DocGenerico();
//        docGenerico.setCadena05(vperPersona.getRlNombre());
//        docGenerico.setCadena06(vperPersona.getRlNroIdentidad());
//        //
//        cargarDocumento();
//    }
//
//    public void cargarDocumento() {
//        documento = new DocDocumento();
//        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(idEmpleador, 0L)));
//    }

    public void cargarEntidades() {
        listaEntidades = iEntidadService.listarPorTipo("FINANCIERA");
    }

    public String guardar() {
        if (bancoDeposito == null || bancoDeposito.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo Banco."));
            return "";
        }
        if (nroComprobanteDeposito == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo nroComprobanteDeposito."));
            return "";
        }
        if (fechaDeposito == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo fechaDeposito."));
            return "";
        }
        if (montoDeposito == null || montoDeposito.equals(BigDecimal.ZERO)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe el campo montoDeposito."));
            return "";
        }
        docGenerico.setCadena01(bancoDeposito);
        docGenerico.setCadena02("" + nroComprobanteDeposito);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        docGenerico.setCadena03(sdf.format(fechaDeposito));
        docGenerico.setCadena04(montoDeposito.toString());
        //

        //documento = iDocumentoService.guardarImpresionRoe(documento, docGenerico, idUsuario.toString(), docDefinicion);//, vperPersona, idUsuarioEmpleador);
        documento = iDocumentoService.guardarDocumentoRoe(docGenerico, idDocumento, perUnidadPK, docDefinicion.getDocDefinicionPK(), bitacoraSession,parametroDocDefinicion);
        
        session.removeAttribute("idDocumento");
//        session.removeAttribute("docDefinicionPK");
        session.removeAttribute("parametroDocDefinicion");
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

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

    public IEntidadService getiEntidadService() {
        return iEntidadService;
    }

    public void setiEntidadService(IEntidadService iEntidadService) {
        this.iEntidadService = iEntidadService;
    }

    public List<ParEntidad> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<ParEntidad> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public IDocGenericoService getiDocGenericoService() {
        return iDocGenericoService;
    }

    public void setiDocGenericoService(IDocGenericoService iDocGenericoService) {
        this.iDocGenericoService = iDocGenericoService;
    }
}