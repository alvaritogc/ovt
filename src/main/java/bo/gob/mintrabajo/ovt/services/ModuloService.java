package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IModuloService;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.repositories.ModuloRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * User: gveramendi Date: 9/23/13 Time: 6:38 PM
 */
@Named("moduloService")
@TransactionAttribute
public class ModuloService implements IModuloService {

    private final ModuloRepository moduloRepository;

    @Inject
    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

//    @Override
    public List<UsrModulo> getAllModulos() {
        List<UsrModulo> allModulos;

        try {
            allModulos = moduloRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allModulos = null;
        }
        return allModulos;
    }

//    @Override
    public UsrModulo save(UsrModulo modulo) {
        UsrModulo usrModulo;
        Timestamp fechaBitacora = new Timestamp(new Date().getTime());
        modulo.setFechaBitacora(fechaBitacora);
        usrModulo = moduloRepository.save(modulo);
        return usrModulo;
    }

//    @Override
    public boolean delete(UsrModulo modulo) {
        boolean deleted = false;
        moduloRepository.delete(modulo);
        deleted = true;
        return deleted;
    }

    @Override
    public UsrModulo findById(String id) {
        UsrModulo modulo;
        modulo = moduloRepository.findOne(id);
        return modulo;
    }


//  
}
