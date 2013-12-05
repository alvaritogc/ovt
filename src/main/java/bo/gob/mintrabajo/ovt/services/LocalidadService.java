package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILocalidadService;
import bo.gob.mintrabajo.ovt.entities.ParLocalidad;
import bo.gob.mintrabajo.ovt.repositories.LocalidadRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 10/28/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("localidadService")
@TransactionAttribute
public class LocalidadService implements ILocalidadService {

    private static final Logger logger = LoggerFactory.getLogger(LocalidadService.class);
    private final LocalidadRepository localidadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public LocalidadService(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }


    public List<ParLocalidad> getAllLocalidades() {

        return localidadRepository.findAll();
    }

    @Override
    public Long localidadSecuencia(String nombreSecuencia) {
        BigDecimal rtn;
        rtn = (BigDecimal) entityManager.createNativeQuery("SELECT " + nombreSecuencia + ".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public ParLocalidad findById(String idLocalidad) {
        return localidadRepository.findOne(idLocalidad);
    }

    public List<ParLocalidad> listarDepartamentos() {
        return localidadRepository.listarDepartamentos();
    }

}
