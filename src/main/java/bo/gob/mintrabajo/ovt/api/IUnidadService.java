package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import java.util.List;

public interface IUnidadService {

    List<PerUnidad> buscarPorPersona(String idPersona);
    public Long obtenerSecuencia(String nombreSecuencia);
    public PerUnidad save(PerUnidad unidad);
    public List<PerUnidad> listarPorPersona(String idPersona);
}
