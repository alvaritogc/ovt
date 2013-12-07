package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import bo.gob.mintrabajo.ovt.entities.ParMensajeContenido;

import java.util.Date;
import java.util.List;

import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface MensajeContenidoRepository extends OpenJpaRepository<ParMensajeContenido, Long> {
    @Query(
            "   select m "
                    + " from ParMensajeContenido m"
                    + " where "
                    + " m.idMensajeApp.idMensajeApp = :idMensajeApp"
                    + " order by m.idMensajeContenido asc"
    )
    List<ParMensajeContenido> listarPorMensajeApp(@Param("idMensajeApp") Long idMensajeApp);


    //    @Query(
//            "   select m "
//            + " from ParMensajeContenido m"
//            + " where "
//            + " m.contenido <> ''"
//            + " and m.contenido IS NOT NULL"
//            + " and m.idMensajeContenido in ("
//            + "     select mb.idMensajeContenido"
//            + "     from ParMensajeBinario mb"
//            + "     where "
//            + "     mb.idMensajeContenido.idMensajeApp.idMensajeApp=:idMensajeApp"
//            + "     and mb.binario is not null"
//            + " )"
//            )
//    List<ParMensajeContenido> tieneImagenes(@Param("idMensajeApp") Long idMensajeApp);
    @Query(
            "   select m "
                    + " from ParMensajeContenido m"
                    + " where "
                    + " m.idMensajeApp.idMensajeApp=:idMensajeApp"
                    + " and m.metadata <> 'N/A'"
                    + " and m.esDescargable = 0 "
            //+ " and m.contenido IS NOT NULL"
    )
    List<ParMensajeContenido> tieneImagenes(@Param("idMensajeApp") Long idMensajeApp);
}
