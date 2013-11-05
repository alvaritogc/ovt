package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author pc01
 */
public interface IUsuarioService {
    public Long login(String username, String password);
    public List<UsrUsuario> getAllUsuarios();
    public UsrUsuario findById(Long id);
    public Long obtenerSecuencia(String nombreSecuencia);
    public UsrUsuario save(UsrUsuario usrUsuario);
    public UsrUsuario obtenerUsuarioPorNombreUsuario(String email);
    public UsrUsuario findByUsuarioAndClave(String email,String clave,String nuevaClave);

}
