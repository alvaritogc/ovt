package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;

import java.util.List;

public interface IBinarioService {
    List<DocBinario> listarPorIdDocumento(Long idDocumento);
    void download(DocBinario docBinario, DocLogImpresion docLogImpresion);
}