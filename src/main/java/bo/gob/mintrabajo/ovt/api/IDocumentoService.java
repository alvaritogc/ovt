package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.DocPlanilla;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;

import java.util.List;

public interface IDocumentoService {
    List<DocDocumento> listarPorPersona(String idPersona);
    void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla);
    void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios);
    DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario);
}