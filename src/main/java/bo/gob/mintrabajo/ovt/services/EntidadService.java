package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IEntidadService;
import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.repositories.EntidadRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("entidadService")
@TransactionAttribute
public class EntidadService implements IEntidadService {

    private final EntidadRepository entidadRepository;

    @Inject
    public EntidadService(EntidadRepository entidadRepository) {
        this.entidadRepository = entidadRepository;
    }

}
