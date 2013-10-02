package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


/**
 * User: gveramendi
 * Date: 10/1/13
 */
@OpenJpaSettings
public interface UserRepository extends OpenJpaRepository<UsrUsuarioEntity, BigDecimal> {

//    @Query("select u from UsrUsuarioEntity u where u.usuario = :usuario and u.clave = :clave")
//    UsrUsuarioEntity findByUsuarioAndClave(@Param("usuario") String usuario, @Param("clave") String clave);

//        UsrUsuarioEntity findByUsuario (String user);
}
