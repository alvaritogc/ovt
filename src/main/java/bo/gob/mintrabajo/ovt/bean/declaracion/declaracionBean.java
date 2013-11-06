package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/29/13
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class declaracionBean implements Serializable {


    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(declaracionBean.class);
    private Long idUsuario;
    private String idPersona;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService idDocumentoService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;
    @ManagedProperty(value = "#{entidadService}")
    private IEntidadService iEntidadService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{vperPersonaService}")
    private IVperPersonaService iVperPersonaService;

    private int parametro;
    private List<ParObligacionCalendario> parObligacionCalendarioLista;
    private List<ParEntidad> parEntidadLista;
    private List<DocDocumento> docDocumentoList;
    private PerPersona perPersona;
    private VperPersona vperPersona;
    private DocPlanilla docPlanilla;
    private boolean esRectificatorio;
    private Date fechaOperacionAux;
    private Long numeroOrden;

    private String fechaTexto;
    private boolean temporalBoolean;
    private PerPersona persona;
    private Date fechaTemp = new Date();

    private String textoBenvenida;
    private DocDocumento documento;
    private String periodo;
    private DocBinario binario;
    private boolean habilita = true;
    private List<DocBinario> listaBinarios;
    private UsrUsuario usuario;
    private UploadedFile file;
    private String nombres[]= new String[3];
    //
    private boolean estaDeclarado;
    private Long idEntidadSalud;

    private Long idRectificatorio;

    @PostConstruct
    public void ini() {
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null)
                parametro = 1;
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null|| !((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p").equals(""))
                parametro = Integer.valueOf(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p"));
        }catch (Exception e) {
//            e.printStackTrace();
            parametro = 1;
        }

        if (parametro==3)
            esRectificatorio=true;
        if(parametro!=1)
            habilita=false;

        idPersona = (String) session.getAttribute("idEmpleador");
        logger.info("buscando persona:"+idPersona);
        persona = iPersonaService.buscarPorId(idPersona);
        //
        logger.info("Realizando la carga de Persona ...");
        idUsuario = (Long) session.getAttribute("idUsuario");
        Long temp = Long.valueOf(idUsuario);
        usuario = iUsuarioService.findById(idUsuario);
        perPersona = iPersonaService.buscarPorId(idPersona);
        docPlanilla = new DocPlanilla();
        docPlanilla.setHaberBasico(BigDecimal.ZERO);
        docPlanilla.setBonoAntiguedad(BigDecimal.ZERO);
        docPlanilla.setBonoProduccion(BigDecimal.ZERO);
        docPlanilla.setSubsidioFrontera(BigDecimal.ZERO);
        docPlanilla.setLaborExtra(BigDecimal.ZERO);
        docPlanilla.setOtrosBonos(BigDecimal.ZERO);
        docPlanilla.setRciva(BigDecimal.ZERO);
        docPlanilla.setAporteAfp(BigDecimal.ZERO);
        docPlanilla.setOtrosDescuentos(BigDecimal.ZERO);
        docPlanilla.setMontoAsegCaja(BigDecimal.ZERO);
        docPlanilla.setMontoAsegAfp(BigDecimal.ZERO);
        //** Controlamos que no puedan acceder a una fecha anterior a la actual  **//
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaTexto = sdf.format(fechaTemp);
        obtenerPeriodoLista();
        obtenerEntidad();
        //** Obtenemos de la Vista a la persona **//
        vperPersona = iVperPersonaService.cargaVistaPersona(perPersona.getIdPersona());
        binario= new DocBinario();
        listaBinarios = new ArrayList<DocBinario>();
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
    }

    public void cargar() {
        generaDocumento();
        verEstadoPlanilla();
        cargarListaPorNumeros();
    }

    public void cargarListaPorNumeros(){
        docDocumentoList= new ArrayList<DocDocumento>();
        docDocumentoList= iDocumentoService.listarPorPersona(idPersona);
    }

    public void verEstadoPlanilla(){
        List<DocDocumento> listaDocumentos;
        try{
            listaDocumentos=iDocumentoService.listarPorPersona(idPersona);
            if(listaDocumentos==null){
                listaDocumentos=new ArrayList<DocDocumento>();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            listaDocumentos=new ArrayList<DocDocumento>();
        }
        estaDeclarado=false;
        for(DocDocumento documento:listaDocumentos){
            if(documento.getCodEstado().getDescripcion().toLowerCase().equals("declarado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("observado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("finalizado")){
                estaDeclarado=true;
            }
        }
    }

    public void generaDocumento(){
        logger.info("generaDocumento()");
        documento = new DocDocumento();
        documento.setIdPersona(persona);
        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(persona.getIdPersona(), 0L)));
        documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1010", (short) 1)));
        documento.setFechaDocumento(new Date());
        documento.setCodEstado(iDocumentoEstadoService.buscarPorId("110"));
        documento.setFechaReferenca(new Date());
        documento.setTipoMedioRegistro("DDJJ");
        documento.setFechaBitacora(new Date());
        documento.setRegistroBitacora(usuario.getUsuario());
    }

    public void generaPlanilla(){
        logger.info("generaPlanilla()");
        docPlanilla.setIdEntidadBanco(iEntidadService.buscaPorId(new Long("2")));
        docPlanilla.setIdEntidadSalud(iEntidadService.buscaPorId(idEntidadSalud));
        docPlanilla.setFechaOperacion(fechaOperacionAux);
        switch (parametro){
            case 1:
                docPlanilla.setTipoPlanilla("DDJJ");
                break;
            case 2:
                docPlanilla.setTipoPlanilla("DDJJSM");
                break;
            case 3:
                docPlanilla.setTipoPlanilla("DDJJRECT");
                break;
            default:
                docPlanilla.setTipoPlanilla("");
                break;
        }
    }

    public void upload(FileUploadEvent evento){
        logger.info("upload(FileUploadEvent evento)");
        file = evento.getFile();
        nombres[listaBinarios.size()]= file.getFileName();
        try{
            binario = new DocBinario();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Timestamp(new Date().getTime()));
            binario.setRegistroBitacora(usuario.getUsuario());
            binario.setBinario(file.getContents());
            listaBinarios.add(binario);

            if(listaBinarios.size()==3)
                habilita=false;
        }catch (Exception e){
            habilita=true;
            e.printStackTrace();
        }
    }

    public String guardaDocumentoPlanillaBinario(){
        try{
            logger.info("Guardando documento, binario y planilla");
            logger.info(documento.toString());
            logger.info(listaBinarios.toString());
            logger.info(docPlanilla.toString());
            generaPlanilla();

//            valida(listaBinarios);

            idDocumentoService.guardaDocumentoPlanillaBinario(documento, docPlanilla, listaBinarios);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Guardado correctamente"));
            return "irEscritorio";
        }catch (Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el formulario",""));
            return null;
        }
    }

    public String irInicio(){
        idPersona=(String)session.getAttribute("idPersona");
        String idEmpleador=(String)session.getAttribute("idEmpleador");
        if(idPersona!=null && idEmpleador!=null && idPersona.equals(idEmpleador)){
            return "irBienvenida";
        }
        else{
            return "irEmpleadorBusqueda";
        }
    }

    public void obtenerPeriodoLista(){
        parObligacionCalendarioLista = new ArrayList<ParObligacionCalendario>();
        parObligacionCalendarioLista = iObligacionCalendarioService.listaObligacionCalendario();
    }

//    public void validaArchivo(){
//
//
//
//        try {
//            for(DocBinario docBinario:listaBinarios){
//                CsvReader csvReader;
////                if(!FilenameUtils.getExtension(docBinario.getTipoDocumento()).toUpperCase().equals(Dominios.EXTENSION_CSV)){
////                    OutputStream output= XlsToCSV.xlsToCsv(new ByteArrayInputStream(docBinario.getBinario()));
////                    csvReader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(((ByteArrayOutputStream) output).toByteArray())));
////                }
////                else
////                    csvReader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(docBinario.getBinario())));
//
//                csvReader= new CsvReader("/home/rvelasquez/Desktop/planillaComercialPrueba.csv");
//
//                csvReader.readRecord();
//                List<String> columnasTitulo =new ArrayList<String>();
//                for (int i=0;i<csvReader.getColumnCount();i++)
//                    columnasTitulo.add(csvReader.get(i));
//
//
//
//
//
//
//                while (csvReader.readRecord()) {
//
//                }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }










//    public boolean valida(List<DocBinario> listaBinarios){
//        List<String> errores = new ArrayList<String>();
//        String errorFilas = "";
//        String errorCampos = "";
//        String mensaje;
//        List <DocPlanillaDetalle>docPlanillaDetalleList = new ArrayList<DocPlanillaDetalle>();
//        boolean verifica =false;
//        try {
//            for(DocBinario docBinario:listaBinarios){
//                CsvReader csvReader;
//                if(!FilenameUtils.getExtension(docBinario.getTipoDocumento()).toUpperCase().equals(Dominios.EXTENSION_CSV)){
//                    OutputStream output= XlsToCSV.xlsToCsv(new ByteArrayInputStream(docBinario.getBinario()));
//                    csvReader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(((ByteArrayOutputStream) output).toByteArray())));
//                }
//                else
//                    csvReader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(docBinario.getBinario())));
//
//
//                int fila = 0;
//                csvReader.readRecord();
//                while (csvReader.readRecord()) {
//                    fila++;
//
//                    int columnCount = csvReader.getColumnCount();
//                    if (columnCount != 33) {
//                        errorFilas = "La fila " + fila + ", tiene " + columnCount + " columnas, esto no es aceptado como un documento válido: o el SEPARADOR = " + csvReader.getDelimiter() + " No es el adecuado ";
//                        errorFilas = errorFilas + fila + ", ";
//                        continue;
//                    }
//
//                    DocPlanillaDetalle docPlanillaDetalle = new DocPlanillaDetalle();
//
////                    docPlanillaDetalle.setIdPlanilla(docPlanilla);
////                    docPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_PLANILLA_DETALLE_SEC"));
//
//
//                    docPlanillaDetalle.setTipoDocumento(csvReader.get(1));
//
//                    if (!UtilityData.isInteger(csvReader.get(2)))
//                        errorCampos = errorCampos + "Número de documento de identidad no es un número válido, ";
//                    else
//                        docPlanillaDetalle.setNumeroDocumento(csvReader.get(2));
//
//                    docPlanillaDetalle.setExtencionDocumento(csvReader.get(3));
//                    docPlanillaDetalle.setAfp(csvReader.get(4));
//                    docPlanillaDetalle.setNuaCua(csvReader.get(5));
//                    docPlanillaDetalle.setNombre(csvReader.get(6)+" "+csvReader.get(7)+" "+csvReader.get(8)+" "+csvReader.get(9)+" "+csvReader.get(10));
//                    docPlanillaDetalle.setNacionalidad(csvReader.get(11));
//                    docPlanillaDetalle.setFechaNacimiento(csvReader.get(12));
//                    docPlanillaDetalle.setSexo(csvReader.get(13));
//                    docPlanillaDetalle.setJubilado(csvReader.get(14));
//                    docPlanillaDetalle.setClasificacionLaboral(csvReader.get(15));
//                    docPlanillaDetalle.setCargo(csvReader.get(16));
//                    docPlanillaDetalle.setFechaIngreso(csvReader.get(17));
//
//                    if (!UtilityData.isInteger(csvReader.get(18))&&!csvReader.get(18).equals(""))
//                        errorCampos = errorCampos + "Horas pagadas (día) debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHorasPagadasDia(csvReader.get(18));
//
//                    if (!UtilityData.isInteger(csvReader.get(19)))
//                        errorCampos = errorCampos + "Días haber básico debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDiasHaberBasico(csvReader.get(19));
//
//                    if (!UtilityData.isInteger(csvReader.get(20)))
//                        errorCampos = errorCampos + "Días pagados (mes) debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDiasPagados(csvReader.get(20));
//
//                    if (!UtilityData.isInteger(csvReader.get(21))&&!csvReader.get(21).equals(""))
//                        errorCampos = errorCampos + "Nº de dominicales debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDominicalMes(csvReader.get(21));
//
//                    if (!UtilityData.isDecimal(csvReader.get(22))&&!csvReader.get(22).equals(""))
//                        errorCampos = errorCampos + "Domingos trabajados debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDominicalTrabajado(csvReader.get(22));
//
//                    if (!UtilityData.isInteger(csvReader.get(23))&&!csvReader.get(23).equals(""))
//                        errorCampos = errorCampos + "Horas extra debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHorasExtra(csvReader.get(23));
//
//                    if (!UtilityData.isInteger(csvReader.get(24))&&!csvReader.get(24).equals(""))
//                        errorCampos = errorCampos + "Horas de recargo nocturno debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHorasNocturno(csvReader.get(24));
//
//                    if (!UtilityData.isInteger(csvReader.get(25))&&!csvReader.get(25).equals(""))
//                        errorCampos = errorCampos + "Horas extra dominicales debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHorasDominicales(csvReader.get(25));
//
//                    if (!UtilityData.isDecimal(csvReader.get(26))&&!csvReader.get(26).equals(""))
//                        errorCampos = errorCampos + "Haber básico debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHaberBasico(csvReader.get(26));
//
//                    if (!UtilityData.isDecimal(csvReader.get(27))&&!csvReader.get(27).equals(""))
//                        errorCampos = errorCampos + "Salario dominical debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHaberBasico(csvReader.get(27));
//
//                    if (!UtilityData.isDecimal(csvReader.get(28))&&!csvReader.get(28).equals(""))
//                        errorCampos = errorCampos + "Monto pagado por domingo trabajado debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHaberBasico(csvReader.get(28));
//
//                    if (!UtilityData.isDecimal(csvReader.get(29)))
//                        errorCampos = errorCampos + "Monto pagado por horas extra debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setMontoHorasExtra(csvReader.get(29));
//
//                    if (!UtilityData.isDecimal(csvReader.get(30))&&!csvReader.get(30).equals(""))
//                        errorCampos = errorCampos + "Monto pagado por horas nocturnas debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHaberBasico(csvReader.get(30));
//
//                    if (!UtilityData.isDecimal(csvReader.get(31))&&!csvReader.get(31).equals(""))
//                        errorCampos = errorCampos + "Monto pagado por horas extra dominicales debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setHaberBasico(csvReader.get(31));
//
//                    if (!UtilityData.isDecimal(csvReader.get(32)))
//                        errorCampos = errorCampos + "Bono de antigüedad debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setBonoAntiguedad(csvReader.get(32));
//
//                    if (!UtilityData.isDecimal(csvReader.get(33))&&!csvReader.get(33).equals(""))
//                        errorCampos = errorCampos + "Bono de producción debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setBonoProduccion(csvReader.get(33));
//
//                    if (!UtilityData.isDecimal(csvReader.get(34))&&!csvReader.get(34).equals(""))
//                        errorCampos = errorCampos + "Subsidio de frontera debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setBonoFrontera(csvReader.get(34));
//
//                    if (!UtilityData.isDecimal(csvReader.get(35)))
//                        errorCampos = errorCampos + "Otros bonos o pagos debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setBonoOtros(csvReader.get(35));
//
//                    if (!UtilityData.isDecimal(csvReader.get(36)))
//                        errorCampos = errorCampos + "Total ganado debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setTotalGanado(csvReader.get(36));
//
//                    if (!UtilityData.isDecimal(csvReader.get(37)))
//                        errorCampos = errorCampos + "Aporte a las AFPs debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDescuentoAfp(csvReader.get(37));
//
//                    if (!UtilityData.isDecimal(csvReader.get(38)))
//                        errorCampos = errorCampos + "RC-IVA debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDescuentoRciva(csvReader.get(38));
//
//                    if (!UtilityData.isDecimal(csvReader.get(39))&&!csvReader.get(39).equals(""))
//                        errorCampos = errorCampos + "Otros descuentos debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDescuentoOtro(csvReader.get(39));
//
//                    if (!UtilityData.isDecimal(csvReader.get(40)))
//                        errorCampos = errorCampos + "Total descuentos debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setDescuentosTotal(csvReader.get(40));
//
//                    if (!UtilityData.isDecimal(csvReader.get(41)))
//                        errorCampos = errorCampos + "Líquido pagable debe ser un número válido, ";
//                    else
//                        docPlanillaDetalle.setLiquidoPagable(csvReader.get(41));
//
//                    docPlanillaDetalleList.add(docPlanillaDetalle);
//
//                    if (errorCampos.length() > 0) {
//                        mensaje = "en la fila " + (fila) + ": " + errorCampos;
//                        errores.add(mensaje);
//                        errorCampos = "";
//                    }
//                }
//            }
//            if (!errorFilas.equals("")) {
//                mensaje = "Las siguientes filas: " + errorFilas + " no tienen la cantidad de campos requerido";
//                errores.add(0, mensaje);
//            }else{
//                if(errores.size()>0){
//                    for(String error:errores)
//                        System.out.println(error);
//                }
//
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return verifica;
//    }

    //** Obtenemos todos las entidades de la tabla ENTIDAD **//
    public void obtenerEntidad() {
        parEntidadLista = iEntidadService.listaEntidad();
    }

    //** Getters and Setters **//
    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public PerPersona getPerPersona() {
        return perPersona;
    }

    public void setPerPersona(PerPersona perPersona) {
        this.perPersona = perPersona;
    }

    public DocPlanilla getDocPlanilla() {
        return docPlanilla;
    }

    public void setDocPlanilla(DocPlanilla docPlanilla) {
        this.docPlanilla = docPlanilla;
    }

    public IEntidadService getiEntidadService() {
        return iEntidadService;
    }

    public void setiEntidadService(IEntidadService iEntidadService) {
        this.iEntidadService = iEntidadService;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    public String getFechaTexto() {
        return fechaTexto;
    }

    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    public boolean isTemporalBoolean() {
        return temporalBoolean;
    }

    public void setTemporalBoolean(boolean temporalBoolean) {
        this.temporalBoolean = temporalBoolean;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public Date getFechaTemp() {
        return fechaTemp;
    }

    public void setFechaTemp(Date fechaTemp) {
        this.fechaTemp = fechaTemp;
    }

    public boolean isEsRectificatorio() {
        return esRectificatorio;
    }

    public void setEsRectificatorio(boolean esRectificatorio) {
        this.esRectificatorio = esRectificatorio;
    }

    public Date getFechaOperacionAux() {
        return fechaOperacionAux;
    }

    public void setFechaOperacionAux(Date fechaOperacionAux) {
        this.fechaOperacionAux = fechaOperacionAux;
    }

    public IObligacionCalendarioService getiObligacionCalendarioService() {
        return iObligacionCalendarioService;
    }

    public void setiObligacionCalendarioService(IObligacionCalendarioService iObligacionCalendarioService) {
        this.iObligacionCalendarioService = iObligacionCalendarioService;
    }

    public List<ParObligacionCalendario> getParObligacionCalendarioLista() {
        return parObligacionCalendarioLista;
    }

    public void setParObligacionCalendarioLista(List<ParObligacionCalendario> parObligacionCalendarioLista) {
        this.parObligacionCalendarioLista = parObligacionCalendarioLista;
    }

    public Long getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Long numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public List<ParEntidad> getParEntidadLista() {
        return parEntidadLista;
    }

    public void setParEntidadLista(List<ParEntidad> parEntidadLista) {
        this.parEntidadLista = parEntidadLista;
    }

    public VperPersona getVperPersona() {
        return vperPersona;
    }

    public void setVperPersona(VperPersona vperPersona) {
        this.vperPersona = vperPersona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IDocumentoService getIdDocumentoService() {
        return idDocumentoService;
    }

    public void setIdDocumentoService(IDocumentoService idDocumentoService) {
        this.idDocumentoService = idDocumentoService;
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public String getTextoBenvenida() {
        return textoBenvenida;
    }

    public void setTextoBenvenida(String textoBenvenida) {
        this.textoBenvenida = textoBenvenida;
    }

    public DocDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(DocDocumento documento) {
        this.documento = documento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public DocBinario getBinario() {
        return binario;
    }

    public void setBinario(DocBinario binario) {
        this.binario = binario;
    }

    public boolean isHabilita() {
        return habilita;
    }

    public void setHabilita(boolean habilita) {
        this.habilita = habilita;
    }

    public List<DocBinario> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinario> listaBinarios) {
        this.listaBinarios = listaBinarios;
    }

    public UsrUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public boolean isEstaDeclarado() {
        return estaDeclarado;
    }

    public void setEstaDeclarado(boolean estaDeclarado) {
        this.estaDeclarado = estaDeclarado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String[] getNombres() {
        return nombres;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public List<DocDocumento> getDocDocumentoList() {
        return docDocumentoList;
    }

    public void setDocDocumentoEntityList(List<DocDocumento> docDocumentoList) {
        this.docDocumentoList = docDocumentoList;
    }

    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public IVperPersonaService getiVperPersonaService() {
        return iVperPersonaService;
    }

    public void setiVperPersonaService(IVperPersonaService iVperPersonaService) {
        this.iVperPersonaService = iVperPersonaService;
    }

    public int getParametro() {
        return parametro;
    }

    public void setParametro(int parametro) {
        this.parametro = parametro;
    }

    public Long getIdEntidadSalud() {
        return idEntidadSalud;
    }

    public void setIdEntidadSalud(Long idEntidadSalud) {
        this.idEntidadSalud = idEntidadSalud;
    }

    public Long getIdRectificatorio() {
        return idRectificatorio;
    }

    public void setIdRectificatorio(Long idRectificatorio) {
        this.idRectificatorio = idRectificatorio;
    }
}