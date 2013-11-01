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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    @ManagedProperty(value = "#{utilService}")
    private IUtilsService iUtilsService;

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
       // listaPersona=  iPersonaService.buscarPorNroNombre("","");



    }

    public void nuevo()throws IOException {

        externalContext.redirect("registroPersona.xhtml");
    }
    public void cargar(){
        System.out.println("======>>> CARGAR: ");
        System.out.println("======>>> CARGAR: ");
        System.out.println("======>>> CARGAR: ");
        Map<String,PerPersona> mapa=new HashMap<String,PerPersona>();
        listaPersona=  iPersonaService. buscarPorNroNombre("1579592010","SUSANA HEREDIA PRADO");
      //  mapa=  iPersonaService. buscarPorNroNombre("1579592010","SUSANA HEREDIA PRADO");

        System.out.println("======>>> RESULTADO: ");
        System.out.println("======>>> RESULTADOS: " + String.valueOf(mapa.size()));
        System.out.println("======>>> RESULTADOS: "+String.valueOf(mapa.size()));
      //  System.out.println("======>>> RESULTADO: "+String.valueOf(listaPersona.size()));
        //System.out.println("======>>> RESULTADO: "+String.valueOf(listaPersona.size()));
    }

   public void limpiar(){
       persona=new PerPersona();
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


}
