package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoEntity;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRecursoRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("usuarioRecursoService")
@TransactionAttribute
public class UsuarioRecursoService implements IUsuarioRecursoService{
    private final UsuarioRecursoRepository usuarioRecursoRepository;

    @Inject
    public UsuarioRecursoService(UsuarioRecursoRepository usuarioRecursoRepository) {
        this.usuarioRecursoRepository = usuarioRecursoRepository;
    }
    
    @Override
    public List<UsrUsuarioRecursoEntity> getAllUsuarios() {
        List<UsrUsuarioRecursoEntity> lista;

        try {
            lista = usuarioRecursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public UsrUsuarioRecursoEntity save(UsrUsuarioRecursoEntity usuarioRecurso) {
        UsrUsuarioRecursoEntity entity;

        try {
            entity = usuarioRecursoRepository.save(usuarioRecurso);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }

    @Override
    public boolean delete(UsrUsuarioRecursoEntity usuarioRecurso) {
        boolean deleted = false;

        try {
            usuarioRecursoRepository.delete(usuarioRecurso);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrUsuarioRecursoEntity findById(BigDecimal id) {
        UsrUsuarioRecursoEntity entity;

        try {
            entity = usuarioRecursoRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }
}
