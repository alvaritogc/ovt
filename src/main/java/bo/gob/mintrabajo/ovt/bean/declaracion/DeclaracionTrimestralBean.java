package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.Util.UtilityData;
import bo.gob.mintrabajo.ovt.Util.XlsToCSV;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import com.csvreader.CsvReader;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
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
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: gmercado
 * Date: 10/29/13
 * Time: 9:34 AM
 */
@ManagedBean
@ViewScoped
public class DeclaracionTrimestralBean implements Serializable {

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DeclaracionTrimestralBean.class);
    private Long idUsuario;
    private String idPersona;
    private Long idUnidad;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
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
    @ManagedProperty(value = "#{planillaDetalleService}")
    private IPlanillaDetalleService iPlanillaDetalleService;
    @ManagedProperty(value = "#{calendarioService}")
    private ICalendarioService iCalendarioService;
    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacionService;

    private int parametro;
    private List<ParObligacionCalendario> parObligacionCalendarioLista;
    private List<ParEntidad> parEntidadLista;
    private List<DocDocumento> docDocumentosParaRectificar;
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

    private boolean estaDeclarado;
    private String estaDeclaradoMensaje;
    private Long idEntidadSalud;

    private boolean verificaValidacion;
    private Long idRectificatorio;
    private List<String> errores = new ArrayList<String>();
    private int tamanioErrores;
    private boolean valor;
    private int tipoEmpresa=1;
    private PerUnidad central;
    private List<PerUnidad> sucursales;
    private PerUnidad unidadSeleccionada;
    private int tamañoSucursales;

    private List<DocPlanillaDetalle> docPlanillaDetalles;
    private String gestion;
    private String trimestre;
    private String e;
    private List<DocAlerta> alertas;
    private int salarioMinimo;
    private String nombreBinario;

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
        if(parametro==2)
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
        docPlanilla.setMontoOperacion(BigDecimal.ZERO);

        salarioMinimo= Integer.valueOf(iParametrizacionService.obtenerParametro(Dominios.DOM_SALARIO, Dominios.PAR_SALARIO_MINIMO).getDescripcion());
        //** Controlamos que no puedan acceder a una fecha anterior a la actual  **//
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaTexto = sdf.format(fechaTemp);
        obtenerEntidad();
        //** Obtenemos de la Vista a la persona **//
        vperPersona = iVperPersonaService.cargaVistaPersona(perPersona.getIdPersona());
        binario= new DocBinario();
        listaBinarios = new ArrayList<DocBinario>();
