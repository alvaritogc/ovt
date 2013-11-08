
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDireccionService;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.DireccionRepository;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import org.springframework.data.domain.PageRequest;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("direccionService")
@TransactionAttribute
public class DireccionService implements IDireccionService{

    private final DireccionRepository direccionRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public DireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }



    @Override
    public PerDireccion save(PerDireccion direccion) {

        return direccionRepository.save(direccion);

    }

//    @Override
    public boolean delete(PerDireccion direccion) {
        boolean deleted = false;
        direccionRepository.delete(direccion);
        return deleted;
    }

     public List<PerDireccion>findByPerUnidad(PerUnidad unidad){
         return direccionRepository.findByPerUnidad(unidad,new PageRequest(1,10));
     }

}
