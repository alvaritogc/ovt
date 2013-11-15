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
import javax.faces.convert.FacesConverter;
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

@ManagedBean(name = "personaBean")
@ViewScoped
public class PersonaBean implements Serializable{

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

    private PerUnidad unidad=new PerUnidad();
    private List<PerUnidad>listaUnidad;

    private  UsrUsuario usuario;



    private boolean mostrar=false;

    private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();

    private static final Logger logger = LoggerFactory.getLogger(PersonaBean.class);

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

    @PostConstruct
    public void ini(){
       persona=new PerPersona();
       persona.setEsNatural(false);
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
                if(!l.getDescripcion().equalsIgnoreCase("BOLIVIA"))
                    listaLocalidad.add(new SelectItem(l.getCodLocalidad(),l.getDescripcion()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void registrar(){

        if(unidad.getTipoEmpresa()==null || unidad.getTipoEmpresa().trim().equals("") ){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de empresa es obligatorio."));
            ini();
            return ;
        }

        if(unidad.getTipoSociedad()==null || unidad.getTipoSociedad().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de sociedad es obligatorio."));
            ini();
            return ;
        }

        if(unidad.getNombreComercial()==null || unidad.getNombreComercial().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre comercial es obligatorio."));
            ini();
            return ;
        }


/*        if(unidad.getFechaNacimiento()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Fecha actividad es obligatorio."));
            ini();
            return ;
        }

        if(unidad.getNroFundaempresa()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. de fundempresa es obligatorio."));
            ini();
            return ;
        }

        if(unidad.getNroAfp()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. AFP es obligatorio."));
            ini();
            return ;
        }

        if(unidad.getNroCajaSalud()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. de Caja de salud es obligatorio."));
            ini();
            return ;
        }*/

        if(unidad.getActividadDeclarada()==null || unidad.getActividadDeclarada().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Actividad declarada es obligatorio."));
            ini();
            return ;
        }

/*        if(unidad.getObservaciones()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Observaciones es obligatorio."));
            ini();
            return ;
        }*/

        if(persona.getNombreRazonSocial()==null || persona.getNombreRazonSocial().trim().equals("")){
          FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nombre o Razon social es obligatorio."));
            ini();
            return ;
        }
        if(persona.getTipoIdentificacion()==null || persona.getTipoIdentificacion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Tipo de identificacion es obligatorio."));
            ini();
            return ;
        }

        if(idLocalidad==null && idLocalidad.equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Localidad es obligatorio."));
            ini();
            return ;
        }

        if(persona.getNroIdentificacion()==null || persona.getNroIdentificacion().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Nro. de identificacion es obligatorio."));
            ini();
            return ;
        }else{
            //validar que nro de identificacion sea unico
            if(iPersonaService.findByNroIdentificacion(persona.getNroIdentificacion())==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL valor del campo Nro. de identificacion ya existe."));
                ini();
                return ;
            }
        }

        if(usuario.getUsuario()==null || usuario.getUsuario().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Usuario es obligatorio."));
            ini();
            return ;
        }else{
            if(iUsuarioService.obtenerUsuarioPorNombreUsuario(usuario.getUsuario())==null){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL valor del campo Usuario ya existe.."));
                ini();
                return ;
            }

            if(!validarEmail(usuario.getUsuario())){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL formato del correo electronico es incorrecto."));
                ini();
                return ;
            }
        }

        if(usuario.getClave()==null || usuario.getClave().trim().equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL campo Contrasenia es obligatorio."));
            ini();
            return ;
        }

       if(!usuario.getClave().equals(confirmarContrasenia)){
           FacesContext.getCurrentInstance().addMessage(null,
                   new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La valor de la Contrasenia debe ser igual al valor del campo Confirmar contrasenia."));
           ini();
           return ;
       }




      final String  REGISTRO_BITACORA="ROE";
        System.out.println("INGRESANDO ................................ ");
      Long seq= iLocalidadService.localidadSecuencia("PER_PERSONA_SEC");
      persona.setIdPersona(seq.toString());
      persona.setCodLocalidad(iLocalidadService.findById(idLocalidad));
      persona.setRegistroBitacora(REGISTRO_BITACORA);
     // persona.setEsNatural(esNatural);

      unidad.setObservaciones("REGISTRO");
      unidad.setFechaNacimiento(new Date());
      unidad.setRegistroBitacora(REGISTRO_BITACORA);
      unidad.setEstadoUnidad(iDominioService.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO,PAR_ESTADO_USUARIO_ACTIVO).getParDominioPK().getValor());
      PerUnidadPK perUnidadPK=new PerUnidadPK();
      perUnidadPK.setIdPersona(persona.getIdPersona());
      perUnidadPK.setIdUnidad(iUnidadService.obtenerSecuencia("PER_UNIDAD_SEC"));
      unidad.setPerUnidadPK(perUnidadPK);

      usuario.setIdUsuario(iUsuarioService.obtenerSecuencia("USR_USUARIO_SEC"));
      usuario.setRegistroBitacora(REGISTRO_BITACORA);
      usuario.setEsDelegado((short)0);
      usuario.setEsInterno((short) 0);
      ParDominio d=iDominioService.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO,PAR_ESTADO__USUARIO_SINCONFIRMAR);
      usuario.setEstadoUsuario(d.getParDominioPK().getValor());
        //usuario.setIdPersona(persona);
      usuario.setTipoAutenticacion(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_AUTENTICACION, PAR_TIPO_AUTENTICACION_LOCAL).getParDominioPK().getValor());

      usuario.setFechaBitacora(new Date());
      usuario.setRegistroBitacora(REGISTRO_BITACORA);

      usuario.setIdPersona(persona);
      usuario.setTipoAutenticacion("LOCAL");


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
            cargaParametricasEmail();
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
}
