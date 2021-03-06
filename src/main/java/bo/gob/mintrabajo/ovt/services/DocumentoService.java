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
import java.text.SimpleDateFormat;
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
    private final AlertaRepository alertaRepository;
    private final AlertaDefinicionRepository alertaDefinicionRepository;
    private final InfoLaboralRepository infoLaboralRepository;
    private final IUtilsService utils;
    private int menores18;
    private int mayores60;

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
                            AlertaRepository alertaRepository,
                            AlertaDefinicionRepository alertaDefinicionRepository,
                            InfoLaboralRepository infoLaboralRepository,
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
        this.alertaRepository = alertaRepository;
        this.alertaDefinicionRepository = alertaDefinicionRepository;
        this.infoLaboralRepository = infoLaboralRepository;
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
    public List<DocDocumento> obtenerPorIdPersonaIdUnidad(String idPersona, Long idUnidad) {
        return documentoRepository.obtenerPorIdPersonaIdUnidad(idPersona,idUnidad);
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

    public void cuenta1860(String fechaNacimiento, Date fecha18, Date fecha60){
        try{
            Date fecha= (new SimpleDateFormat("dd/MM/yyyy")).parse(fechaNacimiento);
            if(fecha18.before(fecha))
                menores18++;
            if (fecha60.after(fecha))
                mayores60++;

        }catch (Exception e){
            System.out.println("fecha incorrecta");
        }
    }

    public void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles, List<DocAlerta> alertas, String bitacoraSession) {
        //guarda documento
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getDocDefinicion().getDocDefinicionPK().getCodDocumento(), docDocumento.getDocDefinicion().getDocDefinicionPK().getVersion()));
        docDocumento = documentoRepository.save(docDocumento);
        logger.info("Guarda Documento: " + docDocumento.getIdDocumento());
        if(docDocumento.getIdDocumentoRef()!=null)
            guardarCambioEstado(docDocumento.getIdDocumentoRef(), "999", bitacoraSession, "Cambio de estado al rectificar un documento.");

        //guarda planilla
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(utils.valorSecuencia("DOC_PLANILLA_SEC"));
        docPlanilla = planillaRepository.save(docPlanilla);
        logger.info("Guarda Planilla: " + docPlanilla.getIdPlanilla());

        menores18=0;
        mayores60=0;
        Date fechaHace18= new Date();
        Date fechaHace60= new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -18);
        fechaHace18.setTime(c.getTime().getTime());
        c.add(Calendar.YEAR, -(60-18));
        fechaHace60.setTime(c.getTime().getTime());


        //guardaPlanillaDetalles
        if(docPlanillaDetalles==null)
            docPlanillaDetalles= new ArrayList<DocPlanillaDetalle>();
        for (DocPlanillaDetalle elemPlanillaDetalle : docPlanillaDetalles) {
            elemPlanillaDetalle.setIdPlanilla(docPlanilla);
            elemPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_PLANILLA_DETALLE_SEC"));
            elemPlanillaDetalle=planillaDetalleRepository.save(elemPlanillaDetalle);
            logger.info("Guarda Planilla Detalle: ", elemPlanillaDetalle.getIdPlanillaDetalle());
            //para infoLaboral
            cuenta1860(elemPlanillaDetalle.getFechaNacimiento(), fechaHace18, fechaHace60);
        }

        //modifica anterior y guarda nuevo infoLaboral
        PerInfolaboral perInfolaboralAnterior;
        try{
            perInfolaboralAnterior = infoLaboralRepository.findByPerUnidad_PerUnidadPKAndEstadoInfolaboral(docDocumento.getPerUnidad().getPerUnidadPK(), Dominios.PER_ESTADO_ACTIVO);
        }
        catch (Exception e){
            perInfolaboralAnterior=null;
        }

        if(perInfolaboralAnterior!=null){
            perInfolaboralAnterior.setEstadoInfolaboral(Dominios.PER_ESTADO_INACTIVO);
            perInfolaboralAnterior=infoLaboralRepository.save(perInfolaboralAnterior);
            logger.info("Guarda Infolaboral Antiguo: ", perInfolaboralAnterior.getIdInfolaboral());
        }
        PerInfolaboral perInfolaboralNuevo= new PerInfolaboral();
        perInfolaboralNuevo.setIdInfolaboral(utils.valorSecuencia("PER_INFOLABORAL_SEC"));
        perInfolaboralNuevo.setPerUnidad(docDocumento.getPerUnidad());
        perInfolaboralNuevo.setNroTotalTrabajadores(docPlanilla.getNroH()+docPlanilla.getNroM());
        perInfolaboralNuevo.setNroHombres(docPlanilla.getNroH());
        perInfolaboralNuevo.setNroMujeres(docPlanilla.getNroM());
        perInfolaboralNuevo.setNroExtranjeros(docPlanilla.getNroExtranjerosH()+docPlanilla.getNroExtranjerosM());
        perInfolaboralNuevo.setNroFijos(docPlanilla.getNroAsegAfp());
        perInfolaboralNuevo.setNroEventuales(perInfolaboralNuevo.getNroTotalTrabajadores()-docPlanilla.getNroAsegAfp());
        perInfolaboralNuevo.setNroMenores18(menores18);
        perInfolaboralNuevo.setNroMayores60(mayores60);
        perInfolaboralNuevo.setNroJubilados(docPlanilla.getNroJubiladosH()+docPlanilla.getNroJubiladosM());
        perInfolaboralNuevo.setNroCapdiferente(docPlanilla.getNroDiscapacidadH()+docPlanilla.getNroDiscapacidadM());
        perInfolaboralNuevo.setTotalPlanilla((((((docPlanilla.getHaberBasico()).add(docPlanilla.getBonoAntiguedad())).add(docPlanilla.getBonoProduccion())).add(docPlanilla.getSubsidioFrontera())).add(docPlanilla.getLaborExtra())).add(docPlanilla.getOtrosBonos()));
        perInfolaboralNuevo.setNroAsegAfp(docPlanilla.getNroAsegAfp());
        perInfolaboralNuevo.setNroAsegCaja(docPlanilla.getNroAsegCaja());
        perInfolaboralNuevo.setMontoAsegAfp(docPlanilla.getMontoAsegAfp());
        perInfolaboralNuevo.setMontoAsegCaja(docPlanilla.getMontoAsegCaja());
        perInfolaboralNuevo.setTipoSindicato(perInfolaboralAnterior!=null?perInfolaboralAnterior.getTipoSindicato():"2");
        perInfolaboralNuevo.setEstadoInfolaboral(Dominios.PER_ESTADO_ACTIVO);
        perInfolaboralNuevo.setFechaBitacora(new Date());
        perInfolaboralNuevo.setRegistroBitacora(bitacoraSession);
        perInfolaboralNuevo= infoLaboralRepository.save(perInfolaboralNuevo);
        logger.info("Guarda Infolaboral Nuevo: ", perInfolaboralNuevo.getIdInfolaboral());

        //guardaAlertas
        DocAlertaDefinicion docAlertaDefinicion= new DocAlertaDefinicion();
        if(alertas==null)
            alertas= new ArrayList<DocAlerta>();
        else
            docAlertaDefinicion = alertaDefinicionRepository.findByDocDefinicion_DocDefinicionPK(docDocumento.getDocDefinicion().getDocDefinicionPK());
        for (DocAlerta elementoAlerta : alertas) {
            elementoAlerta.setEstadoAlerta("Estado Alerta");  //revisar que utilizar
            elementoAlerta.setCodAlerta(docAlertaDefinicion);
            elementoAlerta.setIdDocumento(docDocumento);
            elementoAlerta.setIdAlerta(utils.valorSecuencia("DOC_ALERTA_SEC"));
            elementoAlerta= alertaRepository.save(elementoAlerta);
            logger.info("Guarda Alerta: ", elementoAlerta.getIdAlerta());
        }

        //guarda binarios
        int idBinario = 1;
        for (DocBinario elementoBinario : listaBinarios) {
            elementoBinario.setValidado(docPlanillaDetalles.size()>0?true:false);
            elementoBinario.setDocDocumento(docDocumento);
            elementoBinario.setDocBinarioPK(new DocBinarioPK(idBinario++, docDocumento.getIdDocumento()));
            elementoBinario=binarioRepository.save(elementoBinario);
            logger.info("Guarda Binario: ", elementoBinario.getDocBinarioPK());
        }
    }

    public void guardaDetallesAlertasActualizaInfLab(DocDocumento docDocumento, List<DocPlanillaDetalle> docPlanillaDetalles, List<DocAlerta> alertas){
        menores18=0;
        mayores60=0;
        Date fechaHace18= new Date();
        Date fechaHace60= new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -18);
        fechaHace18.setTime(c.getTime().getTime());
        c.add(Calendar.YEAR, -(60-18));
        fechaHace60.setTime(c.getTime().getTime());


        //guardaPlanillaDetalles
        DocPlanilla docPlanilla=new DocPlanilla();
        docPlanilla=planillaRepository.findByIdDocumento_IdDocumento(docDocumento.getIdDocumento());
        if(docPlanillaDetalles==null)
            docPlanillaDetalles= new ArrayList<DocPlanillaDetalle>();
        for (DocPlanillaDetalle elemPlanillaDetalle : docPlanillaDetalles) {
            elemPlanillaDetalle.setIdPlanilla(docPlanilla);
            elemPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_PLANILLA_DETALLE_SEC"));
            elemPlanillaDetalle=planillaDetalleRepository.save(elemPlanillaDetalle);
            logger.info("Guarda Planilla Detalle: ", elemPlanillaDetalle.getIdPlanillaDetalle());
            //para infoLaboral
            cuenta1860(elemPlanillaDetalle.getFechaNacimiento(), fechaHace18, fechaHace60);
        }

        //modifica anterior y guarda nuevo infoLaboral
        PerInfolaboral perInfolaboralAnterior = infoLaboralRepository.findByPerUnidad_PerUnidadPKAndEstadoInfolaboral(docDocumento.getPerUnidad().getPerUnidadPK(), Dominios.PER_ESTADO_ACTIVO);
        if(perInfolaboralAnterior!=null){
            perInfolaboralAnterior.setNroMenores18(menores18);
            perInfolaboralAnterior.setNroMayores60(mayores60);
            infoLaboralRepository.save(perInfolaboralAnterior);
        }

        //guardaAlertas
        DocAlertaDefinicion docAlertaDefinicion= new DocAlertaDefinicion();
        if(alertas==null)
            alertas= new ArrayList<DocAlerta>();
        else
            docAlertaDefinicion = alertaDefinicionRepository.findByDocDefinicion_DocDefinicionPK(docDocumento.getDocDefinicion().getDocDefinicionPK());
        for (DocAlerta docAlerta : alertas) {
            docAlerta.setEstadoAlerta("Estado Alerta");  //revisar que utilizar
            docAlerta.setCodAlerta(docAlertaDefinicion);
            docAlerta.setIdDocumento(docDocumento);
            docAlerta.setIdAlerta(utils.valorSecuencia("DOC_ALERTA_SEC"));
            logger.info("Guarda Alerta: ", alertaRepository.save(docAlerta).getIdAlerta());
        }

        List <DocBinario> binarioList= binarioRepository.findByIdDocumento(docDocumento.getIdDocumento());
        for (DocBinario docBinario:binarioList){
            docBinario.setValidado(true);
            binarioRepository.save(docBinario);
        }
    }

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
            throw new RuntimeException("Ya se registro una modificacion para este documento.");
        }
        ParParametrizacion parametroCodigoBaja = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_ESTADO_BAJA);
        DocDocumento docDocumentoRoe010 = documentoRepository.buscarRoe010PorUnidad(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), parametroCodigoBaja.getDescripcion());
        if (docDocumentoRoe010 == null) {
            throw new RuntimeException("No existe un Roe activo para este empleador.");
        }

        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_IMPRESION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());

        DocDocumento docDocumento = new DocDocumento();
        docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);

        docDocumento.setCodEstado(documentoEstadoRepository.findOne("010"));//Estado inicial pendiente
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());

        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("MODIFICACION");//MODIFICACION DEL ROE
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        DocGenerico docGenerico = new DocGenerico();
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        return docDocumento;
    }

    @Override
    public DocDocumento guardarBajaRoe(PerUnidadPK perUnidadPK, DocGenerico docGenerico, String registroBitacora) {
        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_BAJA);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
        //
        DocDocumento docDocumento = new DocDocumento();
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);
        docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());
        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);

        if (docGenerico.getCadena03() == null || docGenerico.getCadena03().equals("")) {
            docDocumento.setTipoMedioRegistro("DEFINITIVA");//BAJA DEFINITIVA
        } else {
            docDocumento.setTipoMedioRegistro("TEMPORAL");//BAJA TEMPORAL
        }
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        ///Cambiando el estado del documento roe 010 perteneciente a la unidad
        ParParametrizacion parametroCodigoBaja = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_ESTADO_BAJA);
        DocDocumento docDocumentoRoe010 = documentoRepository.buscarRoe010PorUnidad(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), parametroCodigoBaja.getDescripcion());
        docDocumentoRoe010.setCodEstado(documentoEstadoRepository.findOne(parametroCodigoBaja.getDescripcion()));
        docDocumentoRoe010 = documentoRepository.save(docDocumentoRoe010);
        return docDocumento;
    }

    @Override
    public DocDocumento guardarActualizaRoe(DocDocumento docDocumento, DocGenerico docGenerico, String registroBitacora) {
        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_MODIFICACION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);

        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());
        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro(docDefinicion.getTipoGrupoDocumento());
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));
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
        int numeroInicio = 3;
        if (codDocumento.equals("LC1010") || codDocumento.equals("LC1020") || codDocumento.equals("LC1021")) {
            numeroInicio = 2;
        }
        for (int i = numeroInicio; i < codNumeroS.length(); i++) {
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
            String vAux = "" + verificacion;
            verificacion = new Long((Integer.valueOf("" + vAux.charAt(0)) + Integer.valueOf("" + vAux.charAt(1))));
        }
        //
        numeracion.setUltimoNumero(numeracion.getUltimoNumero() + 1);
        numeracionRepository.save(numeracion);
        //
        Formatter fmtVerificacion = new Formatter();
        fmtVerificacion.format("%01d", verificacion);
        return (new Long("" + codNumero + numeroFormato + fmtVerificacion.toString()));
    }

    public String generateReport(String nomArchivo, String jasper, HashMap<String, Object> parametros) throws ClassNotFoundException, IOException, JRException {
        List<String> lista = new ArrayList<String>();
        lista.add("nadaQueVer");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String rutaWebapp = servletContext.getRealPath("/");
        String rutaPdf = "/reportes/temp/" + nomArchivo;
        String nombrePdf = rutaWebapp + rutaPdf;
        JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(rutaWebapp + jasper));
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(lista));
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(nombrePdf));
        exporter.exportReport();
        return nombrePdf;
    }

    @Override
    public List<DocDocumento> listarPlanillasTrimestrales(String idEmpleador, Date fechaDesde, Date fechaHasta, String codDocumento) {
        return documentoRepository.listarPlanillaALaFecha(idEmpleador, fechaDesde, fechaHasta, codDocumento);
    }

    @Override
    public List<DocDocumento> listarDocumentosPorPersonaUnidad(PerUnidadPK perUnidadPK, Date fechaHasta, Date fechaPlazo) {
        return documentoRepository.listarDocumentosTrimSmPorUnidad(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), fechaHasta, fechaPlazo);
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
            //docDocumentoAdicional.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
            docDocumentoAdicional.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicionAdicional.getDocDefinicionPK().getCodDocumento(), (short) 1));
            docDocumentoAdicional = documentoRepository.save(docDocumentoAdicional);
            //
            DocGenerico docGenericoAdicional = new DocGenerico();
            docGenericoAdicional.setCadena08(docGenerico.getCadena08());
            docGenericoAdicional.setCadena09(docGenerico.getCadena09());
            docGenericoAdicional.setIdDocumento(docDocumentoAdicional);
            docGenericoAdicional.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
            docGenericoRepository.save(docGenericoAdicional);
