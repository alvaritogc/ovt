package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import bo.gob.mintrabajo.ovt.entities.ParLocalidad;

import java.util.List;

import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;

@OpenJpaSettings
public interface LocalidadRepository extends OpenJpaRepository<ParLocalidad, String> {
    @Query("select p"
            + " from ParLocalidad p "
            + " where p.codLocalidad in "
            + " ("
            + "    select d.codLocalidad.codLocalidad"
            + "    from PerDireccion d"
            + " )")
        //+ " where p.tipoLocalidad <> 'PAIS'")
    List<ParLocalidad> listarDepartamentos();

}
