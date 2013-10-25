package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ITransicionService;
import bo.gob.mintrabajo.ovt.entities.DocTransicion;
import bo.gob.mintrabajo.ovt.repositories.TransicionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/10/13
 */

@Named("transicionService")
@TransactionAttribute
public class TransicionService implements ITransicionService {

    private final TransicionRepository transicionRepository;
    private List <DocTransicion> listaTransicion= new ArrayList<DocTransicion>();

    @Inject
    public TransicionService(TransicionRepository transicionRepository) {
        this.transicionRepository = transicionRepository;
    }

//    @Override
   
}
