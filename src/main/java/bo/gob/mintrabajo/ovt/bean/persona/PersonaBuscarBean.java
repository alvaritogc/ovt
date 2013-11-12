package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/25/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean(name = "personaBuscarBean")
@ViewScoped
public class PersonaBuscarBean implements Serializable{


   private ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();
    private HttpSession session;

    private PerPersona persona;
    private List<PerPersona> listaPersona;

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;


    @ManagedProperty(value = "#{utilService}")
    private IUtilsService iUtilsService;

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @PostConstruct
    public void ini(){
        persona=new PerPersona();
        listaPersona=new ArrayList<PerPersona>();
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        //
       // logger.info("Buscando usuario" + idUsuario);
       // UsrUsuario usuario = iUsuarioService.findById(idUsuario);
        System.out.println("=====================>>>> BUSCAR");
       listaPersona=iPersonaService.findAll();
       // Arrays.asList(listaPersona) ;
        //listaPersona =getListaPersona();
       // listaPersona=  iPersonaService.buscarPorNroNombre("","");

    }

    public void irUnidad()throws IOException{
        session.setAttribute("persona",persona);
        externalContext.redirect("registroPersonaUnidad.xhtml");
    }

    public void nuevo()throws IOException {
        externalContext.redirect("registroPersona.xhtml");
    }
    public void cargar(){
        listaPersona=  iPersonaService. buscarPorNroNombre("1579592010","SUSANA HEREDIA PRADO");

    }

   public void limpiar(){
       persona=new PerPersona();
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

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public List<PerPersona> getListaPersona() {
        if(listaPersona!=null)
           listaPersona=iPersonaService.findAll();
        listaPersona=iPersonaService.findAll();
        return listaPersona;
    }

    public void setListaPersona(List<PerPersona> listaPersona) {
        this.listaPersona = listaPersona;
    }


}
