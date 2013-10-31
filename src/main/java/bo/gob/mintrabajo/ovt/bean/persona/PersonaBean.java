package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static  bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_EMPRESA;
import static  bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_SOCIEDAD;
import static  bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_IDENTIFICACION;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/25/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "personaBean")
@ViewScoped
public class PersonaBean implements Serializable{

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

    List<SelectItem>listaTipoEmpresa;
    List<SelectItem>listaTipoSociedad;
    List<SelectItem>listaTipoIdentificacion;

    @PostConstruct
    public void ini(){
       persona=new PerPersona();
       listaPersona=new ArrayList<PerPersona>();
        unidad=new PerUnidad();
        listaUnidad=new ArrayList<PerUnidad>();
        usuario=new UsrUsuario();
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

    public void cambiarNatural(){
      esNatural=persona.isEsNatural()?true:false;
    }

    public void cargar(){
     cargarLocalidad();
     listaTipoEmpresa=cargarListas(listaTipoEmpresa,DOM_TIPOS_EMPRESA);
     listaTipoSociedad=cargarListas(listaTipoEmpresa,DOM_TIPOS_SOCIEDAD);
     listaTipoIdentificacion=cargarListas(listaTipoEmpresa,DOM_TIPOS_IDENTIFICACION);
    }

    public void cargarLocalidad(){
        try {
            List<ParLocalidad>localidades=new ArrayList<ParLocalidad>();
            listaLocalidad=new ArrayList<SelectItem>();
            localidades=iLocalidadService.getAllLocalidades();
            for (ParLocalidad l:localidades){
                listaLocalidad.add(new SelectItem(l.getCodLocalidad(),l.getDescripcion()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void registrar(){

        if(persona.getNombreRazonSocial()!=null && !persona.getNombreRazonSocial().equals("")){

        }

      final String  REGISTRO_BITACORA="ROE";
        System.out.println("INGRESANDO ................................ ");
      Long seq= iLocalidadService.localidadSecuencia("PER_PERSONA_SEC");
      persona.setIdPersona(seq.toString());
      persona.setCodLocalidad(iLocalidadService.findById(idLocalidad));
      persona.setFechaBitacora(new Date());
      persona.setRegistroBitacora(REGISTRO_BITACORA);
      persona.setEsNatural(esNatural);

      unidad.setRegistroBitacora(REGISTRO_BITACORA);
      unidad.setFechaBitacora(new Date());
      unidad.setEstadoUnidad("1");
      unidad.setPerPersona(persona);
      PerUnidadPK perUnidadPK=new PerUnidadPK();
      perUnidadPK.setIdPersona(persona.getIdPersona());
      perUnidadPK.setIdUnidad(iUnidadService.obtenerSecuencia("PER_UNIDAD_SEC"));
      unidad.setPerUnidadPK(perUnidadPK);

      usuario.setIdUsuario(iUsuarioService.obtenerSecuencia("USR_USUARIO_SEC"));
        usuario.setFechaBitacora(new Date());
        usuario.setRegistroBitacora(REGISTRO_BITACORA);
        usuario.setEsDelegado((short)0);
        usuario.setEstadoUsuario("A");
        usuario.setIdPersona(persona);
        usuario.setTipoAutenticacion("LOCAL");
        usuario.setEsInterno((short)0);

        mostrar= iPersonaService.registrar(persona,unidad,usuario);
        if(mostrar)
            RequestContext.getCurrentInstance().execute("dlg.show()");
        else
            RequestContext.getCurrentInstance().execute("dlg.hide()");
     mostrar= iPersonaService.registrar(persona,unidad,usuario);
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("mostrar ---------------------------------- " + mostrar);
        if (mostrar) {
            context.execute("dlg.show()");
            ServicioEnvioEmail see = new ServicioEnvioEmail();
            see.envioEmail(this);
        } else {
            context.execute("dlg.hide()");
        }
    }

    public boolean validarEmail(String email){
        Pattern patron = Pattern.compile("^[\\w-\\.]+\\@[\\w\\.-]+\\.[a-z]{2,4}$");
        Matcher encajador = patron.matcher(email);
        if (encajador.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public String volverLogin()throws IOException {
        return "irInicio";
    }

    /*
      ******************************************
      *
      *             GETTER Y SETTER
      * ****************************************
     */

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
}
