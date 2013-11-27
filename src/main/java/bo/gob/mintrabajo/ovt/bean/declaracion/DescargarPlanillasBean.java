package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
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
import java.util.Date;
import java.util.List;

//

@ManagedBean
@ViewScoped
public class DescargarPlanillasBean {
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DescargarPlanillasBean.class);

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;

    private Long idUsuario;
    private Long idDocumento;
    private List<DocBinario> listaBinarios;
    private DocBinario docBinario;
    private StreamedContent file;
    private UsrUsuario usuario;
    private DocDocumento docDocumento;

    @PostConstruct
    public void ini() {
        docBinario= new DocBinario();
        listaBinarios= new ArrayList<DocBinario>();
        idDocumento = (Long) session.getAttribute("idDocumento");
        docDocumento = iDocumentoService.findById(idDocumento);
        idUsuario = (Long) session.getAttribute("idUsuario");
        usuario = iUsuarioService.findById(idUsuario);
        cargar();
    }

    public void cargar(){
        listaBinarios.clear();
        listaBinarios= iBinarioService.listarPorIdDocumento(idDocumento);
    }

    public void download(){
        DocLogImpresion docLogImpresion= new DocLogImpresion();
        if(docBinario!=null)     {
            docLogImpresion.setIdDocumento(docDocumento);
            docLogImpresion.setFechaBitacora(new Date());
            docLogImpresion.setRegistroBitacora(usuario.getUsuario());
            docLogImpresion.setTipoImpresion(Dominios.DOC_TIPO_DOWNLOAD);
            docLogImpresion.setIdDoclogimpresion(iUtilsService.valorSecuencia("DOC_LOG_IMPRESION_SEC"));
            iBinarioService.download(docBinario, docLogImpresion);
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se encuentra el archivo correspondiente"));
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

    public List<DocBinario> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinario> listaBinarios) {
        this.listaBinarios = listaBinarios;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public DocBinario getDocBinario() {
        return docBinario;
    }

    public void setDocBinario(DocBinario docBinario) {
        this.docBinario = docBinario;
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
