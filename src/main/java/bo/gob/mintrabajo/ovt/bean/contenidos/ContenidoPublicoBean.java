package bo.gob.mintrabajo.ovt.bean.contenidos;

import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.sql.SQLException;

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
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
@ViewScoped
public class ContenidoPublicoBean {
    //
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    @ManagedProperty(value = "#{mensajeContenidoService}")
    private IMensajeContenidoService iMensajeContenidoService;
    @ManagedProperty(value = "#{mensajeBinarioService}")
    private IMensajeBinarioService iMensajeBinarioService;
    //
    private ParMensajeApp mensajeApp;
    //
    private List<ParMensajeContenido> listaMensajeContenido;
    private ParMensajeContenido mensajeContenido;
    //
    private Long idMensajeApp;

    @PostConstruct
    public void ini() {
        logger.info("ContenidosBean.init()");
        //
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        String parametro = params.get("p").toString();
        System.out.println("parametro: " + parametro);
        idMensajeApp = parametro != null ? new Long(parametro) : null;
        //
        mensajeApp = iMensajeAppService.findById(idMensajeApp);
        cargar();
    }

    public void cargar() {
        mensajeContenido = new ParMensajeContenido();
        listaMensajeContenido = iMensajeContenidoService.listarPorMensajeApp(idMensajeApp);
    }

    public void descargar() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            String nombreDocumentoDigital = mensajeContenido.getArchivo();
            String mimeDocumentoDigital = URLConnection.guessContentTypeFromName(nombreDocumentoDigital);
            if (mimeDocumentoDigital == null) {
                mimeDocumentoDigital = "application/octet-stream";
            }
            response.reset();
            response.setContentType(mimeDocumentoDigital);
            response.setHeader("Content-disposition", "attachment; filename=\"" + nombreDocumentoDigital + "\"");
            OutputStream output = response.getOutputStream();
            //output.write(mensajeContenido.getBinario());
            output.write(iMensajeBinarioService.buscarPorMensajeContenido(mensajeContenido.getIdMensajeContenido()).getBinario());
            output.close();
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mensajeContenido = new ParMensajeContenido();
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public IMensajeContenidoService getiMensajeContenidoService() {
        return iMensajeContenidoService;
    }

    public void setiMensajeContenidoService(IMensajeContenidoService iMensajeContenidoService) {
        this.iMensajeContenidoService = iMensajeContenidoService;
    }

    public ParMensajeApp getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(ParMensajeApp mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public List<ParMensajeContenido> getListaMensajeContenido() {
        return listaMensajeContenido;
    }

    public void setListaMensajeContenido(List<ParMensajeContenido> listaMensajeContenido) {
        this.listaMensajeContenido = listaMensajeContenido;
    }

    public ParMensajeContenido getMensajeContenido() {
        return mensajeContenido;
    }

    public void setMensajeContenido(ParMensajeContenido mensajeContenido) {
        this.mensajeContenido = mensajeContenido;
    }

    public IMensajeBinarioService getiMensajeBinarioService() {
        return iMensajeBinarioService;
    }

    public void setiMensajeBinarioService(IMensajeBinarioService iMensajeBinarioService) {
        this.iMensajeBinarioService = iMensajeBinarioService;
    }
}