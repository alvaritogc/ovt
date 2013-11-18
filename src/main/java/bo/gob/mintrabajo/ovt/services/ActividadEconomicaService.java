
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IActividadEconomicaService;
import bo.gob.mintrabajo.ovt.api.IActividadService;
import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.ActividadEconomicaRepository;
import bo.gob.mintrabajo.ovt.repositories.ActividadRepository;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;

/**
 *
 * @author pc01
 */
@Named("actividadEconomicaService")
@TransactionAttribute
public class ActividadEconomicaService implements IActividadEconomicaService {

    private final ActividadEconomicaRepository actividadEconomicaRepository;
    private final DominioRepository dominioRepository;
    private final ActividadRepository actividadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public ActividadEconomicaService(ActividadEconomicaRepository actividadEconomicaRepository,DominioRepository dominioRepository,ActividadRepository actividadRepository) {
        this.actividadEconomicaRepository = actividadEconomicaRepository;
        this.dominioRepository=dominioRepository;
        this.actividadRepository=actividadRepository;
    }


    /*
     * Este metodo realiza un INSERT o UPDATE de un registro de la tabla
     * PAR_ACTIVIDAD_ECONOMICA
     */
    @Override
    public ParActividadEconomica save(ParActividadEconomica actividadEconomica) {

        if(actividadEconomica.getIdActividadEconomica()==null){
            //Nuevo
            actividadEconomica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));
        }
        actividadEconomica.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
        actividadEconomica.setFechaBitacora(new Date());
        return actividadEconomicaRepository.save(actividadEconomica);
    }

    /*
    * Este metodo realiza un INSERT o UPDATE de un registro de la tabla
    * PAR_ACTIVIDAD_ECONOMICA   y la tabla PER_ACTIVIDAD
    * @Param registroBitacora.- Es el usuario de session
    */
    @Override
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

    /*
  *
  * Este metodo obtiene una lista de las actividades que tienen codigo = 0.
  *
   */
    public List<BigDecimal> obtenerActividadEconomicaParaRegistro(){
        List<BigDecimal>lista ;
        lista = (List<BigDecimal>)entityManager.createNativeQuery("SELECT   a.id_actividad_economica\n" +
                "      FROM   par_actividad_economica a\n" +
                "      where level = '2'\n" +
                " START WITH   a.id_actividad_economica2 is null\n" +
                " CONNECT BY   PRIOR a.id_actividad_economica = a.id_actividad_economica2").getResultList();
        entityManager.close();
        return lista;
    }

}
