
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface UnidadRepository extends OpenJpaRepository<PerUnidad, PerUnidadPK>{
    @Query(
            "   select u "
            + " from PerUnidad u"
            + " where "
            + " u.perUnidadPK.idPersona=:idPersona"
            + " and u.estadoUnidad = '1' "
            + " order by  u.perUnidadPK.idUnidad desc "
            )
    List<PerUnidad> buscarPorPersona(@Param("idPersona") String idPersona);

    @Query("select u from PerUnidad u " +
            "where u.perUnidadPK.idPersona=:idPersona" +
            "      and u.estadoUnidad = '1' ")
    PerUnidad unidadPorIdPersona(@Param("idPersona")String idPersona);


    List<PerUnidad> findByPerPersona_IdPersona(String idPersona);

    @Query("select max (u.perUnidadPK.idUnidad) from PerUnidad u " +
            "where u.perUnidadPK.idPersona=:idPersona")
    long obtenerMaximaUnidad(@Param("idPersona")String idPersona);

    @Query("select u from PerUnidad u " +
            "where u.perUnidadPK.idPersona=:idPersona" +
            "      and u.perUnidadPK.idUnidad=:idUnidad" +
            "      and u.estadoUnidad = '1' ")
    PerUnidad obtenerPorIdPersonaIdUnidad(@Param("idPersona")String idPersona,@Param("idUnidad")long idUnidad);

}
