package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocBinarioPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@OpenJpaSettings
public interface BinarioRepository extends OpenJpaRepository<DocBinario, DocBinarioPK> {

    @Query("select d from DocBinario d where d.docBinarioPK.idDocumento = :idDocumento")
    List<DocBinario> findByIdDocumento(@Param("idDocumento") Long idDocumento);
}
