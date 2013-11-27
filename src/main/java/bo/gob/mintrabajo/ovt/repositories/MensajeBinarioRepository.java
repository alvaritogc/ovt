package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import bo.gob.mintrabajo.ovt.entities.ParMensajeBinario;
import java.util.Date;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface MensajeBinarioRepository extends OpenJpaRepository<ParMensajeBinario, Long>{
    
     @Query(
            "   select m "
            + " from ParMensajeBinario m"
            + " where "
            + " m.idMensajeContenido.idMensajeContenido = :idMensajeContenido"
    )
    ParMensajeBinario buscarPorMensajeContenido(@Param("idMensajeContenido") Long idMensajeContenido);
}
