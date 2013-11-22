
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerInfolaboral;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;


import java.util.List;


public interface IInfoLaboralService {
    public PerInfolaboral save(PerInfolaboral infolaboral);
    public List<PerInfolaboral>findByPerUnidad(PerUnidad unidad);

   // public List<PerActividad> findByPerUnidad(PerUnidad unidad);
}