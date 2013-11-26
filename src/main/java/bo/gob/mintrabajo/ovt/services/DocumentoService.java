package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.ejb.TransactionAttribute;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService{
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
    private final IUtilsService utils;
    private HashMap<String,Object> parametros = new HashMap<String,Object>();
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
                            IUtilsService utils) {
        this.documentoRepository = documentoRepository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
        this.logEstadoRepository = logEstadoRepository;
        this.planillaDetalleRepository = planillaDetalleRepository;
        this.docGenericoRepository=docGenericoRepository;
        this.definicionRepository=definicionRepository;
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
        return documentoRepository.findByPerUnidad_PerPersona_IdPersonaOrderByIdDocumentoDesc(idPersona);
    }

    
     @Override
    public DocDocumento guardarCambioEstado(DocDocumento documento, String codEstadoFinal,String idUsuario,String observacionLogEstado) {
        DocLogEstado logEstado=new DocLogEstado();
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

    public void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios, List<DocPlanillaDetalle> docPlanillaDetalles){
        //guarda documento
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento=documentoRepository.save(docDocumento);

        //guarda planilla
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(utils.valorSecuencia("DOC_PLANILLA_SEC"));
        docPlanilla=planillaRepository.save(docPlanilla);

        //guardaPlanillaDetalles
        for(DocPlanillaDetalle elemPlanillaDetalle:docPlanillaDetalles){
            elemPlanillaDetalle.setIdPlanilla(docPlanilla);
            elemPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_DETALLE_SEC"));
            planillaDetalleRepository.save(elemPlanillaDetalle);
        }

        //guarda binarios
        int idBinario= 1;
        for(DocBinario elementoBinario:listaBinarios){
            elementoBinario.setDocDocumento(docDocumento);
            elementoBinario.setDocBinarioPK(new DocBinarioPK(idBinario++, docDocumento.getIdDocumento()));
            binarioRepository.save(elementoBinario);
        }
    }
    
    @Override
    public DocDocumento guardarImpresionRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora, DocDefinicion docDefinicion){//, VperPersona vperPersona, Long idUsuarioEmpleador){
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);
        
        docDocumento.setCodEstado(documentoEstadoRepository.findOne(docDefinicion.getCodEstado().getCodEstado()));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());
        
        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("DDJJ");
        
        
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento=documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);

