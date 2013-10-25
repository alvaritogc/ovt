package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumento, Long> {

    @Query(
            "   select doc "
            + " from DocDocumento doc"
            + " where "
            + " doc.idPersona.idPersona=:idPersona"
            )
    List<DocDocumento> buscarPorPersona(@Param("idPersona") String idPersona);
}
