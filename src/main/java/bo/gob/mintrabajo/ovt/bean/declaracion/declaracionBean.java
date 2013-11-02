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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/29/13
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class declaracionBean implements Serializable {


    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(declaracionBean.class);
    private Long idUsuario;
    private String idPersona;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService idDocumentoService;
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

    private List<ParObligacionCalendario> parObligacionCalendarioLista;
    private List<ParEntidad> parEntidadLista;
    private List<DocDocumento> docDocumentoList;
    private PerPersona perPersona;
    private VperPersona vperPersona;//    arreglar esto para a traves del entityManager
    private DocPlanilla docPlanilla;
    private boolean esRectificatorio;
    private Date fechaOperacionAux;
    private Long numeroOrden;

    private String fechaTexto;
    private String temporal;
    private boolean temporalBoolean;
    private PerPersona persona;
    private Date fechaTemp = new Date();

    private String textoBenvenida;
    private DocDocumento documento;
    private String periodo;
    private DocBinario binario;
    private boolean habilita = true;
    private List<DocBinario> listaBinarios;
    private UsrUsuario usuario;
    private UploadedFile file;
    private String nombres[]= new String[3];
    //
    private boolean estaDeclarado;

    @PostConstruct
    public void ini() {
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
        docPlanilla.setHaberBasico(BigDecimal.ZERO);
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
        //** Controlamos que no puedan acceder a una fecha anterior a la actual  **//
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaTexto = sdf.format(fechaTemp);
        obtenerPeriodoLista();
        obtenerEntidad();
        //** Obtenemos de la Vista a la persona **//
        vperPersona = iVperPersonaService.cargaVistaPersona(perPersona.getIdPersona());
        binario= new DocBinario();
        listaBinarios = new ArrayList<DocBinario>();
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        generaDocumento();
        verEstadoPlanilla();
    }

    public void cargarListaPorNumeros(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String pathReport = (String) servletContext.getRealPath("/");
        esRectificatorio=  pathReport.contains("rectificatoria");
        docDocumentoList= new ArrayList<DocDocumento>();
        docDocumentoList= iDocumentoService.listarPorNumero(idPersona);
    }

    public void verEstadoPlanilla(){
        List<DocDocumento> listaDocumentos;
        try{
            listaDocumentos=iDocumentoService.listarPorPersona(idPersona);
            if(listaDocumentos==null){
                listaDocumentos=new ArrayList<DocDocumento>();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumento>();
        }
        estaDeclarado=false;
        for(DocDocumento documento:listaDocumentos){
            if(documento.getCodEstado().getDescripcion().toLowerCase().equals("declarado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("observado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("finalizado")){
                estaDeclarado=true;
            }
        }
    }

    public void generaDocumento(){
        logger.info("generaDocumento()");
        documento = new DocDocumento();
        documento.setIdPersona(persona);
        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(persona.getIdPersona(), 0L)));
        documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1010", (short) 1)));
        documento.setNumeroDocumento(new BigInteger("100000"));
        documento.setFechaDocumento(new Date());
        documento.setCodEstado(iDocumentoEstadoService.buscarPorId("110"));
        documento.setFechaReferenca(new Date());
        documento.setTipoMedioRegistro("DDJJ");
        documento.setFechaBitacora(new Timestamp(new Date().getTime()));
        documento.setRegistroBitacora(usuario.getUsuario());
    }

    public void generaPlanilla(boolean isSinMovimiento){
        logger.info("generaPlanilla()");
        ParEntidad parEntidad = new ParEntidad();  //************************
        parEntidad.setIdEntidad(new Long("2"));    // Obtener por base de datos !!!!
        docPlanilla.setIdEntidadBanco(parEntidad); //************************
        docPlanilla.setTipoPlanilla(isSinMovimiento ? "DDJJSM" : "DDJJ");
        if (esRectificatorio)
            docPlanilla.setTipoPlanilla("DDJJRECT");
        docPlanilla.setFechaOperacion(new Timestamp(fechaOperacionAux.getTime()));
    }

    public void upload(FileUploadEvent evento){
        logger.info("upload(FileUploadEvent evento)");
        file = evento.getFile();
        nombres[listaBinarios.size()]= file.getFileName();
        try{
            binario = new DocBinario();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Timestamp(new Date().getTime()));
            binario.setRegistroBitacora(usuario.getUsuario());
            binario.setBinario(file.getContents());
            listaBinarios.add(binario);

            if(listaBinarios.size()==3)
                habilita=false;
        }catch (Exception e){
            habilita=true;
            e.printStackTrace();
        }
    }

    public String guardaDocumentoPlanillaBinario(){
        try{
            logger.info("Guardando documento, binario y planilla");
            logger.info(documento.toString());
            logger.info(listaBinarios.toString());
            logger.info(docPlanilla.toString());
            generaPlanilla(false);
            idDocumentoService.guardaDocumentoPlanillaBinario(documento, docPlanilla, listaBinarios);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
            return "irListadoBienvenida";
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

    public void obtenerPeriodoLista(){
        parObligacionCalendarioLista = new ArrayList<ParObligacionCalendario>();
        parObligacionCalendarioLista = iObligacionCalendarioService.obtenerObligacionCalendario();
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

    public List<ParObligacionCalendario> getParObligacionCalendarioLista() {
        return parObligacionCalendarioLista;
    }

    public void setParObligacionCalendarioLista(List<ParObligacionCalendario> parObligacionCalendarioLista) {
        this.parObligacionCalendarioLista = parObligacionCalendarioLista;
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

    public IDocumentoService getIdDocumentoService() {
        return idDocumentoService;
    }

    public void setIdDocumentoService(IDocumentoService idDocumentoService) {
        this.idDocumentoService = idDocumentoService;
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

    public List<DocBinario> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinario> listaBinarios) {
        this.listaBinarios = listaBinarios;
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

    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
        this.temporal = temporal;
    }
    public List<DocDocumento> getDocDocumentoList() {
        return docDocumentoList;
    }

    public void setDocDocumentoEntityList(List<DocDocumento> docDocumentoList) {
        this.docDocumentoList = docDocumentoList;
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
}