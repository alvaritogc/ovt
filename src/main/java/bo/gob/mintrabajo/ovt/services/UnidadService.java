package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("unidadService")
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;

    @Inject
    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }
    
//    @Override
    public List<PerUnidad> getAllUnidades() {
        List<PerUnidad> allUnidades;
        allUnidades = unidadRepository.findAll();
        return allUnidades;
    }
    
//    @Override
    public PerUnidad save(PerUnidad unidad) {
        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.save(unidad);
        return perUnidadEntity;
    }

//    @Override
    public boolean delete(PerUnidad unidad) {
        boolean deleted = false;
        unidadRepository.delete(unidad);
        return deleted;
    }

//    @Override
    public PerUnidad findById(PerUnidadPK id) {
        PerUnidad perUnidadEntity;
        perUnidadEntity = unidadRepository.findOne(id);
        return perUnidadEntity;
    }
    
    @Override
    public List<PerUnidad> buscarPorPersona( String idPersona){
        List<PerUnidad> lista;
        lista = unidadRepository.buscarPorPersona(idPersona);
        return lista;
    }
    
}
