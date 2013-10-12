package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface EntidadRepository extends OpenJpaRepository<ParEntidadEntity, BigDecimal> {
    @Query(
            "   select a "
                    + " from ParEntidadEntity a"
                    + " where "
                    + " a.codigo like 'C%' "
    )
    List<ParEntidadEntity> obtenerCaja();
}
