package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface ObligacionCalendarioRepository extends OpenJpaRepository<ParObligacionCalendario, Long> {

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " :fecha between a.fechaHasta and a.fechaPlazo "
    )
    List<ParObligacionCalendario> buscarPorFecha(@Param("fecha") Date fechaActual);
    
    List<ParObligacionCalendario> findByCodObligacion_CodObligacion(String codObligacion);
  
}
