package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocGenerico;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface DocGenericoRepository extends OpenJpaRepository<DocGenerico, Long> {
    @Query(
            "   select g "
            + " from DocGenerico g"
            + " where "
            + " g.idDocumento.idDocumento=:idDocumento"

            )
    DocGenerico buscarPorDocumento(@Param("idDocumento") Long idDocumento);
}
