package bo.gob.mintrabajo.ovt.bean.publico;

import bo.gob.mintrabajo.ovt.api.IMensajeAppService;
import bo.gob.mintrabajo.ovt.bean.EscritorioBean;
import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
//import java.util.Collection;
//import java.util.Collection;

@ManagedBean
@ViewScoped
public class InformacionPublicaBean {
    //
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;    
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);
    //
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    //
    private String idMensaje;
    private Long idMensajeApp;
    private String rutaInformacionOvt;
    private ParMensajeApp parMensajeApp;

    @PostConstruct
    public void ini() {
        logger.info("InformacionPublicaBean.ini");
        cargarParametros();
        cargar();
    }

    public void cargarParametros(){
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                System.out.println("Error al cargar p, no se encuentra el valor p, p=null");
                idMensaje = "0";
                return;
            }

            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null) {
                idMensaje = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p");
                return;
            }
        } catch (Exception e) {
            idMensaje = "0";
            System.out.println("Error al cargar p: " + e.getMessage());
            return;
        }
    }
    
    public void cargar() {
        try {
            idMensajeApp = new Long(idMensaje);
            parMensajeApp = iMensajeAppService.findById(idMensajeApp);
            rutaInformacionOvt = "file://" + parMensajeApp.getReferencia();
        } catch (Exception e) {
            logger.error("====>>>> Error al cargar <<<<<=====");
            logger.error(e.getMessage());
            rutaInformacionOvt = "/pages/util/default.xhtml";
        }
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public String getRutaInformacionOvt() {
        return rutaInformacionOvt;
    }

    public void setRutaInformacionOvt(String rutaInformacionOvt) {
        this.rutaInformacionOvt = rutaInformacionOvt;
    }
    
}
