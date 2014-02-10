
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerReplegal;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.util.List;


public interface IRepLegalService {
    public PerReplegal save(PerReplegal replegal,String registroBitacora,PerUnidad unidad);
    public List<PerReplegal>obtenerPorIdPersona(String idPersona);
    ///////////////////////////LUIS
    public PerReplegal obtenerPorIdPersonaAndIdUnidad(String idPersona, Long idUnidad);
}