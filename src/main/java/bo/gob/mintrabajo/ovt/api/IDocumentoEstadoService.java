package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;


public interface IDocumentoEstadoService {
    public List<ParDocumentoEstado> listarSiguientesTransiciones(DocDocumento documento);
    public ParDocumentoEstado findById(String id);

}
