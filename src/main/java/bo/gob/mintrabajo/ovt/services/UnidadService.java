package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.ObligacionCalendarioRepository;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;

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
@Named("unidadService")
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;
    private final DominioRepository dominioRepository;
    private final PersonaRepository personaRepository;
    private final ObligacionCalendarioRepository obligacionCalendarioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Inject
    public UnidadService(UnidadRepository unidadRepository,DominioRepository dominioRepository,PersonaRepository personaRepository, ObligacionCalendarioRepository obligacionCalendarioRepository) {
        this.unidadRepository = unidadRepository;
        this.dominioRepository=dominioRepository;
        this.personaRepository=personaRepository;
        this.obligacionCalendarioRepository=obligacionCalendarioRepository;
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

    public PerUnidad save(PerUnidad unidad,PerPersona persona,String registroBitacora) {

        //Si es una nueva unidad
        if(unidad.getPerUnidadPK()==null){
            //Setea clave compuesta
            PerUnidadPK perUnidadPK=new PerUnidadPK();
            perUnidadPK.setIdPersona(persona.getIdPersona());
            long idUnidad=this.obtenerMaximaUnidad(persona.getIdPersona())+1;
            perUnidadPK.setIdUnidad(idUnidad);
            unidad.setPerUnidadPK(perUnidadPK);

            unidad.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_ACTIVO).getParDominioPK().getValor());
            unidad.setPerPersona(persona);
            unidad.setFechaBitacora(new Date());
            unidad.setRegistroBitacora(registroBitacora);
            unidad = unidadRepository.save(unidad);
            return unidad;

        }else{
            //Solo para la unidad 0, se puede modifcar la tabla PerPersona
            if(unidad.getPerUnidadPK().getIdUnidad()==0){
                if(persona.getIdPersona()!=null){
/*
                    PerPersona personaHistorico=personaRepository.findOne(persona.getIdPersona());
                    personaHistorico.setRegistroBitacora(registroBitacora);
                    personaHistorico.setFechaBitacora(new Date());
                    personaHistorico=personaRepository.save(persona);
*/
                    persona.setRegistroBitacora(registroBitacora);
                    persona.setFechaBitacora(new Date());
                    persona=personaRepository.save(persona);
                }
            }

  /*          // - Cambiar el estado de la version anterior
            PerUnidad unidadHistorico=unidadRepository.obtenerPorIdPersonaIdUnidad(persona.getIdPersona(),unidad.getPerUnidadPK().getIdUnidad());
            unidadHistorico.setFechaBitacora(new Date());
            unidadHistorico.setRegistroBitacora(registroBitacora);
            unidadHistorico.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_INACTIVO).getParDominioPK().getValor());
            PerUnidadPK perUnidadPK=new PerUnidadPK();
            perUnidadPK.setIdUnidad(unidad.getPerUnidadPK().getIdUnidad()*(-1));
            perUnidadPK.setIdPersona(persona.getIdPersona());
            unidadHistorico.setPerUnidadPK(perUnidadPK);
            unidadRepository.save(unidadHistorico);

            // - Crear un nuevo registro con los cambios

            //Setea clave compuesta
            perUnidadPK=new PerUnidadPK();
            perUnidadPK.setIdPersona(persona.getIdPersona());
            long idUnidad=unidadHistorico.getPerUnidadPK().getIdUnidad();
            perUnidadPK.setIdUnidad(idUnidad);
            unidad.setPerUnidadPK(perUnidadPK);

            unidad.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_ACTIVO).getParDominioPK().getValor());
            unidad.setPerPersona(persona);
            unidad.setFechaBitacora(new Date());
            unidad.setRegistroBitacora(registroBitacora);
            unidad = unidadRepository.save(unidad);
            return unidad;*/
        }



        unidad.setPerPersona(persona);
        unidad.setFechaBitacora(new Date());
        unidad.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_UNIDAD,PAR_ESTADO_UNIDAD_ACTIVO).getParDominioPK().getValor());
        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.save(unidad);
        System.out.println("+++++++++++++++++++++ >>> SERVICE <<<<<<<<< ++++++++++");
        System.out.println("********** ******* >>> unidad "+perUnidadEntity);
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
    
    
    public List<PerUnidad> buscarPorPersona(String idPersona){
        List<PerUnidad> lista;
        lista=unidadRepository.buscarPorPersona(idPersona);
       // lista = unidadRepository.findByPerPersona_IdPersona(idPersona);
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

    public List<PerUnidad> listarUnidadesSucursalesPorFecha(String idPersona, Date fechaHasta, Date fechaPlazo2){
        return unidadRepository.listarSucursalesPorPersonaYUnidadSegunFecha(idPersona, fechaHasta, fechaPlazo2);
    }

    /*
     *Obtiene el idUnidad maximo de las unidades de una persona o empresa.
     */
    public long obtenerMaximaUnidad(String idPersona){
           return unidadRepository.obtenerMaximaUnidad(idPersona);
    }



}