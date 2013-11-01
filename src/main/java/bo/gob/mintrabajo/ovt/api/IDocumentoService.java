package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;

public interface IDocumentoService {
    List<DocDocumento> listarPorPersona(String idPersona);
    DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario);

}