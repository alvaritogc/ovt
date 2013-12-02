package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.*;
import net.sf.jasperreports.engine.JRException;

import java.util.Date;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IDocumentoService {
    public DocDocumento findById(Long id);

    List<DocDocumento> listarPorPersona(String idPersona);

    //    void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla);
    void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles);

    DocDocumento guardarCambioEstado(DocDocumento documento, String codEstadoFinal, String idUsuario, String observacionLogEstado);

    public DocDocumento guardarBajaRoe(PerUnidadPK perUnidadPK, DocGenerico docGenerico, String registroBitacora);

    //    public DocDocumento guardarImpresionRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora, DocDefinicion docDefinicion);//, VperPersona vperPersona, Long idUsuarioEmpleador);
    public DocDocumento guardarRoeGenerico(PerUnidadPK perUnidadPK, String registroBitacora);

    public List<DocDocumento> listarRoe013(String idPersona, long idUnidad);

    public List<DocDocumento> ObtenerRoes(String idPersona, long idUnidad);


    String generateReport(String nomArchivo, String jasper, HashMap<String, Object> parametros) throws ClassNotFoundException, IOException, JRException;

    public DocDocumento guardarActualizaRoe(DocDocumento docDocumento, DocGenerico docGenerico, String registroBitacora);

    public List<DocDocumento> listarPlanillasTrimestrales(String idEmpleador, Date fechaDesde, Date fechaHasta);

    List<DocDocumento> listarDeclarados(String idEmpleador);

    public DocDocumento guardarDocumentoRoe(DocGenerico docGenerico, Long idDocumento, PerUnidadPK perUnidadPK, DocDefinicionPK docDefinicionPK, String registroBitacora, String parametroDocDefinicionAdicional);

    List<DocDocumento> findByPerUnidad_PerPersona_IdPersonaAndCodEstado_CodEstado(String idPersona, String codEstado);

    public DocDocumento guardarReactivacionRoe(DocGenerico docGenerico, PerUnidadPK perUnidadPK, String registroBitacora);
}