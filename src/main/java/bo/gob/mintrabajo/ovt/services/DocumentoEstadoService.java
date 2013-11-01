package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoEstadoService;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

@Named("documentoEstadoService")
@TransactionAttribute
public class DocumentoEstadoService implements IDocumentoEstadoService{

    private final DocumentoEstadoRepository documentoEstadoRepository;

    @Inject
    public DocumentoEstadoService(DocumentoEstadoRepository documentoEstadoRepository) {
        this.documentoEstadoRepository = documentoEstadoRepository;
    }

    public ParDocumentoEstado retornaDocEstado(String codEstado){
        return documentoEstadoRepository.findByAttribute("codEstado", codEstado, -1, -1).get(0);
    }

    public ParDocumentoEstado retornaCodEstado(String descripcion){
        return documentoEstadoRepository.findByAttribute("descripcion", descripcion, -1, -1).get(0);
    }
    
    @Override
    public List<ParDocumentoEstado> listarSiguientesTransiciones(DocDocumento documento){
        return documentoEstadoRepository.listarSiguientesTransiciones(
                documento.getDocDefinicion().getDocDefinicionPK().getCodDocumento(), 
                documento.getDocDefinicion().getDocDefinicionPK().getVersion(), 
                documento.getCodEstado().getCodEstado());
    }
    
    @Override
    public ParDocumentoEstado findById(String id) {
        return documentoEstadoRepository.findOne(id);
    }
}
