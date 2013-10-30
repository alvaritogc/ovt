package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import java.util.List;
import java.util.Map;

public interface IPersonaService {
    PerPersona findById(String id);
    List<PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial);
    public  List<PerPersona> findAll();
    public PerPersona save(PerPersona persona);
    public Long obtenerSecuencia(String nombreSecuencia);
    public  boolean registrar(PerPersona persona,PerUnidad unidad,UsrUsuario usuario);
}
