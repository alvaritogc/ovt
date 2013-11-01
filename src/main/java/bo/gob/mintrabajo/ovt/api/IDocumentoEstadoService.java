package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;

public interface IDocumentoEstadoService {
    ParDocumentoEstado buscarPorId(String id);
}
