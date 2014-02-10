
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;

import java.util.List;


public interface IDireccionService {
    public PerDireccion save(PerDireccion direccion,String registroBitacora,PerUnidad unidad);
    public List<PerDireccion>obtenerPorIdPersonaYIdUnidad(String idPersona,long idUnidad);
    public List<PerDireccion>obtenerPorIdPersona(String idPersona);
    public PerDireccion obtenerPorId(Long idDireccion);
    public PerDireccion obtenerPorIdPersonaYIdUnidadYEstadoActivo(PerUnidadPK perUnidadPK);
    public PerDireccion obtenerPorIdPersonaAndIdUnidad(String idPersona, Long idUnidad);
}