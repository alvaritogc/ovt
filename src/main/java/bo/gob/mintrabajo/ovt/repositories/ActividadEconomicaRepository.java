package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface ActividadEconomicaRepository extends OpenJpaRepository<ParActividadEconomica, Long>{

/*    @Query("select d " +
            "   from PerDireccion d " +
            "   where d.perUnidad.perUnidadPK.idPersona = :idPersona and " +
            "         d.perUnidad.perUnidadPK.idUnidad = :idUnidad " +
           // "    order by d.idDireccion desc")
            "    ")
    List<PerDireccion>obtenerPorIdPersonaYIdUnidad(@Param("idPersona") String idPersona, @Param("idUnidad") long idUnidad, Pageable pageable);

    List<PerDireccion> findByPerUnidad(@Param("unidad") PerUnidad unidad, Pageable pageable);*/


}
