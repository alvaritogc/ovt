package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.Definicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import bo.gob.mintrabajo.ovt.repositories.DefincionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@TransactionAttribute
public class DefinicionService implements Definicion{

    private final DefincionRepository repository;

    @Inject
    public DefinicionService(DefincionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocDefinicionEntity guardarDefincion(DocDefinicionEntity docDefinicionEntity){
        return repository.save(docDefinicionEntity);
    }
}
