package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

@OpenJpaSettings
public interface EntidadRepository extends OpenJpaRepository<ParEntidad, Long> {

}
