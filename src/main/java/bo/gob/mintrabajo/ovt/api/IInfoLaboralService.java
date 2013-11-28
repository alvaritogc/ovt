
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerInfolaboral;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;


import java.util.List;


public interface IInfoLaboralService {
    public PerInfolaboral save(PerInfolaboral infolaboral,String registroBitacora,PerUnidad unidad);
    public List<PerInfolaboral>findByPerUnidad(PerUnidad unidad);
}