package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrModuloEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:37 PM
 */
public interface IModuloService {

    public List<UsrModuloEntity> getAllModulos();

    public UsrModuloEntity save(UsrModuloEntity modulo);

    public boolean delete(UsrModuloEntity modulo);

    public UsrModuloEntity findById(BigDecimal id);
}
