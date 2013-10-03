package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrModuloEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:31 PM
 */

@OpenJpaSettings
public interface ModuloRepository extends OpenJpaRepository<UsrModuloEntity, BigDecimal> {
    
    @Query(
            "   select m "
            + " from UsrModuloEntity m"
            + " where "
            + " m.idModulo = :idModulo"
            )        
    UsrModuloEntity buscarPorId(@Param("idModulo") String idModulo);
}
