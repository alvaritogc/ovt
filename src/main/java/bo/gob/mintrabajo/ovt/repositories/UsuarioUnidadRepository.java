
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidadPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface UsuarioUnidadRepository extends OpenJpaRepository<PerUsuarioUnidad, PerUsuarioUnidadPK>{


    
}
