package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecursoPK;
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
public class RolRecursoService implements IRolRecursoService {

    private final RolRecursoRepository rolRecursoRepository;

    @Inject
    public RolRecursoService(RolRecursoRepository rolRecursoRepository) {
        this.rolRecursoRepository = rolRecursoRepository;
    }

    //@Override
    public List<UsrRolRecurso> getAllRecursos() {
        List<UsrRolRecurso> lista;

        try {
            lista = rolRecursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

//    @Override
    public UsrRolRecurso save(UsrRolRecurso rolRecurso) {
        UsrRolRecurso entity;
        entity = rolRecursoRepository.save(rolRecurso);
        return entity;
    }

//    @Override
    public boolean delete(UsrRolRecurso rolRecurso) {
        boolean deleted = false;
        rolRecursoRepository.delete(rolRecurso);
        deleted = true;
        return deleted;
    }

//    @Override
    public UsrRolRecurso findById(UsrRolRecursoPK id) {
        UsrRolRecurso entity;
        entity = rolRecursoRepository.findOne(id);
        return entity;
    }
}