//        generaReporteROE(vperPersona, String.valueOf(idUsuarioEmpleador), String.valueOf(vperPersona.getIdPersona()));

        //Actualizar el atributo nroOtro de PerUnidad     por aquiroz
       PerUnidad unidad= unidadRepository.findOne(docDocumento.getPerUnidad().getPerUnidadPK());
        System.out.println("========>>> UNIDAD "+unidad);
        System.out.println("========>>> CAMBIANDO EL VALOR nro de documento"+docDocumento.getNumeroDocumento());
        unidad.setTipoUnidad(String.valueOf(docDocumento.getNumeroDocumento()));
        System.out.println("========>>> MODIFICADO "+unidad);
        unidadRepository.save(unidad);

        return docDocumento;
    }


    @Override
    public List<DocDocumento>  listarRoe013(String idPersona,long idUnidad){
        try {
            return documentoRepository.listarRoe013(idPersona,idUnidad);
        } catch (Exception ex){
           ex.printStackTrace();
            return null;
        }
    }
    public List<DocDocumento>ObtenerRoes(String idPersona,long idUnidad){
        try {
            return documentoRepository.ObtenerRoes(idPersona,idUnidad);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    
    @Override
    public DocDocumento guardarRoeGenerico(PerUnidadPK perUnidadPK ,String registroBitacora){
       List<DocDocumento> lista=documentoRepository.listarRoe013(perUnidadPK.getIdPersona(), perUnidadPK.getIdUnidad());
        if(lista.size()>0){
            throw new RuntimeException("Ya se registro el roe para ese documento");
        }
        
        DocDocumento docDocumento=new DocDocumento();
        docDocumento.setPerUnidad(unidadRepository.findOne(perUnidadPK));
        
        //
        DocDefinicion docDefinicion=definicionRepository.findOne(new DocDefinicionPK("ROE013", (short) 1));
        //
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setDocDefinicion(docDefinicion);
        
        docDocumento.setCodEstado(documentoEstadoRepository.findOne("010"));//Estado inicial
        docDocumento.setFechaDocumento(new Date());
        docDocumento.setFechaReferenca(new Date());
        
        docDocumento.setFechaBitacora(new Date());
        docDocumento.setRegistroBitacora(registroBitacora);
        docDocumento.setTipoMedioRegistro("DDJJ");
        
        
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("ROE012", (short) 1));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento=documentoRepository.save(docDocumento);
        //
        DocGenerico docGenerico=new DocGenerico();
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        return docDocumento;
    }
    
    public DocDocumento guardarBajaRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora){
        DocDefinicion docDefinicion=definicionRepository.findOne(new DocDefinicionPK("ROE012", (short) 1));
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
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento=documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        return docDocumento;
    }
    
    
    
    public DocDocumento guardarActualizaRoe(DocDocumento docDocumento, DocGenerico docGenerico,String registroBitacora){
        DocDefinicion docDefinicion=definicionRepository.findOne(new DocDefinicionPK("ROE013", (short) 1));
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
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden(docDocumento.getCodEstado().getCodEstado(), (short) 1));
        docDocumento=documentoRepository.save(docDocumento);
        //
        docGenerico.setIdDocumento(docDocumento);
        docGenerico.setIdGenerico(utils.valorSecuencia("DOC_GENERICO_SEC"));
        docGenericoRepository.save(docGenerico);
        //
        return docDocumento;
    }



    public long actualizarNumeroDeOrden(String codDocumento, short version) {
        DocNumeracion numeracionBusqueda = new DocNumeracion(new DocNumeracionPK(codDocumento, version));
//        numeracionBusqueda.setCodDocumento(codDocumento);
//        numeracionBusqueda.setVersion(version);
        DocNumeracion numeracion;
        try {
            numeracion = numeracionRepository.findByExample(numeracionBusqueda, null, null, -1, -1).get(0);
        } catch (Exception e) {
            throw new RuntimeException("DocNumeracion no encontrado");
        }
        String codNumeroS =numeracion.getDocNumeracionPK().getCodDocumento();
        String codNumero = "";
        for (int i = 2; i < codNumeroS.length(); i++) {
            codNumero = codNumero + codNumeroS.charAt(i);
        }
        Long numero=new Long(numeracion.getUltimoNumero()+1);
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
        if(verificacion>new Long(9)){
            //System.out.println("verificador > a 9");
            String vAux=""+verificacion;
            verificacion=new Long((Integer.valueOf(""+vAux.charAt(0))+Integer.valueOf(""+vAux.charAt(1))));
            //System.out.println("verificacion: "+verificacion);
        }
        
        //
        numeracion.setUltimoNumero(numeracion.getUltimoNumero()+1);
        numeracionRepository.save(numeracion);
        //
        Formatter fmtVerificacion = new Formatter();
        fmtVerificacion.format("%02d", verificacion);
        return (new Long("" + codNumero + numeroFormato + fmtVerificacion.toString()));
    }


    public String generaReporteLC1010(DocPlanilla docPlanilla, PerPersona persona , DocDocumento docDocumento, PerUnidad perUnidad, VperPersona vperPersona){
        parametros.clear();
        parametros.put("nroOrden", docDocumento.getNumeroDocumento());
        parametros.put("rectificatoria", " ");
        parametros.put("nroRectificatoria", " ");
        parametros.put("totalNacional", "X");
        parametros.put("oficinaCentral", perUnidad.getNombreComercial());
        parametros.put("mesPresentacion", docPlanilla.getParCalendario().getParCalendarioPK().getTipoPeriodo());
        parametros.put("empleadorMTEPS", perUnidad.getNroReferencial());
        parametros.put("razonSocial", persona.getNombreRazonSocial());
        parametros.put("departamento", vperPersona.getDirDepartamento());
        parametros.put("direccion", vperPersona.getDirDireccion());
        parametros.put("telefono", vperPersona.getTelefono());
        parametros.put("patronalSS", perUnidad.getNroCajaSalud());
        parametros.put("ciudadLocalidad", vperPersona.getLocalidad());
        parametros.put("fax", vperPersona.getFax());
        parametros.put("nit", vperPersona.getNroIdentificacion() +"");
        parametros.put("actividadEconomica", vperPersona.getActividadDeclarada());
        parametros.put("zona", vperPersona.getDirZona());
        parametros.put("numero", vperPersona.getDirNroDireccion());
        parametros.put("correoElectronico", vperPersona.getEmail());
        parametros.put("nroAsegurados", docPlanilla.getNroAsegCaja());
        parametros.put("montoAportadoAsegurados",docPlanilla.getMontoAsegCaja());
        parametros.put("gestorSalud", docPlanilla.getIdEntidadSalud().getDescripcion());
        parametros.put("nroAfiliados",docPlanilla.getNroAsegAfp());
        parametros.put("montoAportadoAfiliados",docPlanilla.getMontoAsegAfp());
        parametros.put("haberBasico",docPlanilla.getHaberBasico());
        parametros.put("bonoAntiguedad",docPlanilla.getBonoAntiguedad());
        parametros.put("bonoProduccion",docPlanilla.getBonoProduccion());
        parametros.put("subsidioFrontera",docPlanilla.getSubsidioFrontera());
        parametros.put("laborExtraordinaria",docPlanilla.getLaborExtra());
        parametros.put("otrosBono",docPlanilla.getOtrosBonos());
        parametros.put("aporteAFP",docPlanilla.getAporteAfp());
        parametros.put("rcIVA",docPlanilla.getRciva());
        parametros.put("otrosDescuentos",docPlanilla.getOtrosDescuentos());
        parametros.put("totalMujeres",docPlanilla.getNroM());
        parametros.put("totalVarones",docPlanilla.getNroH());
        parametros.put("mujeresJubiladas",docPlanilla.getNroJubiladosM());
        parametros.put("varonesJubilados",docPlanilla.getNroJubiladosH());
        parametros.put("mujeresExtranjeras",docPlanilla.getNroExtranjerosM());
        parametros.put("varonesExtranjeros",docPlanilla.getNroExtranjerosH());
        parametros.put("mujeresDiscapacidad",docPlanilla.getNroDiscapacidadM());
        parametros.put("varonesDiscapacidad",docPlanilla.getNroDiscapacidadH());
        parametros.put("mujeresContratadas",docPlanilla.getNroContratadosM());
        parametros.put("varonesContratados",docPlanilla.getNroContratadosH());
        parametros.put("mujeresRetiradas",docPlanilla.getNroRetiradosM());
        parametros.put("varonesRetirados",docPlanilla.getNroRetiradosH());
        parametros.put("totalAccidentes",docPlanilla.getNroAccidentes());
        parametros.put("accidentesMuerte",docPlanilla.getNroMuertes());
        parametros.put("enfermedadesTrabajos",docPlanilla.getNroEnfermedades());
//        parametros.put("email",docPlanilla.getIdEntidadBanco()); //----------------;
        Calendar cal = Calendar.getInstance();
        cal.setTime(docPlanilla.getFechaOperacion());
        parametros.put("diaDeposito", cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, 1);
        parametros.put("mesDeposito", cal.get(Calendar.MONTH));
        parametros.put("anioDeposito", cal.get(Calendar.YEAR));
        cal = Calendar.getInstance();
        cal.setTime(docDocumento.getFechaDocumento());
        parametros.put("diaFechaPresentacion", cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, 1);
        parametros.put("mesFechaPresentacion", cal.get(Calendar.MONTH));
        parametros.put("anioFechaPresentacion", cal.get(Calendar.YEAR));
        parametros.put("montoDeposito", docPlanilla.getMontoOperacion());
        parametros.put("nroComprobante",docPlanilla.getNumOperacion());
        parametros.put("nombreEmpleador", vperPersona.getRlNombre());
        parametros.put("nroDocumento", vperPersona.getRlNroIdentidad());
        parametros.put("lugarPresentacion", "Oficina Virtual");
        List<DocBinario> lista= binarioRepository.findByIdDocumento(docDocumento.getIdDocumento());

        for(int i=0;i<3;i++){
            int nroArchivo=i+1;
            parametros.put("archivo"+String.valueOf(nroArchivo), lista!=null && !lista.isEmpty()?lista.get(nroArchivo-1).getTipoDocumento():"");
        }
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        parametros.put("escudoBolivia", servletContext.getRealPath("/")+"/images/escudo.jpg");
        parametros.put("logo",servletContext.getRealPath("/")+"/images/logoMIN.jpg");
        try {
//            generateReport("DDJJ", "/reportes/formularioLC1010V1.jasper");
            return nombrePdf;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR al generar el reporte: "+e.getMessage());
        }
        return null;
    }


    public String generaReporteROE(VperPersona vperPersona, String idUsuario, String idPersona){
        parametros.clear();
        parametros.put("codigoEmpleador", vperPersona.getNroIdentificacion());
        parametros.put("nombreRazonSocial", vperPersona.getNombreRazonSocial());
        parametros.put("departamento", vperPersona.getDirDepartamento());
        parametros.put("domOficina", vperPersona.getDirDireccion());
        parametros.put("repLegal", vperPersona.getRlNombre());
        parametros.put("fechaEmision", (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
        parametros.put("nroUbicaciones", vperPersona.getNroOtro());

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        parametros.put("roe", servletContext.getRealPath("/")+"/images/roe.jpg");

        try {
            nombrePdf="ROE".concat(Util.encriptaMD5(idUsuario.concat(idPersona)))+".pdf";
            HttpServletRequest httpServletRequest = ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext()).getRequest());
            String rutaUrl=(String) httpServletRequest.getRequestURL().toString();
            if(rutaUrl.contains(".xhtml"))
                rutaUrl= rutaUrl.replace("pages/declaracion/impresionRoe.xhtml", "");
            if(rutaUrl.contains(".jsf"))
                rutaUrl= rutaUrl.replace("pages/declaracion/impresionRoe.jsf", "");

            String url=rutaUrl.concat("reportes/temp/")+nombrePdf;
            //genera el QR
            ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();
            file = new File(servletContext.getRealPath("/")+"/images/qr"+UUID.randomUUID()+".png");
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
            //se asigna la imagen QR al reporte
            parametros.put("qr",servletContext.getRealPath("/")+"/images/"+file.getName());
            //manda al metodo generateReport()
//            generateReport(nombrePdf, "/reportes/roe.jasper");
            return nombrePdf;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR al generar el reporte: "+e.getMessage());
        }
        return null;
    }


    public String generateReport(String nomArchivo, String jasper, HashMap<String, Object> parametros) throws ClassNotFoundException, IOException, JRException {
        List<String> lista= new ArrayList<String>();
        lista.add("asd");
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String rutaWebapp = servletContext.getRealPath("/");
        rutaPdf= "/reportes/temp/"+ nomArchivo;
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
    public List<DocDocumento> listarPlanillasTrimestrales(String idEmpleador,Date fechaDesde,Date fechaHasta){
        return documentoRepository.listarPlanillaALaFecha(idEmpleador,fechaDesde, fechaHasta);
    }
}