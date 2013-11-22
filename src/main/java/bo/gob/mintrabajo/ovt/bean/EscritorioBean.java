package bo.gob.mintrabajo.ovt.bean;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
//import java.util.Collection;
//import java.util.Collection;

@ManagedBean
@ViewScoped
public class EscritorioBean {
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
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;
    //
    private String textoBenvenida;
    //
    private UsrUsuario usuario;
    private PerPersona empleador;
    private PerPersona persona;
    private List<PerUnidad> listaUnidades;
    private List<DocDocumento> listaDocumentos;
    //
    private boolean esInterno;
    //
    private DocDocumento docDocumento;
    private DocPlanilla docPlanilla;
    private ParDocumentoEstado parDocumentoEstado;
    private List<ParDocumentoEstado> listaDocumentoEstado;
    private String codEstadoFinal;
    private VperPersona vperPersona;

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
        idPersona = (String) session.getAttribute("idPersona");
        idEmpleador = (String) session.getAttribute("idEmpleador");
        System.out.println("idPersona: " + idPersona);
        System.out.println("idEmpleador: " + idEmpleador);
        //
        usuario = iUsuarioService.findById(idUsuario);
        persona = iPersonaService.findById(idPersona);
        empleador = iPersonaService.findById(idEmpleador);
        if (usuario.getEsInterno() == 1) {
            esInterno = true;
        } else {
            esInterno = false;
        }
        cargar();
    }

    public void cargar() {
        textoBenvenida = "Bienvenido  OVT";
        listaUnidades = iUnidadService.buscarPorPersona(idEmpleador);
        cargarDocumentos();
    }

    public void cargarDocumentos() {
        try {
            listaDocumentos = iDocumentoService.listarPorPersona(idEmpleador);
            if (listaDocumentos == null) {
                listaDocumentos = new ArrayList<DocDocumento>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            listaDocumentos = new ArrayList<DocDocumento>();
        }
    }

    public String download() {
        session.setAttribute("idDocumento", docDocumento.getIdDocumento());
        return "irDownload";
    }

    public String irRealizarCambioDeEstados() {
//        session.setAttribute("idDocumento", docDocumentoEntity.getIdDocumento());
//        session.setAttribute("codEstadoInicial", docDocumentoEntity.getCodEstado());
//        session.setAttribute("codDocumento", docDocumentoEntity.getCodDocumento());
//        session.setAttribute("version", docDocumentoEntity.getVersion());
        return "irCambioEstado";
    }
    public void cargarCambioDeEstados(){
        try {
            docPlanilla=iPlanillaService.buscarPorDocumento(docDocumento.getIdDocumento());
        } catch (Exception e) {
            e.printStackTrace();
            docPlanilla=null;
        }
        
        listaDocumentoEstado=iDocumentoEstadoService.listarSiguientesTransiciones(docDocumento);
        System.out.println("size: "+listaDocumentoEstado.size());
        if(!listaDocumentoEstado.isEmpty()){
            codEstadoFinal=listaDocumentoEstado.get(0).getCodEstado();
        }
        else{
            codEstadoFinal="";
        }
    }
    public String realizarCambioDeEstados(){
        parDocumentoEstado=iDocumentoEstadoService.findById(codEstadoFinal);
        //
        docDocumento=iDocumentoService.guardarCambioEstado(docDocumento, parDocumentoEstado, idPersona);
        //
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("cambioEstadoDialog.hide()");
        //
        cargar();
        return "";
    }

    public void irImprimirDocumento() {
        String codDocumento =docDocumento.getDocDefinicion().getDocDefinicionPK().getCodDocumento();
        String rutaPdf;
        if(codDocumento.equals("LC1010")){
            try{
                vperPersona = iVperPersonaService.cargaVistaPersona(docDocumento.getPerUnidad().getPerPersona().getIdPersona());
                docPlanilla=iPlanillaService.buscarPorDocumento(docDocumento.getIdDocumento());
                rutaPdf= iDocumentoService.generaReporteLC1010(docPlanilla, persona, docDocumento, docDocumento.getPerUnidad(), vperPersona);
                redirecionarReporte(rutaPdf);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        if(codDocumento.equals("ROE010")){
            try{
                vperPersona = iVperPersonaService.cargaVistaPersona(docDocumento.getPerUnidad().getPerPersona().getIdPersona());
                rutaPdf= iDocumentoService.generaReporteROE010(vperPersona);
                redirecionarReporte(rutaPdf);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void redirecionarReporte (String rutaReporte) throws IOException {
        FacesContext facesContext=FacesContext.getCurrentInstance();
        HttpServletResponse response=(HttpServletResponse) facesContext.getExternalContext().getResponse();

        File file=new File(rutaReporte);
        BufferedInputStream input=null;
        BufferedOutputStream output=null;
        try{

            input=new BufferedInputStream(new FileInputStream(file),10240);
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader( "Content-Disposition", "filename=" + file.getName() );
            response.setContentLength((int)file.length());
            output=new BufferedOutputStream(response.getOutputStream(), 10240);

            byte[] buffer=new byte[10240];
            int length;

            while((length=input.read(buffer))>0){
                output.write(buffer,0,length);
            }
            output.flush();
        }finally{
            close(output);
            close(input);
        }

        facesContext.responseComplete();
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    public DocDocumento getDocDocumento() {
        return docDocumento;
    }

    public void setDocDocumento(DocDocumento docDocumento) {
        this.docDocumento = docDocumento;
    }

    public PerPersona getEmpleador() {
        return empleador;
    }

    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }

    public boolean isEsInterno() {
        return esInterno;
    }

    public void setEsInterno(boolean esInterno) {
        this.esInterno = esInterno;
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public DocPlanilla getDocPlanilla() {
        return docPlanilla;
    }

    public void setDocPlanilla(DocPlanilla docPlanilla) {
        this.docPlanilla = docPlanilla;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public List<ParDocumentoEstado> getListaDocumentoEstado() {
        return listaDocumentoEstado;
    }

    public void setListaDocumentoEstado(List<ParDocumentoEstado> listaDocumentoEstado) {
        this.listaDocumentoEstado = listaDocumentoEstado;
    }

    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }
}