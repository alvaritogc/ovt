
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.util.List;


public interface IActividadService {
    public PerActividad save(PerActividad actividad,String registroBitacora,PerUnidad unidad);
    public List<PerActividad> obtenerPorIdPersonaYIdUnidad(PerUnidad unidad);
}