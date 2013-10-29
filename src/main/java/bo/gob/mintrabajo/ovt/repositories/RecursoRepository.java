
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface RecursoRepository extends OpenJpaRepository<UsrRecurso, Long>{
     @Query(
            "   select rec "
            + " from UsrRecurso rec"
            + " where "
            + " rec.idRecurso IN ("
            + "     select rolRec.usrRolRecursoPK.idRecurso "
            + "     from UsrRolRecurso rolRec "
            + "     where "
            + "     rolRec.usrRolRecursoPK.idRol IN ("
            + "         select usRol.usrUsuarioRolPK.idRol"
            + "         from UsrUsuarioRol usRol"
            + "         where usRol.usrUsuarioRolPK.idUsuario = :idUsuario"
            + "     )"
            + " )"
            + " and rec.idRecurso NOT IN ("
            + "     select usRec.usrUsuarioRecursoPK.idRecurso"
            + "     from UsrUsuarioRecurso usRec"
            + "     where usRec.usrUsuarioRecursoPK.idUsuario =: idUsuario"
            + " )"
            )
    List<UsrRecurso> buscarPorUsuario(@Param("idUsuario") Long idUsuario);

}

