package bo.gob.mintrabajo.ovt.bean.administracion;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.api.IUsuarioRolService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.*;
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

    @ManagedProperty(value="#{usuarioRolService}")
    private IUsuarioRolService iUsuarioRolService;

    @ManagedProperty(value="#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value="#{personaService}")
    private IPersonaService iPersonaService;

    private Boolean seleccionado;
    private Long usuarioSelected;

    private List<UsrRol> rolLista;
    private List<PerPersona> seleccionadoLista;

    private static final Logger log = LoggerFactory.getLogger(UsuarioRolAdministracionBean.class);

    public void cargarRoles(){
        seleccionadoLista = new ArrayList<PerPersona>();
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
        cargarRoles();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Asignación de roles ejecutado correctamente"));
        }catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención!", "No se pudo editar la asignació de roles!"));
        }
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
}
