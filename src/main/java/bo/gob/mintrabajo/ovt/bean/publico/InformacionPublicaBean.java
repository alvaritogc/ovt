package bo.gob.mintrabajo.ovt.bean.publico;

import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
//import bo.gob.mintrabajo.ovt.envano.DobleTrabajoConexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Collection;
//import java.util.Collection;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class InformacionPublicaBean {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
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
        System.out.println("=======================================");
        System.out.println("=======================================");
        System.out.println("=======================================");
        System.out.println("InformacionOvtBean");
        System.out.println("=======================================");
        System.out.println("=======================================");
        try {
            session.getAttribute("idUsuario");
        } catch (Exception e) {
        }
        idMensaje="1";
        idMensajeApp=0l;
        cargarParametros();
        cargar();
    }

    public void cargarParametros(){
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                idMensaje = "1";
                return;
            }

            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null) {
                idMensaje = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p");
                return;
            }
        } catch (Exception e) {
            idMensaje = "1";
            System.out.println("Error al cargar p: " + e.getMessage());
            return;
        }
    }
    
    public void cargar() {
        System.out.println("idMensaje: "+idMensaje);
        try {
            idMensajeApp = new Long(idMensaje);
            System.out.println("idMensajeApp: "+idMensajeApp);
            parMensajeApp = iMensajeAppService.findById(idMensajeApp);
            rutaInformacionOvt = "file://" + parMensajeApp.getReferencia();
        } catch (Exception e) {
            e.printStackTrace();
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
