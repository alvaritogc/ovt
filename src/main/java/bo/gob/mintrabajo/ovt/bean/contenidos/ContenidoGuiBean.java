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
public class ContenidoGuiBean implements Serializable {
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
    private List<UsrRecurso> listaRecursos;
    private UsrRecurso recurso;

    @PostConstruct
    public void ini() {
        logger.info("ContenidoGuiBean.init()");
        cargar();
    }

    public void cargar() {
        listaRecursos = iRecursoService.listarPorTipoRecurso("GUI");
    }

    public void adicionarContenido() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("/ovt/pages/contenidos/contenidoRecurso.jsf?p=" + recurso.getIdRecurso());
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("no se pudo redireccionar a la pagina");
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

    public IMensajeBinarioService getiMensajeBinarioService() {
        return iMensajeBinarioService;
    }

    public void setiMensajeBinarioService(IMensajeBinarioService iMensajeBinarioService) {
        this.iMensajeBinarioService = iMensajeBinarioService;
    }

    public List<UsrRecurso> getListaRecursos() {
        return listaRecursos;
    }

    public void setListaRecursos(List<UsrRecurso> listaRecursos) {
        this.listaRecursos = listaRecursos;
    }

    public UsrRecurso getRecurso() {
        return recurso;
    }

    public void setRecurso(UsrRecurso recurso) {
        this.recurso = recurso;
    }
}