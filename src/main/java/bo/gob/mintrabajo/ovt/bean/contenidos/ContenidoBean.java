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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@ViewScoped
public class ContenidoBean {
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
        idMensajeApp = (Long)session.getAttribute("idMensajeApp");
        //idMensajeApp = cargarIdRecurso();
        System.out.println("idMensajeApp: " + idMensajeApp);
        mensajeApp = iMensajeAppService.findById(idMensajeApp);
        cargar();
    }

    public Long cargarIdRecurso() {
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                System.out.println("Error al cargar p, no se encuentra el valor p, p=null");
                return null;
            }

            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null) {
                String p = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p");
                return new Long(p);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar p: " + e.getMessage());
            return null;
        }
        return null;
    }

    public void cargar() {
        mensajeContenido = new ParMensajeContenido();
        listaMensajeContenido = iMensajeContenidoService.listarPorMensajeApp(idMensajeApp);
    }

    public void nuevoContenido() {
        System.out.println("=============================");
        System.out.println("=============================");
        System.out.println("Nuevo contenido");
        System.out.println("=============================");
        System.out.println("=============================");
        mensajeContenido = new ParMensajeContenido();
        mensajeContenido.setEsDescargable(new Short("0"));
        mensajeContenido.setBinario(null);
        mensajeContenido.setMetadata("N/A");
    }
    public void nuevoContenidoDescarga() {
        System.out.println("=============================");
        System.out.println("=============================");
        System.out.println("Nuevo contenido descarga");
        System.out.println("=============================");
        System.out.println("=============================");
        mensajeContenido = new ParMensajeContenido();
        mensajeContenido.setEsDescargable(new Short("1"));
        mensajeContenido.setBinario(null);
        mensajeContenido.setMetadata("N/A");
    }

    public void guardar() {
        System.out.println("texto :" + mensajeContenido.getContenido());
        if(mensajeContenido.getEsDescargable()==new Short("1")){
            mensajeContenido.setContenido("");
        }
        
        //
        mensajeContenido.setIdMensajeApp(mensajeApp);
        
        mensajeContenido = iMensajeContenidoService.save(mensajeContenido);
        //
        mensajeContenido = new ParMensajeContenido();
        cargar();
        //
        mensajeApp = iMensajeAppService.findById(idMensajeApp);
        //mensajeContenido=iMensajeContenidoService.save(mensajeContenido);
        //
        //cargar();
        //
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("condenidoDlg.hide()");
//        context.execute("condenidoDescargaDlg.hide()");
        //
        try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
        catch(Exception e){
            e.printStackTrace();
        }
            
        
        
    }
    
    public void eliminar(){
        System.out.println("eliminar");
        iMensajeContenidoService.delete(mensajeContenido.getIdMensajeContenido());
        mensajeContenido=new ParMensajeContenido();
        cargar();
    }

    public void descargar() {
        System.out.println("descargar");
        System.out.println("mensaje: " + mensajeContenido.getContenido());
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
            output.write(mensajeContenido.getBinario());
            output.close();
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mensajeContenido = new ParMensajeContenido();
    }

    public void subirArchivo(FileUploadEvent event) {
        System.out.println("SubirArchivo");
        //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        //FacesContext.getCurrentInstance().addMessage(null, msg);

        mensajeContenido.setArchivo(event.getFile().getFileName());
        mensajeContenido.setBinario(event.getFile().getContents());
        mensajeContenido.setMetadata(event.getFile().getContentType());
    }

    public String irContenidoRecurso() {
        return "irContenidoRecurso";
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
}