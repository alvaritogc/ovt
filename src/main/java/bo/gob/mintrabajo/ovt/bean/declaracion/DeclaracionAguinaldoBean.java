package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: gmercado
 * Date: 10/29/13
 * Time: 9:34 AM
 */
@ManagedBean
@ViewScoped
public class DeclaracionAguinaldoBean implements Serializable {


    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DeclaracionAguinaldoBean.class);
    private Long idUsuario;
    private String idPersona;
    private Long idUnidad;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;
    @ManagedProperty(value = "#{planillaDetalleService}")
    private IPlanillaDetalleService iPlanillaDetalleService;
    @ManagedProperty(value = "#{calendarioService}")
    private ICalendarioService iCalendarioService;

    private int parametro;
    private ParObligacionCalendario parObligacionCalendarioPeriodo;
    private List<ParEntidad> parEntidadLista;
    private List<DocPlanillaDetalle> docPlanillaDetalles;
    private PerPersona perPersona;
    private VperPersona vperPersona;
    private DocPlanilla docPlanilla;
    private boolean esRectificatorio;
    private Date fechaOperacionAux;
    private Long numeroOrden;

    private String fechaTexto;
    private boolean temporalBoolean;
    private PerPersona persona;
    private Date fechaTemp = new Date();

    private String textoBenvenida;
    private DocDocumento documento;
    private String periodo;
    private DocBinario binario;
    private boolean habilita = true;
    private DocBinario archivoBinario;
    private UsrUsuario usuario;
    private UploadedFile file;
    private String nombres[]= new String[3];
    //
    private boolean estaDeclarado;
    private Long idEntidadSalud;
    private String estaDeclaradoMensaje;
    private boolean verificaValidacion;
//    private Long idRectificatorio;
    private List<String> errores = new ArrayList<String>();
    private boolean valor;
    private int tipoEmpresa=1;
    private PerUnidad unidadSeleccionada;
    private int tamañoSucursales;
    private boolean check_tipoEmpresa;
    private String gestion;
    private List<DocDocumento> docDocumentosParaRectificar;
    private List<DocAlerta> alertas;
    private List<DocPlanilla> docPlanillasParaRectificar;
    private DocPlanilla rectificatorio;
    
    @PostConstruct
    public void ini() {
        parametro = (Integer) session.getAttribute("parametro");
        unidadSeleccionada = (PerUnidad) session.getAttribute("unidadSeleccionada");
        tipoEmpresa = (Integer) session.getAttribute("tipoEmpresa");
        if(parametro==2)
            habilita=false;

        idPersona = (String) session.getAttribute("idEmpleador");
        logger.info("buscando persona:"+idPersona);
        persona = iPersonaService.buscarPorId(idPersona);
        //
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Long) session.getAttribute("idUsuario");
        Long temp = Long.valueOf(idUsuario);
        usuario = iUsuarioService.findById(idUsuario);
        perPersona = iPersonaService.buscarPorId(idPersona);
        docPlanilla = new DocPlanilla();
        docPlanilla.setHaberBasico(BigDecimal.ZERO);//Total Aguinaldo
        docPlanilla.setBonoAntiguedad(BigDecimal.ZERO);
        docPlanilla.setBonoProduccion(BigDecimal.ZERO);
        docPlanilla.setSubsidioFrontera(BigDecimal.ZERO);
        docPlanilla.setLaborExtra(BigDecimal.ZERO);
        docPlanilla.setOtrosBonos(BigDecimal.ZERO);
        docPlanilla.setRciva(BigDecimal.ZERO);
        docPlanilla.setAporteAfp(BigDecimal.ZERO);
        docPlanilla.setOtrosDescuentos(BigDecimal.ZERO);
        docPlanilla.setMontoAsegCaja(BigDecimal.ZERO);
        docPlanilla.setMontoAsegAfp(BigDecimal.ZERO);
        docPlanilla.setMontoOperacion(BigDecimal.ZERO);
        docPlanilla.setNroH(0);
        docPlanilla.setNroM(0);
        docPlanilla.setNroAsegCaja(0);
        docPlanilla.setIdEntidadSalud(null);
        docPlanilla.setNroAsegAfp(0);
        docPlanilla.setLaborExtra(BigDecimal.ZERO);
        docPlanilla.setNroJubiladosH(0);
        docPlanilla.setNroJubiladosM(0);
        docPlanilla.setNroExtranjerosH(0);
        docPlanilla.setNroExtranjerosM(0);
        docPlanilla.setNroDiscapacidadH(0);
        docPlanilla.setNroDiscapacidadM(0);
        docPlanilla.setNroContratadosH(0);
        docPlanilla.setNroContratadosM(0);
        docPlanilla.setNroRetiradosH(0);
        docPlanilla.setNroRetiradosM(0);
        docPlanilla.setNroAccidentes(0);
        docPlanilla.setNroMuertes(0);
        docPlanilla.setNroEnfermedades(0);
        docPlanilla.setNroRetiradosH(0);
        docPlanilla.setNumOperacion("");
        //** Controlamos que no puedan acceder a una fecha anterior a la actual  **//
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaTexto = sdf.format(fechaTemp);
        gestion=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        parObligacionCalendarioPeriodo= iObligacionCalendarioService.buscarAguinaldoPorGestion(gestion);
        periodo =parObligacionCalendarioPeriodo.getParCalendario().getTipoCalendario();
        obtenerEntidad();
        //** Obtenemos de la Vista a la persona **//
        vperPersona = iVperPersonaService.cargaVistaPersona(perPersona.getIdPersona());
        binario= new DocBinario();
