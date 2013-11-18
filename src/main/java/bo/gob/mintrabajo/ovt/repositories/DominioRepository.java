package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@OpenJpaSettings
public interface DominioRepository extends OpenJpaRepository<ParDominio, ParDominioPK>{

    @Query("select p from ParDominio p where p.parDominioPK.idDominio = :dominio order by p.descripcion")
    List<ParDominio>obtenerDominioPorNombre(@Param("dominio") String dominio);

    @Query("select p from ParDominio p where p.parDominioPK.idDominio = :dominio and p.parDominioPK.valor = :valor")
    ParDominio obtenerDominioPorNombreYValor(@Param("dominio")String dominio,@Param("valor")String valor);
    
    @Query("select p "
            + " from ParDominio p "
            + " where p.parDominio.parDominioPK.idDominio=:dominioPadre"
            + " and p.parDominio.parDominioPK.valor=:valorPadre"
            + " order by p.descripcion asc" )
    List<ParDominio> obtenerDominioPorDominioPadreYValorPadre(@Param("dominioPadre")String dominioP,@Param("valorPadre")String valorP);
    
    @Query("select p "
            + " from ParDominio p "
            + " where p.parDominioPK.idDominio = :dominio and p.parDominioPK.valor <> :valor")
    List<ParDominio> obtenerDominioPorNombreDistintoValor(@Param("dominio")String dominio,@Param("valor")String valor);


}
