package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/12/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("logImpresionService")
@TransactionAttribute
public class LogImpresionService implements ILogImpresionService {
    private final LogImpresionRepository logImpresionRepository;

    @Inject
    public LogImpresionService(LogImpresionRepository logImpresionRepository) {
        this.logImpresionRepository = logImpresionRepository;
    }

    @Override
    public void guarda(List<DocLogImpresionEntity> lista){
        int a=2000;
        for (DocLogImpresionEntity docLogImpresionEntity:lista){
             docLogImpresionEntity.setIdDoclogimpresion(a++);
            logImpresionRepository.save(docLogImpresionEntity);
        }

    }
}
