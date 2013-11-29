package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * User: Renato Velasquez Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentoService.class);
    private final DocumentoRepository documentoRepository;
    private final DocumentoEstadoRepository documentoEstadoRepository;
    private final PlanillaRepository planillaRepository;
    private final BinarioRepository binarioRepository;
    private final UnidadRepository unidadRepository;
    private final NumeracionRepository numeracionRepository;
    private final LogEstadoRepository logEstadoRepository;
    private final PlanillaDetalleRepository planillaDetalleRepository;
    private final DocGenericoRepository docGenericoRepository;
    private final DefinicionRepository definicionRepository;
    private final ParametrizacionRepository parametrizacionRepository;
    private final IUtilsService utils;
    private HashMap<String, Object> parametros = new HashMap<String, Object>();
    private String rutaPdf;
    private String nombrePdf;
    private File file;

    @Inject
    public DocumentoService(DocumentoRepository documentoRepository,
            DocumentoEstadoRepository documentoEstadoRepository,
            PlanillaRepository planillaRepository,
            BinarioRepository binarioRepository,
            UnidadRepository unidadRepository,
            NumeracionRepository numeracionRepository,
            LogEstadoRepository logEstadoRepository,
            PlanillaDetalleRepository planillaDetalleRepository,
            DocGenericoRepository docGenericoRepository,
            DefinicionRepository definicionRepository,
            ParametrizacionRepository parametrizacionRepository,
            IUtilsService utils) {
        this.documentoRepository = documentoRepository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
        this.logEstadoRepository = logEstadoRepository;
        this.planillaDetalleRepository = planillaDetalleRepository;
        this.docGenericoRepository = docGenericoRepository;
        this.definicionRepository = definicionRepository;
        this.parametrizacionRepository = parametrizacionRepository;
        this.utils = utils;
    }

    //    @Override
    public List<DocDocumento> getAllDocumentos() {
        List<DocDocumento> lista;
        lista = documentoRepository.findAll();
        return lista;
    }

    //    @Override
    public DocDocumento save(DocDocumento documento) {
        DocDocumento entity;
        entity = documentoRepository.save(documento);
        return entity;
    }

    //    @Override
    public boolean delete(DocDocumento documento) {
        boolean deleted = false;
        documentoRepository.delete(documento);
        return deleted;
    }

    //    @Override
    public DocDocumento findById(Long id) {
        DocDocumento entity;
        entity = documentoRepository.findOne(id);
        return entity;
    }

    public List<DocDocumento> listarPorPersona(String idPersona) {
        //return documentoRepository.findByPerUnidad_PerPersona_IdPersonaOrderByIdDocumentoDesc(idPersona);
        return documentoRepository.listarPorPersona(idPersona);
    }

    @Override
    public DocDocumento guardarCambioEstado(DocDocumento documento, String codEstadoFinal, String idUsuario, String observacionLogEstado) {
        DocLogEstado logEstado = new DocLogEstado();
        logEstado.setIdDocumento(documentoRepository.findOne(documento.getIdDocumento()));
        logEstado.setCodEstadoFinal(documentoEstadoRepository.findOne(codEstadoFinal));
        logEstado.setCodEstadoInicial(documentoEstadoRepository.findOne(documento.getCodEstado().getCodEstado()));
        logEstado.setRegistroBitacora(idUsuario);
        logEstado.setFechaBitacora(new Date());
        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
        logEstado.setObservacion(observacionLogEstado);
        logEstadoRepository.save(logEstado);
        //
        documento.setCodEstado(documentoEstadoRepository.findOne(codEstadoFinal));
        return documentoRepository.save(documento);
    }

    public void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles) {
        //guarda documento
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
//        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getDocDefinicion().getDocDefinicionPK().getCodDocumento(), docDocumento.getDocDefinicion().getDocDefinicionPK().getVersion()));
        docDocumento = documentoRepository.save(docDocumento);
        logger.info("Guarda" + docDocumento);
        //guarda planilla

        docPlanilla.setIdDocumento(docDocumento);

        docPlanilla.setIdPlanilla(utils.valorSecuencia("DOC_PLANILLA_SEC"));

        logger.info("Guarda" + planillaRepository.save(docPlanilla));

        //guardaPlanillaDetalles
//        for(DocPlanillaDetalle elemPlanillaDetalle:docPlanillaDetalles){
//            elemPlanillaDetalle.setIdPlanilla(docPlanilla);
//            elemPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_DETALLE_SEC"));
//            logger.info("Guarda",planillaDetalleRepository.save(elemPlanillaDetalle));
//        }

        //guarda binarios
        int idBinario = 1;
        for (DocBinario elementoBinario : listaBinarios) {
            elementoBinario.setDocDocumento(docDocumento);
            elementoBinario.setDocBinarioPK(new DocBinarioPK(idBinario++, docDocumento.getIdDocumento()));
            logger.info("Guarda" + binarioRepository.save(elementoBinario));
        }
    }

