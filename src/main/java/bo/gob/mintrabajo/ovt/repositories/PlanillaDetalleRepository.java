package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocPlanillaDetalle;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * User: Renato Velasquez
 * Date: 10/29/13
 */
@OpenJpaSettings
public interface PlanillaDetalleRepository extends OpenJpaRepository<DocPlanillaDetalle, Long> {
    @Query("select count (dpd) from DocPlanillaDetalle dpd where dpd.fechaNacimiento < :fechaHace18")
    Long obtieneMenores18(@Param("fechaHace18") Date fechaHace18);

    @Query("select count (dpd) from DocPlanillaDetalle dpd where dpd.fechaNacimiento > :fechaHace60")
    Long obtieneMenores60(@Param("fechaHace60") Date fechaHace60);
}
