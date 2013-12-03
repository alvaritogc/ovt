package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocLogEstado;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;

import java.math.BigDecimal;
import java.util.List;

import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface LogEstadoRepository extends OpenJpaRepository<DocLogEstado, Long> {
    @Query(
            "   select le "
                    + " from DocLogEstado le"
                    + " join fetch le.codEstadoFinal"
                    + " join fetch le.codEstadoInicial"
                    + " where "
                    + " le.idDocumento.idDocumento=:idDocumento"
                    + " order by le.idLogestado desc"
    )
    List<DocLogEstado> listarPorDocumento(@Param("idDocumento") Long idDocumento);

}
