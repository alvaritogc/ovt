/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author pc01
 */
public interface IRecursoService {
    
    public List<UsrRecursoEntity> getAllRecursos();
    public UsrRecursoEntity save(UsrRecursoEntity recurso);
    public boolean delete(UsrRecursoEntity recurso);
    public UsrRecursoEntity findById(BigDecimal id);
    public List<UsrRecursoEntity> buscarPorUsuario(BigDecimal idUsuario);
    
}
