/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRolEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
public interface IRolService {
    
    public List<UsrRolEntity> getAllRoles();
    public UsrRolEntity save(UsrRolEntity rol);
    public boolean delete(UsrRolEntity rol);
    public UsrRolEntity findById(BigDecimal id);
    
}
