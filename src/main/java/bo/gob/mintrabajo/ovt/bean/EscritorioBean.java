package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collection;

import java.util.List;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;



@ManagedBean
@ViewScoped
public class EscritorioBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private DocDocumento docDocumentoEntity;
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    //
    private String textoBenvenida;
    //
    private UsrUsuario usuario;
    private PerPersona empleador;
    private PerPersona persona;
    private List<PerUnidad> listaUnidades;
    private List<DocDocumento> listaDocumentos;
//    private VperPersonaEntity vperPersonaEntity;
    //
    private boolean esFuncionario;
    //
    private boolean detenerFacesMessages;

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idPersona=(String) session.getAttribute("idPersona");
        idEmpleador=(String) session.getAttribute("idEmpleador");
        System.out.println("idPersona: "+idPersona);
        System.out.println("idEmpleador: "+idEmpleador);
        //
        usuario=iUsuarioService.findById(idUsuario);
        persona=iPersonaService.findById(idPersona);
        empleador=iPersonaService.findById(idEmpleador);
        if(usuario.getEsInterno()==1){
            esFuncionario=false;
        }
        else{
            esFuncionario=true;
        }
        cargar();
        detenerFacesMessages=false;
    }
    
    public void abrirPanel(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dlgMensaje.show()");
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");  
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        detenerFacesMessages=true;
    }

    public void cargar() {
        textoBenvenida="Bienvenido  OVT";
        listaUnidades=iUnidadService.buscarPorPersona(idEmpleador);
        cargarDocumentos();
    }
    
    public void cargarDocumentos(){
        try{
            listaDocumentos=iDocumentoService.listarPorPersona(idEmpleador);
            if(listaDocumentos==null){
            listaDocumentos=new ArrayList<DocDocumento>();
        }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumento>();
        }
    }
    
     public String download(){
        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
        return "irDownload";
    }


    public String irRealizarCambioDeEstados(){
//        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
//        session.setAttribute("codEstadoInicial", docDocumentoEntity.getCodEstado());
//        session.setAttribute("codDocumento", docDocumentoEntity.getCodDocumento());
//        session.setAttribute("version", docDocumentoEntity.getVersion());
        return "irCambioEstado";
    }

    public void irImprimirDocumento(){
//        PerUnidad perUnidadEntity = new PerUnidad();
//        DocPlanillaEntity docPlanillaEntity = new DocPlanillaEntity();
//        try{
//            docPlanillaEntity=  iDocumentoService.retornaPlanilla(docDocumentoEntity.getIdDocumento());
//            perUnidadEntity=  iDocumentoService.retornaUnidad(idPersona);
//            iPlanillaService.generaReporte(docPlanillaEntity, persona, docDocumentoEntity, perUnidadEntity, vperPersonaEntity);
//
//            redirecionarReporte ("");
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
    }


//    private static void redirecionarReporte (String rutaReporte) throws IOException {
//        FacesContext facesContext=FacesContext.getCurrentInstance();
//        HttpServletResponse response=(HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//        File file=new File(rutaReporte);
//        BufferedInputStream input=null;
//        BufferedOutputStream output=null;
//        try{
//
//            input=new BufferedInputStream(new FileInputStream(file),10240);
//            response.reset();
//            response.setHeader("Content-Type", "application/pdf");
//            response.setHeader("Content-Length", String.valueOf(file.length()));
//            output=new BufferedOutputStream(response.getOutputStream(),10240);
//
//            byte[] buffer=new byte[10240];
//            int length;
//
//            while((length=input.read(buffer))>0){
//                output.write(buffer,0,length);
//            }
//            output.flush();
//        }finally{
//            close(output);
//            close(input);
//        }
//
//        facesContext.responseComplete();
//    }

//    private static void close(Closeable resource) {
//        if (resource != null) {
//            try {
//                resource.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public String download(){
//        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
//        return "irDownload";
//    }

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

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public List<PerUnidad> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<PerUnidad> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<DocDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    public DocDocumento getDocDocumentoEntity() {
        return docDocumentoEntity;
    }

    public void setDocDocumentoEntity(DocDocumento docDocumentoEntity) {
        this.docDocumentoEntity = docDocumentoEntity;
    }

    public boolean isEsFuncionario() {
        return esFuncionario;
    }

    public void setEsFuncionario(boolean esFuncionario) {
        this.esFuncionario = esFuncionario;
    }

    public boolean isDetenerFacesMessages() {
        return detenerFacesMessages;
    }

    public void setDetenerFacesMessages(boolean detenerFacesMessages) {
        this.detenerFacesMessages = detenerFacesMessages;
    }

    public PerPersona getEmpleador() {
        return empleador;
    }

    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }
}
