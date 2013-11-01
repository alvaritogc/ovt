package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface DocumentoEstadoRepository extends OpenJpaRepository<ParDocumentoEstado, String> {
    @Query(
            "   select p "
            + " from ParDocumentoEstado p"
            + " where "
            + " p.codEstado IN ("
            + "     select t.parDocumentoEstado1.codEstado "
            + "     from DocTransicion t "
            + "     where "
            + "     t.parDocumentoEstado.codEstado = :codEstadoInicial"
            + "     and t.docDefinicion.docDefinicionPK.codDocumento = :codDocumento"
            + "     and t.docDefinicion.docDefinicionPK.version = :version"
            + " )"
            )
    List<ParDocumentoEstado> listarSiguientesTransiciones(@Param("codDocumento") String codDocumento,@Param("version") short version,@Param("codEstadoInicial") String codEstadoInicial);
}
