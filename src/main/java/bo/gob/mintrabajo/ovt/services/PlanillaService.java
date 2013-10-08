package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("planillaService")
@TransactionAttribute
public class PlanillaService implements IPlanillaService {
    private final PlanillaRepository planillaRepository;

    @Inject
    public PlanillaService(PlanillaRepository planillaRepository) {
        this.planillaRepository = planillaRepository;
    }
}
