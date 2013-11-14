
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDireccionService;
import bo.gob.mintrabajo.ovt.api.IRepLegalService;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerReplegal;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.DireccionRepository;
import bo.gob.mintrabajo.ovt.repositories.RepLegalRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("repLegalService")
@TransactionAttribute
public class RepLegalService implements IRepLegalService{

    private final RepLegalRepository repLegalRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public RepLegalService(RepLegalRepository repLegalRepository) {
        this.repLegalRepository=repLegalRepository;
    }

    @Override
    public PerReplegal save(PerReplegal replegal) {

        if(replegal.getIdReplegal()==null){
            //Nuevo
            replegal.setIdReplegal(this.obtenerSecuencia("PER_REPLEGAL_SEC"));
        }
        //preguntar q significa este valor
        replegal.setEstadoRepLegal("A");
        replegal.setFechaBitacora(new Date());
        return repLegalRepository.save(replegal);

    }

//    @Override
    public boolean delete(PerReplegal replegal) {
        boolean deleted = false;
        repLegalRepository.delete(replegal);
        return deleted;
    }

     public List<PerReplegal>obtenerPorIdPersona(String idPersona){
         Sort sort=new Sort(Sort.Direction.DESC,"idReplegal");
         return repLegalRepository.obtenerPorIdPersona(idPersona, new PageRequest(0, 10,sort));
     }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
