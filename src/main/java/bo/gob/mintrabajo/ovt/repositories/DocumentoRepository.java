package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumento, Long> {

    List<DocDocumento> findByPerUnidad_PerPersona_IdPersonaOrderByIdDocumentoDesc(String idPersona);

//    List<DocDocumento> findByIdPersona_IdPersona(String idPersona);
    @Query(
            "   select d "
            + " from DocDocumento d"
            + " where "
            + " d.perUnidad.perPersona.idPersona=:idPersona"
            )
    List<DocDocumento> listarPorPersona(@Param("idPersona") String idPersona);

}
