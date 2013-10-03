package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoEntity;
import java.math.BigDecimal;
import java.util.List;


public interface IUsuarioRecursoService {
    
    public List<UsrUsuarioRecursoEntity> getAllUsuarios();
    public UsrUsuarioRecursoEntity save(UsrUsuarioRecursoEntity usuarioRecurso);
    public boolean delete(UsrUsuarioRecursoEntity usuarioRecurso);
    public UsrUsuarioRecursoEntity findById(BigDecimal id);
    
}
