package bo.gob.mintrabajo.ovt.bean.administracion;

import bo.gob.mintrabajo.ovt.Util.Util;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.*;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/13/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class UsuarioAdministracionBean {

    @ManagedProperty(value="#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;

    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;

    private String idLocalidad;
    private String confirmarContrasenia;
    private Date fechaNacimiento;

    private UsrUsuario usuarioSelected;
    private PerPersona personaSelected;
    private PerUnidad unidadSelected;

    private List<UsrUsuario> usuarioFiltroLista;
    private List<UsrUsuario> usuarioSecundariaLista;
    private List<SelectItem> tipoDocumentoLista;
    private List<SelectItem> tipoSociedadLista;
    private List<SelectItem> tipoEmpresaLista;
    private List<SelectItem> localidadLista;

    private static final Logger log = LoggerFactory.getLogger(UsuarioAdministracionBean.class);
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @PostConstruct
    public void cargarUsuario() {
        usuarioSecundariaLista = new ArrayList<UsrUsuario>();
        usuarioSecundariaLista.addAll(iUsuarioService.obtenerUsuariosIntenos());
        cargar();
    }

    public void cargar(){
        cargarLocalidad();
        //obtenerTipoDocumento();
        tipoEmpresaLista=cargarListas(tipoEmpresaLista, DOM_TIPOS_EMPRESA);
        tipoSociedadLista=cargarListas(tipoSociedadLista, DOM_TIPOS_SOCIEDAD);
        tipoDocumentoLista=cargarListas(tipoDocumentoLista, DOM_TIPOS_IDENTIFICACION);
    }

    public void cargarPersonaPorUsuario(){
        System.out.println("==>> usuarioSelected " + usuarioSelected);
        personaSelected = iPersonaService.obtenerPersonaPorUsuario(usuarioSelected);
        System.out.println("==>> personaSelected " + personaSelected);
        try {
            unidadSelected = iUnidadService.listarUnidadesSucursales(personaSelected.getIdPersona()).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("==> unidadSelected " + unidadSelected);
        idLocalidad = personaSelected.getCodLocalidad().getCodLocalidad();
    }

    public boolean guardarUsuario(){
        if(!usuarioSelected.getClave().equals(confirmarContrasenia)){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La valor de la Contraseña debe ser igual al valor del campo Confirmar contraseña."));
            return false;
        }
        try{
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo guardarUsuario()");
        personaSelected.setIdPersona(iUtilsService.valorSecuencia("PER_PERSONA_SEC").toString());
        personaSelected.setCodLocalidad(iLocalidadService.findById(idLocalidad));
        personaSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
        personaSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
        PerUnidadPK perUnidadPK=new PerUnidadPK();
        perUnidadPK.setIdPersona(personaSelected.getIdPersona());
        perUnidadPK.setIdUnidad(iUtilsService.valorSecuencia("PER_UNIDAD_SEC"));
        unidadSelected.setPerUnidadPK(perUnidadPK);
        unidadSelected.setEstadoUnidad(iDominioService.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO, PAR_ESTADO_USUARIO_ACTIVO).getParDominioPK().getValor());
        unidadSelected.setFechaNacimiento(fechaNacimiento);
        unidadSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
        unidadSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
        String passwordEncriptado = Util.encriptaMD5(usuarioSelected.getClave());
        usuarioSelected.setClave(passwordEncriptado);
        usuarioSelected.setIdUsuario(iUtilsService.valorSecuencia("USR_USUARIO_SEC"));
        usuarioSelected.setTipoAutenticacion(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_AUTENTICACION, PAR_TIPO_AUTENTICACION_LOCAL).getParDominioPK().getValor());
        usuarioSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
        usuarioSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
        iPersonaService.guardarUsuarioInterno(personaSelected, unidadSelected, usuarioSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("nuevoDlg.hide();");
        cargarUsuario();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario creó correctamente"));
        return true;
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no fue creado! comuníquese con el administrador"));
            log.info("Error " + e.getMessage());
            return false;
        }
    }

    public void editarUsuario() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo editarUsuario()");
        try {
            personaSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
            personaSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
            unidadSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
            unidadSelected.setRegistroBitacora(personaSelected.getRegistroBitacora());
            iPersonaService.editarPersona(personaSelected, unidadSelected, idLocalidad);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("edicionDlg.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se editó correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no se pudo editar!"));
            log.info("Error " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String descripcionEstado(String valor){
        return Util.descripcionDominio("ESTADO", valor);
    }

    public void inhabilitarUsuario() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo eliminarUsuario()");
        try {
            usuarioSelected.setFechaRehabilitacion(new Timestamp(0));
            usuarioSelected.setFechaInhabilitacion(new Timestamp(new Date().getTime()));
            usuarioSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
            usuarioSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
            iUsuarioService.save(usuarioSelected);
            cargarUsuario();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario fue inhabilitado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no inhabilitado!"));
            log.info("Error " + e.getMessage());
        }
    }

    public void habilitarUsuario() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo habilitarUsuario()");
        try {
            usuarioSelected.setFechaRehabilitacion(new Timestamp(new Date().getTime()));
            usuarioSelected.setFechaInhabilitacion(new Timestamp(0));
            usuarioSelected.setFechaBitacora(new Timestamp(new Date().getTime()));
            usuarioSelected.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());
            iUsuarioService.save(usuarioSelected);
            cargarUsuario();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario fue habilitado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no pudo ser habilitado!"));
        }
    }

    public void instanciarUsuario(){
        usuarioSelected = new UsrUsuario();
        personaSelected = new PerPersona();
        unidadSelected = new PerUnidad();
    }


    // *** Obtener listados *** //

    public void cargarLocalidad() {
        List<ParLocalidad> localidades = new ArrayList<ParLocalidad>();
        localidadLista = new ArrayList<SelectItem>();
        localidades = iLocalidadService.getAllLocalidades();
        for (ParLocalidad l : localidades) {
            if (!l.getDescripcion().equalsIgnoreCase("BOLIVIA"))
                localidadLista.add(new SelectItem(l.getCodLocalidad(), l.getDescripcion()));
        }
    }

    public List<SelectItem> cargarListas(List<SelectItem>lista,String dominio){
        lista=new ArrayList<SelectItem>();
            List<ParDominio>valoresDominio=iDominioService.obtenerItemsDominio(dominio);
            for(ParDominio d:valoresDominio){
                lista.add(new SelectItem(d.getParDominioPK().getValor(),d.getDescripcion()));
            }
        return lista;
    }


    // *** Getters && Setters *** //
    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public UsrUsuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(UsrUsuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public ILocalidadService getiLocalidadService() {
        return iLocalidadService;
    }

    public void setiLocalidadService(ILocalidadService iLocalidadService) {
        this.iLocalidadService = iLocalidadService;
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

    public PerPersona getPersonaSelected() {
        return personaSelected;
    }

    public void setPersonaSelected(PerPersona personaSelected) {
        this.personaSelected = personaSelected;
    }

    public PerUnidad getUnidadSelected() {
        return unidadSelected;
    }

    public void setUnidadSelected(PerUnidad unidadSelected) {
        this.unidadSelected = unidadSelected;
    }

    public List<SelectItem> getTipoDocumentoLista() {
        return tipoDocumentoLista;
    }

    public void setTipoDocumentoLista(List<SelectItem> tipoDocumentoLista) {
        this.tipoDocumentoLista = tipoDocumentoLista;
    }

    public List<SelectItem> getLocalidadLista() {
        return localidadLista;
    }

    public void setLocalidadLista(List<SelectItem> localidadLista) {
        this.localidadLista = localidadLista;
    }

    public List<SelectItem> getTipoSociedadLista() {
        return tipoSociedadLista;
    }

    public void setTipoSociedadLista(List<SelectItem> tipoSociedadLista) {
        this.tipoSociedadLista = tipoSociedadLista;
    }

    public List<SelectItem> getTipoEmpresaLista() {
        return tipoEmpresaLista;
    }

    public void setTipoEmpresaLista(List<SelectItem> tipoEmpresaLista) {
        this.tipoEmpresaLista = tipoEmpresaLista;
    }

    public String getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(String idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<UsrUsuario> getUsuarioSecundariaLista() {
        return usuarioSecundariaLista;
    }

    public void setUsuarioSecundariaLista(List<UsrUsuario> usuarioSecundariaLista) {
        this.usuarioSecundariaLista = usuarioSecundariaLista;
    }

    public List<UsrUsuario> getUsuarioFiltroLista() {
        return usuarioFiltroLista;
    }

    public void setUsuarioFiltroLista(List<UsrUsuario> usuarioFiltroLista) {
        this.usuarioFiltroLista = usuarioFiltroLista;
    }
}
