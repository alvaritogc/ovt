package bo.gob.mintrabajo.ovt.bean.contenidos;

import bo.gob.mintrabajo.ovt.bean.*;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
@ViewScoped
public class ContenidoBean implements Serializable {
    //
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(EscritorioBean.class);
    //
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
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
    private byte[] binario;
    //
    private Long idMensajeApp;
    private boolean edicion;
    private boolean tieneImagenes;
    private String bitacoraSession;

    @PostConstruct
    public void ini() {
        logger.info("ContenidosBean.init()");
        idMensajeApp = (Long) session.getAttribute("idMensajeApp");
        mensajeApp = iMensajeAppService.findById(idMensajeApp);
        bitacoraSession = (String) session.getAttribute("bitacoraSession");
        cargar();
    }

    public void cargar() {
        mensajeContenido = new ParMensajeContenido();
        listaMensajeContenido = iMensajeContenidoService.listarPorMensajeApp(idMensajeApp);
    }

    public void nuevoContenido() {
        edicion = false;
        mensajeContenido = new ParMensajeContenido();
        mensajeContenido.setEsDescargable(new Short("0"));
        mensajeContenido.setMetadata("N/A");
        binario = null;
        tieneImagenes = iMensajeContenidoService.tieneImagenes(mensajeApp.getIdMensajeApp());
    }

    public void nuevoContenidoDescarga() {
        edicion = false;
        mensajeContenido = new ParMensajeContenido();
        mensajeContenido.setEsDescargable(new Short("1"));
        mensajeContenido.setMetadata("N/A");
        binario = null;
    }


    public void modificarContenido() {
        edicion = true;
        tieneImagenes = true;
    }

    public String guardar() {
        if (mensajeContenido.getEsDescargable() == new Short("1")) {
            mensajeContenido.setContenido("");
            if (binario == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR
                                , "Error", "No se encontro el archivo."));
                return "";
            }
        }
        mensajeContenido.setIdMensajeApp(mensajeApp);
        mensajeContenido = iMensajeContenidoService.save(mensajeContenido, binario, bitacoraSession);
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void eliminar() {
        iMensajeContenidoService.delete(mensajeContenido.getIdMensajeContenido());
        mensajeContenido = new ParMensajeContenido();
        cargar();
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
            output.write(iMensajeBinarioService.buscarPorMensajeContenido(mensajeContenido.getIdMensajeContenido()).getBinario());
            output.close();
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mensajeContenido = new ParMensajeContenido();
    }

    public void subirArchivo(FileUploadEvent event) {
        mensajeContenido.setArchivo(event.getFile().getFileName());
        binario = event.getFile().getContents();
        mensajeContenido.setMetadata(event.getFile().getContentType());
    }

    public void irContenidoRecurso() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("/ovt/pages/contenidos/contenidoRecurso.jsf?p=" + mensajeApp.getIdRecurso().getIdRecurso());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
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

    public boolean isEdicion() {
        return edicion;
    }

    public void setEdicion(boolean edicion) {
        this.edicion = edicion;
    }

    public IMensajeBinarioService getiMensajeBinarioService() {
        return iMensajeBinarioService;
    }

    public void setiMensajeBinarioService(IMensajeBinarioService iMensajeBinarioService) {
        this.iMensajeBinarioService = iMensajeBinarioService;
    }

    public byte[] getBinario() {
        return binario;
    }

    public void setBinario(byte[] binario) {
        this.binario = binario;
    }

    public boolean isTieneImagenes() {
        return tieneImagenes;
    }

    public void setTieneImagenes(boolean tieneImagenes) {
        this.tieneImagenes = tieneImagenes;
    }
}