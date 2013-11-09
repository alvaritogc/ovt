package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.repositories.EntidadRepository;
import java.math.BigDecimal;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("entidadService")
@TransactionAttribute
public class EntidadService implements IEntidadService {

    private final EntidadRepository entidadRepository;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public EntidadService(EntidadRepository entidadRepository) {
        this.entidadRepository = entidadRepository;
    }
    
    @Override
    public List<ParEntidad> listaEntidad(){
        List<ParEntidad> lista;
        try {
            lista = entidadRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public List<ParEntidad> listaEntidadPorOrden(){
        List<ParEntidad> lista;
        try {
            lista = entidadRepository.listaPorOrdenDescripcionDeEntidad();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public ParEntidad saveEntidad(ParEntidad entidad){               
        ParEntidad parEntidad;
        try {
            parEntidad = entidadRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
            parEntidad = null;
        }
        return parEntidad;
    }
    
    @Override
    public void guardarEliminar(String sw,ParEntidad entidad){
        ParEntidad parEntidad;
        try {
            if(sw.equals("a") ){
                entidad.setIdEntidad(this.valorSecuencia("PAR_ENTIDAD_SEC"));
                parEntidad = entidadRepository.save(entidad);
            }
            if(sw.equals("b")){
                
                entidadRepository.save(entidad);
            }
            if(sw.equals("c")){
                entidadRepository.delete(entidad);
            }
        } catch (Exception e) {
            
        }
    }
    
    @Override
    public boolean deleteEntidad(ParEntidad entidad){
        boolean deleted = false;
        try {
            System.out.println("====> " + entidad.getDescripcion() +" === "+entidad.getCodigo() + " === " + entidad.getIdEntidad());
            entidadRepository.delete(entidad);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }
        return deleted;
    }

    public ParEntidad buscaPorId(Long idEntidad){
         return entidadRepository.findOne(idEntidad);
    }
    
    
    public Long valorSecuencia (String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }
}
