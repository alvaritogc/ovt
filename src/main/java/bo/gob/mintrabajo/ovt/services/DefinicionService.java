package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import bo.gob.mintrabajo.ovt.repositories.DefinicionRepository;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;


@Named("definitionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {
    
    private final DefinicionRepository repository;

    @Inject
    public DefinicionService(DefinicionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocDefinicionEntity guardarDefincion(DocDefinicionEntity docDefinicionEntity){
        return repository.save(docDefinicionEntity);
    }

    @Override
    public long getSize() {
        return repository.count();
    }
    
    @Override
    public List<DocDefinicionEntity> getAllDefinicion() {
        List<DocDefinicionEntity> lista;

        try {
            lista = repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public DocDefinicionEntity save(DocDefinicionEntity definicion) {
        DocDefinicionEntity entity;
        try {
            entity = repository.save(definicion);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public boolean delete(DocDefinicionEntity definicion) {
        boolean deleted = false;
        try {
            repository.delete(definicion);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public DocDefinicionEntity findById(BigDecimal id) {
        DocDefinicionEntity entity;

        try {
            entity = repository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }
}
