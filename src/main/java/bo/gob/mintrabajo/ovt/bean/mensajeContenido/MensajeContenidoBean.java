package bo.gob.mintrabajo.ovt.bean.mensajeContenido;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
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
    @ManagedProperty(value = "#{mensajeContenidoService}")
    private IMensajeContenidoService iMensajeContenidoService;
    @ManagedProperty(value = "#{mensajeBinarioService}")
    private IMensajeBinarioService iMensajeBinarioService;
    //
    private Long idRecurso;
    private ParMensajeApp mensajeApp;
    private List<ParMensajeContenido> listaMensajeContenido;
    private ParMensajeContenido mensajeContenido;
    //
    private boolean mostrarFacesMessages;
    private boolean detenerFacesMessages;
    //

    @PostConstruct
    public void ini() {
        logger.info("MensajeContenidoBean.init()");
        String parametro = "";
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Map params = ec.getRequestParameterMap();
            parametro = params.get("rec").toString();
        } catch (Exception e) {
            System.out.println("No se encontro el parametro");
            parametro = "0";
        }
        System.out.println("parametro: " + parametro);
        idRecurso = new Long(parametro);
        cargar();
        System.out.println("mostrarFacesMessages: " + mostrarFacesMessages);
    }

    public void cargar() {
        detenerFacesMessages = false;
        listaMensajeContenido = new ArrayList<ParMensajeContenido>();
        //
        List<ParMensajeApp> listaMensajeApp = iMensajeAppService.listarPorRecurso(idRecurso);
        if (listaMensajeApp != null && listaMensajeApp.size() > 0) {
            mensajeApp = listaMensajeApp.get(0);
            listaMensajeContenido = iMensajeContenidoService.listarPorMensajeApp(mensajeApp.getIdMensajeApp());
        } else {
            mostrarFacesMessages = false;
        }
        //
        if (listaMensajeApp.size() > 0) {
            mostrarFacesMessages = true;
        } else {
            mostrarFacesMessages = false;
        }
    }

    public void abrirPanel() {
        if (mostrarFacesMessages) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgParMensajeApp.show()");
        }
        detenerFacesMessages = true;
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

    public List<ParMensajeContenido> getListaMensajeContenido() {
        return listaMensajeContenido;
    }

    public void setListaMensajeContenido(List<ParMensajeContenido> listaMensajeContenido) {
        this.listaMensajeContenido = listaMensajeContenido;
    }

    public ParMensajeApp getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(ParMensajeApp mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public IMensajeContenidoService getiMensajeContenidoService() {
        return iMensajeContenidoService;
    }

    public void setiMensajeContenidoService(IMensajeContenidoService iMensajeContenidoService) {
        this.iMensajeContenidoService = iMensajeContenidoService;
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
