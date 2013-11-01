package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface UsuarioRepository extends OpenJpaRepository<UsrUsuario, Long>{

    @Query(" select u from UsrUsuario u where lower(u.usuario)  = lower(:email)")
    UsrUsuario findByUsuario(@Param("email")String email);
}
