package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface DireccionRepository extends OpenJpaRepository<PerDireccion, Long>{

    @Query("select d " +
            "   from PerDireccion d " +
            "   where d.perUnidad.perUnidadPK.idPersona = :idPersona and " +
            "         d.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
            "    ")
    List<PerDireccion>obtenerPorIdPersonaYIdUnidad(@Param("idPersona") String idPersona,@Param("idUnidad")long idUnidad, Pageable pageable);

    @Query("select d " +
            "   from PerDireccion d " +
            "   where d.perUnidad.perUnidadPK.idPersona = :idPersona " +
            "         and lower(d.estado) = lower('A')")
    List<PerDireccion>obtenerPorIdPersona(@Param("idPersona") String idPersona, Pageable pageable);

    List<PerDireccion> findByPerUnidad(@Param("unidad") PerUnidad unidad, Pageable pageable);

    @Query("select d " +
            "   from PerDireccion d " +
            "   where d.perUnidad.perUnidadPK.idPersona = :idPersona and " +
            "         d.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
            "         and d.estado like 'A'")
    PerDireccion obtenerPorIdPersonaYIdUnidadYEstadoActivo(@Param("idPersona") String idPersona, @Param("idUnidad")long idUnidad);
}
