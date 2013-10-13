package bo.gob.mintrabajo.ovt.bean.Formulario;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: gmercado Date: 10/8/13 Time: 2:17 PM To
 * change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class formularioUnicoBean implements Serializable{

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(formularioUnicoBean.class);
    private Integer idUsuario;
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

    private List<ParObligacionCalendarioEntity> parObligacionCalendarioLista;
    private List<ParEntidadEntity> parEntidadEntityLista;
    private PerPersonaEntity perPersonaEntity;
    private VperPersonaEntity vperPersonaEntity;
    private DocPlanillaEntity docPlanillaEntity;
    private boolean esRectificatorio=false;
    private Date fechaOperacionAux;
    private Long numeroOrden;

    private String temporal;
    private boolean temporalBoolean;
    private PerPersonaEntity persona;
    private Date fechaTemp = new Date();

    private String textoBenvenida;
    private DocDocumentoEntity documento;
    private String periodo;
    private DocBinarioEntity binario;
    private boolean habilita = true;
    private List<DocBinarioEntity> listaBinarios;
    private UsrUsuarioEntity usuario;
    //
    private boolean estaDeclarado;

    @PostConstruct
    public void ini() {
        idPersona = (String) session.getAttribute("idEmpleador");
        logger.info("buscando persona:"+idPersona);
        persona = iPersonaService.buscarPorId(idPersona);
        //
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal temp = BigDecimal.valueOf(idUsuario);
        usuario = iUsuarioService.findById(temp);
        perPersonaEntity = iPersonaService.buscarPorId(idPersona);
        docPlanillaEntity = new DocPlanillaEntity();
        obtenerPeriodoLista();
        obtenerEntidad();
        //** Obtenemos de la Vista a la persona **//
        vperPersonaEntity = DobleTrabajoConexion.obtenerPersona(perPersonaEntity.getIdPersona());
        binario= new DocBinarioEntity();
        listaBinarios = new ArrayList<DocBinarioEntity>();
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        generaDocumento();
        verEstadoPlanilla();
    }
    public void verEstadoPlanilla(){
        List<DocDocumentoEntity> listaDocumentos;
         try{
            listaDocumentos=iDocumentoService.listarPorPersona(idPersona);
            if(listaDocumentos==null){
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
        estaDeclarado=false;
        for(DocDocumentoEntity documento:listaDocumentos){
            if(documento.getDocumentoEstado().getDescripcion().toLowerCase().equals("declarado")
                    || documento.getDocumentoEstado().getDescripcion().toLowerCase().equals("observado")
                    || documento.getDocumentoEstado().getDescripcion().toLowerCase().equals("finalizado")){
                estaDeclarado=true;
            }
        }
    }

    public void generaDocumento(){
        logger.info("generaDocumento()");
        documento = new DocDocumentoEntity();
        documento.setIdPersona(persona.getIdPersona());
        documento.setIdUnidad((iUnidadService.listarPorPersona(persona.getIdPersona()).get(0)).getIdUnidad());
        documento.setCodDocumento("LC1010");
        documento.setVersion(1);
        documento.setNumeroDocumento(100000L);
        documento.setFechaDocumento(new Timestamp(new Date().getTime()));
        //codEstado clave foranea de DocEstadoEntity
        documento.setCodEstado("110");
        documento.setFechaReferenca(new Timestamp(new Date().getTime()));
        documento.setTipoMedioRegistro("DDJJ");
        documento.setFechaBitacora(new Timestamp(new Date().getTime()));
        documento.setRegistroBitacora(usuario.getUsuario());
        System.out.println(documento);
    }

    public void generaPlanilla(){
        logger.info("generaPlanilla()");
        docPlanillaEntity.setIdEntidadBanco(2);
        docPlanillaEntity.setTipoPlanilla("DDJJ");
        docPlanillaEntity.setFechaOperacion(new Timestamp(fechaOperacionAux.getTime()));
    }

    public void upload(FileUploadEvent evento){
        logger.info("upload(FileUploadEvent evento)");
        UploadedFile file = evento.getFile();
        try{
            binario = new DocBinarioEntity();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Timestamp(new Date().getTime()));
            binario.setRegistroBitacora(usuario.getUsuario());
            binario.setIdBinario(10);
            binario.setBinario(file.getContents());
            listaBinarios.add(binario);
            if(listaBinarios.size()==3)
                habilita=false;
        }catch (Exception e){
            habilita=true;
            e.printStackTrace();
        }
    }

    public String guardaDocumentoBinarioPlanilla(){
        try{
            logger.info("Guardando documento, binario y planilla");
            logger.info(documento.toString());
            logger.info(listaBinarios.toString());
            logger.info(docPlanillaEntity.toString());
            generaPlanilla();
            idDocumentoService.guardaDocumentoBinarioPlanilla(documento, listaBinarios, docPlanillaEntity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
            return "irListadoBienvenida";
        }catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el formulario",""));
            return null;
        }
    }
    
    public String irBienvenida(){
        return "irBienvenida";
    }

    public void obtenerPeriodoLista(){
        parObligacionCalendarioLista = new ArrayList<ParObligacionCalendarioEntity>();
        parObligacionCalendarioLista = iObligacionCalendarioService.obtenerObligacionCalendario();
    }


    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public void obtenerEntidad() {
            parEntidadEntityLista = iEntidadService.getEntidadLista();
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

    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
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

    public List<ParObligacionCalendarioEntity> getParObligacionCalendarioLista() {
        return parObligacionCalendarioLista;
    }

    public void setParObligacionCalendarioLista(List<ParObligacionCalendarioEntity> parObligacionCalendarioLista) {
        this.parObligacionCalendarioLista = parObligacionCalendarioLista;
    }

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public List<ParEntidadEntity> getParEntidadEntityLista() {
        return parEntidadEntityLista;
    }

    public void setParEntidadEntityLista(List<ParEntidadEntity> parEntidadEntityLista) {
        this.parEntidadEntityLista = parEntidadEntityLista;
    }

    public VperPersonaEntity getVperPersonaEntity() {
        return vperPersonaEntity;
    }

    public void setVperPersonaEntity(VperPersonaEntity vperPersonaEntity) {
        this.vperPersonaEntity = vperPersonaEntity;
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

    public DocDocumentoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumentoEntity documento) {
        this.documento = documento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public DocBinarioEntity getBinario() {
        return binario;
    }

    public void setBinario(DocBinarioEntity binario) {
        this.binario = binario;
    }

    public boolean isHabilita() {
        return habilita;
    }

    public void setHabilita(boolean habilita) {
        this.habilita = habilita;
    }

    public List<DocBinarioEntity> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinarioEntity> listaBinarios) {
        this.listaBinarios = listaBinarios;
    }

    public UsrUsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuarioEntity usuario) {
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
}