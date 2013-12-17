package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaDetalleService;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaDetalle;
import bo.gob.mintrabajo.ovt.repositories.PlanillaDetalleRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Renato Velasquez
 * Date: 10/29/13
 */
@Named("planillaDetalleService")
@TransactionAttribute
public class PlanillaDetalleService implements IPlanillaDetalleService {

    private final PlanillaDetalleRepository planillaDetalleRepository;

    @Inject
    public PlanillaDetalleService(PlanillaDetalleRepository planillaDetalleRepository) {
        this.planillaDetalleRepository = planillaDetalleRepository;
    }

    public DocPlanillaDetalle guardar(DocPlanillaDetalle docPlanillaDetalle){
           return planillaDetalleRepository.save(docPlanillaDetalle);
    }

//    public Long obtieneMenores18(){
//        return planillaDetalleRepository.obtieneMenores18();
//    }
}