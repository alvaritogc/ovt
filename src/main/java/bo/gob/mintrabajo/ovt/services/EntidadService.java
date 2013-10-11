package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;
import bo.gob.mintrabajo.ovt.repositories.EntidadRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */

@Named("entidadService")
@TransactionAttribute
public class EntidadService implements IEntidadService {

    private final EntidadRepository entidadRepository;

    @Inject
    public EntidadService(EntidadRepository entidadRepository) {
        this.entidadRepository = entidadRepository;
    }

    @Override
    public List<ParEntidadEntity> getEntidadLista(){
            return entidadRepository.findByAttribute("codigo","CNS", -1, -1);
    }
}
