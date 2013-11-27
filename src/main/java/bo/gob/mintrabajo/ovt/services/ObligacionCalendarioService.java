package bo.gob.mintrabajo.ovt.services;

//import bo.gob.mintrabajo.ovt.api.ICalendarioService;
import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import bo.gob.mintrabajo.ovt.entities.ParCalendarioPK;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import bo.gob.mintrabajo.ovt.repositories.CalendarioRepository;
import bo.gob.mintrabajo.ovt.repositories.ObligacionCalendarioRepository;
import bo.gob.mintrabajo.ovt.repositories.ObligacionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private final CalendarioRepository calendarioRepository;
    private final ObligacionRepository obligacionRepository;


    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public ObligacionCalendarioService(ObligacionCalendarioRepository obligacionCalendarioRepository,CalendarioRepository calendarioRepository, ObligacionRepository obligacionRepository) {
        this.obligacionCalendarioRepository = obligacionCalendarioRepository;
        this.calendarioRepository=calendarioRepository;
        this.obligacionRepository=obligacionRepository;
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
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario,
                                                            ParCalendarioPK parCalendarioPK,String REGISTRO_BITACORA, String parObligacion, boolean evento){
        ParObligacionCalendario poc=new ParObligacionCalendario();

        if(obligacionCalendario.getIdObligacionCalendario()==null && !evento){
            poc.setIdObligacionCalendario(this.valorSecuencia("PAR_OBLIGACION_CAL_SEC"));
        }else{
            poc=obligacionCalendarioRepository.findOne(obligacionCalendario.getIdObligacionCalendario());
        }

        poc.setParCalendario(calendarioRepository.findOne(parCalendarioPK));
        poc.setCodObligacion(obligacionRepository.findOne(parObligacion));
        poc.setTipoCalendario(obligacionCalendario.getTipoCalendario());

        poc.setFechaDesde(obligacionCalendario.getFechaDesde());
        poc.setFechaHasta(obligacionCalendario.getFechaHasta());
        poc.setFechaPlazo(obligacionCalendario.getFechaPlazo());

        poc.setFechaBitacora(new Date());
        poc.setRegistroBitacora(REGISTRO_BITACORA);

        try {
            obligacionCalendario = obligacionCalendarioRepository.save(poc);
        } catch (Exception e) {
            e.printStackTrace();
            obligacionCalendario = null;
        }
        return obligacionCalendario;
    }

    @Override
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario){
        boolean deleted = false;
        try {
            ParObligacionCalendario poc = obligacionCalendarioRepository.findOne(obligacionCalendario.getIdObligacionCalendario());
            obligacionCalendarioRepository.delete(poc);
            obligacionCalendarioRepository.flush();
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

    @Override
    public List<ParObligacionCalendario> listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion(){
        List<ParObligacionCalendario> lista;
        try {
            lista = obligacionCalendarioRepository.listaPorOrdenDescripcionDeObligacion();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    public Long valorSecuencia (String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public List<ParObligacionCalendario> listaObligacionCalendarioPorGestion(String gestionActual){
        List<ParObligacionCalendario> lista;
        try {
            lista = obligacionCalendarioRepository.listarPorGestion(gestionActual);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public ParObligacionCalendario findById(Long id){
        return obligacionCalendarioRepository.findOne(id);
    }
    
    @Override
    public ParObligacionCalendario buscarPorPlatriALaFecha(){
        return obligacionCalendarioRepository.listarPlanillaTrimPorFechaHastaFechaPlazo(new Date());
    }

}
