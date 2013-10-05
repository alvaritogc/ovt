package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecursoEntity;
import bo.gob.mintrabajo.ovt.repositories.RolRecursoRepository;
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
@Named("rolRecursoService")
@TransactionAttribute
public class RolRecursoService implements IRolRecursoService{
    
    private final RolRecursoRepository rolRecursoRepository;

    @Inject
    public RolRecursoService(RolRecursoRepository rolRecursoRepository) {
        this.rolRecursoRepository = rolRecursoRepository;
    }
    
    @Override
    public List<UsrRolRecursoEntity> getAllRecursos() {
        List<UsrRolRecursoEntity> lista;

        try {
            lista = rolRecursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public UsrRolRecursoEntity save(UsrRolRecursoEntity rolRecurso) {
        
        UsrRolRecursoEntity entity;

        try {
            entity = rolRecursoRepository.save(rolRecurso);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }

    @Override
    public boolean delete(UsrRolRecursoEntity rolRecurso) {
        boolean deleted = false;

        try {
            rolRecursoRepository.delete(rolRecurso);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrRolRecursoEntity findById(BigDecimal id) {
        UsrRolRecursoEntity entity;

        try {
            entity = rolRecursoRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }
    
}
