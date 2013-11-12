package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.EntidadRepository;
import java.math.BigDecimal;
import java.util.Date;

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
    public ParEntidad saveEntidad(ParEntidad entidad,String REGISTRO_BITACORA,PerUnidad unidad, boolean evento){                       
      ParEntidad pe= new ParEntidad();
      if(entidad.getIdEntidad()==null && !evento ){
          pe.setIdEntidad(this.valorSecuencia("PAR_ENTIDAD_SEC"));
      }else{
          pe = entidadRepository.findOne(entidad.getIdEntidad());
      }
      pe.setDescripcion(entidad.getDescripcion());
      pe.setTipoEntidad(entidad.getTipoEntidad());
      pe.setCodigo(entidad.getCodigo());

        //pe.setPerUnidad(unidad);
        pe.setFechaBitacora(new Date());
        pe.setRegistroBitacora(REGISTRO_BITACORA);
            
        try {
            entidad = entidadRepository.save(pe);
        } catch (Exception e) {
            e.printStackTrace();
            entidad = null;
        }
        return entidad;
    }
    
    @Override
    public boolean deleteEntidad(ParEntidad entidad){
        boolean deleted = false;
        try {
            ParEntidad pe = entidadRepository.findOne(entidad.getIdEntidad());
            entidadRepository.delete(pe);
            entidadRepository.flush();
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
