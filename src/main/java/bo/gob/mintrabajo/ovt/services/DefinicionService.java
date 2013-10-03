package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import bo.gob.mintrabajo.ovt.repositories.DefincionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;


@Named("definitionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {

    private final DefincionRepository repository;

    @Inject
    public DefinicionService(DefincionRepository repository) {
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
}
