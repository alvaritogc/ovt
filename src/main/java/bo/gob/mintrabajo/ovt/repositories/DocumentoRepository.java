package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumento, Long> {
    List<DocDocumento> findByRegistroBitacoraAndIdDocumento(String a, Long id);


    @Query(
            "   select doc "
            + " from DocDocumento doc"
            + " join fetch doc.codEstado"
            + " join fetch doc.docDefinicion"
            + " where "
            + " doc.idPersona.idPersona=:idPersona"
            )
    List<DocDocumento> buscarPorPersona(@Param("idPersona") String idPersona);
}
