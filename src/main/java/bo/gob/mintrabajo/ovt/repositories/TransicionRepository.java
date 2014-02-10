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
    @Query("select p "
            + " from DocTransicion p "
            + " where "
            + " p.docTransicionPK.codDocumento = :codigo and "
            + " p.docTransicionPK.version = :version and "
            + " p.docTransicionPK.codEstadoInicial = :estadoInicial and "
            + " p.docTransicionPK.codEstadoFinal =:estadoFinal")
    DocTransicion obtieneTransicionPk(@Param("codigo")String codigo, @Param("version")short version, 
            @Param("estadoInicial")String estadoInicial, @Param("estadoFinal")String estadoFinal);
    
    @Query("select p "
            + " from DocTransicion p "
            + " where "
            + " p.docTransicionPK.codDocumento = :documento")
    List<DocTransicion> listaTransicionPorDocumento(@Param("documento")String tipoDocumento);

}
