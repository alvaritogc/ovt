
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolEntity;
import java.math.BigDecimal;
import java.util.List;


public interface IUsuarioRolService {
    
    public List<UsrUsuarioRolEntity> getAllUsuarios();
    public UsrUsuarioRolEntity save(UsrUsuarioRolEntity usuarioRol);
    public boolean delete(UsrUsuarioRolEntity usuarioRol);
    public UsrUsuarioRolEntity findById(BigDecimal id);
    
}
