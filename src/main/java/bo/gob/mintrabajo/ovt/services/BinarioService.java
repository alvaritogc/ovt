package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("binService")
@TransactionAttribute
public class BinarioService implements IBinarioService{

    private final BinarioRepository repository;

    @Inject
    public BinarioService(BinarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocBinarioEntity guardarBinario(DocBinarioEntity docBinariosEntity){
        return repository.save(docBinariosEntity);
    }

    @Override
    public Long contar(){
        return repository.count();
    }
}
