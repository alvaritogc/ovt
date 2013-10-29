
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface UnidadRepository extends OpenJpaRepository<PerUnidad, PerUnidadPK>{
    @Query(
            "   select u "
            + " from PerUnidad u"
            + " where "
            + " u.perUnidadPK.idPersona=:idPersona"
            )
    List<PerUnidad> buscarPorPersona(@Param("idPersona") String idPersona);
    
}
