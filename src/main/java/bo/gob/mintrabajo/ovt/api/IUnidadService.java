package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;

import java.util.List;

public interface IUnidadService {

    List<PerUnidad> buscarPorPersona(String idPersona);
    Long obtenerSecuencia(String nombreSecuencia);
    PerUnidad save(PerUnidad unidad);
    public PerUnidad save(PerUnidad unidad,PerPersona persona,String registroBitacora);
    List<PerUnidad> listarPorPersona(String idPersona);
    PerUnidad obtienePorId(PerUnidadPK perUnidadPK);
    List<PerUnidad> listarUnidadesSucursales(String idPersona);
    long obtenerMaximaUnidad(String idPersona);

}