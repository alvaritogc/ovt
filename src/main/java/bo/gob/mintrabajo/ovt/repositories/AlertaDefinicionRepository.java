package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocAlertaDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * User: Renato Velasquez
 * Date: 12/4/13
 */
@OpenJpaSettings
public interface AlertaDefinicionRepository extends OpenJpaRepository<DocAlertaDefinicion, String> {
    DocAlertaDefinicion findByDocDefinicion_DocDefinicionPK(DocDefinicionPK docDefinicionPK);
}
