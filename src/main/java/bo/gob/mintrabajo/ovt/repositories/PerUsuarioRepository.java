package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.PerUsuario;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface PerUsuarioRepository extends OpenJpaRepository<PerUsuario, PerUsuarioPK>{

   @Query(" select pu from PerUsuario pu " +
            "where pu.perUsuarioPK.idPersona in (select p.idPersona from PerPersona p where p.nroIdentificacion = :NIT)  " +
            " and " +
            "      pu.perUsuarioPK.idUsuario in (select u.idUsuario from UsrUsuario u where u.usuario = :email)")
    PerUsuario obtenerPorNITyEmail(@Param("NIT") String NIT,@Param("email") String email);
}
