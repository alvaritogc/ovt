
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@OpenJpaSettings
public interface PersonaRepository extends OpenJpaRepository<PerPersona, String>{
    List<PerPersona> findByIdPersonaAndEsNatural(String idPersona, boolean natural);

    @Query("SELECT DISTINCT a FROM PerPersona a " +
           "WHERE a.idPersona IN (select b.idPersona from UsrUsuario b where b.idUsuario =:idUsuario)")
    PerPersona obtenerPersonaPorIdUsuario(@Param("idUsuario")Long idUsuario);
}
