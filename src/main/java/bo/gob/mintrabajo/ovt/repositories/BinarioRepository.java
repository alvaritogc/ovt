package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocBinarioPK;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@OpenJpaSettings
public interface BinarioRepository extends OpenJpaRepository<DocBinario, DocBinarioPK> {
}
