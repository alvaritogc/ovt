
package bo.gob.mintrabajo.ovt.services;


import bo.gob.mintrabajo.ovt.api.IInfoLaboralService;
import bo.gob.mintrabajo.ovt.entities.PerInfolaboral;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.InfoLaboralRepository;
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
import static bo.gob.mintrabajo.ovt.Util.Sequencias.PER_INFOLABORAL_SEC;

/**
 *
 * @author pc01
 */
@Named("infoLaboralService")
@TransactionAttribute
public class InfoLaboralService implements IInfoLaboralService {

    private final InfoLaboralRepository infoLaboralRepository;
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public InfoLaboralService(InfoLaboralRepository infoLaboralRepository,DominioRepository dominioRepository) {
        this.infoLaboralRepository = infoLaboralRepository;
        this.dominioRepository=dominioRepository;
    }


    /*
     * Este metodo realiza un INSERT o UPDATE de un registro de la tabla
     * PAR_INFO_LABORAL
     */
    @Override
    public PerInfolaboral save(PerInfolaboral infolaboral,String registroBitacora,PerUnidad unidad) {

        if(infolaboral.getIdInfolaboral()==null){
            //Nuevo
            infolaboral.setIdInfolaboral(this.obtenerSecuencia(PER_INFOLABORAL_SEC));
            infolaboral.setEstadoInfolaboral(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            infolaboral.setPerUnidad(unidad);
            infolaboral.setFechaBitacora(new Date());
            infolaboral.setRegistroBitacora(registroBitacora);
            return infoLaboralRepository.save(infolaboral);
        } else{
            // - Cambiar el estado de infoLaboral
            PerInfolaboral infoLaboralHistorico=infoLaboralRepository.findOne(infolaboral.getIdInfolaboral());
            infoLaboralHistorico.setEstadoInfolaboral(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_INACTIVO).getParDominioPK().getValor());
            infoLaboralHistorico.setFechaBitacora(new Date());
            infoLaboralHistorico.setRegistroBitacora(registroBitacora);
            infoLaboralRepository.save(infoLaboralHistorico);

            // - Crear un nuevo registro con los nuevos cambios
            infolaboral.setIdInfolaboral(this.obtenerSecuencia(PER_INFOLABORAL_SEC));
            infolaboral.setEstadoInfolaboral(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            infolaboral.setPerUnidad(unidad);
            infolaboral.setFechaBitacora(new Date());
            infolaboral.setRegistroBitacora(registroBitacora);
            return infoLaboralRepository.save(infolaboral);

        }
    }

    /*
    * Este metodo realiza un INSERT o UPDATE de un registro de la tabla
    * PAR_ACTIVIDAD_ECONOMICA   y la tabla PER_ACTIVIDAD
    * @Param registroBitacora.- Es el usuario de session
    */
    /*@Override
    public void save(ParActividadEconomica actividadEconomica,PerUnidad unidad,String registroBitacora){

        boolean nuevo=false;
        PerActividad actividad=new PerActividad();
        actividadEconomica.setRegistroBitacora(registroBitacora);
        actividadEconomica.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
        actividadEconomica.setFechaBitacora(new Date());

        if(actividadEconomica.getIdActividadEconomica()==null){
            actividadEconomica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));

            actividad.setFechaBitacora(new Date());
            actividad.setRegistroBitacora(registroBitacora);
            actividad.setPerUnidad(unidad);

            actividad.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
            actividad.setIdActividad(this.obtenerSecuencia("PER_ACTIVIDAD_SEC"));

            nuevo=true;
        }

        actividadEconomica= actividadEconomicaRepository.save(actividadEconomica);
        if(nuevo) {
            actividad.setIdActividadEconomica(actividadEconomica);
            actividadRepository.save(actividad);
        }

    }




    @Override
    public ParActividadEconomica findByIdActividadEconomica(Long idActividadEconomica){
        try{
            return actividadEconomicaRepository.findOne(idActividadEconomica);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }*/

     /*
     *  Obtiene una lista con las informacionLaboral de una empresa o persona, pero
     *  que se encuentren en estado ACTIVO.
     *
      */
    @Override
    public List<PerInfolaboral>findByPerUnidad(PerUnidad unidad){
        Sort sort=new Sort(Sort.Direction.DESC,"idInfolaboral");
        return infoLaboralRepository.obtenerPorIdPersonaYIdUnidad(unidad.getPerUnidadPK().getIdPersona(), unidad.getPerUnidadPK().getIdUnidad(), new PageRequest(0, 500,sort));
    }

    public  List<PerInfolaboral>obtenerPorIdPersona(String idPersona){
        return infoLaboralRepository.obtenerPorIdPersona(idPersona);
    }

//    @Override
    public boolean delete(PerInfolaboral infolaboral) {
        boolean deleted = false;
        infoLaboralRepository.delete(infolaboral);
        return deleted;
    }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

    @Override
    public PerInfolaboral obtienePorUnidadPKEstado(PerUnidadPK perUnidadPK, String estado){
        return infoLaboralRepository.findByPerUnidad_PerUnidadPKAndEstadoInfolaboral(perUnidadPK, estado);
    }
    
    /////////////////////////////////LUIS
    @Override
    public List<PerInfolaboral> obtienePorIdPersonaAndIdUnidad(String idPersona, Long idUnidad){
        return infoLaboralRepository.obtenerPorIdPersonaAndIdUnidad(idPersona, idUnidad);
    }
}
