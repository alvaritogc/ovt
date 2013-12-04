package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IAlertaService;
import bo.gob.mintrabajo.ovt.entities.DocAlerta;
import bo.gob.mintrabajo.ovt.repositories.AlertaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Renato Velasquez
 * Date: 12/4/13
 */
@Named("alertaService")
@TransactionAttribute
public class AlertaService implements IAlertaService {
    private final AlertaRepository alertaRepository;

    @Inject
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public DocAlerta guarda(DocAlerta docAlerta){
        return alertaRepository.save(docAlerta);
    }
}
