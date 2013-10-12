package bo.gob.mintrabajo.ovt.bean.Planillas;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class PresentacionPlanillasBean {

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private String idPersona;
    private static final Logger logger = LoggerFactory.getLogger(PresentacionPlanillasBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService idDocumentoService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;

    @ManagedProperty(value = "#{logImpresionService}")
    private ILogImpresionService iLogImpresionService;
    //variables
    private String textoBenvenida;
    private DocDocumentoEntity documento;
    private PerPersonaEntity persona;
    private String periodo;
    private DocBinarioEntity binario;
    private DocPlanillaEntity planilla;
    private boolean habilita = true;
    private List<DocBinarioEntity> listaBinarios;
    private UsrUsuarioEntity usuario;

    @PostConstruct
    public void ini() {



        binario= new DocBinarioEntity();
        planilla= new DocPlanillaEntity();
        listaBinarios = new ArrayList<DocBinarioEntity>();
        logger.info("BienvenidaBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        usuario= new UsrUsuarioEntity();
        usuario = iUsuarioService.findById(bi);
        logger.info("usuario ok");
        persona = iPersonaService.buscarPorId(usuario.getIdPersona());
        logger.info("persona ok");

        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        generaDocumento();
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
        documento.setCodEstado("000");
        documento.setFechaReferenca(new Timestamp(new Date().getTime()));
        documento.setTipoMedioRegistro("DDJJ");
        documento.setFechaBitacora(new Timestamp(new Date().getTime()));
        documento.setRegistroBitacora(usuario.getUsuario());
        System.out.println(documento);
    }

    public void generaPlanilla(){
        logger.info("generaPlanilla()");
        planilla.setTipoPlanilla("DDJJ");
        planilla.setFechaOperacion(new Timestamp(new Date().getTime()));
//        planilla.setPeriodo();
    }

    public void upload(FileUploadEvent evento){
        logger.info("upload(FileUploadEvent evento)");
        UploadedFile file = evento.getFile();
        try{
            binario = new DocBinarioEntity();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Timestamp(new Date().getTime()));
            binario.setRegistroBitacora("OVT");
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

    public void guardaDocumentoBinarioPlanilla(){
        List<DocLogImpresionEntity> l= new ArrayList<DocLogImpresionEntity>();

        for(int i=1;i<=3;i++){
            DocLogImpresionEntity impresion = new DocLogImpresionEntity();
            impresion.setRegistroBitacora("Registro"+i);
            impresion.setIdDocumento(1);
            impresion.setFechaBitacora(new Timestamp(new Date().getTime()));
            l.add(impresion);

        }
        iLogImpresionService.guarda(l);
//        try{
//            logger.info("Guardando documento, binario y planilla");
//            logger.info(documento.toString());
//            logger.info(listaBinarios.toString());
//            logger.info(planilla.toString());
//            generaPlanilla();
//            idDocumentoService.guardaDocumentoBinarioPlanilla(documento, listaBinarios, planilla);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public String getTextoBenvenida() {
        return textoBenvenida;
    }

    public void setTextoBenvenida(String textoBenvenida) {
        this.textoBenvenida = textoBenvenida;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public PerPersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PerPersonaEntity persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public DocDocumentoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumentoEntity documento) {
        this.documento = documento;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public boolean isHabilita() {
        return habilita;
    }

    public void setHabilita(boolean habilita) {
        this.habilita = habilita;
    }

    public ILogImpresionService getiLogImpresionService() {
        return iLogImpresionService;
    }

    public void setiLogImpresionService(ILogImpresionService iLogImpresionService) {
        this.iLogImpresionService = iLogImpresionService;
    }
}