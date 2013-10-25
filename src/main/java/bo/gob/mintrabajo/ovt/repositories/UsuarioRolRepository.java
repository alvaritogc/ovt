package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRol;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface UsuarioRolRepository extends OpenJpaRepository<UsrUsuarioRol, UsrUsuarioRolPK>{
}
