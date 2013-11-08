package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
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

/*    @Query("select d from PerDireccion d where d.PerUnidad.PerUnidadPK.idPersona = :dominio and d.PerUnidad.PerUnidadPK.idUnidad order by d.idDireccion")
    List<ParDominio>obtenerPorIdPersonaYIdUnidad(@Param("idPersona") String idPersona,@Param("idUnidad")long idUnidad);*/

    List<PerDireccion> findByPerUnidad(@Param("unidad") PerUnidad unidad, Pageable pageable);

}
