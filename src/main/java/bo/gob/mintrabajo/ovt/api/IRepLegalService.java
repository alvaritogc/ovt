
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerReplegal;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.util.List;


public interface IRepLegalService {
    public PerReplegal save(PerReplegal replegal);
    public List<PerReplegal> findByPerUnidad(PerUnidad unidad);
}