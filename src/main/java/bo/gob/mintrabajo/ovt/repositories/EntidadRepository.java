package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@OpenJpaSettings
public interface EntidadRepository extends OpenJpaRepository<ParEntidad, Long> {
    @Query(
            "   select a "
                    + " from ParEntidad a"
                    + " order by a.descripcion asc "
    )
    List<ParEntidad> listaPorOrdenDescripcionDeEntidad();

    List<ParEntidad> findByTipoEntidad(String tipoEntidad);
}
