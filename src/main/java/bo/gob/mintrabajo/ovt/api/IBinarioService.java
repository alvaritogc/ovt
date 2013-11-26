package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocBinario;

import java.util.List;

public interface IBinarioService {
    List<DocBinario> listarPorIdDocumento(Long idDocumento);

}