package bo.gob.mintrabajo.ovt.bean.administracion;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/15/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class UsuarioRolAdministracionBean {

    @ManagedProperty(value="#{rolService}")
    private IRolService iRolService;

    @ManagedProperty(value="#{recursoService}")
    private IRecursoService iRecursoService;

    @ManagedProperty(value="#{usuarioRolService}")
    private IUsuarioRolService iUsuarioRolService;

    @ManagedProperty(value="#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value="#{usuarioRecursoService}")
    private IUsuarioRecursoService iUsuarioRecursoService;

    private Boolean seleccionado;
    private Long usuarioSelected;
    private UsrRecurso recursoSelected;
    private String tipoPermiso;
    private Short denegado;

    private Date fechaExpiracion;
    private Date fechaActual;
    private List<UsrRol> rolLista;
    private List<PerPersona> seleccionadoLista;
    private List<UsrRecurso> recursoLista;
    private List<UsrRecurso> usuarioRecursoLista;

    private static final Logger log = LoggerFactory.getLogger(UsuarioRolAdministracionBean.class);

    @PostConstruct
    public void ini(){
        usuarioRecursoLista = new ArrayList<UsrRecurso>();
        //recursoLista = iRecursoService.obtenerTodosRecursoLista();
    }

    public void buscarRolesPorUsuario(){
        seleccionadoLista = new ArrayList<PerPersona>();
        recursoLista = iRecursoService.buscarPorUsuario(usuarioSelected);
        cargarRecursoTemporales();

        rolLista = iRolService.getAllRoles();
        for(UsrRol ur : rolLista){
            UsrUsuarioRolPK llave = new UsrUsuarioRolPK();
            llave.setIdRol(ur.getIdRol());
            llave.setIdUsuario(usuarioSelected);
            boolean tmp = iUsuarioRolService.tieneRelacionUsuarioRol(llave);
            if(tmp){
                PerPersona p = new PerPersona();
                p.setEsNatural(true);
                seleccionadoLista.add(p);
            }else{
                PerPersona p = new PerPersona();
                p.setEsNatural(false);
                seleccionadoLista.add(p);
            }
        }
    }

    public void cargarRecursoTemporales(){
        try{
            usuarioRecursoLista = iRecursoService.obtenerRecursoEnUsuarioRecurso(usuarioSelected);
        }catch (Exception e){
            log.info("Error de la api !!!!!!!!!!");
        }
    }

    public void guardarUsuarioRol() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo guardarUsuarioRol()");
        try{
        for (int i = 0; i < rolLista.size(); i++) {
            if (seleccionadoLista.get(i).getEsNatural()) {
                iPersonaService.guardarUsuarioRol(usuarioSelected, rolLista.get(i).getIdRol());
            }else{
                iPersonaService.eliminarUsuarioRol(usuarioSelected, rolLista.get(i).getIdRol());
            }
        }
        buscarRolesPorUsuario();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Asignación de roles ejecutado correctamente"));
        }catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención!", "No se pudo editar la asignació de roles!"));
        }
    }

    public void asignarRecursoTemporal(){
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo asignarRecursoTemporal()");
        log.info("Asignando el recurso temporal " + recursoSelected.getDescripcion() + " Expira el " + fechaExpiracion);

        if(fechaExpiracion == null){
            fechaExpiracion = new Date();
        }
        UsrUsuarioRecurso ur = new UsrUsuarioRecurso();
        ur.setFechaLimite(fechaExpiracion);
        ur.setUsrRecurso(recursoSelected);
        ur.setWx(tipoPermiso);
        iUsuarioRecursoService.save(ur, usuarioSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Recurso asignado correctamente"));
        fechaExpiracion = null;
        buscarRolesPorUsuario();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("asignaDlg.hide();");
    }

    public void editarUsuarioRecurso() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo editarUsuarioRecurso()");
        try {
            UsrUsuarioRecurso ur = new UsrUsuarioRecurso();
            ur.setWx(tipoPermiso);
            ur.setFechaLimite(new java.sql.Date(fechaExpiracion.getTime()));
            ur.setEsDenegado(denegado);

            UsrUsuarioRecursoPK usrUsuarioRecursoPK = new UsrUsuarioRecursoPK();
            usrUsuarioRecursoPK.setIdUsuario(usuarioSelected);
            usrUsuarioRecursoPK.setIdRecurso(recursoSelected.getIdRecurso());
            iUsuarioRecursoService.editar(ur, usrUsuarioRecursoPK);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Recurso temporal editado correctamente"));
            log.info("Editado correctamente ................. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarRecursoTemporal(){
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo eliminarRecursoTemporal()");
        UsrUsuarioRecursoPK usrUsuarioRecursoPK = new UsrUsuarioRecursoPK();
        usrUsuarioRecursoPK.setIdUsuario(usuarioSelected);
        usrUsuarioRecursoPK.setIdRecurso(recursoSelected.getIdRecurso());
        boolean tmp = iUsuarioRecursoService.eliminarUsuarioRecurso(usrUsuarioRecursoPK);
        if(tmp){
            cargarRecursoTemporales();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se quitó el recurso temporal " + recursoSelected.getDescripcion()));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "No se pudo quitar el recurso!"));
        }
    }

    public void cargarUsuarioRecursoData(){
        UsrUsuarioRecursoPK usrUsuarioRecursoPK = new UsrUsuarioRecursoPK();
        usrUsuarioRecursoPK.setIdUsuario(usuarioSelected);
        usrUsuarioRecursoPK.setIdRecurso(recursoSelected.getIdRecurso());
        UsrUsuarioRecurso ur = iUsuarioRecursoService.buscarUnUsuarioRecurso(usrUsuarioRecursoPK);
        tipoPermiso = ur.getWx();
        fechaExpiracion = ur.getFechaLimite();
        denegado = ur.getEsDenegado();

    }


    public List<UsrUsuario> complete(String usuarioLogin){
        try{
        return iUsuarioService.buscarPorUsuario(usuarioLogin);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /// *** Getters && Setters *** ///
    public IRolService getiRolService() {
        return iRolService;
    }

    public void setiRolService(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    public IUsuarioRolService getiUsuarioRolService() {
        return iUsuarioRolService;
    }

    public void setiUsuarioRolService(IUsuarioRolService iUsuarioRolService) {
        this.iUsuarioRolService = iUsuarioRolService;
    }

    public List<UsrRol> getRolLista() {
        return rolLista;
    }

    public void setRolLista(List<UsrRol> rolLista) {
        this.rolLista = rolLista;
    }

    public List<PerPersona> getSeleccionadoLista() {
        return seleccionadoLista;
    }

    public void setSeleccionadoLista(List<PerPersona> seleccionadoLista) {
        this.seleccionadoLista = seleccionadoLista;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Long getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Long usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }

    public IUsuarioRecursoService getiUsuarioRecursoService() {
        return iUsuarioRecursoService;
    }

    public void setiUsuarioRecursoService(IUsuarioRecursoService iUsuarioRecursoService) {
        this.iUsuarioRecursoService = iUsuarioRecursoService;
    }

    public List<UsrRecurso> getRecursoLista() {
        return recursoLista;
    }

    public void setRecursoLista(List<UsrRecurso> recursoLista) {
        this.recursoLista = recursoLista;
    }

    public List<UsrRecurso> getUsuarioRecursoLista() {
        return usuarioRecursoLista;
    }

    public void setUsuarioRecursoLista(List<UsrRecurso> usuarioRecursoLista) {
        this.usuarioRecursoLista = usuarioRecursoLista;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Date getFechaActual() {
        return fechaActual = new Date();
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public UsrRecurso getRecursoSelected() {
        return recursoSelected;
    }

    public void setRecursoSelected(UsrRecurso recursoSelected) {
        this.recursoSelected = recursoSelected;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public Short getDenegado() {
        return denegado;
    }

    public void setDenegado(Short denegado) {
        this.denegado = denegado;
    }
}
