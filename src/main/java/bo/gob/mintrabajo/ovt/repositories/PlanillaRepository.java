package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface PlanillaRepository extends OpenJpaRepository<DocPlanillaEntity, BigDecimal> {
}
