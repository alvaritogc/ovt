package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/8/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface UsrModuloRepository extends OpenJpaRepository<UsrModulo, String> {
}
