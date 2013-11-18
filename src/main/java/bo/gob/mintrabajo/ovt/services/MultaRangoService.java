package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IMultaRangoService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.ParMultaRango;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.repositories.MultaRangoRepository;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named("multaRangoService")
@TransactionAttribute
public class MultaRangoService implements IMultaRangoService {

    private final MultaRangoRepository repository;

    @Inject
    public MultaRangoService(MultaRangoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParMultaRango> getAll
            () {
        return repository.findAll();
    }
    
    @Override
    public ParMultaRango findById(Long id){
        return repository.findOne(id);
    }
    
    @Override
    public ParMultaRango buscarPorRango(Long idMulta,BigDecimal rango) {
        List<ParMultaRango> lista=repository.buscarPorRango(idMulta,rango);
        if(lista.size()==0){
            return null;
        }
        return lista.get(0);
    }


    
}
