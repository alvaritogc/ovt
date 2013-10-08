package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface EntidadRepository extends OpenJpaRepository<ParEntidadEntity, BigDecimal> {
}
