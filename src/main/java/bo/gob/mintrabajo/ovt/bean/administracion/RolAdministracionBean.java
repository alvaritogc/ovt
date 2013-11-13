package bo.gob.mintrabajo.ovt.bean.administracion;

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.api.IUsrModuloService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.entities.UsrRol;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/13/13
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RolAdministracionBean {

    @ManagedProperty(value="#{rolService}")
    private IRolService iRolService;

    @ManagedProperty(value="#{usrModuloService}")
    private IUsrModuloService iUsrModuloService;

    @ManagedProperty(value="#{dominioService}")
    private IDominioService iDominioService;

    private short esInterno;
    private UsrRol rolSelected;
    private List<UsrRol> rolLista;
    private List<UsrModulo> moduloLista;

    private static final Logger log = LoggerFactory.getLogger(RolAdministracionBean.class);

    @PostConstruct
    public void cargaRolLista(){
        rolLista = iRolService.getAllRoles();
        moduloLista = iUsrModuloService.obtenerUsrModuloLista();
    }

    public void guardarRol(){
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo guardarRol()");
        iRolService.save(rolSelected, esInterno);
        RequestContext context = RequestContext.getCurrentInstance();
        cargaRolLista();
        context.execute("nuevoDlg.hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El rol se creó correctamente"));
    }

    public void editarRol(){
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo editarRol()");
        iRolService.save(rolSelected, esInterno);
        RequestContext context = RequestContext.getCurrentInstance();
        cargaRolLista();
        context.execute("edicionDlg.hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El rol se editó correctamente"));
    }

    public void eliminarRol(){
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo eliminarRol()");
        log.info("Eliminado el rol " + rolSelected.getIdModulo() + " . " + rolSelected.getNombre());
        log.info("Procesando ....... ");
        boolean tmp = iRolService.delete(rolSelected);
        if(tmp) {
            cargaRolLista();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El rol " + rolSelected.getNombre() + " se eliminó correctamente" ));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cuidado", "El rol no fue eliminado"));
        }
    }

    public List<ParDominio> getDominioLista(){
        return iDominioService.obtenerItemsDominio("ESTADO");
    }

    public void instanciaRol(){
        rolSelected = new UsrRol();
    }

    // *** Getters && Setters *** //
    public IRolService getiRolService() {
        return iRolService;
    }

    public void setiRolService(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    public List<UsrRol> getRolLista() {
        return rolLista;
    }

    public void setRolLista(List<UsrRol> rolLista) {
        this.rolLista = rolLista;
    }

    public IUsrModuloService getiUsrModuloService() {
        return iUsrModuloService;
    }

    public void setiUsrModuloService(IUsrModuloService iUsrModuloService) {
        this.iUsrModuloService = iUsrModuloService;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public UsrRol getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(UsrRol rolSelected) {
        this.rolSelected = rolSelected;
    }

    public List<UsrModulo> getModuloLista() {
        return moduloLista;
    }

    public void setModuloLista(List<UsrModulo> moduloLista) {
        this.moduloLista = moduloLista;
    }

    public short getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(short esInterno) {
        this.esInterno = esInterno;
    }
}
