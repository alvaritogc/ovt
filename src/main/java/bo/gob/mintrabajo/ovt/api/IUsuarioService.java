package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import java.util.List;

/**
 *
 * @author pc01
 */
public interface IUsuarioService {
    public Long login(String username, String password);
    public Long loginConfirmacion(String username, String password);
    public List<UsrUsuario> getAllUsuarios();
    public List<UsrUsuario> obtenerUsuariosIntenos();
    public UsrUsuario findById(Long id);
    public Long obtenerSecuencia(String nombreSecuencia);
    public UsrUsuario save(UsrUsuario usrUsuario);
    public UsrUsuario obtenerUsuarioPorNombreUsuario(String email);
    public String cambiarContrasenia(String email,String clave,String nuevaClave,String confirmarClave);
    public String cambiarContrasenia(Long idUsuario,String clave,String nuevaClave,String confirmarClave);
    public String cambiarContraseniaNueva(Long idUsuario, String nuevaClave, String confirmarClave);
    public void cambiarLogin(Long idUsuario,String loginNuevo);
    public List<UsrUsuario> buscarPorUsuario(String usuario);
    public List<UsrUsuario> obtenerUsuarioPorIdPersona(String idPersona);
}
