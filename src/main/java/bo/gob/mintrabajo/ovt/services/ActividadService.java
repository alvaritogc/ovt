
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IActividadService;
import bo.gob.mintrabajo.ovt.api.IDireccionService;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.ActividadRepository;
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
import java.util.Date;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_ESTADO_UNIDAD;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO_UNIDAD_ACTIVO;

/**
 *
 * @author pc01
 */
@Named("actividadService")
@TransactionAttribute
public class ActividadService implements IActividadService {

    private final ActividadRepository actividadRepository;
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public ActividadService(ActividadRepository actividadRepository,DominioRepository dominioRepository) {
        this.actividadRepository = actividadRepository;
        this.dominioRepository=dominioRepository;
    }


    @Override
    public PerActividad save(PerActividad actividad) {

        if(actividad.getIdActividad()==null){
            //Nuevo
            actividad.setIdActividad(this.obtenerSecuencia("PER_ACTIVIDAD_SEC"));
        }
        //preguntar q significa este valor
        actividad.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_ACTIVO).getParDominioPK().getValor());
        actividad.setFechaBitacora(new Date());
        return actividadRepository.save(actividad);

    }

    @Override
    public List<PerActividad>obtenerPorIdPersonaYIdUnidad(PerUnidad unidad){
        Sort sort=new Sort(Sort.Direction.DESC,"idActividad");
        return actividadRepository.obtenerPorIdPersonaYIdUnidad(unidad.getPerUnidadPK().getIdPersona(), unidad.getPerUnidadPK().getIdUnidad(), new PageRequest(0, 10,sort));
    }

//    @Override
    public boolean delete(PerActividad actividad) {
        boolean deleted = false;
        actividadRepository.delete(actividad);
        return deleted;
    }



    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
