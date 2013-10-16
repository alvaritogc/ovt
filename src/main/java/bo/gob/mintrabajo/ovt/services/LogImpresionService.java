package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/12/13
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
        Long a=2000L;
        for (DocLogImpresionEntity docLogImpresionEntity:lista){
             docLogImpresionEntity.setIdDoclogimpresion(a++);
            logImpresionRepository.save(docLogImpresionEntity);
        }

    }
}
