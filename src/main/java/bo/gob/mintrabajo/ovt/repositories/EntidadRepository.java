package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;

@OpenJpaSettings
public interface EntidadRepository extends OpenJpaRepository<ParEntidad, Long> {
    @Query(
            "   select a "
                    + " from ParEntidad a"
                    + " order by a.descripcion asc "
    )
    List<ParEntidad> listaPorOrdenDescripcionDeEntidad();
}
