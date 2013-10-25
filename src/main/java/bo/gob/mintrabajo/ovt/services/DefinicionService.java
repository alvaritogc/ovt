package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import bo.gob.mintrabajo.ovt.repositories.DefinicionRepository;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

@Named("definicionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {

    private final DefinicionRepository repository;

    @Inject
    public DefinicionService(DefinicionRepository repository) {
        this.repository = repository;
    }

//    @Override
    public DocDefinicion guardarDefincion(DocDefinicion docDefinicion) {
        return repository.save(docDefinicion);
    }

//    @Override
    public long getSize() {
        return repository.count();
    }

//    @Override
    public List<DocDefinicion> getAllDefinicion() {
        List<DocDefinicion> lista;
        lista = repository.findAll();
        return lista;
    }

//    @Override
    public DocDefinicion save(DocDefinicion definicion) {
        DocDefinicion entity;
        entity = repository.save(definicion);
        return entity;
    }

//    @Override
    public boolean delete(DocDefinicion definicion) {
        boolean deleted = false;
        repository.delete(definicion);

        return deleted;
    }
}
