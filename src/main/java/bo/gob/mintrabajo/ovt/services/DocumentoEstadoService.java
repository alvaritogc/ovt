package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoEstadoService;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

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
}
