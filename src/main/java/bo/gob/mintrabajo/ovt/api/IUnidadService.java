package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;

import java.util.List;

public interface IUnidadService {

    List<PerUnidad> buscarPorPersona(String idPersona);
    Long obtenerSecuencia(String nombreSecuencia);
    PerUnidad save(PerUnidad unidad);
    List<PerUnidad> listarPorPersona(String idPersona);
    PerUnidad obtienePorId(PerUnidadPK perUnidadPK);
}