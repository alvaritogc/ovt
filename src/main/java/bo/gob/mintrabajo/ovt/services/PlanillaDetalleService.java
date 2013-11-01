package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.Util.UtilityData;
import bo.gob.mintrabajo.ovt.api.IPlanillaDetalleService;
import bo.gob.mintrabajo.ovt.entities.DocPlanilla;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaDetalle;
import bo.gob.mintrabajo.ovt.repositories.PlanillaDetalleRepository;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;
import com.csvreader.CsvReader;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 10/29/13
 */
@Named("planillaDetalleService")
@TransactionAttribute
public class PlanillaDetalleService implements IPlanillaDetalleService {

    private final PlanillaRepository planillaRepository;
    private DocPlanillaDetalle docPlanillaDetalle= new DocPlanillaDetalle();
    private List<DocPlanillaDetalle> docPlanillaDetalleList;
    private List<String> errores = new ArrayList<String>();

    @Inject
    public PlanillaDetalleService(PlanillaRepository planillaRepository) {
        this.planillaRepository = planillaRepository;
    }

}