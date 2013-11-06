package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import bo.gob.mintrabajo.ovt.repositories.ObligacionCalendarioRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: gmercado Date: 10/10/13 Time: 5:56 PM To
 * change this template use File | Settings | File Templates.
 */
@Named("obligacionCalendarioService")
@TransactionAttribute
public class ObligacionCalendarioService implements IObligacionCalendarioService {

    private final ObligacionCalendarioRepository obligacionCalendarioRepository;

    @Inject
    public ObligacionCalendarioService(ObligacionCalendarioRepository obligacionCalendarioRepository) {
        this.obligacionCalendarioRepository = obligacionCalendarioRepository;
    }
    
    @Override
    public List<ParObligacionCalendario> listaObligacionCalendario(){
        List<ParObligacionCalendario> lista;
        try {
            lista = obligacionCalendarioRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public List<ParObligacionCalendario> listaObligacionCalendarioPorObligacion(String codObligacion){
        List<ParObligacionCalendario> lista;
        try {
            lista = obligacionCalendarioRepository.findByCodObligacion_CodObligacion(codObligacion);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario){               
        ParObligacionCalendario parObligacionCalendario;
        try {
            parObligacionCalendario = obligacionCalendarioRepository.save(obligacionCalendario);
        } catch (Exception e) {
            e.printStackTrace();
            parObligacionCalendario = null;
        }
        return parObligacionCalendario;
    }
    
    @Override
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario){
        boolean deleted = false;
        try {
            obligacionCalendarioRepository.delete(obligacionCalendario);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }
        return deleted;
    }

    public List<ParObligacionCalendario> obtenerObligacionCalendario() {
        long oneday = TimeUnit.DAYS.toMillis(1);
        List<ParObligacionCalendario> tmpLista = obligacionCalendarioRepository.buscarPorFecha(new Timestamp(new Date().getTime()));
        return tmpLista;
    }

}
