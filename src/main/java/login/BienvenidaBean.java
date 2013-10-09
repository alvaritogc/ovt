package login;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//

@ManagedBean
@ViewScoped
public class BienvenidaBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private int idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String idUnidad;

    private int idDocumento;
    private static final Logger logger = LoggerFactory.getLogger(BienvenidaBean.class);
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
    //
    private String textoBenvenida;
    //
    private PerPersonaEntity persona;
    private List<PerUnidadEntity> listaUnidades;
    //
    private List<DocDocumentoEntity> listaDocumentos;

    @PostConstruct
    public void ini() {
        logger.info("BienvenidaBean.init()");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        BigDecimal bi = BigDecimal.valueOf(idUsuario);
        logger.info("Buscando usuario" + bi);
        UsrUsuarioEntity usuario = iUsuarioService.findById(bi);
        logger.info("usuario ok");
        logger.info("buscando persona");
        idPersona=(String) session.getAttribute("idEmpleador");
        persona=iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
//        try{
//            idPersona=(String) session.getAttribute("idEmpleador");
//            persona=iPersonaService.buscarPorId(idPersona);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            idPersona=null;
//            idPersona=(String) session.getAttribute("idEmpleador");
//            persona=iPersonaService.buscarPorId(usuario.getIdPersona());
//        }
        //persona=iPersonaService.buscarPorId(usuario.getIdPersona());
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        textoBenvenida="Bienvenido  OVT";
        listaUnidades=iUnidadService.listarPorPersona(persona.getIdPersona());
        cargarDocumentos();
    }
    public void cargarDocumentos(){
        try{
            listaDocumentos=iDocumentoService.listarPorPersona(persona.getIdPersona());
            if(listaDocumentos==null){
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumentoEntity>();
        }
    }


    public String irRealizarCambioDeEstados(){
        return "irInicio";
    }
    public void irImprimirDocumento(){


        DocPlanillaEntity docPlanillaEntity = new DocPlanillaEntity();
        try{

            docPlanillaEntity=  iDocumentoService.retornaPlanilla(idDocumento);
//            if(docPlanillaEntity==null){
//                docPlanillaEntity= new DocPlanillaEntity();
//                docPlanillaEntity.setAporteAfp(BigDecimal.ZERO);
//                docPlanillaEntity.set
//            }


            iPlanillaService.generaReporte(docPlanillaEntity);
        }
        catch(Exception e){
            e.printStackTrace();
        }


//        return "irInicio";
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

    public List<PerUnidadEntity> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<PerUnidadEntity> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<DocDocumentoEntity> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocDocumentoEntity> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }
}
