package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IModuloService;
import bo.gob.mintrabajo.ovt.entities.UsrModuloEntity;
import bo.gob.mintrabajo.ovt.repositories.ModuloRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:38 PM
 */

@Named("moduloService")
@TransactionAttribute
public class ModuloService implements IModuloService {

    private final ModuloRepository moduloRepository;

    @Inject
    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    @Override
    public List<UsrModuloEntity> getAllModulos() {
        List<UsrModuloEntity> allModulos;

        try {
            allModulos = moduloRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allModulos = null;
        }
        return allModulos;
    }

    @Override
    public UsrModuloEntity save(UsrModuloEntity modulo) {
        UsrModuloEntity usrModulo;

        try {
            Timestamp fechaBitacora = new Timestamp(new Date().getTime());
            modulo.setFechaBitacora(fechaBitacora);

            usrModulo = moduloRepository.save(modulo);
        } catch (Exception e) {
            e.printStackTrace();
            usrModulo = null;
        }

        return usrModulo;
    }

    @Override
    public boolean delete(UsrModuloEntity modulo) {
        boolean deleted = false;

        try {
            moduloRepository.delete(modulo);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public UsrModuloEntity findById(BigDecimal id) {
        UsrModuloEntity modulo;

        try {
            modulo = moduloRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            modulo = null;
        }

        return modulo;
    }
    
    @Override
    public UsrModuloEntity buscarPorId( String idModulo){
        UsrModuloEntity modulo;
        try {
            modulo = moduloRepository.buscarPorId(idModulo);
        } catch (Exception e) {
            e.printStackTrace();
            modulo = null;
        }
        return modulo;
    }
}
