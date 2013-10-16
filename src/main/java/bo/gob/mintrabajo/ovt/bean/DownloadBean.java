package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//

@ManagedBean
@ViewScoped
public class DownloadBean {
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DownloadBean.class);

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;

    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;

    private Long idDocumento;
//    private DocDocumentoEntity docDocumentoEntity;
    private List<DocBinarioEntity> listaBinarios;
    private DocBinarioEntity docBinarioEntity;
    private StreamedContent file;

    @PostConstruct
    public void ini() {
//        docDocumentoEntity= new DocDocumentoEntity();
        docBinarioEntity= new DocBinarioEntity();
        listaBinarios= new ArrayList<DocBinarioEntity>();
        idDocumento = (Long) session.getAttribute("idDocumento");
//        docDocumentoEntity=iDocumentoService.findById(BigDecimal.valueOf(idDocumento));
        cargar();
    }

    public void cargar(){
        listaBinarios.clear();
        listaBinarios= iBinarioService.listaBinariosPorDocumento(idDocumento);
    }

    public void download(){
        if(docBinarioEntity==null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se encuentra el archivo correspondiente"));
        else
            iBinarioService.download(docBinarioEntity);
    }

    public String volver(){
        return "irBienvenida";
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public List<DocBinarioEntity> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinarioEntity> listaBinarios) {
        this.listaBinarios = listaBinarios;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public DocBinarioEntity getDocBinarioEntity() {
        return docBinarioEntity;
    }

    public void setDocBinarioEntity(DocBinarioEntity docBinarioEntity) {
        this.docBinarioEntity = docBinarioEntity;
    }
}
