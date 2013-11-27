package bo.gob.mintrabajo.ovt.bean.contenidos;

import bo.gob.mintrabajo.ovt.api.IMensajeBinarioService;
import bo.gob.mintrabajo.ovt.api.IMensajeContenidoService;
import bo.gob.mintrabajo.ovt.entities.ParMensajeBinario;
import bo.gob.mintrabajo.ovt.entities.ParMensajeContenido;
import java.io.ByteArrayInputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class Images {

    private StreamedContent image;

    @ManagedProperty("#{param.id}")
    private Long id;
    //
    @ManagedProperty(value = "#{mensajeContenidoService}")
    private IMensajeContenidoService iMensajeContenidoService;
    @ManagedProperty(value = "#{mensajeBinarioService}")
    private IMensajeBinarioService iMensajeBinarioService;

//    @EJB
//    private ImageService service;
    

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            image = new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            //ParMensajeContenido mensajeContenido=iMensajeContenidoService.findById(id);
            ParMensajeBinario mensajeContenido=iMensajeBinarioService.buscarPorMensajeContenido(id);
            image = new DefaultStreamedContent(new ByteArrayInputStream(mensajeContenido.getBinario()));
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StreamedContent getImage() {
        return image;
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

}