/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrRecursoEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;

//import javax.persistence.Query;
//import org.apache.openjpa.kernel.Query;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Victor Torrez
 */
@OpenJpaSettings
public interface RecursoRepository extends OpenJpaRepository<UsrRecursoEntity, BigDecimal>{

    
//    @Query(
//            "   select rec "
//            + " from UsrRecursoEntity rec fetch all properties"
//            + " where "
//            + " r.idRecurso IN ("
//            + "     select rolRec.idRecurso "
//            + "     from UsrRolRecursoEntity rolRec "
//            + "     where "
//            + "     rolRec.idRol IN ("
//            + "         select usRol.idRol"
//            + "         from UsrUsuarioRolEntity usRol"
//            + "         where usRol.idUsuario = :idUsuario"
//            + "     )"
//            + " )"
//            //+ " and rec.idRecurso NOT IN ("
//            //+ "     select usRec.idRecurso"
//            //+ "     from UsrUsuarioRecursoEntity usRec"
//            //+ "     where usRec.idUsuario = :idUsuario"
//            //+ " )"
//            )

    @Query(
            "   select rec "
            + " from UsrRecursoEntity rec"
            + " where "
            + " rec.idRecurso IN ("
            + "     select rolRec.idRecurso "
            + "     from UsrRolRecursoEntity rolRec "
            + "     where "
            + "     rolRec.idRol IN ("
            + "         select usRol.idRol"
            + "         from UsrUsuarioRolEntity usRol"
            + "         where usRol.idUsuario = :idUsuario"
            + "     )"
            + " )"
            + " and rec.idRecurso NOT IN ("
            + "     select usRec.idRecurso"
            + "     from UsrUsuarioRecursoEntity usRec"
            + "     where usRec.idUsuario =: idUsuario"
            + " )"
            )
    List<UsrRecursoEntity> buscarPorUsuario(@Param("idUsuario") BigDecimal idUsuario);
    
}

