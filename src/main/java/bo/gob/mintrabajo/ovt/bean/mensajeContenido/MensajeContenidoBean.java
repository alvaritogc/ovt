package bo.gob.mintrabajo.ovt.bean.mensajeContenido;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
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
public class MensajeContenidoBean {
    //
    private static final Logger logger = LoggerFactory.getLogger(MensajeContenidoBean.class);
    //
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    //
    private String idRecursoString;
    //
    private boolean mostrarFacesMessages;
    private boolean detenerFacesMessages;
    private String rutaContenidoFacesMessages;
    //
    @PostConstruct
    public void ini() {
        logger.info("MensajeContenidoBean.init()");
        cargarParametro();
        Long idRecurso = new Long(idRecursoString);
        detenerFacesMessages = false;
        try{
            ParMensajeApp mensaje = iMensajeAppService.buscarPorRecurso(idRecurso);
            rutaContenidoFacesMessages="file://"+mensaje.getReferencia();
            mostrarFacesMessages=true;
        }catch(Exception e){
            System.out.println("Error al encontrar el recurso");
            rutaContenidoFacesMessages="/pages/util/default.xhtml";
            mostrarFacesMessages=false;
        }
        System.out.println("mostrarFacesMessages: "+mostrarFacesMessages);
        System.out.println("ruta: "+rutaContenidoFacesMessages);
    }
    
    public void cargarParametro(){
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                idRecursoString = "1";
                return;
            }
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null) {
                idRecursoString = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p");
                return;
            }
        } catch (Exception e) {
            idRecursoString = "1";
            System.out.println("Error al cargar p: " + e.getMessage());
            return;
        }
        
    }

    public void abrirPanel() {
        if(mostrarFacesMessages){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgParMensajeApp.show()");
        }
        detenerFacesMessages = true;
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public boolean isMostrarFacesMessages() {
        return mostrarFacesMessages;
    }

    public void setMostrarFacesMessages(boolean mostrarFacesMessages) {
        this.mostrarFacesMessages = mostrarFacesMessages;
    }

    public boolean isDetenerFacesMessages() {
        return detenerFacesMessages;
    }

    public void setDetenerFacesMessages(boolean detenerFacesMessages) {
        this.detenerFacesMessages = detenerFacesMessages;
    }

    public String getRutaContenidoFacesMessages() {
        return rutaContenidoFacesMessages;
    }

    public void setRutaContenidoFacesMessages(String rutaContenidoFacesMessages) {
        this.rutaContenidoFacesMessages = rutaContenidoFacesMessages;
    }
}
