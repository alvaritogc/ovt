package bo.gob.mintrabajo.ovt.bean.parametrizacion;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 * User: LHVD
 * Date: 10/25/13
 */

@ManagedBean
@ViewScoped
public class EntidadBean implements Serializable{
    
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadServiceAux;
//    @ManagedProperty(value = "#{entidadService}")
//    private IEntidadService iEntidadServiceAux1;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    
     private HttpSession session;
    private Long idUsuario;
    
    private List<ParEntidad> listaEntidad;
    
    //para el formulario edicion y agregar
    private ParEntidad entidad=new ParEntidad();
    private List<PerUnidad> listaUnidad=null;
    private List<PerPersona> listaPersona;
    private List<ParDominio> listaDominio;
    private String dominio="";
    private PerUnidad unidad=null;
    private PerPersona persona;
    private boolean evento=false;
    private String sw;

    @PostConstruct
    public void ini() {
        idUsuario = null;
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaEntidad =new ArrayList<ParEntidad>();
        //listaEntidad= iEntidadService.listaEntidad();
        listaEntidad= iEntidadService.listaEntidadPorOrden();
        listaDominio = new ArrayList<ParDominio>();
        listaDominio = iDominioService.obtenerItemsDominio("TENTIDAD");
    }
    
    public void confirmaEliminar(){  
        try {
            if(iEntidadService.deleteEntidad(entidad)){
                //listaEntidad= iEntidadService.listaEntidad();
                listaEntidad= iEntidadService.listaEntidadPorOrden();
                entidad=new ParEntidad();
            }
        } catch (Exception e) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgMensaje.show()");
            e.printStackTrace();
        }
    } 
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();
        if(entidad.getDescripcion().isEmpty()){ return;}
        if(entidad.getCodigo().isEmpty()){return;}
        if(dominio.isEmpty()){return;}
        context.execute("dlgFormEntidad.hide();");
        
        entidad.setDescripcion(entidad.getDescripcion().toUpperCase());
        entidad.setCodigo(entidad.getCodigo().toUpperCase());
        entidad.setTipoEntidad(dominio.toUpperCase());
        
        //final String  REGISTRO_BITACORA="OVT";
        final String  REGISTRO_BITACORA=idUsuario.toString();        
        Date fechaBitacora = new Date();
        try {
            if(entidad.getIdEntidad()==null && evento==false){
                entidad.setIdEntidad(iUtilsService.valorSecuencia("PAR_ENTIDAD_SEC"));
            }
            entidad.setPerUnidad(unidad);
            entidad.setFechaBitacora(fechaBitacora);
            entidad.setRegistroBitacora(REGISTRO_BITACORA);
            ParEntidad pe = iEntidadService.saveEntidad(entidad);
                //listaEntidad= iEntidadService.listaEntidad();
               
           limpiar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void guardarModificar1(){
        System.out.println("=======" + sw);   
                RequestContext context = RequestContext.getCurrentInstance();
        if(entidad.getDescripcion().isEmpty()){ return;}
        if(entidad.getCodigo().isEmpty()){return;}
        if(dominio.isEmpty()){return;}
        context.execute("dlgFormEntidad.hide();");
        
                //final String  REGISTRO_BITACORA="OVT";
        final String  REGISTRO_BITACORA=idUsuario.toString();        
        Date fechaBitacora = new Date();
        
        if(sw.equals("a")){
                entidad.setDescripcion(entidad.getDescripcion().toUpperCase());
            entidad.setCodigo(entidad.getCodigo().toUpperCase());
            entidad.setTipoEntidad(dominio.toUpperCase());
            entidad.setFechaBitacora(fechaBitacora);
            entidad.setRegistroBitacora(REGISTRO_BITACORA);
            iEntidadServiceAux.guardarEliminar(sw, entidad);
        }

        if(sw.equals("b")){
            iEntidadServiceAux.guardarEliminar(sw, entidad);
        }
        
        if(sw.equals("c")){
            iEntidadServiceAux.guardarEliminar(sw, entidad);
        }
    }
    
    public void limpiar(){
        listaEntidad= iEntidadService.listaEntidadPorOrden();
        entidad=new ParEntidad();
        evento=false;
    }
    
    //GET and SET

    /**
     * @return the iEntidadService
     */
    public IEntidadService getiEntidadService() {
        return iEntidadService;
    }

    /**
     * @param iEntidadService the iEntidadService to set
     */
    public void setiEntidadService(IEntidadService iEntidadService) {
        this.iEntidadService = iEntidadService;
    }

    /**
     * @return the listaEntidad
     */
    public List<ParEntidad> getListaEntidad() {
        return listaEntidad;
    }

    /**
     * @param listaEntidad the listaEntidad to set
     */
    public void setListaEntidad(List<ParEntidad> listaEntidad) {
        this.listaEntidad = listaEntidad;
    }

    /**
     * @return the listaUnidad
     */
    public List<PerUnidad> getListaUnidad() {
        return listaUnidad;
    }

    /**
     * @param listaUnidad the listaUnidad to set
     */
    public void setListaUnidad(List<PerUnidad> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    /**
     * @return the listaPersona
     */
    public List<PerPersona> getListaPersona() {
        return listaPersona;
    }

    /**
     * @param listaPersona the listaPersona to set
     */
    public void setListaPersona(List<PerPersona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    /**
     * @return the iPersonaService
     */
    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    /**
     * @param iPersonaService the iPersonaService to set
     */
    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    /**
     * @return the iUnidadService
     */
    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    /**
     * @param iUnidadService the iUnidadService to set
     */
    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    /**
     * @return the unidad
     */
    public PerUnidad getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(PerUnidad unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the persona
     */
    public PerPersona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    /**
     * @return the entidad
     */
    public ParEntidad getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(ParEntidad entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the evento
     */
    public boolean isEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(boolean evento) {
        this.evento = evento;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    /**
     * @return the iDominioService
     */
    public IDominioService getiDominioService() {
        return iDominioService;
    }

    /**
     * @param iDominioService the iDominioService to set
     */
    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    /**
     * @return the listaDominio
     */
    public List<ParDominio> getListaDominio() {
        return listaDominio;
    }

    /**
     * @param listaDominio the listaDominio to set
     */
    public void setListaDominio(List<ParDominio> listaDominio) {
        this.listaDominio = listaDominio;
    }

    /**
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * @param dominio the dominio to set
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * @return the iEntidadServiceAux
     */
    public IEntidadService getiEntidadServiceAux() {
        return iEntidadServiceAux;
    }

    /**
     * @param iEntidadServiceAux the iEntidadServiceAux to set
     */
    public void setiEntidadServiceAux(IEntidadService iEntidadServiceAux) {
        this.iEntidadServiceAux = iEntidadServiceAux;
    }

//    /**
//     * @return the iEntidadServiceAux1
//     */
//    public IEntidadService getiEntidadServiceAux1() {
//        return iEntidadServiceAux1;
//    }
//
//    /**
//     * @param iEntidadServiceAux1 the iEntidadServiceAux1 to set
//     */
//    public void setiEntidadServiceAux1(IEntidadService iEntidadServiceAux1) {
//        this.iEntidadServiceAux1 = iEntidadServiceAux1;
//    }

    /**
     * @return the sw
     */
    public String getSw() {
        return sw;
    }

    /**
     * @param sw the sw to set
     */
    public void setSw(String sw) {
        this.sw = sw;
    }

}