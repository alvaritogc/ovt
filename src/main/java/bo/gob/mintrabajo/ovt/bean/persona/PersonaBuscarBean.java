package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_IDENTIFICACION;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/25/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean(name = "personaBuscarBean")
@ViewScoped
public class PersonaBuscarBean implements Serializable {


   private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();
    private HttpSession session;
    private PerPersona persona;
    private List<PerPersona> listaPersona;


    private List<SelectItem>listaTipoIdentificacion;
    private String nombreRazonSocial;
    private String tipoIdentificacion;
    private String nroIdentificacion;


    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{utilService}")
    private IUtilsService iUtilsService;

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;

    @PostConstruct
    public void ini(){

        listaPersona=new ArrayList<PerPersona>();
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        //listaPersona=iPersonaService.findAll();
       // listaPersona=iPersonaService.buscarPorNroNombre(nombreRazonSocial,tipoIdentificacion);

          cargar();
    }

    public void irUnidad()throws IOException{
        session.setAttribute("persona",persona);
        externalContext.redirect("registroPersonaUnidad.xhtml");
    }

    public void nuevo()throws IOException {
        externalContext.redirect("registroPersona.xhtml");
    }
    public void cargar(){
        listaPersona=  iPersonaService. buscarPorNroNombre(nombreRazonSocial,tipoIdentificacion,nroIdentificacion);
        listaTipoIdentificacion=cargarListas(listaTipoIdentificacion,DOM_TIPOS_IDENTIFICACION);
    }

   public void limpiar(){
      nombreRazonSocial="";
       tipoIdentificacion="";
       nroIdentificacion="";
       cargar();
   }

    /*
     **
     * Este metodo se utliza para cargar las listas
     * del tipo SelectItem(para el componente <p:selectOneMenu>).
     *@Param lista .- Es la variable que se utiliza para llenar los valores de dominio
     *@Param dominio .- Representa un dominio de la tabla PAR_DOMINIO. Estos valores
     *                  estan parametrizados en la clase Dominios.java
     */
    public List<SelectItem> cargarListas(List<SelectItem>lista,String dominio){
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

    /*
     * ************************************
     *      GETTER Y SETTER
     * ************************************
     */

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public List<PerPersona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<PerPersona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }
}
