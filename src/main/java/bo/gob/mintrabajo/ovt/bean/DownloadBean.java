package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;

    private Integer idUsuario;
    private Long idDocumento;
    private List<DocBinarioEntity> listaBinarios;
    private DocBinarioEntity docBinarioEntity;
    private StreamedContent file;
    UsrUsuarioEntity usuario;

    @PostConstruct
    public void ini() {
        docBinarioEntity= new DocBinarioEntity();
        listaBinarios= new ArrayList<DocBinarioEntity>();
        idDocumento = (Long) session.getAttribute("idDocumento");
        idUsuario = (Integer) session.getAttribute("idUsuario");
        usuario = iUsuarioService.findById(BigDecimal.valueOf(idUsuario));
        cargar();
    }

    public void cargar(){
        listaBinarios.clear();
        listaBinarios= iBinarioService.listaBinariosPorDocumento(idDocumento);
    }

    public void download(){
        DocLogImpresionEntity docLogImpresionEntity= new DocLogImpresionEntity();
        if(docBinarioEntity!=null)     {
            docLogImpresionEntity.setIdDocumento(docBinarioEntity.getIdDocumento());
            docLogImpresionEntity.setFechaBitacora(new Timestamp(new Date().getTime()));
            docLogImpresionEntity.setRegistroBitacora(usuario.getUsuario());
            docLogImpresionEntity.setIdDoclogimpresion(iUtilsService.valorSecuencia("DOC_LOG_IMPRESION_SEC"));
            iBinarioService.download(docBinarioEntity, docLogImpresionEntity);
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se encuentra el archivo correspondiente"));
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

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }
}
