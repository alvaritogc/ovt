package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/12/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface LogImpresionRepository extends OpenJpaRepository<DocLogImpresionEntity, BigDecimal> {
}
