package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerInfolaboral;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface InfoLaboralRepository extends OpenJpaRepository<PerInfolaboral, Long>{
    @Query("select il " +
            "   from PerInfolaboral il " +
            "   where il.perUnidad.perUnidadPK.idPersona = :idPersona and " +
            "         il.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
            "        and lower(il.estadoInfolaboral) = lower('A') ")
    List<PerInfolaboral> obtenerPorIdPersonaYIdUnidad(@Param("idPersona") String idPersona, @Param("idUnidad") long idUnidad, Pageable pageable);

    @Query("select il " +
            "   from PerInfolaboral il " +
            "   where il.perUnidad.perUnidadPK.idPersona = :idPersona " +
            "        and lower(il.estadoInfolaboral) = lower('A') ")
    List<PerInfolaboral>obtenerPorIdPersona(@Param("idPersona")String idPersona);

    PerInfolaboral findByPerUnidad_PerUnidadPKAndEstadoInfolaboral(PerUnidadPK perUnidadPK, String estado);
}
