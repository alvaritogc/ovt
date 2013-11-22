package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.*;

import java.util.List;

public interface IDocumentoService {
    List<DocDocumento> listarPorPersona(String idPersona);
//    void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla);
    void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles);
    DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario);
    public DocDocumento guardarBajaRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora);
    public DocDocumento guardarImpresionRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora);
    String generaReporte(DocPlanilla docPlanilla, PerPersona persona , DocDocumento docDocumento, PerUnidad perUnidad, VperPersona vperPersona);
}