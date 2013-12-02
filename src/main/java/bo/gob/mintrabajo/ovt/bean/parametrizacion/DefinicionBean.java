package bo.gob.mintrabajo.ovt.bean.parametrizacion;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_GRUPO_DOCUMENTO;
import bo.gob.mintrabajo.ovt.Util.Util;
/**
 *
 * @author Alvaro
 */
@ManagedBean
@ViewScoped
public class DefinicionBean {
    @ManagedProperty(value="#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value="#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value="#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value="#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;

    private static final Logger logger = LoggerFactory.getLogger(DefinicionBean.class);
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private String idUsuario;

    private DocDefinicion docDefinicion=new DocDefinicion();

    private String codDocumento;
    private Short version;
    private List<DocDefinicion> listaDocDefinicion;
    private List<ParDocumentoEstado> listaDocumentoEstados;
    private String nombre;
    private String tipoGrupoDocumento;
    private String alias;
    private Date fechaBitacora;
    private String registroBitacora;
    private boolean sw;
    private PerPersona perPersona;
    private List<SelectItem>listaTipoGrupoDocumento;
    private String codEstado;
     private boolean estadoDoc;

    @PostConstruct
    public void init(){
        //docDefinicion = new DocDefinicion();
        perPersona = new PerPersona();
        idUsuario=(String) session.getAttribute("idPersona");
        perPersona=iPersonaService.findById(idUsuario);
        listaDocDefinicion = new ArrayList<DocDefinicion>();
        //llenarLista();
        llenarListaOrden();
        //listaDocDefinicion=iDefinicionService.listaPorOrdenDocDefinicion();
        this.cargar();

    }

    /*
 **
 * Este metodo se utliza para cargar las listas
 * del tipo SelectItem(para el componente <p:selectOneMenu>).
 *@Param lista .- Es la variable que se utiliza para llenar los valores de dominio
 *@Param dominio .- Representa un dominio de la tabla PAR_DOMINIO. Estos valores
 *                  estan parametrizados en la clase Dominios.java
 */
    public List<SelectItem> cargarListas(List<SelectItem>lista, String dominio){
        lista=new ArrayList<SelectItem>();
        try{
            List<ParDominio>valoresDominio=iDominioService.obtenerItemsDominio(dominio);
            for(ParDominio d:valoresDominio){
                lista.add(new SelectItem(d.getParDominioPK().getValor(),d.getDescripcion()));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public List<SelectItem> getListaTipoGrupoDocumento() {
        return listaTipoGrupoDocumento;
    }

    public void setListaTipoGrupoDocumento(List<SelectItem> listaTipoGrupoDocumento) {
        this.listaTipoGrupoDocumento = listaTipoGrupoDocumento;
    }


    public void cargar(){
        listaTipoGrupoDocumento=cargarListas(listaTipoGrupoDocumento,DOM_TIPOS_GRUPO_DOCUMENTO);
        listaDocumentoEstados = iDocumentoEstadoService.listarDocumentoEstados();
    }
    
    public String descripcionDefinicion(String valor){
        return Util.descripcionDominio("TGRUPODOCUMENTO", valor);
    }

    public void guardarDefinicion(){
//        codDocumento = "LC1010";
//        version=(short)1;

        if(sw)
        {//GUERDA UNO NUEVO
            List<DocDefinicion> li;//=new ArrayList<DocDefinicion>() ;
            li = llenarListaCodDocumento(codDocumento.toUpperCase(), version);
            if (li.isEmpty())
            {
                boolean guardado = iDefinicionService.saveDefincion(docDefinicion, codDocumento, version, codEstado
                        , "OVT", estadoDoc, tipoGrupoDocumento);
                if(!guardado){
                    FacesMessage msg = new FacesMessage("Error Codigo ya existente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("dlgFormDocDefinicion.hide();");
                init();
            }
            else//           no guarda
            {
                FacesMessage msg = new FacesMessage("Error Codigo ya existente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println("Error Codigo ya existente");
            }

        } else {
            boolean guardado = iDefinicionService.saveDefincion(docDefinicion, codDocumento, version, codEstado
                        , "OVT", estadoDoc,tipoGrupoDocumento);
            if(!guardado){
                    FacesMessage msg = new FacesMessage("Error Codigo ya existente");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgFormDocDefinicion.hide();");
            init();
        }

    }
    public void confirmaEliminarDocDefinicion(){
        try {
            if(iDefinicionService.eliminarDefinicion(docDefinicion)){
                //listaEntidad= iEntidadService.listaEntidad();
                listaDocDefinicion=iDefinicionService.listarDefiniciones();
                docDefinicion=new DocDefinicion();
                init();
            }
        } catch (Exception e) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgMensaje.show()");
            e.printStackTrace();
        }
    }
    public void llenarLista(){
        listaDocDefinicion=iDefinicionService.listarDefiniciones();
    }

    public void llenarListaOrden(){
        listaDocDefinicion=iDefinicionService.listaPorOrdenDocDefinicion();// .listaDocDefinicion();//listaPorOrdenDocDefinicion
    }

    public List<DocDefinicion> llenarListaCodDocumento(String cod, Short ver){
        listaDocDefinicion=iDefinicionService.listaCodDocumento(cod,ver);// .listaDocDefinicion();//listaPorOrdenDocDefinicion
        return listaDocDefinicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoGrupoDocumento() {
        return tipoGrupoDocumento;
    }

    public void setTipoGrupoDocumento(String tipoGrupoDocumento) {
        this.tipoGrupoDocumento = tipoGrupoDocumento;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public PerPersona getPerPersona() {
        return perPersona;
    }

    public void setPerPersona(PerPersona perPersona) {
        this.perPersona = perPersona;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<DocDefinicion> getListaDocDefinicion(){
        return listaDocDefinicion;

    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public void limpiar(){
        listaDocDefinicion=iDefinicionService.listaPorOrdenDocDefinicion();
        docDefinicion=new DocDefinicion();
        cargar();
        codDocumento="";
        tipoGrupoDocumento="";
        codEstado="";
        version=0;
        System.out.println("coddefinicion => "+codDocumento+" version=> "+version);
        sw=true;
        estadoDoc=true;
    }

    public void modTexto(){
        sw=false;
    }

    public boolean isSw() {
        return sw;
    }

    public void setSw(boolean sw) {
        this.sw = sw;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public List<ParDocumentoEstado> getListaDocumentoEstados() {
        return listaDocumentoEstados;
    }

    public void setListaDocumentoEstados(List<ParDocumentoEstado> listaDocumentoEstados) {
        this.listaDocumentoEstados = listaDocumentoEstados;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    /**
     * @return the estadoDoc
     */
    public boolean isEstadoDoc() {
        return estadoDoc;
    }

    /**
     * @param estadoDoc the estadoDoc to set
     */
    public void setEstadoDoc(boolean estadoDoc) {
        this.estadoDoc = estadoDoc;
    }
}