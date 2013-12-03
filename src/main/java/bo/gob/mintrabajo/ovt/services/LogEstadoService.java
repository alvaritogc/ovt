package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogEstadoService;
import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogEstado;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.repositories.LogEstadoRepository;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

@Named("logEstadoService")
@TransactionAttribute
public class LogEstadoService implements ILogEstadoService {
    private final LogEstadoRepository logEstadoRepository;

    @Inject
    public LogEstadoService(LogEstadoRepository logEstadoRepository) {
        this.logEstadoRepository = logEstadoRepository;
    }

    public List<DocLogEstado> listarPorDocumento(Long idDocumento) {
        return logEstadoRepository.listarPorDocumento(idDocumento);
    }
}