//        idPersona = (String) session.getAttribute("idEmpleador");
//        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
        if (parametro==5){
            esRectificatorio=true;
            rectificatorio = iPlanillaService.buscarPorDocumento(iDocumentoService.buscarPorUnindad(unidadSeleccionada.getPerUnidadPK()).getIdDocumento());
        }
        valor=true;
    }

    public void cargar() {
        generaDocumento();
        if(parametro==5)
            cargarDocumentosParaRectificar();
    }

    public void cargarDocumentosParaRectificar(){
        docPlanillasParaRectificar= new ArrayList<DocPlanilla>();
        docPlanillasParaRectificar= iPlanillaService.listarPlanillasParaRectificar(idPersona, "LC1020");
    }

//    public void seleccionaTrimestre(){
//        periodo = iPlanillaService.buscarPorDocumento(idRectificatorio).getParCalendario().getParCalendarioPK().getTipoPeriodo();
//    }

    public void habilitaTipoEmpresa(){

        if(tipoEmpresa==2){
           check_tipoEmpresa=false;
        }
        else{
            check_tipoEmpresa=true;
        }
    }

    public boolean isCheck_tipoEmpresa() {
        return check_tipoEmpresa;
    }

    public void setCheck_tipoEmpresa(boolean check_tipoEmpresa) {
        this.check_tipoEmpresa = check_tipoEmpresa;
    }
    public void generaDocumento(){
        logger.info("generaDocumento()");
        documento = new DocDocumento();
//        documento.setIdPersona(persona);

        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(persona.getIdPersona(), 0L)));
        System.out.println("documento==>");
        System.out.println(documento);
        if(parametro==4)
            documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1020", (short) 1)));
        else{
            if(parametro==5)
                documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1021", (short) 1)));
        }

        documento.setFechaDocumento(new Date());
        documento.setCodEstado(iDocumentoEstadoService.buscarPorId("110"));
        documento.setFechaReferenca(new Date());
        if(tipoEmpresa!=2)
            documento.setTipoMedioRegistro("CONSOLIDADO");
        else
            documento.setTipoMedioRegistro("SUCURSAL");
        documento.setFechaBitacora(new Date());
        documento.setRegistroBitacora(usuario.getUsuario());
    }

    public void generaPlanilla(){
        logger.info("generaPlanilla()");
        docPlanilla.setIdEntidadBanco(iEntidadService.buscaPorId(new Long("2")));
        docPlanilla.setIdEntidadSalud(iEntidadService.buscaPorId(new Long("13")));
        docPlanilla.setFechaOperacion(fechaOperacionAux);
        docPlanilla.setParCalendario(iCalendarioService.obtenerCalendarioPorGestionYPeriodo(gestion, periodo));


        switch (parametro){
            case 4:
                docPlanilla.setTipoPlanilla("DDJJ");
                break;
            case 5:
                docPlanilla.setTipoPlanilla("DDJJRECT");
                break;
            default:
                docPlanilla.setTipoPlanilla("");
                break;
        }
    }

    public void upload(FileUploadEvent evento){
        logger.info("upload(FileUploadEvent evento)");
        file = evento.getFile();
        try{
            binario = new DocBinario();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Timestamp(new Date().getTime()));
            binario.setRegistroBitacora(usuario.getUsuario());
            binario.setBinario(file.getContents());
            habilita=false;
        }catch (Exception e){
            habilita=true;
            e.printStackTrace();
        }
    }

    public String guardaDocumentoPlanillaBinarioAgunaldoBean(){
           habilita=true;//deshabilita el boton de envio
        try{
            if(parametro==5)
                documento.setIdDocumentoRef(rectificatorio.getIdDocumento());
            logger.info("Guardando documento, binario y planilla");
            logger.info(documento.toString());
            logger.info(docPlanilla.toString());
            generaPlanilla();
            documento.setPerUnidad(unidadSeleccionada);
            List<DocBinario> listaBinarios= new ArrayList<DocBinario>();
            listaBinarios.add(binario);
            iDocumentoService.guardaDocumentoPlanillaBinario(documento, docPlanilla, listaBinarios, docPlanillaDetalles, alertas);
            return "irEscritorio";
        }catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el formulario",""));
            return null;
        }
    }

    public String irInicio(){
        idPersona=(String)session.getAttribute("idPersona");
        String idEmpleador=(String)session.getAttribute("idEmpleador");
        if(idPersona!=null && idEmpleador!=null && idPersona.equals(idEmpleador)){
            return "irBienvenida";
        }
        else{
            return "irEmpleadorBusqueda";
        }
    }

    public String mensajeError(int i, String titulo){
        return "Fila: "+i+" y Columna: "+ titulo+ ";";
    }

    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public void obtenerEntidad() {
        parEntidadLista = iEntidadService.listaEntidad();
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

    public PerPersona getPerPersona() {
        return perPersona;
    }

    public void setPerPersona(PerPersona perPersona) {
        this.perPersona = perPersona;
    }

    public DocPlanilla getDocPlanilla() {
        return docPlanilla;
    }

    public void setDocPlanilla(DocPlanilla docPlanilla) {
        this.docPlanilla = docPlanilla;
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

    public String getFechaTexto() {
        return fechaTexto;
    }

    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    public boolean isTemporalBoolean() {
        return temporalBoolean;
    }

    public void setTemporalBoolean(boolean temporalBoolean) {
        this.temporalBoolean = temporalBoolean;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
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

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public List<ParEntidad> getParEntidadLista() {
        return parEntidadLista;
    }

    public void setParEntidadLista(List<ParEntidad> parEntidadLista) {
        this.parEntidadLista = parEntidadLista;
    }

    public VperPersona getVperPersona() {
        return vperPersona;
    }

    public void setVperPersona(VperPersona vperPersona) {
        this.vperPersona = vperPersona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public String getTextoBenvenida() {
        return textoBenvenida;
    }

    public void setTextoBenvenida(String textoBenvenida) {
        this.textoBenvenida = textoBenvenida;
    }

    public DocDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumento documento) {
        this.documento = documento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public DocBinario getBinario() {
        return binario;
    }

    public void setBinario(DocBinario binario) {
        this.binario = binario;
    }

    public boolean isHabilita() {
        return habilita;
    }

    public void setHabilita(boolean habilita) {
        this.habilita = habilita;
    }

    public UsrUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public boolean isEstaDeclarado() {
        return estaDeclarado;
    }

    public void setEstaDeclarado(boolean estaDeclarado) {
        this.estaDeclarado = estaDeclarado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String[] getNombres() {
        return nombres;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }

    public int getParametro() {
        return parametro;
    }

    public void setParametro(int parametro) {
        this.parametro = parametro;
    }

    public Long getIdEntidadSalud() {
        return idEntidadSalud;
    }

    public void setIdEntidadSalud(Long idEntidadSalud) {
        this.idEntidadSalud = idEntidadSalud;
    }

    public IPlanillaDetalleService getiPlanillaDetalleService() {
        return iPlanillaDetalleService;
    }

    public void setiPlanillaDetalleService(IPlanillaDetalleService iPlanillaDetalleService) {
        this.iPlanillaDetalleService = iPlanillaDetalleService;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public PerUnidad getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(PerUnidad unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public int getTamañoSucursales() {
        return tamañoSucursales;
    }

    public void setTamañoSucursales(int tamañoSucursales) {
        this.tamañoSucursales = tamañoSucursales;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public ICalendarioService getiCalendarioService() {
        return iCalendarioService;
    }

    public void setiCalendarioService(ICalendarioService iCalendarioService) {
        this.iCalendarioService = iCalendarioService;
    }

    public String getEstaDeclaradoMensaje() {
        return estaDeclaradoMensaje;
    }

    public void setEstaDeclaradoMensaje(String estaDeclaradoMensaje) {
        this.estaDeclaradoMensaje = estaDeclaradoMensaje;
    }

    public ParObligacionCalendario getParObligacionCalendarioPeriodo() {
        return parObligacionCalendarioPeriodo;
    }

    public void setParObligacionCalendarioPeriodo(ParObligacionCalendario parObligacionCalendarioPeriodo) {
        this.parObligacionCalendarioPeriodo = parObligacionCalendarioPeriodo;
    }

    public List<DocDocumento> getDocDocumentosParaRectificar() {
        return docDocumentosParaRectificar;
    }

    public void setDocDocumentosParaRectificar(List<DocDocumento> docDocumentosParaRectificar) {
        this.docDocumentosParaRectificar = docDocumentosParaRectificar;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public List<DocPlanilla> getDocPlanillasParaRectificar() {
        return docPlanillasParaRectificar;
    }

    public void setDocPlanillasParaRectificar(List<DocPlanilla> docPlanillasParaRectificar) {
        this.docPlanillasParaRectificar = docPlanillasParaRectificar;
    }

    public DocPlanilla getRectificatorio() {
        return rectificatorio;
    }

    public void setRectificatorio(DocPlanilla rectificatorio) {
        this.rectificatorio = rectificatorio;
    }
}