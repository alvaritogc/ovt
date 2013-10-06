package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import java.math.BigDecimal;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumentoEntity, BigDecimal> {
}
