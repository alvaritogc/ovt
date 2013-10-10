package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendarioEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/10/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface ObligacionCalendarioRepository extends OpenJpaRepository<ParObligacionCalendarioEntity, BigDecimal> {
}
