package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import javax.ejb.TransactionAttribute;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("binService")
@TransactionAttribute
public class BinarioService implements IBinarioService{

    private final BinarioRepository binarioRepository;
    private final LogImpresionRepository logImpresionRepository;

    @Inject
    public BinarioService(BinarioRepository binarioRepository, LogImpresionRepository logImpresionRepository) {
        this.binarioRepository = binarioRepository;
        this.logImpresionRepository = logImpresionRepository;
    }


    public List<DocBinario> listarPorIdDocumento(Long idDocumento){
       return  binarioRepository.findByIdDocumento(idDocumento);
    }

    public void download(DocBinario docBinario, DocLogImpresion docLogImpresion){
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            // TODO Cambiar en base al metadata...
            String nombreDocumentoDigital = docBinario.getTipoDocumento();
//            String mimeDocumentoDigital = URLConnection.guessContentTypeFromName(nombreDocumentoDigital);
            String mimeDocumentoDigital=docBinario.getMetadata();
            //
            if (mimeDocumentoDigital == null)
                mimeDocumentoDigital = "application/octet-stream";
            response.reset();
            response.setContentType(mimeDocumentoDigital);
            response.setHeader("Content-disposition", "attachment; filename=\"" + nombreDocumentoDigital + "\"");
            OutputStream output = response.getOutputStream();
            output.write(docBinario.getBinario());
            output.close();
            facesContext.responseComplete();
            logImpresionRepository.save(docLogImpresion);

            } catch (Exception e) {
            System.out.println("====>>>> Error al descargar el archivo <<<<<=====");
            System.out.println(e.getMessage());
        }
    }
}
