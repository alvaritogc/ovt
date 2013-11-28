
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPerUsuarioService;
import bo.gob.mintrabajo.ovt.repositories.PerUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author QNA
 */
@Named("perUsuarioService")
@TransactionAttribute
public class PerUsuarioService implements IPerUsuarioService{
    private static final Logger logger = LoggerFactory.getLogger(PerUsuarioService.class);
    private final PerUsuarioRepository perUsuarioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public PerUsuarioService(PerUsuarioRepository perUsuarioRepository) {
        this.perUsuarioRepository = perUsuarioRepository;
    }

//    @Override
//    public PerUsuario obtenerPorNITyEmail(String NIT, String email){
//        logger.info("====>>>> PerUsuario obtenerPorNITyEmail NIT:"+NIT+",Email: "+email+")");
//        return   perUsuarioRepository.obtenerPorNITyEmail(NIT,email);
//    }
}
