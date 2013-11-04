
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad;
import bo.gob.mintrabajo.ovt.repositories.UsuarioUnidadRepository;
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
@Named("usuarioUnidadService")
@TransactionAttribute
public class UsuarioUnidadService implements IUsuarioUnidadService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioUnidadService.class);
    private final UsuarioUnidadRepository usuarioUnidadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public UsuarioUnidadService(UsuarioUnidadRepository usuarioUnidadRepository) {
        this.usuarioUnidadRepository = usuarioUnidadRepository;
    }

    @Override
    public PerUsuarioUnidad obtenerPorNITyEmail(String NIT, String email){
        logger.info("====>>>> PerUsuario obtenerPorNITyEmail NIT:"+NIT+",Email: "+email+")");
        return  usuarioUnidadRepository.obtenerPorNITyEmail(NIT,email);
    }
}
