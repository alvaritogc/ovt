
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDireccionService;
import bo.gob.mintrabajo.ovt.api.IRepLegalService;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerReplegal;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.DireccionRepository;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
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

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;
import static bo.gob.mintrabajo.ovt.Util.Sequencias.*;

/**
 *
 * @author pc01
 */
@Named("repLegalService")
@TransactionAttribute
public class RepLegalService implements IRepLegalService{

    private final RepLegalRepository repLegalRepository;
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public RepLegalService(RepLegalRepository repLegalRepository,DominioRepository dominioRepository) {
        this.repLegalRepository=repLegalRepository;
        this.dominioRepository=dominioRepository;
    }

    @Override
    public PerReplegal save(PerReplegal replegal,String registroBitacora,PerUnidad unidad) {

        System.out.println("======>>> CAMBIANDO unidad "+unidad);

        if(replegal.getIdReplegal()==null){
            //Nuevo
            replegal.setIdReplegal(this.obtenerSecuencia(PER_REPLEGAL_SEC));

            replegal.setPerUnidad(unidad);
            replegal.setEstadoRepLegal(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            replegal.setFechaBitacora(new Date());
            replegal.setRegistroBitacora(registroBitacora);
            return repLegalRepository.save(replegal);
        }else{
            // - Cambiar el estado del repLegal
            PerReplegal replegalAnterior=repLegalRepository.findOne(replegal.getIdReplegal());
            replegalAnterior.setEstadoRepLegal(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_INACTIVO).getParDominioPK().getValor());
            replegalAnterior.setFechaBitacora(new Date());
            replegalAnterior.setRegistroBitacora(registroBitacora);
            repLegalRepository.save(replegalAnterior);

            // - Crear un nuevo registro con los nuevos cambios
            replegal.setIdReplegal(this.obtenerSecuencia(PER_REPLEGAL_SEC));
           // replegal.setPerUnidad(replegalAnterior.getPerUnidad());
            replegal.setPerUnidad(unidad);
            replegal.setEstadoRepLegal(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            replegal.setFechaBitacora(new Date());
            replegal.setRegistroBitacora(registroBitacora);
            return repLegalRepository.save(replegal);

        }
    }

//    @Override
    public boolean delete(PerReplegal replegal) {
        boolean deleted = false;
        repLegalRepository.delete(replegal);
        return deleted;
    }

    // Obtiene una lista con los representantes legales activos de una persona u empleador.
     public List<PerReplegal>obtenerPorIdPersona(String idPersona){
         Sort sort=new Sort(Sort.Direction.DESC,"idReplegal");
         return repLegalRepository.obtenerPorIdPersona(idPersona, new PageRequest(0, 500,sort));
     }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
