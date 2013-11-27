package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import java.util.Date;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface MensajeAppRepository extends OpenJpaRepository<ParMensajeApp, Long>{
//    @Query(
//            "   select m "
//            + " from ParMensajeApp m"
//            + " where "
//            + " m.idRecurso.etiqueta = :etiqueta "
//            )
//    List<ParMensajeApp> buscarPorRecurso(@Param("etiqueta") String etiqueta);
    
     @Query(
            "   select m "
            + " from ParMensajeApp m"
            + " where "
            + " m.idRecurso.idRecurso = :idRecurso"
            + " and ((:fecha between m.fechaDesde and m.fechaHasta) or (m.fechaDesde<:fecha and m.fechaHasta is null )) "
            + " order by m.idMensajeApp desc"
    )
    List<ParMensajeApp> listarPorRecursoYFecha(@Param("idRecurso") Long idRecurso,@Param("fecha") Date fecha);
     
    @Query(
            "   select m "
            + " from ParMensajeApp m"
            + " where "
            + " m.idRecurso.idRecurso = :idRecurso "
            + " order by m.idMensajeApp desc"
    )
    List<ParMensajeApp> buscarPorRecurso(@Param("idRecurso") Long idRecurso);
}
