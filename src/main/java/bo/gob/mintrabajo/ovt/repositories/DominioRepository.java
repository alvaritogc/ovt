
package bo.gob.mintrabajo.ovt.repositories;
import bo.gob.mintrabajo.ovt.entities.ParDominioEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
/**
 *
 * @author pc01
 */
@OpenJpaSettings
public interface DominioRepository extends OpenJpaRepository<ParDominioEntity, BigDecimal>{
}
