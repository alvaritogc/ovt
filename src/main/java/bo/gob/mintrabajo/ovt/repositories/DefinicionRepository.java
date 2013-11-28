package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface DefinicionRepository extends OpenJpaRepository<DocDefinicion, DocDefinicionPK> {

    List<DocDefinicion> findByNombreAndFechaBitacora(String nombre, Date fechaBitacora);

    @Query("select p "
    + " from DocDefinicion p "
    + " where p.docDefinicionPK.codDocumento = :codDocumento")
    List<DocDefinicion> listaVersionesPorCodDocumento(@Param("codDocumento") String codDocumento);

    @Query("select p "
    + " from DocDefinicion p "
    + " where p.docDefinicionPK.codDocumento = :codDocumento and "
    + " p.docDefinicionPK.version = :version")
    DocDefinicion obtenerDocDefinicion(@Param("codDocumento") String codDocumento, @Param("version") short version);

    @Query(
            "   select a "
    + " from DocDefinicion a"
    + " order by a.nombre asc ")
    List<DocDefinicion> listaPorOrdenDocDefinicion();

    @Query(
            "select a from DocDefinicion a where (a.docDefinicionPK.codDocumento like :cod and a.docDefinicionPK.version =:ver)")
    List<DocDefinicion> listaCodDocumento(@Param("cod") String cod, @Param("ver") Short ver);

    @Query(
            "   select d "
            + " from DocDefinicion d"
            + " where"
            + " d.docDefinicionPK.codDocumento=:codDocumento "
            + " and d.estado= 'A' "
            )
    DocDefinicion buscarPorCodDocumentoActivo(@Param("codDocumento") String codDocumento);
}
