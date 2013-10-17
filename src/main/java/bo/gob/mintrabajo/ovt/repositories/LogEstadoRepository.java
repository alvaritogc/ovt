package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocLogEstadoEntity;
import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface LogEstadoRepository extends OpenJpaRepository<DocLogEstadoEntity, Long>{
    
}
