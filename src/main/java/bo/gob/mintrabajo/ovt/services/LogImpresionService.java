package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

@Named("logImpresionService")
@TransactionAttribute
public class LogImpresionService implements ILogImpresionService {
    private final LogImpresionRepository logImpresionRepository;

    @Inject
    public LogImpresionService(LogImpresionRepository logImpresionRepository) {
        this.logImpresionRepository = logImpresionRepository;
    }

    public DocLogImpresion guarda(DocLogImpresion docLogImpresion){
        return logImpresionRepository.save(docLogImpresion);
    }
}