//        idPersona = (String) session.getAttribute("idEmpleador");
//        persona = iPersonaService.buscarPorId(idPersona);
        logger.info("persona ok");
        cargar();
        if (parametro==3){
            esRectificatorio=true;
            unidadSeleccionada=central;
        }
        valor=true;
        gestion=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        obtenerPeriodoLista();
        tamanioErrores=2;
    }

    public void abrirPanel() {
        if(valor && !estaDeclarado && parametro!=3)
            RequestContext.getCurrentInstance().execute("seleccionEmpresa.show()");
        valor = false;
    }

    public void cargar() {
        generaDocumento();
        verEstadoPlanilla();
        cargarDocumentosParaRectificar();
        listaCentralSucursales();
    }


    public void listaCentralSucursales(){
        central = new PerUnidad();
        sucursales = new ArrayList<PerUnidad>();
        List<PerUnidad> listaUnidades;
        listaUnidades=iUnidadService.listarUnidadesSucursales(idPersona);
        for(PerUnidad sucursal:listaUnidades)  {
            if(sucursal.getPerUnidadPK().getIdUnidad()==0)
                central=sucursal;
            else
                sucursales.add(sucursal);
        }
        tamañoSucursales=sucursales.size();
    }

    public void cargarDocumentosParaRectificar(){
        docDocumentosParaRectificar= new ArrayList<DocDocumento>();
        docDocumentosParaRectificar= iDocumentoService.listarDocumentosParaRectificar(idPersona, "LC1010");
    }


    public void seleccionaEmpresa(){
        if(tipoEmpresa!=2)
            unidadSeleccionada=central;
        else
            unidadSeleccionada=iUnidadService.obtienePorId(new PerUnidadPK(idPersona, idUnidad));
    }

    public void seleccionaTrimestre(){
        periodo = iDocumentoService.findById(idRectificatorio).getDocPlanilla().getParCalendario().getParCalendarioPK().getTipoPeriodo();
    }

    public void verEstadoPlanilla(){
        ParObligacionCalendario parObligacionCalendario;
        try {
            parObligacionCalendario=iObligacionCalendarioService.buscarPorPlatriALaFecha();
        } catch (Exception e) {
            parObligacionCalendario=null;
        }
        if(parObligacionCalendario==null){
            estaDeclaradoMensaje="Solo puede realizar la Declaración Jurada dentro del plazo establecido.";
            estaDeclarado=true;
            return;
        }



        List<DocDocumento> listaDocumentos;
        try{
            //listaDocumentos=iDocumentoService.listarPorPersona(idPersona);
            listaDocumentos=iDocumentoService.listarPlanillasTrimestrales(idPersona, parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo(), "LC1010");
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
            if((documento.getCodEstado().getDescripcion().toLowerCase().equals("declarado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("observado")
                    || documento.getCodEstado().getDescripcion().toLowerCase().equals("finalizado")) && parametro==1){
                estaDeclarado=true;
                estaDeclaradoMensaje="Usted ya realizo la declaracion jurada.";
            }
        }
    }

    public void generaDocumento(){
        logger.info("generaDocumento()");
        documento = new DocDocumento();

        documento.setPerUnidad(iUnidadService.obtienePorId(new PerUnidadPK(persona.getIdPersona(), 0L)));

        if(parametro==1)
            documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1010", (short) 1)));//trimestral
        else{
            if(parametro==2)
                documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1011", (short) 1)));//sin movimiento
            else
                documento.setDocDefinicion(iDefinicionService.buscaPorId(new DocDefinicionPK("LC1012", (short) 1)));//rectificatoria
        }

        documento.setFechaDocumento(new Date());
        documento.setCodEstado(iDocumentoEstadoService.buscarPorId("110"));
        documento.setFechaReferenca(new Date());

        documento.setFechaBitacora(new Date());
        documento.setRegistroBitacora(usuario.getUsuario());
    }

    public void generaPlanilla(){
        logger.info("generaPlanilla()");
        docPlanilla.setIdEntidadBanco(iEntidadService.buscaPorId(new Long("2")));
        if(idEntidadSalud!=null)
            docPlanilla.setIdEntidadSalud(iEntidadService.buscaPorId(idEntidadSalud));
        docPlanilla.setFechaOperacion(fechaOperacionAux);
        //nuevos atributos
        docPlanilla.setNroEmpleador(vperPersona.getNroOtro());
        docPlanilla.setNroPatronalss(vperPersona.getNroCajaSalud());
        docPlanilla.setNombreRazonSocial(vperPersona.getNombreRazonSocial());
        if(vperPersona.getActividadDeclarada()!=null && UtilityData.isInteger(vperPersona.getActividadDeclarada()))
            docPlanilla.setIdActividadEconomica(new Long(vperPersona.getActividadDeclarada()));
        else
            docPlanilla.setIdActividadEconomica(0L);
        docPlanilla.setCodLocalidadCiudad(vperPersona.getCodLocalidad());
        docPlanilla.setCodLocalidadPais(vperPersona.getLocalidad());
        docPlanilla.setZona(vperPersona.getDirZona());
        docPlanilla.setDireccion(vperPersona.getDirDireccion());
        docPlanilla.setTelefono(vperPersona.getTelefono());
        docPlanilla.setFax(vperPersona.getFax());
        docPlanilla.setIdReplegal(vperPersona.getRlNroIdentidad());        //revisar el id aprtir de la BD
        docPlanilla.setCodLocalidadPresentacion(vperPersona.getLocalidad());
        docPlanilla.setParCalendario(iCalendarioService.obtenerCalendarioPorGestionYPeriodo(gestion, periodo));


        switch (parametro){
            case 1:
                docPlanilla.setTipoPlanilla("DDJJ");
                documento.setTipoMedioRegistro("DDJJ");
                break;
            case 2:
                docPlanilla.setTipoPlanilla("DDJJSM");
                documento.setTipoMedioRegistro("DDJJSM");
                break;
            case 3:
                docPlanilla.setTipoPlanilla("DDJJRECT");
                documento.setTipoMedioRegistro("DDJJRECT");
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
            binario.setInputStream(file.getInputstream());
            listaBinarios.add(binario);

            if(listaBinarios.size()==3)
                habilita=false;
        }catch (Exception e){
            habilita=true;
            e.printStackTrace();
        }
    }

    public String guardaDocumentoPlanillaBinario(ActionEvent actionEvent){
        if(parametro==3 && idRectificatorio==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", "No puede guardarse, ya que no se pueden obtener declaraciones."));
            return null;
        }

        validaArchivo(listaBinarios);

        if(errores.size()==0 && verificaValidacion){
            try{
                if(parametro==3)
                    documento.setIdDocumentoRef(iDocumentoService.findById(idRectificatorio));
                logger.info("Guardando documento, binario y planilla");
                logger.info(documento.toString());
                logger.info(listaBinarios.toString());
                logger.info(docPlanilla.toString());
                generaPlanilla();
                documento.setPerUnidad(unidadSeleccionada);
                iDocumentoService.guardaDocumentoPlanillaBinario(documento, docPlanilla, listaBinarios, docPlanillaDetalles, alertas);
                return "irEscritorio";
            }catch (Exception e){
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el formulario",""));
            }
        }else{
            binario = new DocBinario();
            listaBinarios.clear();
        }
        return null;
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
        parObligacionCalendarioLista = iObligacionCalendarioService.listaObligacionCalendarioPorGestion(gestion);
    }

    public String mensajeError(int i, String titulo){
        return nombreBinario+": Fila: \""+i+"\" y Columna: \""+ titulo+ "\"; ";
    }

    public void validaArchivo(List<DocBinario> listaBinarios){
        try {
            docPlanillaDetalles = new ArrayList<DocPlanillaDetalle>();
            errores = new ArrayList<String>();
            alertas = new ArrayList<DocAlerta>();
            int valorPlanilla=0;
            for(DocBinario docBinario:listaBinarios){
                CsvReader registro;
                if(!FilenameUtils.getExtension(docBinario.getTipoDocumento()).toUpperCase().equals(Dominios.EXTENSION_CSV)){
                    File file = XlsToCSV.conversion(docBinario);
                    registro = new CsvReader(file.getAbsolutePath());
                }
                else
                    registro = new CsvReader(new InputStreamReader(new ByteArrayInputStream(docBinario.getBinario())));

                registro.readHeaders();
                int c=0;
                //TODO nombre del documento extraido del metadata
                nombreBinario=docBinario.getTipoDocumento();
                while (registro.readRecord()){
                    if(c==0){
                        switch (registro.getColumnCount()){
                            case 43:
                                valorPlanilla=1;  //industrial
                                break;
                            case 33:
                                valorPlanilla=2;  //comercial
                                break;
                            case 31:
                                valorPlanilla=3;  //reducido (MyPEs)
                                break;
                            default:
                                return;
                        }
                    }
                    c++;
                    int columna=1;
                    DocPlanillaDetalle docPlanillaDetalle = new DocPlanillaDetalle();
                    DocAlerta docAlerta = new DocAlerta();

                    //1
                    docPlanillaDetalle.setTipoDocumento(registro.get(registro.getHeader(columna++)));

                    //2
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna))))
                        docPlanillaDetalle.setNumeroDocumento(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//3
                    docPlanillaDetalle.setExtencionDocumento(registro.get(registro.getHeader(columna++)));
                    //4
                    docPlanillaDetalle.setAfp(registro.get(registro.getHeader(columna++)));
                    //5
                    docPlanillaDetalle.setNuaCua(registro.get(registro.getHeader(columna++)));
                    //6.7.8.9.10
                    docPlanillaDetalle.setNombre(registro.get(registro.getHeader(columna++))+" "+registro.get(registro.getHeader(columna++))+" "+registro.get(registro.getHeader(columna++))+" "+registro.get(registro.getHeader(columna++))+" "+registro.get(registro.getHeader(columna++)));
                    //11
                    docPlanillaDetalle.setNacionalidad(registro.get(registro.getHeader(columna++)));
                    //12
                    docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna++)));
                    //13
                    docPlanillaDetalle.setSexo(registro.get(registro.getHeader(columna++)));
                    //14
                    docPlanillaDetalle.setJubilado(registro.get(registro.getHeader(columna++)));
                    //15
                    docPlanillaDetalle.setClasificacionLaboral(registro.get(registro.getHeader(columna++)));
                    //16
                    docPlanillaDetalle.setCargo(registro.get(registro.getHeader(columna++)));
                    //17
                    docPlanillaDetalle.setFechaIngreso(registro.get(registro.getHeader(columna++)));
                    //18
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna))))
                        docPlanillaDetalle.setHorasPagadasDia(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//19
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna))))
                        docPlanillaDetalle.setDiasHaberBasico(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//20

                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna))))
                        docPlanillaDetalle.setDiasPagados(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//21
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setDominicalMes(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                if(!registro.get(columna).equals("")){
                                    if((int) Double.parseDouble(registro.get(columna))<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario+": El registro en la fila \""+c+"\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//22
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setDominicalTrabajado(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                if(!registro.get(columna).equals("")){
                                    if((int) Double.parseDouble(registro.get(columna))<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//23
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //24
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasNocturno(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                if(!registro.get(columna).equals(""))
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //25
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasDominicales(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //26
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                if(!registro.get(columna).equals("")){
                                    if((int) Double.parseDouble(registro.get(columna))<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 2:
                                if(!registro.get(columna).equals(""))
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //27
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHaberDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //28
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setMontoDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //29
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                if(!registro.get(columna).equals(""))
                                    docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    if(valorPlanilla!=3){
                        columna++; //30
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                            switch (valorPlanilla){
                                case 1:
                                    docPlanillaDetalle.setMontoHorasNocturno(registro.get(registro.getHeader(columna)));
                                    break;
                                case 2:
                                    docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                                    break;
                            }
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //31
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals("")){
                            switch (valorPlanilla){
                                case 1:
                                    docPlanillaDetalle.setMontoHorasDominical(registro.get(registro.getHeader(columna)));
                                    break;
                                case 2:
                                    if(!registro.get(columna).equals(""))
                                        docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                                    else
                                        errores.add(mensajeError(c, registro.getHeader(columna)));
                                    break;
                            }
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                    }
                    if(valorPlanilla==1){
                        columna++; //32
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //33
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setBonoProduccion(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //34
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setBonoFrontera(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //35
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //36
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna))))
                            docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //37
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //38
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //39
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //40
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).equals(""))
                            docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //41
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna))))
                            docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                    }
                    if(docAlerta.getObservacion()!=null)
                        alertas.add(docAlerta);
                    docPlanillaDetalles.add(docPlanillaDetalle);
                }
            }
            tamanioErrores = errores.size();
            verificaValidacion=true;
        }
        catch (Exception e){
            verificaValidacion=false;
            e.printStackTrace();
        }
    }

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

    public List<DocDocumento> getDocDocumentosParaRectificar() {
        return docDocumentosParaRectificar;
    }

    public void setDocDocumentosParaRectificar(List<DocDocumento> docDocumentosParaRectificar) {
        this.docDocumentosParaRectificar = docDocumentosParaRectificar;
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

    public IPlanillaDetalleService getiPlanillaDetalleService() {
        return iPlanillaDetalleService;
    }

    public void setiPlanillaDetalleService(IPlanillaDetalleService iPlanillaDetalleService) {
        this.iPlanillaDetalleService = iPlanillaDetalleService;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public PerUnidad getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(PerUnidad unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public List<PerUnidad> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<PerUnidad> sucursales) {
        this.sucursales = sucursales;
    }

    public PerUnidad getCentral() {
        return central;
    }

    public void setCentral(PerUnidad central) {
        this.central = central;
    }

    public int getTamañoSucursales() {
        return tamañoSucursales;
    }

    public void setTamañoSucursales(int tamañoSucursales) {
        this.tamañoSucursales = tamañoSucursales;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public ICalendarioService getiCalendarioService() {
        return iCalendarioService;
    }

    public void setiCalendarioService(ICalendarioService iCalendarioService) {
        this.iCalendarioService = iCalendarioService;
    }

    public String getEstaDeclaradoMensaje() {
        return estaDeclaradoMensaje;
    }

    public void setEstaDeclaradoMensaje(String estaDeclaradoMensaje) {
        this.estaDeclaradoMensaje = estaDeclaradoMensaje;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public List<DocAlerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<DocAlerta> alertas) {
        this.alertas = alertas;
    }

    public boolean isVerificaValidacion() {
        return verificaValidacion;
    }

    public void setVerificaValidacion(boolean verificaValidacion) {
        this.verificaValidacion = verificaValidacion;
    }

    public int getTamanioErrores() {
        return tamanioErrores;
    }

    public void setTamanioErrores(int tamanioErrores) {
        this.tamanioErrores = tamanioErrores;
    }

    public IParametrizacionService getiParametrizacionService() {
        return iParametrizacionService;
    }

    public void setiParametrizacionService(IParametrizacionService iParametrizacionService) {
        this.iParametrizacionService = iParametrizacionService;
    }
}