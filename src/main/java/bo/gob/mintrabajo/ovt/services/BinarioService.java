package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;

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

    @Inject
    public BinarioService(BinarioRepository binarioRepository) {
        this.binarioRepository = binarioRepository;
    }

    @Override
    public DocBinarioEntity guardarBinario(DocBinarioEntity docBinariosEntity){
        return binarioRepository.save(docBinariosEntity);
    }

    @Override
    public Long contar(){
        return binarioRepository.count();
    }

    @Override
    public List<DocBinarioEntity> listaBinariosPorDocumento(Long idDocumento){
        return binarioRepository.findByIdDocumento(idDocumento);
    }

    @Override
    public DocBinarioEntity obtienePorIdDocumentoIdBinario(int idDocumento, int idBinario){
        return binarioRepository.findByIdDocumentoAndIdBinario(idDocumento, idBinario);
    }

    @Override
    public void download(DocBinarioEntity docBinarioEntity){
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            //Cambiar en base al metadata...
            String nombreDocumentoDigital = docBinarioEntity.getTipoDocumento();
//            String mimeDocumentoDigital = URLConnection.guessContentTypeFromName(nombreDocumentoDigital);
            String mimeDocumentoDigital=docBinarioEntity.getMetadata();
            //
            if (mimeDocumentoDigital == null)
                mimeDocumentoDigital = "application/octet-stream";
            response.reset();
            response.setContentType(mimeDocumentoDigital);
            response.setHeader("Content-disposition", "attachment; filename=\"" + nombreDocumentoDigital + "\"");
            OutputStream output = response.getOutputStream();
            output.write(docBinarioEntity.getBinario());
            output.close();
            facesContext.responseComplete();
            } catch (Exception e) {
                System.out.println("El archivo no se descargo correctamente.");
                e.printStackTrace();
        }
    }
}
