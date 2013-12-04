package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocAlerta;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * User: Renato Velasquez
 * Date: 12/4/13
 */
@OpenJpaSettings
public interface AlertaRepository extends OpenJpaRepository<DocAlerta, Long> {
}
