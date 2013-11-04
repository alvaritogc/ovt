package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.VperPersona;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/1/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
@OpenJpaSettings
public interface VperPersonaRepository extends OpenJpaRepository<VperPersona, String> {

}
