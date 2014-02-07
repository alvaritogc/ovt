package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerReplegal;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface RepLegalRepository extends OpenJpaRepository<PerReplegal, Long>{

    @Query("select rp " +
            "   from PerReplegal rp " +
            "   where rp.perUnidad.perUnidadPK.idPersona = :idPersona " +
            "         and lower(rp.estadoRepLegal)  =  lower('A')")
    List<PerReplegal>obtenerPorIdPersona(@Param("idPersona") String idPersona, Pageable pageable);
    
    @Query("select rp " +
            "   from PerReplegal rp " +
            "   where rp.perUnidad.perUnidadPK.idPersona = :idPersona " +
            "   and rp.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
            "         and lower(rp.estadoRepLegal)  =  lower('A')")
    PerReplegal obtenerPorIdPersonaAndIdUnidad(@Param("idPersona") String idPersona, @Param("idUnidad") Long idUnidad);
}
