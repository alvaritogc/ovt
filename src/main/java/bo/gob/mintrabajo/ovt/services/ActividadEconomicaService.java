
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IActividadEconomicaService;
import bo.gob.mintrabajo.ovt.api.IActividadService;
import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.repositories.ActividadEconomicaRepository;
import bo.gob.mintrabajo.ovt.repositories.ActividadRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author pc01
 */
@Named("actividadEconomicaService")
@TransactionAttribute
public class ActividadEconomicaService implements IActividadEconomicaService {

    private final ActividadEconomicaRepository actividadEconomicaRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public ActividadEconomicaService(ActividadEconomicaRepository actividadEconomicaRepository) {
        this.actividadEconomicaRepository = actividadEconomicaRepository;
    }


    @Override
    public ParActividadEconomica save(ParActividadEconomica actividadEconomica) {

        if(actividadEconomica.getIdActividadEconomica()==null){
            //Nuevo
            actividadEconomica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));
        }
        //preguntar q significa este valor
        actividadEconomica.setEstado("A");
        actividadEconomica.setFechaBitacora(new Date());
        return actividadEconomicaRepository.save(actividadEconomica);

    }

//    @Override
    public boolean delete(ParActividadEconomica actividadEconomica) {
        boolean deleted = false;
        actividadEconomicaRepository.delete(actividadEconomica);
        return deleted;
    }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