//            PerUnidad unidad = unidadRepository.findOne(docDocumentoAdicional.getPerUnidad().getPerUnidadPK());
//            unidad.setTipoUnidad(String.valueOf(docDocumentoAdicional.getNumeroDocumento()));
//            unidadRepository.save(unidad);
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
            if (parametroDocDefinicionAdicional != null && !parametroDocDefinicionAdicional.equals("")) {
                docDocumento.setTipoMedioRegistro("REGISTRO");//SOLICITUD DE IMPRESION DEL ROE
            } else {
                docDocumento.setTipoMedioRegistro("SOLICITUD");//SOLICITUD DE IMPRESION DEL ROE
            }

            System.out.println("codEstadoDocumento: " + docDefinicion.getDocDefinicionPK().getCodDocumento());
            docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));

            docDocumento = documentoRepository.save(docDocumento);
        } else {
            //Si ya tiene un documento actualizara codEstado de 010 al q tenga en docDefinicion ej 013
            docDocumento = documentoRepository.findOne(idDocumento);
            guardarCambioEstado(docDocumento, docDefinicion.getCodEstado().getCodEstado(), registroBitacora, "LLENADO DEL COMPROBANTE DE DEPOSITO");
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

    @Override
    public boolean validarReactivacionRoe(PerUnidadPK perUnidadPK) {
        List<DocDocumento> lista = documentoRepository.listarPorPersona(perUnidadPK.getIdPersona());
        if (lista == null || lista.size() == 0) {
            throw new RuntimeException("No se encontro un Roe para este empleador.");
        }
        ParParametrizacion parametroCodigoBaja = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_ESTADO_BAJA);
        DocDocumento docDocumentoRoe010 = documentoRepository.buscarRoe010PorUnidad(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), parametroCodigoBaja.getDescripcion());
        if (docDocumentoRoe010 != null) {
            throw new RuntimeException("Ya existe un Roe activo para este empleador.");
        }
        return true;
    }

    @Override
    public DocDocumento guardarReactivacionRoe(DocGenerico docGenerico, PerUnidadPK perUnidadPK, String registroBitacora) {
        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_REACTIVACION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
        //
        DocDocumento docDocumento = new DocDocumento();
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);
        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());
        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("N/A");//SIN TIPO
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicion.getDocDefinicionPK().getCodDocumento(), (short) 1));
        docDocumento = documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        //
        ParParametrizacion parParametrizacionAdicional = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_INSCRIPCION);
        DocDefinicion docDefinicionAdicional = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacionAdicional.getDescripcion());
        DocDocumento docDocumentoAdicional = new DocDocumento();
        docDocumentoAdicional.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumentoAdicional.setDocDefinicion(docDefinicionAdicional);
        docDocumentoAdicional.setCodEstado(documentoEstadoRepository.findOne(docDefinicionAdicional.getCodEstado().getCodEstado()));//Estado inicial
        docDocumentoAdicional.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        docDocumentoAdicional.setFechaDocumento(new Date());
        docDocumentoAdicional.setFechaReferenca(new Date());
        docDocumentoAdicional.setFechaBitacora(new Date());
        docDocumentoAdicional.setRegistroBitacora(registroBitacora);
        docDocumentoAdicional.setTipoMedioRegistro("REACTIVACION");//MODIFICACION DEL ROE
        docDocumentoAdicional.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicionAdicional.getDocDefinicionPK().getCodDocumento(), (short) 1));
        docDocumentoAdicional = documentoRepository.save(docDocumentoAdicional);
        //
        DocGenerico docGenericoAdicional = new DocGenerico();
        docGenericoAdicional.setCadena08(docGenerico.getCadena08());
        docGenericoAdicional.setCadena09(docGenerico.getCadena09());
        docGenericoAdicional.setIdDocumento(docDocumentoAdicional);
        docGenericoAdicional.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenericoAdicional);
        //
        ParParametrizacion parParametrizacionImpresion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_IMPRESION);
        DocDefinicion docDefinicionImpresion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacionImpresion.getDescripcion());

        DocDocumento docDocumentoImpresion = new DocDocumento();
        docDocumentoImpresion.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        //
        docDocumentoImpresion.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumentoImpresion.setDocDefinicion(docDefinicionImpresion);
        docDocumentoImpresion.setCodEstado(documentoEstadoRepository.findOne("010"));//Estado inicial pendiente
        docDocumentoImpresion.setFechaDocumento(new Date());
        docDocumentoImpresion.setFechaReferenca(new Date());
        docDocumentoImpresion.setFechaBitacora(new Date());
        docDocumentoImpresion.setRegistroBitacora(registroBitacora);
        docDocumentoImpresion.setTipoMedioRegistro("REACTIVACION");//MODIFICACION DEL ROE

        docDocumentoImpresion.setNumeroDocumento(actualizarNumeroDeOrden(docDefinicionImpresion.getDocDefinicionPK().getCodDocumento(), (short) 1));
        docDocumentoImpresion = documentoRepository.save(docDocumentoImpresion);
        //
        DocGenerico docGenericoImpresion = new DocGenerico();
        docGenericoImpresion.setIdDocumento(docDocumentoImpresion);
        docGenericoImpresion.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenericoImpresion);
        //
        return docDocumento;
    }

    public List<DocDocumento> listarDocumentosParaRectificar(String idPersona, String codDocumento) {
        return documentoRepository.listarDocumentosParaRectificar(idPersona, codDocumento);
    }

    public boolean existeRoe(String idPersona) {
        ParParametrizacion parParametrizacion = parametrizacionRepository.obtenerParametro(Dominios.DOM_DOCUMENTO, Dominios.PAR_DOCUMENTO_ROE_INSCRIPCION);
        DocDefinicion docDefinicion = definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
        List<DocDocumento> listaDocumentos = documentoRepository.listarPorDocDefinicionYCodEstado(idPersona, docDefinicion.getDocDefinicionPK().getCodDocumento(), docDefinicion.getCodEstado().getCodEstado());
        if (listaDocumentos == null || listaDocumentos.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<DocDocumento> listarDocumentosPorpersonaUnidadFechasCodDocumento(String idPersona, Date fechaDesde, Date fechaHasta, String codDocumento) {
        return documentoRepository.listarDocumentosPorpersonaUnidadFechasCodDocumento(idPersona, fechaDesde, fechaHasta, codDocumento);
    }

    public DocDocumento buscarPorUnindad(PerUnidadPK perUnidadPK){
        return documentoRepository.buscarPorUnindad(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad());
    }

    public List<DocDocumento> listarDocumentosPorpersonaUnidadFechasCodDocumentos(String idPersona, Date fechaDesde, Date fechaHasta, String codDocumento1, String codDocumento2) {
        return documentoRepository.listarDocumentosPorpersonaUnidadFechasCodDocumentos(idPersona, fechaDesde, fechaHasta, codDocumento1, codDocumento2);
    }

    public List<DocDocumento> listarDocumentosPorUnidadCodFechaHastaPlazo2(PerUnidadPK perUnidadPK, String codDocumento, Date fechaHasta, Date fechaPlazo2) {
        return documentoRepository.listarDocumentosPorUniCodFecha(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), codDocumento, fechaHasta, fechaPlazo2);
    }

    public List<DocDocumento> listarDocumentosPorPersonaUnidadFechasHastaPlazoCodDocumentos(PerUnidadPK perUnidadPK,Date fechaHasta, Date fechaPlazo2, String codDocumento1, String codDocumento2) {
        return documentoRepository.listarDocumentosPorPersonaUnidadFechasHastaPlazoCodDocumentos(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad(), fechaHasta, fechaPlazo2, codDocumento1, codDocumento2);
    }

    public List<DocDocumento> listarDocumentosPorPersonaEntreFechasTrim(String idPersona,Date fechaHasta, Date fechaPlazo) {
        return documentoRepository.listarDocumentosPorPersonaEntreFechasTrim(idPersona, fechaHasta, fechaPlazo);
    }

    public List<DocDocumento> listarDocumentosPorPersonaEntreFechasAgui(String idPersona,Date fechaHasta, Date fechaPlazo) {
        return documentoRepository.listarDocumentosPorPersonaEntreFechasAgui(idPersona, fechaHasta, fechaPlazo);
    }
}