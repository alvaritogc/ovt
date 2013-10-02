/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
public interface IPersonaService {
    
    public List<PerPersonaEntity> getAllPersonas();
    public PerPersonaEntity save(PerPersonaEntity persona);
    public boolean delete(PerPersonaEntity persona);
    public PerPersonaEntity findById(BigDecimal id);
    
}
