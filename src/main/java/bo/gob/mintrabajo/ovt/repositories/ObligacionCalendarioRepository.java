package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendarioEntity;
import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/10/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface ObligacionCalendarioRepository extends OpenJpaRepository<ParObligacionCalendarioEntity, BigDecimal> {
    
    @Query(
            "   select a "
            + " from ParObligacionCalendarioEntity a"
            + " where "
            + " :fecha between a.fechaHasta and a.fechaPlazo "
            )
    List<ParObligacionCalendarioEntity> buscarPorFecha(@Param("fecha") Timestamp fechaActual);

    @Query(
            "   select a.gestion "
                    + " from ParObligacionCalendarioEntity a"
                    + " where "
                    + " :fecha between a.fechaHasta and a.fechaPlazo "
    )
    String buscarGestionActual(@Param("fecha") Timestamp fechaActual);
}
