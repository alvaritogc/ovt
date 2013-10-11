package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoEstado;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstadoEntity;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/10/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("documentoEstadoService")
@TransactionAttribute
public class DocumentoEstadoService implements IDocumentoEstado{

    private final DocumentoEstadoRepository documentoEstadoRepository;

    @Inject
    public DocumentoEstadoService(DocumentoEstadoRepository documentoEstadoRepository) {
        this.documentoEstadoRepository = documentoEstadoRepository;
    }

    public ParDocumentoEstadoEntity retornaDocEstado(String codEstado){
        return documentoEstadoRepository.findByAttribute("codEstado", codEstado, -1, -1).get(0);
    }

    public ParDocumentoEstadoEntity retornaCodEstado(String descripcion){
        return documentoEstadoRepository.findByAttribute("descripcion", descripcion, -1, -1).get(0);
    }
}
