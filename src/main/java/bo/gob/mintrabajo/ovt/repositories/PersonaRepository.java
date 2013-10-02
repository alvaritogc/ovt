
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;


@OpenJpaSettings
public interface PersonaRepository extends OpenJpaRepository<PerPersonaEntity, BigDecimal>{
    
}
