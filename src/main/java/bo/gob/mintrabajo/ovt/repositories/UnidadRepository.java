
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnidadRepository extends OpenJpaRepository<PerUnidadEntity, BigDecimal>{
    
    @Query(
            "   select u "
            + " from PerUnidadEntity u"
            + " where "
            + " u.idUnidad IN ("
            + "     select perU.perUnidad.idUnidad"
            + "     from PerUsuarioEntity perU"
            + "     where perU.usrUsuarioByIdUsuario.idUsuario IN ( "
            + "         select usr.idUsuario"
            + "         from UsrUsuarioEntity usr" 
            + "         where usr.esDelegado=1" 
            + "         and usr.idUsuario = :idUsuario" 
            + "     )" 
            + " )"
            + " or u.idPersona IN ("
            + "     select p.idPersona "
            + "     from PerPersonaEntity p"
            + "     where p.idPersona IN ( "
            + "         select usr.perPersonaByIdPersona.idPersona"
            + "         from UsrUsuarioEntity usr" 
            + "         where usr.esInterno=1" 
            + "         and usr.idUsuario = :idUsuario" 
            + "     )" 
            + " )"
            )
    List<PerUnidadEntity> listarPorUsuario(@Param("idUsuario") BigDecimal idUsuario);
    
}
