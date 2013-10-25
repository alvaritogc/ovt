package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import java.util.List;

public interface IDocumentoService {
    List<DocDocumento> listarPorPersona(String idPersona);

}