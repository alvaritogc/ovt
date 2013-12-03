package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocLogEstado;

import java.util.List;

public interface ILogEstadoService {
    public List<DocLogEstado> listarPorDocumento(Long idDocumento);

}
