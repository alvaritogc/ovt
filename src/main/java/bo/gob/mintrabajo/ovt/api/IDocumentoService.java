package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.*;
import java.util.Date;

import java.util.List;

public interface IDocumentoService {
    public DocDocumento findById(Long id);
    List<DocDocumento> listarPorPersona(String idPersona);
//    void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla);
    void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles);
    DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario);
    public DocDocumento guardarBajaRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora);
    public DocDocumento guardarImpresionRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora, DocDefinicion docDefinicion, VperPersona vperPersona, Long idUsuarioEmpleador);
    String generaReporteLC1010(DocPlanilla docPlanilla, PerPersona persona , DocDocumento docDocumento, PerUnidad perUnidad, VperPersona vperPersona);
//    String generaReporteROE(VperPersona vperPersona, String idUsuario, String idPersona);
    public DocDocumento guardarRoeGenerico(PerUnidadPK perUnidadPK ,String registroBitacora);

    public List<DocDocumento>  listarRoe013(String idPersona,long idUnidad);

    public List<DocDocumento>ObtenerRoes(String idPersona,long idUnidad);
    public DocDocumento guardarActualizaRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora);
    public List<DocDocumento> listarPlanillasTrimestrales(String idEmpleador,Date fechaDesde,Date fechaHasta);
}