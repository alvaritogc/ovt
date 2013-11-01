package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("unidadService")
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    @Inject
    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
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