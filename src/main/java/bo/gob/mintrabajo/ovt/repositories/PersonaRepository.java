
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.util.List;


@OpenJpaSettings
public interface PersonaRepository extends OpenJpaRepository<PerPersona, String>{
    List<PerPersona> findByIdPersonaAndEsNatural(String idPersona, boolean natural);

    PerPersona findByNroIdentificacion(String nroIdentificacion);
}
