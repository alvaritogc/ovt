
package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.*;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@OpenJpaSettings
public interface UsuarioUnidadRepository extends OpenJpaRepository<PerUsuarioUnidad, PerUsuarioUnidadPK>{

    @Query(" select puu from PerUsuarioUnidad puu " +
            "where puu.perUsuarioUnidadPK.idPersona in (select p.idPersona from PerPersona p where p.nroIdentificacion = :NIT)  " +
            " and " +
            "      puu.perUsuarioUnidadPK.idUsuario in (select u.idUsuario from UsrUsuario u where u.usuario = :email)")
    PerUsuarioUnidad obtenerPorNITyEmail(@Param("NIT") String NIT,@Param("email") String email);

    @Query(" select perUnidad from PerUsuarioUnidad perUnidad " +
            "where perUnidad.perUsuarioUnidadPK.idPersona = :idPersona")
    PerUsuarioUnidad unidadPorIdPersona(@Param("idPersona") String idPersona);
}
