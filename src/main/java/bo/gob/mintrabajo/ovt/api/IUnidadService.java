package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;

import java.util.Date;
import java.util.List;

public interface IUnidadService {

    List<PerUnidad> buscarPorPersona(String idPersona);
    Long obtenerSecuencia(String nombreSecuencia);
    PerUnidad save(PerUnidad unidad);
    public PerUnidad save(PerUnidad unidad,PerPersona persona,String registroBitacora);
    List<PerUnidad> listarPorPersona(String idPersona);
    PerUnidad obtienePorId(PerUnidadPK perUnidadPK);
    List<PerUnidad> listarUnidadesSucursales(String idPersona);
    List<PerUnidad> listarUnidadesSucursalesPorFecha(String idPersona, String codDocumento, Date fechaHasta, Date fechaPlazo2);
    long obtenerMaximaUnidad(String idPersona);
    /////////////////////////////////////////LUIS
    public PerUnidad obtenerPorIdPersonaIdUnidad(String idPersona,long idUnidad);

}