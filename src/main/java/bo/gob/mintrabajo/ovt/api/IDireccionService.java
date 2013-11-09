
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.util.List;


public interface IDireccionService {
    public PerDireccion save(PerDireccion direccion);
    //public List<PerDireccion> obtenerItemsDominio(String dominio);
    public List<PerDireccion> findByPerUnidad(PerUnidad unidad);
}