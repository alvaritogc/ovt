
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParMultaRango;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface MultaRangoRepository extends OpenJpaRepository<ParMultaRango, Long>{
     @Query(
            "   select mr "
            + " from ParMultaRango mr"
            + " where "
            + " mr.rangoInicial  <= :rango"
            + " and mr.rangoFinal>= :rango"
            + " and mr.idMulta.idMulta=:idMulta" 
            )
    List<ParMultaRango> buscarPorRango(@Param("idMulta") Long idMulta,@Param("rango") BigDecimal rango);

}

