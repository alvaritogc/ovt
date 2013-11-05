package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/4/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface ParametrizacionRepository extends OpenJpaRepository<ParParametrizacion, ParParametrizacionPK>{

    @Query("select p from ParParametrizacion p where p.parParametrizacionPK.idParametro = :parametro and p.parParametrizacionPK.valor = :valor")
    ParParametrizacion obtenerParametro(@Param("parametro")String parametro, @Param("valor")String valor);
}
