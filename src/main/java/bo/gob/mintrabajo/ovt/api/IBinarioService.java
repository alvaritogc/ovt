package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;

import java.util.List;

/**
 * User: renato
 * Date: 03-10-13
 */
public interface IBinarioService {
    DocBinarioEntity guardarBinario(DocBinarioEntity docBinariosEntity);

    Long contar();

    List<DocBinarioEntity> listaBinariosPorDocumento(Long idDocumento);

    DocBinarioEntity obtienePorIdDocumentoIdBinario(int idDocumento, int idBinario);

    void download(DocBinarioEntity docBinarioEntity, DocLogImpresionEntity docLogImpresionEntity);
}