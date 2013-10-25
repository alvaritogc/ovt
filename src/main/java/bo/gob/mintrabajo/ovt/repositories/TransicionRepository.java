package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocTransicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


@OpenJpaSettings
public interface TransicionRepository extends OpenJpaRepository<DocTransicion, DocTransicionPK>{

}
