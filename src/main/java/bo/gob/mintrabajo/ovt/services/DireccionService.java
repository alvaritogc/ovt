
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
import org.springframework.data.domain.Sort;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
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

        if(direccion.getIdDireccion()==null){
            //Nuevo
            direccion.setIdDireccion(this.obtenerSecuencia("PER_DIRECCION_SEC"));
        }
        //preguntar q significa este valor
        direccion.setEstado("0");
        direccion.setFechaBitacora(new Date());
        return direccionRepository.save(direccion);

    }

//    @Override
    public boolean delete(PerDireccion direccion) {
        boolean deleted = false;
        direccionRepository.delete(direccion);
        return deleted;
    }

     public List<PerDireccion>findByPerUnidad(PerUnidad unidad){

         //return direccionRepository.findByPerUnidad(unidad,new PageRequest(1,10));
         Sort sort=new Sort(Sort.Direction.DESC,"idDireccion");
         System.out.println("=====>>>>  idPersona "+unidad.getPerUnidadPK().getIdPersona());
         System.out.println("=====>>>>  idUnidad "+unidad.getPerUnidadPK().getIdUnidad());
         List<PerDireccion>direccion=direccionRepository.obtenerPorIdPersonaYIdUnidad(unidad.getPerUnidadPK().getIdPersona(), unidad.getPerUnidadPK().getIdUnidad(), new PageRequest(1, 10,sort));
         System.out.println("====>>> direccion encontrados: "+direccionRepository.findAll(new PageRequest(0, 50)));
         return direccionRepository.obtenerPorIdPersonaYIdUnidad(unidad.getPerUnidadPK().getIdPersona(), unidad.getPerUnidadPK().getIdUnidad(), new PageRequest(0, 10,sort));
     }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
