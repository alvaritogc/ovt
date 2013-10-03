package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinariosService;
import bo.gob.mintrabajo.ovt.entities.DocBinariosEntity;
import bo.gob.mintrabajo.ovt.repositories.BinariosRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named
@TransactionAttribute
public class BinariosService implements IBinariosService{

    private final BinariosRepository repository;

    @Inject
    public BinariosService(BinariosRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocBinariosEntity guardarBinario(DocBinariosEntity docBinariosEntity){
        return repository.save(docBinariosEntity);
    }
}
