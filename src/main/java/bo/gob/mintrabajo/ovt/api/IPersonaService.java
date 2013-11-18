package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import java.util.List;

public interface IPersonaService {
    PerPersona findById(String id);
    public List<PerPersona> buscarPorNroNombre(final String nombreRazonSocial,final String tipoIdentificacion,final String nroIdentificacion);
    public  List<PerPersona> findAll();
    public PerPersona save(PerPersona persona);
    public Long obtenerSecuencia(String nombreSecuencia);
    public  boolean registrar(PerPersona persona,PerUnidad unidad,UsrUsuario usuario);
    public PerPersona buscarPorId(String id);
    public void cambiarEstadoUsuario(Long usuario);
    public boolean eliminarRegistro(String perPersona, UsrUsuario usrUsuario);
    List<PerPersona> listarPorSucursal(String idPersona);
    PerPersona obtienePorCentral(String idPersona);
    public PerPersona findByNroIdentificacion(String nroIdentificacion);
    public PerPersona obtenerPersonaPorUsuario(UsrUsuario usrUsuario);
    public void editarPersona(PerPersona persona, PerUnidad unidad, String idLocalidad);
    public boolean guardarUsuarioInterno(PerPersona persona,PerUnidad unidad,UsrUsuario usuario);
    public boolean guardarUsuarioRol(Long idUsuario, Long idRol);
    public void eliminarUsuarioRol(Long idUsuario, Long idRol);
}
