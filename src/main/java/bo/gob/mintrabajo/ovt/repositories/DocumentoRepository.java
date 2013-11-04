package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;

import java.util.List;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumento, Long> {

    List<DocDocumento> findByIdPersona_IdPersona(String idPersona);
}