//    @Override
//    public DocDocumento guardarImpresionRoe(DocDocumento docDocumento, DocGenerico docGenerico, String registroBitacora, DocDefinicion docDefinicion) {//, VperPersona vperPersona, Long idUsuarioEmpleador){
//        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
//        docDocumento.setDocDefinicion(docDefinicion);
//
//        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
//        docDocumento.setFechaDocumento(new Date());
//        docDocumento.setFechaReferenca(new Date());
//
//        docDocumento.setFechaBitacora(new Date());
//        docDocumento.setRegistroBitacora(registroBitacora);
//        docDocumento.setTipoMedioRegistro("DDJJ");
//
//
//        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
//        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
//        docDocumento = documentoRepository.save(docDocumento);
//        //
//        docGenerico.setIdDocumento(docDocumento);
//        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
//        docGenericoRepository.save(docGenerico);
//
////        generaReporteROE(vperPersona, String.valueOf(idUsuarioEmpleador), String.valueOf(vperPersona.getIdPersona()));
//
//        //Actualizar el atributo nroOtro de PerUnidad     por aquiroz
//        PerUnidad unidad = unidadRepository.findOne(docDocumento.getPerUnidad().getPerUnidadPK());
//        System.out.println("========>>> UNIDAD " + unidad);
//        System.out.println("========>>> CAMBIANDO EL VALOR nro de documento" + docDocumento.getNumeroDocumento());
//        unidad.setTipoUnidad(String.valueOf(docDocumento.getNumeroDocumento()));
//        System.out.println("========>>> MODIFICADO " + unidad);
//        unidadRepository.save(unidad);
//
//        return docDocumento;
//    }
    @Override
    public List<DocDocumento> listarRoe013(String idPersona, long idUnidad) {
        try {
            return documentoRepository.listarRoe011(idPersona, idUnidad);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<DocDocumento> ObtenerRoes(String idPersona, long idUnidad) {
        try {
            return documentoRepository.ObtenerRoes(idPersona, idUnidad);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public DocDocumento guardarRoeGenerico(PerUnidadPK perUnidadPK, String registroBitacora) {
        List<DocDocumento> lista = documentoRepository.listarRoe011(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad());
        if (lista.size() > 0) {
            throw new RuntimeException("Ya se registro el roe para ese documento");
        }

        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_MODIFICACION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());

        DocDocumento docDocumento = new DocDocumento();
        docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        //
        //DocDefinicion docDefinicion = definicionRepository.findOne(new DocDefinicionPK("ROE011", (short) 1));
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);

        docDocumento.setCodEstado(documentoEstadoRepository.findOne("010"));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());

        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro(docDefinicion.getTipoGrupoDocumento());


        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        DocGenerico docGenerico = new DocGenerico();
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        return docDocumento;
    }

    @Override
    public DocDocumento guardarBajaRoe(DocDocumento docDocumento, DocGenerico docGenerico, String registroBitacora) {
        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_BAJA);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
        //DocDefinicion docDefinicion = definicionRepository.findOne(new DocDefinicionPK("ROE012", (short) 1));
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);

        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());

        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("DDJJ");


        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        return docDocumento;
    }

    @Override
    public DocDocumento guardarActualizaRoe(DocDocumento docDocumento, DocGenerico docGenerico, String registroBitacora) {

        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_IMPRESION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());

        //DocDefinicion docDefinicion = definicionRepository.findOne(new DocDefinicionPK("ROE013", (short) 1));
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);

        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());

        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("DDJSM");


        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        //
        return docDocumento;
    }

    public long actualizarNumeroDeOrden(String codDocumento, short version) {
        DocNumeracion numeracion;
        try {
            numeracion = numeracionRepository.findOne(new DocNumeracionPK(codDocumento, version));
        } catch (Exception e) {
            throw new RuntimeException("DocNumeracion no encontrado");
        }
        String codNumeroS = numeracion.getDocNumeracionPK().getCodDocumento();
        String codNumero = "";
        for (int i = 2; i < codNumeroS.length(); i++) {
            codNumero = codNumero + codNumeroS.charAt(i);
        }
        Long numero = new Long(numeracion.getUltimoNumero() + 1);
        //
        Formatter fmt = new Formatter();
        fmt.format("%07d", numero);
        String numeroFormato = fmt.toString();
        //
        String numeroSinVerificacion = "" + codNumero + numeroFormato;
        String numeroVerificacion = "";
        int contador = 2;
        for (int i = 0; i < numeroSinVerificacion.length(); i++) {
            numeroVerificacion = "" + contador + numeroVerificacion;
            contador++;
            if (contador > 7) {
                contador = 2;
            }
        }
        //
        Long sumatoria = new Long(0);
        //
        for (int i = 0; i < numeroSinVerificacion.length(); i++) {
            Long multiplicacion = (new Long("" + numeroSinVerificacion.charAt(i))) * (new Long("" + numeroVerificacion.charAt(i)));
            sumatoria = sumatoria + multiplicacion;
        }
        Long modulo = sumatoria % 11;
        Long verificacion = 11 - modulo;
        //
        if (verificacion > new Long(9)) {
            //System.out.println("verificador > a 9");
            String vAux = "" + verificacion;
            verificacion = new Long((Integer.valueOf("" + vAux.charAt(0)) + Integer.valueOf("" + vAux.charAt(1))));
            //System.out.println("verificacion: "+verificacion);
        }

        //
        numeracion.setUltimoNumero(numeracion.getUltimoNumero() + 1);
        numeracionRepository.save(numeracion);
        //
        Formatter fmtVerificacion = new Formatter();
        fmtVerificacion.format("%02d", verificacion);
        return (new Long("" + codNumero + numeroFormato + fmtVerificacion.toString()));
    }

    public String generateReport(String nomArchivo, String jasper, HashMap<String, Object> parametros) throws ClassNotFoundException, IOException, JRException {
        List<String> lista = new ArrayList<String>();
        lista.add("asd");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String rutaWebapp = servletContext.getRealPath("/");
        rutaPdf = "/reportes/temp/" + nomArchivo;
        nombrePdf = rutaWebapp + rutaPdf;
        JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(rutaWebapp + jasper));
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(lista));
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(nombrePdf));
        exporter.exportReport();
        return nombrePdf;
    }

    @Override
    public List<DocDocumento> listarPlanillasTrimestrales(String idEmpleador, Date fechaDesde, Date fechaHasta) {
        return documentoRepository.listarPlanillaALaFecha(idEmpleador, fechaDesde, fechaHasta);
    }

    public List<DocDocumento> listarDeclarados(String idEmpleador) {
        return documentoRepository.listarDeclarados(idEmpleador);
    }

    @Override
    public DocDocumento guardarDocumentoRoe(DocGenerico docGenerico, Long idDocumento, PerUnidadPK perUnidadPK, DocDefinicionPK docDefinicionPK, String registroBitacora, String parametroDocDefinicionAdicional) {
        if (parametroDocDefinicionAdicional != null && !parametroDocDefinicionAdicional.equals("")) {
            ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, parametroDocDefinicionAdicional);
            DocDefinicion docDefinicionAdicional = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
            DocDocumento docDocumentoAdicional = new DocDocumento();
            //
            docDocumentoAdicional.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
            docDocumentoAdicional.setDocDefinicion(docDefinicionAdicional);
            //
            docDocumentoAdicional.setCodEstado(documentoEstadoRepository.findOne(docDefinicionAdicional.getCodEstado().getCodEstado()));//Estado inicial
            docDocumentoAdicional.setPerUnidad(unidadRepository.findOne(perUnidadPK));
            docDocumentoAdicional.setFechaDocumento(new Date());
            docDocumentoAdicional.setFechaReferenca(new Date());

            docDocumentoAdicional.setFechaBitacora(new Date());
            docDocumentoAdicional.setRegistroBitacora(registroBitacora);
            docDocumentoAdicional.setTipoMedioRegistro(docDefinicionAdicional.getTipoGrupoDocumento());

            System.out.println("codEstadoDocumento: " + docDefinicionAdicional.getDocDefinicionPK().getCodDocumento());
            docDocumentoAdicional.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
            docDocumentoAdicional = documentoRepository.save(docDocumentoAdicional);
            //
            DocGenerico docGenericoAdicional = new DocGenerico();
            docGenericoAdicional.setCadena05(docGenerico.getCadena05());
            docGenericoAdicional.setCadena06(docGenerico.getCadena06());
            docGenericoAdicional.setIdDocumento(docDocumentoAdicional);
            docGenericoAdicional.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
            docGenericoRepository.save(docGenericoAdicional);
            
            PerUnidad unidad = unidadRepository.findOne(docDocumentoAdicional.getPerUnidad().getPerUnidadPK());
            System.out.println("========>>> UNIDAD " + unidad);
            System.out.println("========>>> CAMBIANDO EL VALOR nro de documento" + docDocumentoAdicional.getNumeroDocumento());
            unidad.setTipoUnidad(String.valueOf(docDocumentoAdicional.getNumeroDocumento()));
            System.out.println("========>>> MODIFICADO " + unidad);
            unidadRepository.save(unidad);
        }


        DocDefinicion docDefinicion = definicionRepository.findOne(docDefinicionPK);
        //
        DocDocumento docDocumento;
        if (idDocumento == null) {
            //Si no tiene  documento crerara un nuevo, segun su docDefinicion
            docDocumento = new DocDocumento();
            //
            docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
            docDocumento.setDocDefinicion(docDefinicion);
            //
            docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
            docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
            docDocumento.setFechaDocumento(new Date());
            docDocumento.setFechaReferenca(new Date());

            docDocumento.setFechaBitacora(new Date());
            docDocumento.setRegistroBitacora(registroBitacora);
            docDocumento.setTipoMedioRegistro(docDefinicion.getTipoGrupoDocumento());

            System.out.println("codEstadoDocumento: " + docDefinicion.getDocDefinicionPK().getCodDocumento());
            docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
            //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));
            //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getCodEstado().getCodEstado(), (short) 1));

            docDocumento = documentoRepository.save(docDocumento);
        } else {
            //Si ya tiene un documento actualizara codEstado de 010 al q tenga en docDefinicion ej 013
            docDocumento = documentoRepository.findOne(idDocumento);
            docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
            docDocumento = documentoRepository.save(docDocumento);
        }

        //
        docGenerico.setIdDocumento(docDocumento);
        if (docGenerico.getIdGenerico() == null) {
            //si el documento es nuevo creara un nuevo docGenerico
            docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        }
        docGenericoRepository.save(docGenerico);
        //
        return docDocumento;
    }

    public List<DocDocumento> findByPerUnidad_PerPersona_IdPersonaAndCodEstado_CodEstado(String idPersona, String codEstado) {
        return documentoRepository.findByPerUnidad_PerPersona_IdPersonaAndCodEstado_CodEstado(idPersona, codEstado);
    }
}