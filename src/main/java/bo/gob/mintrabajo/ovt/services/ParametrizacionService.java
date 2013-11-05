package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.repositories.ParametrizacionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/4/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("parametrizacionService")
@TransactionAttribute
public class ParametrizacionService implements IParametrizacionService {

    public ParametrizacionService(){
    }

    private ParametrizacionRepository parametrizacionRepository;

    @Inject
    public ParametrizacionService(ParametrizacionRepository parametrizacionRepository) {
        this.parametrizacionRepository = parametrizacionRepository;
    }

    public ParParametrizacion obtenerParametro(String idParametro, String valor){
        return parametrizacionRepository.obtenerParametro(idParametro, valor);
    }


    // ***** Getter y Setters *****//
    public ParametrizacionRepository getParametrizacionRepository() {
        return parametrizacionRepository;
    }

    public void setParametrizacionRepository(ParametrizacionRepository parametrizacionRepository) {
        this.parametrizacionRepository = parametrizacionRepository;
    }
}