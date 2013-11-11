package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import java.util.List;

public interface IPersonaService {
    PerPersona findById(String id);
    List<PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial);
    public  List<PerPersona> findAll();
    public PerPersona save(PerPersona persona);
    public Long obtenerSecuencia(String nombreSecuencia);
    public  boolean registrar(PerPersona persona,PerUnidad unidad,UsrUsuario usuario);
    public PerPersona buscarPorId(String id);
    public void cambiarEstadoUsuario(Long usuario);
    public boolean eliminarRegistro(String perPersona, UsrUsuario usrUsuario);
}
