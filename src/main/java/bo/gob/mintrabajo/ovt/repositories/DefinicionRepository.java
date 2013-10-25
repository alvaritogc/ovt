package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;

import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface DefinicionRepository extends OpenJpaRepository<DocDefinicion, DocDefinicionPK> {
}
