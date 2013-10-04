package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@OpenJpaSettings
public interface BinarioRepository extends OpenJpaRepository<DocBinarioEntity, BigDecimal> {
}
