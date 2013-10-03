package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
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
@Named
@TransactionAttribute
public class RecursoService implements IRecursoService{
    
    private final RecursoRepository recursoRepository;

    @Inject
    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }
    
    @Override
    public List<UsrRecursoEntity> getAllRecursos() {
        List<UsrRecursoEntity> allRecursos;

        try {
            allRecursos = recursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allRecursos = null;
        }
        return allRecursos;
    }
    
    @Override
    public UsrRecursoEntity save(UsrRecursoEntity recurso) {
        return recursoRepository.save(recurso);
        /*
        UsrRecursoEntity usrRecursoEntity;

        try {
            usrRecursoEntity = recursoRepository.save(recurso);
        } catch (Exception e) {
            e.printStackTrace();
            usrRecursoEntity = null;
        }

        return usrRecursoEntity;*/
    }

    @Override
    public boolean delete(UsrRecursoEntity recurso) {
        boolean deleted = false;

        try {
            recursoRepository.delete(recurso);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrRecursoEntity findById(BigDecimal id) {
        UsrRecursoEntity usrRecursoEntity;

        try {
            usrRecursoEntity = recursoRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            usrRecursoEntity = null;
        }

        return usrRecursoEntity;
    }
    
    @Override
    public List<UsrRecursoEntity> buscarPorUsuario(BigDecimal idUsuario) {
        List<UsrRecursoEntity> allRecursos;

        try {
            allRecursos = recursoRepository.buscarPorUsuario(idUsuario);
            //allRecursos = recursoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allRecursos = null;
        }
        return allRecursos;
        //return null;
    }
    
}
