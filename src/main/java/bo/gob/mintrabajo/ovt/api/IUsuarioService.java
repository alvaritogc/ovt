/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
public interface IUsuarioService {
    public List<UsrUsuarioEntity> getAllUsuarios();
    public UsrUsuarioEntity save(UsrUsuarioEntity usuario);
    public boolean delete(UsrUsuarioEntity usuario);
    public UsrUsuarioEntity findById(BigDecimal id);
    public boolean login(String username, String password);
}
