package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_ESTADO_USUARIO;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO_USUARIO_ACTIVO;

/**
 *
 * @author pc01
 */
@Named("unidadService")
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;
    private final DominioRepository dominioRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Inject
    public UnidadService(UnidadRepository unidadRepository,DominioRepository dominioRepository) {
        this.unidadRepository = unidadRepository;
        this.dominioRepository=dominioRepository;
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
       this.obtenerSecuencia("PER_UNIDAD_SEC");

        PerUnidadPK perUnidadPK=new PerUnidadPK();
        perUnidadPK.setIdPersona(persona.getIdPersona());
        perUnidadPK.setIdUnidad(this.obtenerSecuencia("PER_UNIDAD_SEC"));
        unidad.setPerUnidadPK(perUnidadPK);

        unidad.setEstadoUnidad(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO,PAR_ESTADO_USUARIO_ACTIVO).getParDominioPK().getValor());
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
}