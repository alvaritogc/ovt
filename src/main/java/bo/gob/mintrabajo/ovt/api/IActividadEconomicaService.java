
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;


public interface IActividadEconomicaService {
    public ParActividadEconomica save(ParActividadEconomica actividadEconomica);
    public void save(ParActividadEconomica actividadEconomica,PerUnidad unidad,String registroBitacora);
}