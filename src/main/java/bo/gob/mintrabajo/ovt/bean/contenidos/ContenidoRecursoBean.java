package bo.gob.mintrabajo.ovt.bean.contenidos;

import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;

//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
//import java.util.Collection;
//import java.util.Collection;

@ManagedBean
@ViewScoped
public class ContenidoRecursoBean {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    //
    private static final Logger logger = LoggerFactory.getLogger(ContenidoRecursoBean.class);
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    //
    private List<ParMensajeApp> listaMensajeApp;
    private ParMensajeApp mensajeApp;
    //
    private Long idRecurso;

    @PostConstruct
    public void ini() {
        logger.info("ContenidosBean.init()");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String parametro= params.get("p").toString();
        idRecurso=parametro!=null?new Long(parametro):new Long("1000");
        cargar();
    }
    
    public void cargar(){
        mensajeApp=new ParMensajeApp();
        listaMensajeApp=iMensajeAppService.listarPorRecurso(idRecurso);
    }
    
    public String editar(){
        session.setAttribute("idMensajeApp", mensajeApp.getIdMensajeApp());
        return "irContenidos";
    }
    public void nuevo(){
        mensajeApp=new ParMensajeApp();
    }
    
    public void guardar(){
        mensajeApp=iMensajeAppService.guardar(mensajeApp,idRecurso);
        mensajeApp=new ParMensajeApp();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("contenidoRecursoDlg.hide()");
        cargar();
    }
    
    public void eliminar(){
        iMensajeAppService.delete(mensajeApp.getIdMensajeApp());
        cargar();
    }
    
    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }
    
    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }
    
    public List<ParMensajeApp> getListaMensajeApp() {
        return listaMensajeApp;
    }
    
    public void setListaMensajeApp(List<ParMensajeApp> listaMensajeApp) {
        this.listaMensajeApp = listaMensajeApp;
    }
    
    public ParMensajeApp getMensajeApp() {
        return mensajeApp;
    }
    
    public void setMensajeApp(ParMensajeApp mensajeApp) {
        this.mensajeApp = mensajeApp;
    }
    
    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }
    
    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }
    
}