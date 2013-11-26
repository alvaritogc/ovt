
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

import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_ESTADO;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO_ACTIVO;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO_INACTIVO;
import static bo.gob.mintrabajo.ovt.Util.Sequencias.PER_DIRECCION_SEC;

/**
 *
 * @author pc01
 */
@Named("direccionService")
@TransactionAttribute
public class DireccionService implements IDireccionService{

    private final DireccionRepository direccionRepository;
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public DireccionService(DireccionRepository direccionRepository,DominioRepository dominioRepository) {
        this.direccionRepository = direccionRepository;
        this.dominioRepository=dominioRepository;
    }



    @Override
    public PerDireccion save(PerDireccion direccion,String registroBitacora,PerUnidad unidad) {

        if(direccion.getIdDireccion()==null){
            //Nuevo
            direccion.setIdDireccion(this.obtenerSecuencia(PER_DIRECCION_SEC));
            direccion.setPerUnidad(unidad);
            direccion.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            direccion.setFechaBitacora(new Date());
            direccion.setRegistroBitacora(registroBitacora);
           return direccionRepository.save(direccion);
        }else{
            // - Cambia el estado de direccion
            PerDireccion direccionHistorico=direccionRepository.findOne(direccion.getIdDireccion());
            direccionHistorico.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_INACTIVO).getParDominioPK().getValor());
            direccionHistorico.setFechaBitacora(new Date());
            direccionHistorico.setRegistroBitacora(registroBitacora);
            direccionRepository.save(direccionHistorico);

            //- Crear un nuevo registro con los nuevos cambios
            direccion.setIdDireccion(this.obtenerSecuencia(PER_DIRECCION_SEC));
            direccion.setPerUnidad(unidad);
            direccion.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            direccion.setFechaBitacora(new Date());
            direccion.setRegistroBitacora(registroBitacora);
            return direccionRepository.save(direccion);
        }
/*        direccion.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
        direccion.setFechaBitacora(new Date());
        return direccionRepository.save(direccion);*/

    }

//    @Override
    public boolean delete(PerDireccion direccion) {
        boolean deleted = false;
        direccionRepository.delete(direccion);
        return deleted;
    }

     public List<PerDireccion>obtenerPorIdPersonaYIdUnidad(String idPersona,long idUnidad){
         Sort sort=new Sort(Sort.Direction.DESC,"idDireccion");
         return direccionRepository.obtenerPorIdPersonaYIdUnidad(idPersona, idUnidad, new PageRequest(0, 10,sort));
     }

    /*
     *Obtiene una lista con las direcciones activas de una persona
     */
    public List<PerDireccion>obtenerPorIdPersona(String idPersona ){
        Sort sort=new Sort(Sort.Direction.DESC,"idDireccion");
        return direccionRepository.obtenerPorIdPersona(idPersona, new PageRequest(0, 10,sort));
    }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

}
