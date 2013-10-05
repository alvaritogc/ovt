
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.entities.ParDominioEntity;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;

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
@Named("dominioService")
@TransactionAttribute
public class DominioService implements IDominioService{
    
    private final DominioRepository dominioRepository;

    @Inject
    public DominioService(DominioRepository dominioRepository) {
        this.dominioRepository = dominioRepository;
    }

    @Override
    public List<ParDominioEntity> getAllDominios() {
        List<ParDominioEntity> allModulos;

        try {
            allModulos = dominioRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allModulos = null;
        }
        return allModulos;
    }

    @Override
    public ParDominioEntity save(ParDominioEntity dominio) {
        ParDominioEntity parDominioEntity;

        try {
            parDominioEntity = dominioRepository.save(dominio);
        } catch (Exception e) {
            e.printStackTrace();
            parDominioEntity = null;
        }

        return parDominioEntity;
    }

    @Override
    public boolean delete(ParDominioEntity dominio) {
        boolean deleted = false;

        try {
            dominioRepository.delete(dominio);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }

        return deleted;
    }

    @Override
    public ParDominioEntity findById(BigDecimal id) {
        ParDominioEntity parDominioEntity;

        try {
            parDominioEntity = dominioRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            parDominioEntity = null;
        }

        return parDominioEntity;
    }
    
}
