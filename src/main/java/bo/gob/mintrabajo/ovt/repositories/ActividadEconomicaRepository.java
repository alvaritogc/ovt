package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface ActividadEconomicaRepository extends OpenJpaRepository<ParActividadEconomica, Long>{

    @Query
            ("select ac " +
            "   from ParActividadEconomica ac " +
            "   where ac.codActividadEconomica like 'A' or ac.codActividadEconomica like 'B' or " +
                    " ac.codActividadEconomica like 'C' or ac.codActividadEconomica like 'D' or" +
                    " ac.codActividadEconomica like 'E' or ac.codActividadEconomica like 'F' or " +
                    " ac.codActividadEconomica like 'G' or ac.codActividadEconomica like 'H' or " +
                    " ac.codActividadEconomica like 'I' or ac.codActividadEconomica like 'J' or " +
                    " ac.codActividadEconomica like 'K' or ac.codActividadEconomica like 'L' or " +
                    " ac.codActividadEconomica like 'M' or ac.codActividadEconomica like 'N' or " +
                    " ac.codActividadEconomica like 'O' or ac.codActividadEconomica like 'P' or " +
                    " ac.codActividadEconomica like 'Q' order by ac.codActividadEconomica asc" +
            "    ")
    List<ParActividadEconomica> obtenerActividadEconomicaParaRegistro();




}
