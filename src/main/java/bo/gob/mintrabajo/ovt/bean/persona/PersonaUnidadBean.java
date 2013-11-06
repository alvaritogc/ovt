package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.*;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/25/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "personaUnidadBean")
@ViewScoped
public class PersonaUnidadBean implements Serializable{

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;

    @ManagedProperty(value = "#{utilService}")
    private IUtilsService iUtilsService;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;


    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;

    @ManagedProperty(value="#{parametrizacionService}")
    private IParametrizacionService iParametrizacion;


    private PerPersona persona=new PerPersona();
    private List<PerPersona>listaPersona=new ArrayList<PerPersona>();

    private String idLocalidad;
    private List<SelectItem>listaLocalidad;

    private PerUnidad unidad;
    private List<PerUnidad>listaUnidad;

    private  UsrUsuario usuario;

    private boolean esNatural=false;

    private boolean mostrar=false;

    private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();

    private static final Logger logger = LoggerFactory.getLogger(PersonaUnidadBean.class);

    List<SelectItem>listaTipoEmpresa;
    List<SelectItem>listaTipoSociedad;
    List<SelectItem>listaTipoIdentificacion;

    private String from;
    private String subject;
    private String urlRedireccion;
    private String cuerpoMensaje;
    private String password;
    private String host;
    private String port;

    private String confirmarContrasenia;

    private PerDireccion direccion;
    private List<PerDireccion>listaDireccion;
    private PerReplegal repLegal;
    private List<PerReplegal> listaRepLegal;
    private PerActividad actividad;
    private List<PerActividad> listaActividad;
    private ParActividadEconomica actividadEconomica;
    private List<ParActividadEconomica>listaActividadEconomica;

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }




    @PostConstruct
    public void ini(){
       persona=new PerPersona();
       listaPersona=new ArrayList<PerPersona>();
        unidad=new PerUnidad();
        listaUnidad=new ArrayList<PerUnidad>();
        usuario=new UsrUsuario();
       // cargar();
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






    public void cargaParametricasEmail() {
        try {
            from = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_CUENTA_EMAIL).getDescripcion();
            subject = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_ASUNTO).getDescripcion();
            urlRedireccion = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_URL).getDescripcion();
            cuerpoMensaje = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_MENSAJE).getDescripcion();
            password = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PASSWORD).getDescripcion();
            host = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_SERVIDOR).getDescripcion();
            port = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PUERTO).getDescripcion();
        } catch (NullPointerException ne) {
            logger.info("El parámetro no existe en base de datos ...");
        }
    }

     /*
      ******************************************
      *
      *             GETTER Y SETTER
      * ****************************************
     */


    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public List<SelectItem> getListaTipoEmpresa() {
        return listaTipoEmpresa;
    }

    public void setListaTipoEmpresa(List<SelectItem> listaTipoEmpresa) {
        this.listaTipoEmpresa = listaTipoEmpresa;
    }

    public List<SelectItem> getListaTipoSociedad() {
        return listaTipoSociedad;
    }

    public void setListaTipoSociedad(List<SelectItem> listaTipoSociedad) {
        this.listaTipoSociedad = listaTipoSociedad;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public UsrUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public String getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(String idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public List<SelectItem> getListaLocalidad() {
        return listaLocalidad;
    }

    public void setListaLocalidad(List<SelectItem> listaLocalidad) {
        this.listaLocalidad = listaLocalidad;
    }

    public ILocalidadService getiLocalidadService() {
        return iLocalidadService;
    }

    public void setiLocalidadService(ILocalidadService iLocalidadService) {
        this.iLocalidadService = iLocalidadService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IParametrizacionService getiParametrizacion() {
        return iParametrizacion;
    }

    public void setiParametrizacion(IParametrizacionService iParametrizacion) {
        this.iParametrizacion = iParametrizacion;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public List<PerPersona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<PerPersona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public PerUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(PerUnidad unidad) {
        this.unidad = unidad;
    }

    public List<PerUnidad> getListaUnidad() {
        return listaUnidad;
    }

    public void setListaUnidad(List<PerUnidad> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    public boolean isEsNatural() {
        return esNatural;
    }

    public void setEsNatural(boolean esNatural) {
        this.esNatural = esNatural;
    }
    // ****  Envio de Emails **** //
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrlRedireccion() {
        return urlRedireccion;
    }

    public void setUrlRedireccion(String urlRedireccion) {
        this.urlRedireccion = urlRedireccion;
    }

    public String getCuerpoMensaje() {
        return cuerpoMensaje;
    }

    public void setCuerpoMensaje(String cuerpoMensaje) {
        this.cuerpoMensaje = cuerpoMensaje;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }





    public PerDireccion getDireccion() {
        return direccion;
    }

    public void setDireccion(PerDireccion direccion) {
        this.direccion = direccion;
    }





    public List<PerDireccion> getListaDireccion() {
        return listaDireccion;
    }

    public void setListaDireccion(List<PerDireccion> listaDireccion) {
        this.listaDireccion = listaDireccion;
    }



    public PerReplegal getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(PerReplegal repLegal) {
        this.repLegal = repLegal;
    }


    public List<PerReplegal> getListaRepLegal() {
        return listaRepLegal;
    }

    public void setListaRepLegal(List<PerReplegal> listaRepLegal) {
        this.listaRepLegal = listaRepLegal;
    }


    public PerActividad getActividad() {
        return actividad;
    }

    public void setActividad(PerActividad actividad) {
        this.actividad = actividad;
    }


    public List<PerActividad> getListaActividad() {
        return listaActividad;
    }

    public void setListaActividad(List<PerActividad> listaActividad) {
        this.listaActividad = listaActividad;
    }


    public ParActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(ParActividadEconomica actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }


    public List<ParActividadEconomica> getListaActividadEconomica() {
        return listaActividadEconomica;
    }

    public void setListaActividadEconomica(List<ParActividadEconomica> listaActividadEconomica) {
        this.listaActividadEconomica = listaActividadEconomica;
    }


}
