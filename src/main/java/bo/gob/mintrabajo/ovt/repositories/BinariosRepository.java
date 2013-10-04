package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocBinariosEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@OpenJpaSettings
public interface BinariosRepository extends OpenJpaRepository<DocBinariosEntity, BigDecimal> {
}
