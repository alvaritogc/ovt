package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendarioEntity;
import bo.gob.mintrabajo.ovt.repositories.ObligacionCalendarioRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/10/13
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("obligacionCalendarioService")
@TransactionAttribute
public class ObligacionCalendarioService implements IObligacionCalendarioService {

    private final ObligacionCalendarioRepository obligacionCalendarioRepository;

    @Inject
    public ObligacionCalendarioService(ObligacionCalendarioRepository obligacionCalendarioRepository) {
        this.obligacionCalendarioRepository = obligacionCalendarioRepository;
    }

    public List<ParObligacionCalendarioEntity> obtenerObligacionCalendario(){
             List<ParObligacionCalendarioEntity> tmpLista = obligacionCalendarioRepository.findAll();
        return tmpLista;
    }

}