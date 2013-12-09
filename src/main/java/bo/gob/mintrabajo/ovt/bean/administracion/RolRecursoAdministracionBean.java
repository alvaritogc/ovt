package bo.gob.mintrabajo.ovt.bean.administracion;

import bo.gob.mintrabajo.ovt.Util.ArtificioEntity;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IRolRecursoService;
import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/17/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RolRecursoAdministracionBean {

    @ManagedProperty(value="#{rolRecursoService}")
    private IRolRecursoService iRolRecursoService;

    @ManagedProperty(value="#{recursoService}")
    private IRecursoService iRecursoService;

    @ManagedProperty(value="#{rolService}")
    private IRolService iRolService;

    private Long idRol;
    private String tipoPermiso;
    private List<UsrRol> rolLista;
    private List<UsrRecurso> recursoLista;
    private List<ArtificioEntity> seleccionadoLista;

    private static final Logger log = LoggerFactory.getLogger(RolRecursoAdministracionBean.class);

    @PostConstruct
    public void ini(){
        rolLista =   iRolService.getAllRoles();
    }

    public void buscarRecursoPorRolRecurso(){
        seleccionadoLista = new ArrayList<ArtificioEntity>();
        recursoLista = new ArrayList<UsrRecurso>();
        recursoLista.addAll(iRecursoService.obtenerTodosRecursoLista());

        for(UsrRecurso r : recursoLista){
            UsrRolRecursoPK usrRolRecursoPK = new UsrRolRecursoPK();
            usrRolRecursoPK.setIdRol(idRol);
            usrRolRecursoPK.setIdRecurso(r.getIdRecurso());
            UsrRolRecurso tmp = iRolRecursoService.tieneRelacionRolRecurso(usrRolRecursoPK);
            if(tmp != null){
                r.setAux1(true);
                r.setCadenaAuxiliar(tmp.getWx());
            }else{
                r.setAux1(false);
            }
        }
    }

    public void guardarRolRecurso() {
        log.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo guardarRolRecurso()");
        try {
            for (UsrRecurso ur :recursoLista) {
                if(ur.getAux1()){
                    UsrRolRecurso rr = new UsrRolRecurso();
                    rr.setWx(ur.getCadenaAuxiliar());
                    iRolRecursoService.guardarRolRecurso(rr, idRol, ur.getIdRecurso());
                }else{
                    iRolRecursoService.eliminarRolRecurso(idRol, ur.getIdRecurso());
                }


//                if (seleccionadoLista.get(i).getAux1()) {
//                    UsrRolRecurso rr = new UsrRolRecurso();
//                    if (seleccionadoLista.get(i).getAux2() && seleccionadoLista.get(i).getAux3()) {
//                        rr.setWx("WX");
//                    } else {
//
//                        if (seleccionadoLista.get(i).getAux2()) {
//                            rr.setWx("W");
//                        }
//                        if (seleccionadoLista.get(i).getAux3()) {
//                            rr.setWx("X");
//                        }
//                    }
//                    iRolRecursoService.guardarRolRecurso(rr, idRol, recursoLista.get(i).getIdRecurso());
//                } else {
//                    iRolRecursoService.eliminarRolRecurso(idRol, recursoLista.get(i).getIdRecurso());
//                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Asignación de recursos ejecutado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "la asignación de recursos falló"));
            e.printStackTrace();
        }
    }

    /// *** Getters && Setters *** ///
    public IRolRecursoService getiRolRecursoService() {
        return iRolRecursoService;
    }

    public void setiRolRecursoService(IRolRecursoService iRolRecursoService) {
        this.iRolRecursoService = iRolRecursoService;
    }

    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }

    public IRolService getiRolService() {
        return iRolService;
    }

    public void setiRolService(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    public List<UsrRecurso> getRecursoLista() {
        return recursoLista;
    }

    public void setRecursoLista(List<UsrRecurso> recursoLista) {
        this.recursoLista = recursoLista;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public List<UsrRol> getRolLista() {
        return rolLista;
    }

    public void setRolLista(List<UsrRol> rolLista) {
        this.rolLista = rolLista;
    }

    public List<ArtificioEntity> getSeleccionadoLista() {
        return seleccionadoLista;
    }

    public void setSeleccionadoLista(List<ArtificioEntity> seleccionadoLista) {
        this.seleccionadoLista = seleccionadoLista;
    }
}
