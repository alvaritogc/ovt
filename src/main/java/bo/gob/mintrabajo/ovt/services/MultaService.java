package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IMultaRangoService;
import bo.gob.mintrabajo.ovt.api.IMultaService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.ParMulta;
import bo.gob.mintrabajo.ovt.entities.ParMultaRango;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.repositories.MultaRepository;
import bo.gob.mintrabajo.ovt.repositories.RecursoRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named("multaService")
@TransactionAttribute
public class MultaService implements IMultaService {

    private final MultaRepository repository;

    @Inject
    public MultaService(MultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParMulta> getAll() {
        return repository.findAll();
    }
    
    @Override
    public ParMulta findById(Long id){
        return repository.findOne(id);
    }
    
    @Override
    public ParMulta buscarTipoMulta(String tipoMulta) {
        List<ParMulta> lista=repository.findByAttribute("tipoMulta", tipoMulta, -1, -1);
        if(lista.size()==0){
            System.out.println("No se encontro la multa");
            return null;
        }
        return lista.get(0);
    }


    
}
