
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@OpenJpaSettings
public interface PersonaRepository extends OpenJpaRepository<PerPersonaEntity, BigDecimal>{
    
     @Query(
            "   select per "
            + " from PerPersonaEntity per"
            + " where "
            + " per.nroIdentificacion like  :nroIdentificacion "
            + " or per.nombreRazonSocial like  :nombreRazonSocial "
            )
    List<PerPersonaEntity> buscarPorNumeroNombre(@Param("nroIdentificacion") String nroIdentificacion,@Param("nombreRazonSocial") String nombreRazonSocial);
    
}
