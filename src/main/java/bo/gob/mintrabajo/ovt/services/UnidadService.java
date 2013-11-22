package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;
import org.springframework.data.repository.query.Param;

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
@Named("unidadService")
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;
    private final DominioRepository dominioRepository;
    private final PersonaRepository personaRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Inject
    public UnidadService(UnidadRepository unidadRepository,DominioRepository dominioRepository,PersonaRepository personaRepository) {
        this.unidadRepository = unidadRepository;
        this.dominioRepository=dominioRepository;
        this.personaRepository=personaRepository;
    }
    

    public List<PerUnidad> getAllUnidades() {
        List<PerUnidad> allUnidades;
        allUnidades = unidadRepository.findAll();
        return allUnidades;
    }

    public PerUnidad save(PerUnidad unidad) {

        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.save(unidad);

        return perUnidadEntity;
    }

    public PerUnidad save(PerUnidad unidad,PerPersona persona) {

        System.out.println("===>> unidad "+unidad);
        //Si es una nueva unidad
        if(unidad.getPerUnidadPK()==null){
            PerUnidadPK perUnidadPK=new PerUnidadPK();
            perUnidadPK.setIdPersona(persona.getIdPersona());
            long idUnidad=this.obtenerMaximaUnidad(persona.getIdPersona())+1;
            perUnidadPK.setIdUnidad(idUnidad);
          //  perUnidadPK.setIdUnidad(this.obtenerSecuencia("PER_UNIDAD_SEC"));
            unidad.setPerUnidadPK(perUnidadPK);
        }else{
            if(unidad.getPerUnidadPK().getIdUnidad()==0){
                if(persona.getIdPersona()!=null){
                    persona.setFechaBitacora(new Date());
                    System.out.println("===>> GUARDANDO PERSONA "+persona);
                    persona=personaRepository.save(persona);
                }
            }
        }



        unidad.setPerPersona(persona);
        unidad.setFechaBitacora(new Date());
        unidad.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_ACTIVO).getParDominioPK().getValor());
        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.save(unidad);
        return perUnidadEntity;
    }

    public boolean delete(PerUnidad unidad) {
        boolean deleted = false;
        unidadRepository.delete(unidad);
        return deleted;
    }

    public PerUnidad findById(PerUnidadPK id) {
        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.findOne(id);
        return perUnidadEntity;
    }
    
    
    public List<PerUnidad> buscarPorPersona( String idPersona){
        List<PerUnidad> lista;
        lista = unidadRepository.buscarPorPersona(idPersona);
        return lista;
    }
    
    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }
    
    public List<PerUnidad> listarPorPersona(String idPersona){
        List<PerUnidad> lista;
        try {
            lista = unidadRepository.findByAttribute("idPersona", idPersona, -1, -1);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    public PerUnidad obtienePorId(PerUnidadPK perUnidadPK){
        return unidadRepository.findByPK(perUnidadPK);
    }

    public List<PerUnidad> listarUnidadesSucursales(String idPersona){
        return unidadRepository.findByPerPersona_IdPersona(idPersona);
    }

    /*
     *Obtiene el idUnidad maximo de las unidades de una persona o empresa.
     */
    public long obtenerMaximaUnidad(String idPersona){
           return unidadRepository.obtenerMaximaUnidad(idPersona);
    }
}