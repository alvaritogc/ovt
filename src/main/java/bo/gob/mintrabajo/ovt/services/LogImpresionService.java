package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("logImpresionService")
@TransactionAttribute
public class LogImpresionService implements ILogImpresionService {
    private final LogImpresionRepository logImpresionRepository;

    @Inject
    public LogImpresionService(LogImpresionRepository logImpresionRepository) {
        this.logImpresionRepository = logImpresionRepository;
    }

//    @Override
    public void guarda(List<DocLogImpresion> lista){
        Long a=2000L;
        for (DocLogImpresion docLogImpresionEntity:lista){
             docLogImpresionEntity.setIdDoclogimpresion(a++);
            logImpresionRepository.save(docLogImpresionEntity);
        }

    }
}
