package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocPlanilla;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface PlanillaRepository extends OpenJpaRepository<DocPlanilla, Long> {
    @Query(
            "   select p "
            + " from DocPlanilla p "
            + " where "
            + " p.idDocumento.idDocumento=:idDocumento"
            )
    List<DocPlanilla> buscarPorDocumento(@Param("idDocumento") Long idDocumento);

    List<DocPlanilla> findByIdDocumento_PerUnidad_PerPersona_IdPersona(String idPersona);
}
