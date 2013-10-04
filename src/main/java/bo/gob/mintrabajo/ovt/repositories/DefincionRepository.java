package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface DefincionRepository extends OpenJpaRepository<DocDefinicionEntity, BigDecimal> {
}
