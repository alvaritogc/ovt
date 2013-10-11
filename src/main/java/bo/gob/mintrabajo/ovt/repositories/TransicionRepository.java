package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocTransicionEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/10/13
 */
@OpenJpaSettings
public interface TransicionRepository extends OpenJpaRepository<DocTransicionEntity, BigDecimal>{

    @Query("select d from DocTransicionEntity d where d.codDocumento= :codDocumento and d.version = :version and d.codEstadoInicial= :codEstadoInicial")
    List<DocTransicionEntity> findByCodDocumentoAndVersionAndCodEstadoInicial(@Param("codDocumento") String codDocumento,
                                   @Param("version") int version, @Param("codEstadoInicial") String codEstadoInicial);

}
