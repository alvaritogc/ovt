/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParDominioEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
public interface IDominioService {
    
    public List<ParDominioEntity> getAllDominios();
    public ParDominioEntity save(ParDominioEntity dominio);
    public boolean delete(ParDominioEntity dominio);
    public ParDominioEntity findById(BigDecimal id);
    
}
