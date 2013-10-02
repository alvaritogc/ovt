package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
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
@Named
@TransactionAttribute
public class UnidadService implements IUnidadService{
    private final UnidadRepository unidadRepository;

    @Inject
    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }
    
    @Override
    public List<PerUnidadEntity> getAllUnidades() {
        List<PerUnidadEntity> allUnidades;

        try {
            allUnidades = unidadRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allUnidades = null;
        }
        return allUnidades;
    }
    
    @Override
    public PerUnidadEntity save(PerUnidadEntity unidad) {
        PerUnidadEntity perUnidadEntity;

        try {
            perUnidadEntity = unidadRepository.save(unidad);
        } catch (Exception e) {
            e.printStackTrace();
            perUnidadEntity = null;
        }

        return perUnidadEntity;
    }

    @Override
    public boolean delete(PerUnidadEntity unidad) {
        boolean deleted = false;

        try {
            unidadRepository.delete(unidad);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public PerUnidadEntity findById(BigDecimal id) {
        PerUnidadEntity perUnidadEntity;

        try {
            perUnidadEntity = unidadRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            perUnidadEntity = null;
        }

        return perUnidadEntity;
    }
    
}
