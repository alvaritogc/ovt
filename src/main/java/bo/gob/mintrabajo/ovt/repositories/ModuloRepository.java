package bo.gob.mintrabajo.ovt.repositories;

import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:31 PM
 */

@OpenJpaSettings
public interface ModuloRepository extends OpenJpaRepository<UsrModuloEntity, BigDecimal> {
}
