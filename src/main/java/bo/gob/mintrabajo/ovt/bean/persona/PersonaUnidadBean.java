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
import java.util.Collections;
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

    public IUnidadService getiUnidadService2() {
        return iUnidadService2;
    }

    public void setiUnidadService2(IUnidadService iUnidadService2) {
        this.iUnidadService2 = iUnidadService2;
    }

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService2;



    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadServiceModificar;



    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;


    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;


    @ManagedProperty(value="#{direccionService}")
    private IDireccionService iDireccionService;

    @ManagedProperty(value = "#{repLegalService}")
    private IRepLegalService iRepLegalService;

    @ManagedProperty(value  ="#{actividadEconomicaService}")
    private IActividadEconomicaService iActividadEconomicaService;

    @ManagedProperty(value = "#{actividadService}")
    private IActividadService iActividadService;

    private PerPersona persona=new PerPersona();

    private String idLocalidad;
    private List<SelectItem>listaLocalidad;

    private PerUnidad unidad;
    private List<PerUnidad>listaUnidad;



    private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();

    private static final Logger logger = LoggerFactory.getLogger(PersonaUnidadBean.class);

    List<SelectItem>listaTipoEmpresa;
    List<SelectItem>listaTipoSociedad;
    List<SelectItem>listaTipoIdentificacion;


    private PerDireccion direccion;
    private List<PerDireccion>listaDireccion;
    private PerDireccion direccionPrincipal;
    private List<SelectItem>listaTipoDirecciones;

    private PerReplegal repLegal;
    private List<PerReplegal> listaRepLegal;
    private PerReplegal repLegalPrincipal;

    private PerActividad actividad;
    private List<PerActividad> listaActividad;
    private PerActividad actividadPrincipal;

    private ParActividadEconomica actividadEconomica;
    private List<ParActividadEconomica>listaActividadEconomica;

    private ParActividadEconomica actividadEconomicaPrincipal;

    private PerUnidad unidadRegistro;

    private String titulo;
    private String tituloDlgUnidad;
    private String tituloDialog;
    private String iconoUnidad;

    @PostConstruct
    public void ini(){

        persona=new PerPersona();
        persona=(PerPersona)session.getAttribute("persona");
        titulo=persona.getNombreRazonSocial().toUpperCase()+" "+persona.getApellidoPaterno().toUpperCase()+" "+persona.getApellidoMaterno().toUpperCase();

        actividadEconomica=new ParActividadEconomica();
        repLegal=new PerReplegal();

        unidadRegistro=new PerUnidad();

 /*       //Cargando Representante Legal
        listaRepLegal=iRepLegalService.findByPerUnidad(unidad);
        repLegalPrincipal=new PerReplegal();
        if(!listaRepLegal.isEmpty()){
            repLegalPrincipal=listaRepLegal.get(0);
        }*/
        cargar();
        //Cargando Actividad Economica
        listaActividad=iActividadService.findByPerUnidad(unidad);
        actividadEconomicaPrincipal=new ParActividadEconomica();
        if(listaActividad!=null){
            if(!listaActividad.isEmpty()){
                repLegalPrincipal=listaRepLegal.get(0);
                actividadEconomicaPrincipal=listaActividad.get(0).getIdActividadEconomica();
            }
        }
    }

    /*
     *******************************************
     *          METODOS UNIDAD
     ******************************************
     */
    /*
     *  Setea la variable unidad, que representa la unidad principal.
     *  Setea la variable  listaUnidad, el cual se muestra en el componente <p:datagrid>
     */
    public void cargarUnidad(){
        //representa la unidad principal
        unidad=new PerUnidad();
        listaUnidad=new ArrayList<PerUnidad>();
        //Obtiene en una lista auxiliar las unidades de la persona
        List<PerUnidad>listaUnidadAux=iUnidadService.buscarPorPersona(persona.getIdPersona());
        //setea la unidad principal
        unidad=listaUnidadAux.get(listaUnidadAux.size()-1);
        // carga la varible listaUnidad desde el primer registro hasta el penultimo
        // se carga asi, por que en el ultimo registro esta la unidadPrincipal
        listaUnidad=listaUnidadAux.subList(0,listaUnidadAux.size()-1);
    }

    public void nuevo(){
        //unidadRegistro=new PerUnidad();
        direccion=new PerDireccion();
        repLegal=new PerReplegal();
        actividadPrincipal=new PerActividad();
    }

    public void procesarUnidad(){
        System.out.println("=====>>>>INGRESANDO A  GUARDAR UNIDAD PROCESAR UNIDAD ARIEL ARIEL");
        final String  REGISTRO_BITACORA="ROE";
        unidadRegistro.setRegistroBitacora(REGISTRO_BITACORA);
        unidadRegistro=iUnidadServiceModificar.save(unidadRegistro,persona);
        ini();
        if(unidadRegistro==null){
            RequestContext.getCurrentInstance().execute("dlgUnidad.show()");
        }else {
            RequestContext.getCurrentInstance().execute("dlgUnidad.hide()");
        }
    }

    public void modificarUnidad(){
        System.out.println("=====>>>>INGRESANDO A  MODIFICARUNIDAD");
        final String  REGISTRO_BITACORA="ROE";
        //unidadRegistro.setPerPersona(persona);
       // unidadRegistro.setRegistroBitacora(REGISTRO_BITACORA);
       // unidadRegistro.setFechaBitacora(new Date());
        //unidadRegistro.setEstadoUnidad(iDominioService.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO,PAR_ESTADO_USUARIO_ACTIVO).getParDominioPK().getValor());
/*        PerUnidadPK perUnidadPK=new PerUnidadPK();
        perUnidadPK.setIdPersona(persona.getIdPersona());
        perUnidadPK.setIdUnidad(iUnidadService.obtenerSecuencia("PER_UNIDAD_SEC"));
        unidad.setPerUnidadPK(perUnidadPK);*/

        unidadRegistro=iUnidadServiceModificar.save(unidadRegistro);
        ini();
        if(unidadRegistro==null){
            RequestContext.getCurrentInstance().execute("dlgUnidad.show()");
        }else {
            RequestContext.getCurrentInstance().execute("dlgUnidad.hide()");
        }


        System.out.println("=====>>>> GUARDAR UNIDAD OK");
    }


    public  void cargar(){
        cargarLocalidad();
        listaTipoEmpresa=cargarListas(listaTipoEmpresa,DOM_TIPOS_EMPRESA);
        listaTipoSociedad=cargarListas(listaTipoEmpresa,DOM_TIPOS_SOCIEDAD);
        listaTipoIdentificacion=cargarListas(listaTipoEmpresa,DOM_TIPOS_IDENTIFICACION);
        listaTipoDirecciones=cargarListas(listaTipoDirecciones,DOM_TIPO_DIRECCION);
        cargarUnidad();
        cargarDireccion();
        cargarRepLegal();
    }

    /*
     *******************************************
     *          METODOS DIRECCION
     ******************************************
     */
    public void procesarDireccion(){

        direccion.setPerUnidad(unidadRegistro);
        direccion.setCodLocalidad(iLocalidadService.findById(idLocalidad));
        //Cambiar por el usuario de la session
        direccion.setRegistroBitacora(persona.getNombreRazonSocial());
            iDireccionService.save(direccion);
        ini();
        RequestContext.getCurrentInstance().execute("dlgDireccion.hide()");
    }

    public void cargarDireccion(){
        // Cargando Direccion
        listaDireccion= iDireccionService.obtenerPorIdPersona(unidad.getPerPersona().getIdPersona());
        if (!listaDireccion.isEmpty()){
            direccionPrincipal= listaDireccion.get(listaDireccion.size()-1);
            System.out.println("=====>> NRO. UNIDADES "+listaUnidad.size());
            System.out.println("=====>> NRO. DIRECCION "+listaDireccion.size());
            if (listaDireccion.size()>1){
                for (int i=listaDireccion.size()-2;i>=0;i--){
                    System.out.println("=====>> DIRECCION "+listaDireccion.get(i).getIdDireccion()+" idUnidad "+listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad());
                    for (int j=listaUnidad.size()-1;j>=0;j--){
                        System.out.println("=====>> UNIDAD "+listaUnidad.get(j).getPerUnidadPK().getIdUnidad());
                        if(listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==listaUnidad.get(j).getPerUnidadPK().getIdUnidad()){
                            listaUnidad.get(j).setDireccion(listaDireccion.get(i));
                            break;
                        }
                    }
                }
            }
        }
    }
    public void cargarLocalidad(){
        try {
            List<ParLocalidad>localidades=new ArrayList<ParLocalidad>();
            listaLocalidad=new ArrayList<SelectItem>();
            localidades=iLocalidadService.getAllLocalidades();
            for (ParLocalidad l:localidades){
                if(!l.getDescripcion().equalsIgnoreCase("BOLIVIA"))
                    listaLocalidad.add(new SelectItem(l.getCodLocalidad(),l.getDescripcion()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /*
     *******************************************
     *          METODOS REPLEGAL
     ******************************************
     */
    public void procesarRepLegal(){

        repLegal.setPerUnidad(unidadRegistro);
        //Preguntar que valor debe tener esta variable
        //repLegal.setEstadoRepLegal("v");
        //Cambiar por el usuario de la session
        repLegal.setRegistroBitacora(persona.getNombreRazonSocial());
        iRepLegalService.save(repLegal);
        ini();
        RequestContext.getCurrentInstance().execute("dlgRepLegal.hide()");
    }

    public void cargarRepLegal(){

        listaRepLegal=iRepLegalService.obtenerPorIdPersona(unidad.getPerPersona().getIdPersona());
        if(!listaRepLegal.isEmpty()){
            repLegalPrincipal= listaRepLegal.get(listaRepLegal.size()-1);
            if (listaRepLegal.size()>1){
                for (int i=listaRepLegal.size()-2;i>=0;i--){
                    for (int j=listaUnidad.size()-1;j>=0;j--){
                        if(listaRepLegal.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==listaUnidad.get(j).getPerUnidadPK().getIdUnidad()){
                            listaUnidad.get(j).setRepLegal(listaRepLegal.get(i));
                            break;
                        }
                    }
                }
            }
        }

        listaDireccion= iDireccionService.obtenerPorIdPersona(unidad.getPerPersona().getIdPersona());
        if (!listaDireccion.isEmpty()){
            direccionPrincipal= listaDireccion.get(listaDireccion.size()-1);
            if (listaDireccion.size()>1){
                for (int i=listaDireccion.size()-2;i>=0;i--){
                    System.out.println("=====>> DIRECCION "+listaDireccion.get(i).getIdDireccion()+" idUnidad "+listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad());
                    for (int j=listaUnidad.size()-1;j>=0;j--){
                        System.out.println("=====>> UNIDAD "+listaUnidad.get(j).getPerUnidadPK().getIdUnidad());
                        if(listaDireccion.get(i).getPerUnidad().getPerUnidadPK().getIdUnidad()==listaUnidad.get(j).getPerUnidadPK().getIdUnidad()){
                            listaUnidad.get(j).setDireccion(listaDireccion.get(i));
                            break;
                        }
                    }
                }
            }
        }
    }

    /*
      *******************************************
      *          METODOS ACTIVIDAD DECLARADA
      ******************************************
      */
    public void procesarActividadEconomica(){

        //Cambiar por el usuario de la session
         actividadEconomica.setRegistroBitacora(persona.getNombreRazonSocial());
        actividadEconomica=iActividadEconomicaService.save(actividadEconomica);

        //Cambiar por el usuario de la session
        actividad=new PerActividad();
        actividad.setRegistroBitacora(persona.getNombreRazonSocial());
        actividad.setPerUnidad(unidad);
        actividad.setIdActividadEconomica(actividadEconomica);
        iActividadService.save(actividad);
        ini();
        //Cerrar dialog
        RequestContext.getCurrentInstance().execute("dlgActividadDeclarada.hide()");

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
      ******************************************
      *
      *             GETTER Y SETTER
      * ****************************************
     */

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

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
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

    public PerUnidad getUnidadRegistro() {
        return unidadRegistro;
    }

    public void setUnidadRegistro(PerUnidad unidadRegistro) {
        this.unidadRegistro = unidadRegistro;
    }


    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloDlgUnidad() {
        return tituloDlgUnidad;
    }

    public void setTituloDlgUnidad(String tituloDlgUnidad) {
        this.tituloDlgUnidad = tituloDlgUnidad;
    }


    public String getTituloDialog() {
        return tituloDialog;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }



    public String getIconoUnidad() {
        return iconoUnidad;
    }

    public void setIconoUnidad(String iconoUnidad) {
        this.iconoUnidad = iconoUnidad;
    }

    public IUnidadService getiUnidadServiceModificar() {
        return iUnidadServiceModificar;
    }

    public void setiUnidadServiceModificar(IUnidadService iUnidadServiceModificar) {
        this.iUnidadServiceModificar = iUnidadServiceModificar;
    }

    public IDireccionService getiDireccionService() {
        return iDireccionService;
    }

    public void setiDireccionService(IDireccionService iDireccionService) {
        this.iDireccionService = iDireccionService;
    }


    public List<SelectItem> getListaLocalidad() {
        return listaLocalidad;
    }

    public void setListaLocalidad(List<SelectItem> listaLocalidad) {
        this.listaLocalidad = listaLocalidad;
    }

    public PerDireccion getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(PerDireccion direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public IRepLegalService getiRepLegalService() {
        return iRepLegalService;
    }

    public void setiRepLegalService(IRepLegalService iRepLegalService) {
        this.iRepLegalService = iRepLegalService;
    }

    public PerReplegal getRepLegalPrincipal() {
        return repLegalPrincipal;
    }

    public void setRepLegalPrincipal(PerReplegal repLegalPrincipal) {
        this.repLegalPrincipal = repLegalPrincipal;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public IActividadEconomicaService getiActividadEconomicaService() {
        return iActividadEconomicaService;
    }

    public void setiActividadEconomicaService(IActividadEconomicaService iActividadEconomicaService) {
        this.iActividadEconomicaService = iActividadEconomicaService;
    }

    public IActividadService getiActividadService() {
        return iActividadService;
    }

    public void setiActividadService(IActividadService iActividadService) {
        this.iActividadService = iActividadService;
    }


    public ParActividadEconomica getActividadEconomicaPrincipal() {
        return actividadEconomicaPrincipal;
    }

    public void setActividadEconomicaPrincipal(ParActividadEconomica actividadEconomicaPrincipal) {
        this.actividadEconomicaPrincipal = actividadEconomicaPrincipal;
    }

    public List<SelectItem> getListaTipoDirecciones() {
        return listaTipoDirecciones;
    }

    public void setListaTipoDirecciones(List<SelectItem> listaTipoDirecciones) {
        this.listaTipoDirecciones = listaTipoDirecciones;
    }
}