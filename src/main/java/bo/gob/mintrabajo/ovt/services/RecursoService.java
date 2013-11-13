package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("recursoService")
@TransactionAttribute
public class RecursoService implements IRecursoService {

    private final RecursoRepository recursoRepository;

    @Inject
    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

//    @Override
    public List<UsrRecurso> getAllRecursos() {
        List<UsrRecurso> allRecursos;
        allRecursos = recursoRepository.findAll();
        return allRecursos;
    }

//    @Override
    public boolean delete(UsrRecurso recurso) {
        boolean deleted = false;
        recursoRepository.delete(recurso);
        deleted = true;
        return deleted;
    }

    @Override
    public UsrRecurso findById(Long id) {
        return recursoRepository.findOne(id);
    }

    @Override
    public List<UsrRecurso> buscarPorUsuario(Long idUsuario) {
        return recursoRepository.buscarPorUsuario(idUsuario);
    }
    
    @Override
    public List<UsrRecurso> listarPorTipoRecurso(String tipoRecurso) {
        return recursoRepository.findByAttribute("tipoRecurso", tipoRecurso, -1, -1);
    }
    
}
