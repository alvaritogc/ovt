package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsrModuloService;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.repositories.UsrModuloRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/8/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("usrModuloService")
@TransactionAttribute
public class UsrModuloService implements IUsrModuloService {

    private UsrModuloRepository usrModuloRepository;

    @Inject
    public UsrModuloService(UsrModuloRepository usrModuloRepository) {
        this.usrModuloRepository = usrModuloRepository;
    }

    public List<UsrModulo> obtenerUsrModuloLista(){
        return usrModuloRepository.findAll();
    }
}
