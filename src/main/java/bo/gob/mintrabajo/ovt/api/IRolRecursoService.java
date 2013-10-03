package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRolRecursoEntity;
import java.math.BigDecimal;
import java.util.List;


public interface IRolRecursoService {
    
    public List<UsrRolRecursoEntity> getAllRecursos();
    public UsrRolRecursoEntity save(UsrRolRecursoEntity rolRecurso);
    public boolean delete(UsrRolRecursoEntity rolRecurso);
    public UsrRolRecursoEntity findById(BigDecimal id);
    
}
