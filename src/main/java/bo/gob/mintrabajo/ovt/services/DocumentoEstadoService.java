package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoEstadoService;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("documentoEstadoService")
@TransactionAttribute
public class DocumentoEstadoService implements IDocumentoEstadoService {

    private final DocumentoEstadoRepository documentoEstadoRepository;

    @Inject
    public DocumentoEstadoService(DocumentoEstadoRepository documentoEstadoRepository) {
        this.documentoEstadoRepository = documentoEstadoRepository;
    }

    public ParDocumentoEstado buscarPorId(String id){
        return documentoEstadoRepository.findOne(id);
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

    public List<ParDocumentoEstado> listarDocumentoEstados(){
        return documentoEstadoRepository.findAll();
    }

    public ParDocumentoEstado guardarModificarDocumentoEstado(ParDocumentoEstado parDocumentoEstado){
        return documentoEstadoRepository.save(parDocumentoEstado);
    }

    public void eliminarDocumentoEstado(ParDocumentoEstado parDocumentoEstado){
       documentoEstadoRepository.delete(parDocumentoEstado);
    }
}
