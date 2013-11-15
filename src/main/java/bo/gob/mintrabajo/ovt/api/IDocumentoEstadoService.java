package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;

public interface IDocumentoEstadoService {
    ParDocumentoEstado buscarPorId(String id);
    public ParDocumentoEstado findById(String id);
    List<ParDocumentoEstado> listarSiguientesTransiciones(DocDocumento documento);
    List<ParDocumentoEstado> listarDocumentoEstados();
    ParDocumentoEstado guardarModificarDocumentoEstado(ParDocumentoEstado parDocumentoEstado);
    void eliminarDocumentoEstado(ParDocumentoEstado parDocumentoEstado);

}
