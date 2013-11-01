package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface DefinicionRepository extends OpenJpaRepository<DocDefinicion, DocDefinicionPK> {

    List<DocDefinicion> findByNombreAndFechaBitacora(String nombre, Date fechaBitacora);

}
