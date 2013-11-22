package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface ActividadRepository extends OpenJpaRepository<PerActividad, Long>{

    @Query("select pa " +
            "   from PerActividad pa " +
            "   where pa.perUnidad.perUnidadPK.idPersona = :idPersona and " +
            "         pa.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
           // "    order by d.idDireccion desc")
            "    ")
    List<PerActividad>obtenerPorIdPersonaYIdUnidad(@Param("idPersona") String idPersona, @Param("idUnidad") long idUnidad, Pageable pageable);
}
