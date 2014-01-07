package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocPlanillaDetalle;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 10/29/13
 */
@OpenJpaSettings
public interface PlanillaDetalleRepository extends OpenJpaRepository<DocPlanillaDetalle, Long> {
    List<DocPlanillaDetalle> findByIdPlanilla_IdPlanilla(Long idPlanilla);
}
