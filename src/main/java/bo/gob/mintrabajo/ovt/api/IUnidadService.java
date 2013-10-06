/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
public interface IUnidadService {
    
    public List<PerUnidadEntity> getAllUnidades();
    public PerUnidadEntity save(PerUnidadEntity unidad);
    public boolean delete(PerUnidadEntity unidad);
    public PerUnidadEntity findById(BigDecimal id);
    public List<PerUnidadEntity> listarPorPersona( String idPersona);
    
}
