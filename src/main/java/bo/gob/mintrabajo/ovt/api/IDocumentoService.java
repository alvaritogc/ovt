package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.DocPlanilla;

import java.util.List;

public interface IDocumentoService {
    List<DocDocumento> listarPorPersona(String idPersona);
    List<DocDocumento> listarPorNumero(String idPersona);
    public void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla);

}