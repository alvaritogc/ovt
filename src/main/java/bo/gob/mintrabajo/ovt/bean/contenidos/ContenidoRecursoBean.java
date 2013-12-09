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
import javax.faces.application.FacesMessage;
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
    private UsrRecurso recurso;
    private boolean edicion;

    @PostConstruct
    public void ini() {
        logger.info("ContenidosBean.init()");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String parametro = params.get("p").toString();
        idRecurso = parametro != null ? new Long(parametro) : new Long("1000");
        recurso = iRecursoService.findById(idRecurso);
        cargar();
    }

    public void cargar() {
        mensajeApp = new ParMensajeApp();
        listaMensajeApp = iMensajeAppService.listarPorRecurso(idRecurso);
    }

    public String editar() {
        session.setAttribute("idMensajeApp", mensajeApp.getIdMensajeApp());
        return "irContenidos";
    }

    public void nuevo() {
        mensajeApp = new ParMensajeApp();
        edicion = false;

    }

    public String guardar() {
        System.out.println("=====================================");
        System.out.println("=====================================");
        System.out.println("=====================================");
        System.out.println("Guardar");
        System.out.println("=====================================");
        System.out.println("=====================================");
        System.out.println("=====================================");
        if (mensajeApp.getMensaje() == null || mensajeApp.getMensaje().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Error", "Debe ingresar el campo Etiqueta."));
            return "";
        }
        if (mensajeApp.getReferencia() == null || mensajeApp.getReferencia().trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Error", "Debe ingresar la referencia."));
            return "";
        }
        if (mensajeApp.getFechaDesde() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Error", "Debe ingresar la Fecha desde."));
            return "";
        }
        if (mensajeApp.getFechaHasta() != null) {
            if (mensajeApp.getFechaDesde().after(mensajeApp.getFechaHasta())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR
                                , "Error", "La Fecha hasta no puede ser mayor a la Fecha desde."));
                return "";

            }
        }
        mensajeApp = iMensajeAppService.guardar(mensajeApp, idRecurso);
        mensajeApp = new ParMensajeApp();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("contenidoRecursoDlg.hide()");
        cargar();
        return "";
    }

    public void eliminar() {
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

    public boolean isEdicion() {
        return edicion;
    }

    public void setEdicion(boolean edicion) {
        this.edicion = edicion;
    }

    public UsrRecurso getRecurso() {
        return recurso;
    }

    public void setRecurso(UsrRecurso recurso) {
        this.recurso = recurso;
    }

}