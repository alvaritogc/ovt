package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrRol;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface RolRepository extends OpenJpaRepository<UsrRol, Long>{

    @Query(" select r from UsrRol r where r.idModulo.idModulo = :idModulo")
    UsrRol obtenerPorModulo(@Param("idModulo") String idModulo);

    List<UsrRol> findByIdModulo(@Param("idModulo") String idModulo);
    UsrRol findByIdRol(@Param("idRol") Long idRol);

}
