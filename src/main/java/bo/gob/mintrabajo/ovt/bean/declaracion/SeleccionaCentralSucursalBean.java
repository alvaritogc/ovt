package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.Util.UtilityData;
import bo.gob.mintrabajo.ovt.Util.XlsToCSV;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import com.csvreader.CsvReader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.io.FilenameUtils;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Renato Velasquez Date: 12/7/13
 */
@ManagedBean
@ViewScoped
public class SeleccionaCentralSucursalBean implements Serializable {

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacionService;
    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DeclaracionAguinaldoBean.class);
    private Long idUsuario;
    private String idPersona;
    private PerPersona persona;
    private Long idUnidad;
    private UsrUsuario usuario;
    private int parametro;

    private int tipoEmpresa;
    private PerUnidad central;
    private PerUnidad unidadSeleccionada;
    private List<PerUnidad> sucursales;

    private String mensajeValidacion;
    private boolean habilitado;
    private ParObligacionCalendario periodoGestion= new ParObligacionCalendario();

    //  uploadarchivo
    public static Cache<String, List<DocBinario>> binarios = CacheBuilder.newBuilder().maximumSize(600).build();
    public static Cache<String, List<DocPlanillaDetalle>> planillaDetalles = CacheBuilder.newBuilder().maximumSize(600).build();
    public static Cache<String, List<DocAlerta>> planillaAlertas = CacheBuilder.newBuilder().maximumSize(600).build();
    private List<DocPlanillaDetalle> docPlanillaDetalles;
    private UploadedFile file;
    private String nombres[] = new String[3];
    private List<DocBinario> listaBinarios;
    private DocBinario binario;
    private boolean habilitaTrim;
    private boolean habilitaAgui;
    private String bitacoraSession;
    private List<String> errores = new ArrayList<String>();
    private List<DocAlerta> alertas;
    private String nombreBinario;
    private int salarioMinimo;
    private int tamanioErrores;
    private boolean verificaValidacion;
    private int trimestralAuto;
    private int aguinaldoAuto;

    private int masculino;
    private int femenino;
    private int masculinoJubilado;
    private int femeninoJubilado;
    private int masculinoExtranjero;
    private int femeninoExtranjero;
    private int masculinoContratadoTrim;
    private int femeninoContratadoTrim;

    private double haberBasico;
    private double bonoAntiguedad;
    private double bonoProduccion;
    private double subsidioFrontera;
    private double laborExtraordinaria;
    private double otrosBonos;
    private double aporteAFP;
    private double rcIVA;
    private double otrosDescuentos;
    private String codDocumento;

    //////
    private boolean delegado = false;
    private boolean consolidado=true;
    private boolean sucursal=true;

    @PostConstruct
    public void ini() {
        bitacoraSession = (String) session.getAttribute("bitacoraSession");
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                parametro = 1;
            }
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null || !((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p").equals("")) {
                parametro = Integer.valueOf(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p"));
            }
        } catch (Exception e) {
            parametro = 1;
        }

        habilitado = true;
        salarioMinimo = Integer.valueOf(iParametrizacionService.obtenerParametro(Dominios.DOM_SALARIO, Dominios.PAR_SALARIO_MINIMO).getDescripcion());
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        idUsuario = (Long) session.getAttribute("idUsuario");
        usuario = iUsuarioService.findById(idUsuario);
        ///////////////////////////LUIS
        delegado = "siDelegado".equals((String) session.getAttribute("delegado"));
        if (delegado) {
            tipoEmpresa = 2;
        } else {
            tipoEmpresa = 1;
        }
        listaBinarios = new ArrayList<DocBinario>();
        cargar();
    }

    public void cargar() {
        verficaPeriodoGestion();
        listaCentralSucursales();
        verficaHabilitacionUpload();
    }

    public void verficaPeriodoGestion() {
        List<DocDocumento> docs = new ArrayList<DocDocumento>();
        switch (parametro){
            case 1:
                codDocumento = "LC1010";
                periodoGestion = iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo(new Date());
                docs= iDocumentoService.listarDocumentosPorPersonaEntreFechasTrim(idPersona, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                if(periodoGestion==null) {
                    mensajeValidacion = "Fuera de rango para realizar la declaración jurada.";
                    habilitado = false;
                }
                break;
            case 2:
                codDocumento = "LC1011";
                periodoGestion = iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo(new Date());
                docs= iDocumentoService.listarDocumentosPorPersonaEntreFechasTrim(idPersona, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                if(periodoGestion==null) {
                    mensajeValidacion = "Fuera de rango para realizar la declaración jurada sin movimiento.";
                    habilitado = false;
                }

                break;
            case 3:
                codDocumento = "LC1012";
                periodoGestion = iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo2(new Date());
                docs= iDocumentoService.listarDocumentosPorPersonaEntreFechasTrim(idPersona, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                if(periodoGestion==null) {
                    mensajeValidacion = "Fuera de rango para realizar la declaración jurada rectificatoria.";
                    habilitado = false;
                }
                break;
            case 4:
                codDocumento = "LC1020";
                periodoGestion = iObligacionCalendarioService.listarPlanillaAguiPorFechaHastaFechaPlazo(new Date());
                docs= iDocumentoService.listarDocumentosPorPersonaEntreFechasAgui(idPersona, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                if(periodoGestion==null) {
                    mensajeValidacion = "Fuera de rango para realizar la declaración jurada de aguinaldo.";
                    habilitado = false;
                }
                break;
            case 5:
                codDocumento = "LC1021";
                periodoGestion = iObligacionCalendarioService.listarPlanillaAguiPorFechaHastaFechaPlazo2(new Date());
                docs= iDocumentoService.listarDocumentosPorPersonaEntreFechasAgui(idPersona, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                if(periodoGestion==null) {
                    mensajeValidacion = "Fuera de rango para realizar la declaración jurada rectificatoria de aguinaldo.";
                    habilitado = false;
                }
                break;
        }
        for (DocDocumento docu: docs){
            if(docu.getTipoMedioRegistro().toUpperCase().equalsIgnoreCase("CONSOLIDADO")){
                consolidado=true;
                sucursal=false;
            }
            if(docu.getTipoMedioRegistro().toUpperCase().equalsIgnoreCase("SUCURSAL")){
                consolidado=false;
                sucursal=true;
            }
        }
    }

    public void listaCentralSucursales() {
        if (periodoGestion != null) {
            central = new PerUnidad();
            sucursales = new ArrayList<PerUnidad>();
            //////////////////////////////////////////LUIS
            List<PerUnidad> listaUnidades = new ArrayList<PerUnidad>();
            if (delegado) {
                List<PerUsuarioUnidad> listaSucursalesDelegadas = iPersonaService.listaUsuarioUnidadPorIdUsuarioIdPersona(idUsuario, idPersona);
                for (PerUsuarioUnidad perUsuarioUnidad : listaSucursalesDelegadas) {
                    if (perUsuarioUnidad.getEstado().equals("A")) {
                        List<DocPlanilla> docPlanillaVerifica = new ArrayList<DocPlanilla>();
                        PerUnidad unidad = iUnidadService.obtenerPorIdPersonaIdUnidad(idPersona, perUsuarioUnidad.getPerUsuarioUnidadPK().getIdUnidad());
                        docPlanillaVerifica = iPlanillaService.listaPlanillasTrimestrales(idPersona, unidad.getPerUnidadPK().getIdUnidad(), codDocumento,
                                periodoGestion.getParCalendario().getParCalendarioPK().getGestion(), periodoGestion.getParCalendario().getParCalendarioPK().getTipoPeriodo());
                        if (docPlanillaVerifica.isEmpty()) {
                            listaUnidades.add(unidad);
                        }
                    }
                }
            } else {
                listaUnidades = iUnidadService.listarUnidadesSucursalesPorFecha(idPersona, codDocumento, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
            }
            //////////////////////////////////////////////
            //List<PerUnidad> listaUnidades = iUnidadService.listarUnidadesSucursalesPorFecha(idPersona, codDocumento, periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
            for (PerUnidad sucursal : listaUnidades) {
                if (sucursal.getPerUnidadPK().getIdUnidad() == 0) {
                    central = sucursal;
                }
                sucursales.add(sucursal);
            }
        }
    }

    public void verficaHabilitacionUpload() {
        if (parametro == 1 || parametro == 2 || parametro == 3) {
            habilitaTrim = false;
            trimestralAuto = Integer.valueOf(iParametrizacionService.obtenerParametro(Dominios.DOM_FORMULARIO, Dominios.PAR_AUTOLLENADO_TRIMESTRAL).getDescripcion());
            if ((parametro == 1 || parametro == 3) && trimestralAuto == 1) {
                habilitaTrim = true;
            }
        }
        if (parametro == 4 || parametro == 5) {
            habilitaAgui = false;
            aguinaldoAuto = Integer.valueOf(iParametrizacionService.obtenerParametro(Dominios.DOM_FORMULARIO, Dominios.PAR_AUTOLLENADO_AGUINALDO).getDescripcion());
            if ((parametro == 4 || parametro == 5) && aguinaldoAuto == 1) {
                habilitaAgui = true;
            }
        }
    }

    public String seleccionaUnidad() {
        if (periodoGestion != null) {
            if (aguinaldoAuto == 1 || trimestralAuto == 1) {
                if (((parametro == 1 || parametro == 3) && listaBinarios.size() < 3) || (parametro == 4 && listaBinarios.size() == 0)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "No subió la cantidad necesaria de archivos."));
                    return null;
                }
            }
        }else{
            mensajeValidacion = "Fuera de rango para realizar la declaración jurada.";
            habilitado = false;
            return null;
        }

        if (tipoEmpresa != 2) {
            unidadSeleccionada = central;
        } else {
            unidadSeleccionada = iUnidadService.obtienePorId(new PerUnidadPK(idPersona, idUnidad));
        }

        if (verEstadoPlanilla()) {
            session.setAttribute("parametro", parametro);
            session.setAttribute("unidadSeleccionada", unidadSeleccionada);
            session.setAttribute("periodoGestion", periodoGestion);
            session.setAttribute("tipoEmpresa", tipoEmpresa);
//          reo revisar
            //verifica trimestralAuto y aguinaldoAuto: SI (1) ó NO (0), para el llenado del formulario...
            if (habilitaTrim || habilitaAgui) {
                validaArchivo(listaBinarios);
                if (errores.size() == 0 && verificaValidacion) {
                    binarios.put("binarios", listaBinarios);
                    planillaDetalles.put("planillaDetalles", docPlanillaDetalles);
                    planillaAlertas.put("planillaAlertas", alertas);
                    session.setAttribute("haberBasico", haberBasico);
                    session.setAttribute("bonoAntiguedad", bonoAntiguedad);
                    session.setAttribute("bonoProduccion", bonoProduccion);
                    session.setAttribute("subsidioFrontera", subsidioFrontera);
                    session.setAttribute("laborExtraordinaria", laborExtraordinaria);
                    session.setAttribute("otrosBonos", otrosBonos);

                    session.setAttribute("masculino", masculino);
                    session.setAttribute("femenino", femenino);

                    if (parametro != 4) {
                        session.setAttribute("aporteAFP", aporteAFP);
                        session.setAttribute("rcIVA", rcIVA);
                        session.setAttribute("otrosDescuentos", otrosDescuentos);

                        session.setAttribute("masculinoJubilado", masculinoJubilado);
                        session.setAttribute("femeninoJubilado", femeninoJubilado);
                        session.setAttribute("masculinoExtranjero", masculinoExtranjero);
                        session.setAttribute("femeninoExtranjero", femeninoExtranjero);
                        session.setAttribute("masculinoContratadoTrim", masculinoContratadoTrim);
                        session.setAttribute("femeninoContratadoTrim", femeninoContratadoTrim);
                    }
                } else {
                    binario = new DocBinario();
                    listaBinarios.clear();
                    verficaHabilitacionUpload();
                    nombres = new String[3];
                    return null;
                }
            }
            if (parametro == 1 || parametro == 2 || parametro == 3) {
                return "irDeclaracionTrimestral";
            }
            if (parametro == 4 || parametro == 5) {
                return "irDeclaracionAguinaldo";
            }
        }
        return null;
    }

    // upload de archivos
    public void upload(FileUploadEvent evento) {
        logger.info("upload(FileUploadEvent evento)");
        file = evento.getFile();
        nombres[listaBinarios.size()] = file.getFileName();
        try {
            binario = new DocBinario();
            binario.setTipoDocumento(file.getFileName());
            binario.setMetadata(file.getContentType());
            binario.setFechaBitacora(new Date());
            binario.setRegistroBitacora(bitacoraSession);
            binario.setBinario(file.getContents());
            binario.setInputStream(file.getInputstream());
            listaBinarios.add(binario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Éxito", binario.getTipoDocumento() + " fue cargado satisfactoriamente."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String mensajeError(int i, String titulo) {
        return nombreBinario + ": Fila: \"" + i + "\" y Columna: \"" + titulo + "\"; ";
    }

    public void validaArchivo(List<DocBinario> listaBinarios) {
        try {
            docPlanillaDetalles = new ArrayList<DocPlanillaDetalle>();
            errores = new ArrayList<String>();
            alertas = new ArrayList<DocAlerta>();
            int valorPlanilla = 0;
            for (DocBinario docBinario : listaBinarios) {
                CsvReader registro;
                if (!FilenameUtils.getExtension(docBinario.getTipoDocumento()).toUpperCase().equals(Dominios.EXTENSION_CSV)) {
                    File file = XlsToCSV.conversion(docBinario);
                    registro = new CsvReader(file.getAbsolutePath());
                } else {
                    registro = new CsvReader(new InputStreamReader(new ByteArrayInputStream(docBinario.getBinario())));
                }

                registro.readHeaders();
                int c = 0;
                masculino = 0;
                femenino = 0;
                masculinoJubilado = 0;
                femeninoJubilado = 0;
                masculinoExtranjero = 0;
                femeninoExtranjero = 0;
                masculinoContratadoTrim = 0;
                femeninoContratadoTrim = 0;

                haberBasico = 0;
                bonoAntiguedad = 0;
                bonoProduccion = 0;
                subsidioFrontera = 0;
                laborExtraordinaria = 0;
                otrosBonos = 0;
                aporteAFP = 0;
                rcIVA = 0;
                otrosDescuentos = 0;
                //TODO nombre del documento extraido del metadata
                nombreBinario = docBinario.getTipoDocumento();
                while (registro.readRecord()) {
                    if (c == 0) {
                        switch (registro.getColumnCount()) {
                            case 43:
                                valorPlanilla = 1;  //industrial
                                break;
                            case 33:
                                valorPlanilla = 2;  //comercial
                                break;
                            case 31:
                                valorPlanilla = 3;  //reducido (MyPEs)
                                break;
                            default:
                                return;
                        }
                    }
                    c++;
                    int columna = 1;
                    DocPlanillaDetalle docPlanillaDetalle = new DocPlanillaDetalle();
                    DocAlerta docAlerta = new DocAlerta();

                    //1
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setTipoDocumento(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//2
                    if (UtilityData.isInteger(registro.get(registro.getHeader(columna))) && !registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNumeroDocumento(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//3
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setExtencionDocumento(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//4
                    docPlanillaDetalle.setAfp(registro.get(registro.getHeader(columna)));

                    columna++;//5
                    docPlanillaDetalle.setNuaCua(registro.get(registro.getHeader(columna)));

                    columna++;//6
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNombre(registro.get(registro.getHeader(columna)) + " ");
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }
                    columna++;//7
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre() + " " + registro.get(registro.getHeader(columna)));
                    }

                    columna++;//8
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre() + " " + registro.get(registro.getHeader(columna)));
                    }

                    columna++;//9
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre() + " " + registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//10
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre() + " " + registro.get(registro.getHeader(columna)));
                    }

                    columna++;//11
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setNacionalidad(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//12
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        if (registro.get(registro.getHeader(columna)).length() >= 15) {
                            docPlanillaDetalle.setFechaNacimiento(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'BOT' yyyy").parse(registro.get(registro.getHeader(columna)))));
                        } else {
                            docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna)));
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//13
                    if (!registro.get(registro.getHeader(columna)).equals("")) {
                        docPlanillaDetalle.setSexo(registro.get(registro.getHeader(columna)));
                        if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("M") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARON") || docPlanillaDetalle.getSexo().toUpperCase().equals("MASCULINO") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARÓN")) ){
                            masculino++;
                        }
                        if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("F") || docPlanillaDetalle.getSexo().toUpperCase().equals("MUJER") || docPlanillaDetalle.getSexo().toUpperCase().equals("FEMENINO"))) {
                            femenino++;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//14
                    if (!registro.get(registro.getHeader(columna)).equals("")) {
                        docPlanillaDetalle.setJubilado(registro.get(registro.getHeader(columna)));

                        //masculino
                        if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("M") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARON") || docPlanillaDetalle.getSexo().toUpperCase().equals("MASCULINO") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARÓN") )) {
                            if (docPlanillaDetalle.getJubilado().toUpperCase().equals("SI")) {
                                masculinoJubilado++;
                            }
                            if (!docPlanillaDetalle.getNacionalidad().toUpperCase().equals("BOLIVIA") || !docPlanillaDetalle.getNacionalidad().toUpperCase().equals("BOLIVIANA")) {
                                masculinoExtranjero++;
                            }
                        }

                        //femenino
                        if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("F") || docPlanillaDetalle.getSexo().toUpperCase().equals("MUJER") || docPlanillaDetalle.getSexo().toUpperCase().equals("FEMENINO"))) {
                            if (docPlanillaDetalle.getJubilado().toUpperCase().equals("SI")) {
                                femeninoJubilado++;
                            }
                            if (!docPlanillaDetalle.getNacionalidad().toUpperCase().equals("BOLIVIA") || !docPlanillaDetalle.getNacionalidad().toUpperCase().equals("BOLIVIANA")) {
                                femeninoExtranjero++;
                            }
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//15
                    docPlanillaDetalle.setClasificacionLaboral(registro.get(registro.getHeader(columna)));

                    columna++;//16
                    if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setCargo(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//17
                    if (!registro.get(registro.getHeader(columna)).equals("")) {
                        if (registro.get(registro.getHeader(columna)).length() > 15) {
                            docPlanillaDetalle.setFechaIngreso(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'BOT' yyyy").parse(registro.get(registro.getHeader(columna)))));

                            Date fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").parse(docPlanillaDetalle.getFechaIngreso());
                            if (fechaIngreso.after(periodoGestion.getFechaDesde()) && fechaIngreso.before(periodoGestion.getFechaHasta())) {
                                //masculino
                                if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("M") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARON") || docPlanillaDetalle.getSexo().toUpperCase().equals("MASCULINO") || docPlanillaDetalle.getSexo().toUpperCase().equals("VARÓN") || docPlanillaDetalle.getSexo()!=null) ){
                                    masculinoContratadoTrim++;
                                }

                                //femenino
                                if (docPlanillaDetalle.getSexo()!=null &&(docPlanillaDetalle.getSexo().toUpperCase().equals("F") || docPlanillaDetalle.getSexo().toUpperCase().equals("MUJER") || docPlanillaDetalle.getSexo().toUpperCase().equals("FEMENINO") || docPlanillaDetalle.getSexo()!=null) ){
                                    femeninoContratadoTrim++;
                                }
                            }
                        } else {
                            docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna)));
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//18
                    if (UtilityData.isInteger(registro.get(registro.getHeader(columna))) && !registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setHorasPagadasDia(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//19
                    if (UtilityData.isInteger(registro.get(registro.getHeader(columna))) && !registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setDiasHaberBasico(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//20
                    if (UtilityData.isInteger(registro.get(registro.getHeader(columna))) && !registro.get(registro.getHeader(columna)).isEmpty()) {
                        docPlanillaDetalle.setDiasPagados(registro.get(registro.getHeader(columna)));
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//21
                    if (UtilityData.isInteger(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                if (!registro.get(registro.getHeader(columna)).isEmpty()) {
                                    docPlanillaDetalle.setDominicalMes(registro.get(registro.getHeader(columna)));
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                            case 2:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                if (!registro.get(columna).isEmpty()) {
                                    if ((int) Double.parseDouble(registro.get(columna).replace(",", ".")) < salarioMinimo) {
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    }
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if (!docPlanillaDetalle.getHaberBasico().isEmpty()) {
                                        haberBasico = haberBasico + Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                    }
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//22
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setDominicalTrabajado(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                if (!registro.get(columna).isEmpty()) {
                                    int numero = (int) Double.parseDouble(registro.get(columna).replace(",", "."));
                                    if (numero < salarioMinimo) {
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    }
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if (!docPlanillaDetalle.getHaberBasico().isEmpty()) {
                                        haberBasico = haberBasico + Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                    }
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getBonoAntiguedad().isEmpty()) {
                                    bonoAntiguedad = bonoAntiguedad + Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++;//23
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getBonoOtros().isEmpty()) {
                                    otrosBonos = otrosBonos + Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //24
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setHorasNocturno(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getBonoAntiguedad().isEmpty()) {
                                    bonoAntiguedad = bonoAntiguedad + Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                                }
                                break;
                            case 3:
                                if (!registro.get(columna).isEmpty()) {
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //25
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setHorasDominicales(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getBonoOtros().isEmpty()) {
                                    otrosBonos = otrosBonos + Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                                }
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoAfp().isEmpty()) {
                                    aporteAFP = aporteAFP + Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //26
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                if (!registro.get(columna).isEmpty()) {
                                    if ((int) Double.parseDouble(registro.get(columna).replace(",", ".")) < salarioMinimo) {
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    }
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if (!docPlanillaDetalle.getHaberBasico().isEmpty()) {
                                        haberBasico = haberBasico + Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                    }
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                            case 2:
                                if (!registro.get(columna).isEmpty()) {
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoRciva().isEmpty()) {
                                    rcIVA = rcIVA + Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //27
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setHaberDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoAfp().isEmpty()) {
                                    aporteAFP = aporteAFP + Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                                }
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoOtro().isEmpty()) {
                                    otrosDescuentos = otrosDescuentos + Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //28
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setMontoDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoRciva().isEmpty()) {
                                    rcIVA = rcIVA + Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                                }
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    columna++; //29
                    if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                        switch (valorPlanilla) {
                            case 1:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                if (!docPlanillaDetalle.getDescuentoOtro().isEmpty()) {
                                    otrosDescuentos = otrosDescuentos + Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                                }
                                break;
                            case 3:
                                if (!registro.get(columna).isEmpty()) {
                                    docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                                } else {
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                }
                                break;
                        }
                    } else {
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    }

                    if (valorPlanilla != 3) {
                        columna++; //30
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            switch (valorPlanilla) {
                                case 1:
                                    docPlanillaDetalle.setMontoHorasNocturno(registro.get(registro.getHeader(columna)));
                                    break;
                                case 2:
                                    docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                                    break;
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //31
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            switch (valorPlanilla) {
                                case 1:
                                    docPlanillaDetalle.setMontoHorasDominical(registro.get(registro.getHeader(columna)));
                                    break;
                                case 2:
                                    if (!registro.get(columna).isEmpty()) {
                                        docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                                    } else {
                                        errores.add(mensajeError(c, registro.getHeader(columna)));
                                    }
                                    break;
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }
                    }
                    if (valorPlanilla == 1) {
                        columna++; //32
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getBonoAntiguedad().isEmpty()) {
                                bonoAntiguedad = bonoAntiguedad + Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //33
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setBonoProduccion(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getBonoProduccion().isEmpty()) {
                                bonoProduccion = bonoProduccion + Double.parseDouble(docPlanillaDetalle.getBonoProduccion());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //34
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setBonoFrontera(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getBonoFrontera().isEmpty()) {
                                subsidioFrontera = subsidioFrontera + Double.parseDouble(docPlanillaDetalle.getBonoFrontera());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //35
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getBonoOtros().isEmpty()) {
                                otrosBonos = otrosBonos + Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //36
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) && !registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //37
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getDescuentoAfp().isEmpty()) {
                                aporteAFP = aporteAFP + Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //38
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getDescuentoRciva().isEmpty()) {
                                rcIVA = rcIVA + Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //39
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                            if (!docPlanillaDetalle.getDescuentoOtro().isEmpty()) {
                                otrosDescuentos = otrosDescuentos + Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                            }
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //40
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) || registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }

                        columna++; //41
                        if (UtilityData.isDecimal(registro.get(registro.getHeader(columna))) && !registro.get(columna).isEmpty()) {
                            docPlanillaDetalle.setLiquidoPagable(registro.get(registro.getHeader(columna)));
                        } else {
                            errores.add(mensajeError(c, registro.getHeader(columna)));
                        }
                    }
                    if (docAlerta.getObservacion() != null) {
                        alertas.add(docAlerta);
                    }
                    docPlanillaDetalles.add(docPlanillaDetalle);
                }
            }
            tamanioErrores = errores.size();
            verificaValidacion = true;
        } catch (Exception e) {
            verificaValidacion = false;
            e.printStackTrace();
        }
    }

    public boolean verEstadoPlanilla(){
        List<DocDocumento> listaDocumentos;
        habilitado = true;
        if(unidadSeleccionada.getPerUnidadPK()!=null){
            switch (parametro){
                case 1:
                    listaDocumentos=iDocumentoService.listarDocumentosPorPersonaUnidadFechasHastaPlazoCodDocumentos(unidadSeleccionada.getPerUnidadPK(),periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2(), "LC1010", "LC1011");
                    if(listaDocumentos.size()!=0){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La presentación trimestral ya fue declarada como sin movimiento."));
                        return false;
                    }
                    return true;
                case 2:
                    listaDocumentos=iDocumentoService.listarDocumentosPorPersonaUnidadFechasHastaPlazoCodDocumentos(unidadSeleccionada.getPerUnidadPK(),periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2(), "LC1010", "LC1011");
                    if(listaDocumentos.size()!=0){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La presentación trimestral ya fue declarada."));
                        return false;
                    }
                    return true;
                case 3:
                    listaDocumentos=iDocumentoService.listarDocumentosPorUnidadCodFechaHastaPlazo2(unidadSeleccionada.getPerUnidadPK(), "LC1010", periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                    if(listaDocumentos.size()==0){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe declaración para ser rectificada."));
                        return false;
                    }
                    return true;

                case 4:
                    return true;
                case 5:
                    listaDocumentos=iDocumentoService.listarDocumentosPorUnidadCodFechaHastaPlazo2(unidadSeleccionada.getPerUnidadPK(), "LC1020", periodoGestion.getFechaHasta(), periodoGestion.getFechaPlazo2());
                    if(listaDocumentos.size()==0){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe declaración para ser rectificada."));
                        return false;
                    }
                    return true;
            }
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No seleccionó alguna sucursal o ya realizó la declaracion como una consolidada."));
        return false;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public PerUnidad getCentral() {
        return central;
    }

    public void setCentral(PerUnidad central) {
        this.central = central;
    }

    public List<PerUnidad> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<PerUnidad> sucursales) {
        this.sucursales = sucursales;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public int getParametro() {
        return parametro;
    }

    public void setParametro(int parametro) {
        this.parametro = parametro;
    }

    public IObligacionCalendarioService getiObligacionCalendarioService() {
        return iObligacionCalendarioService;
    }

    public void setiObligacionCalendarioService(IObligacionCalendarioService iObligacionCalendarioService) {
        this.iObligacionCalendarioService = iObligacionCalendarioService;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
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

    public boolean isHabilitaTrim() {
        return habilitaTrim;
    }

    public void setHabilitaTrim(boolean habilitaTrim) {
        this.habilitaTrim = habilitaTrim;
    }

    public boolean isHabilitaAgui() {
        return habilitaAgui;
    }

    public void setHabilitaAgui(boolean habilitaAgui) {
        this.habilitaAgui = habilitaAgui;
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

    public IParametrizacionService getiParametrizacionService() {
        return iParametrizacionService;
    }

    public void setiParametrizacionService(IParametrizacionService iParametrizacionService) {
        this.iParametrizacionService = iParametrizacionService;
    }

    public int getAguinaldoAuto() {
        return aguinaldoAuto;
    }

    public int getTrimestralAuto() {
        return trimestralAuto;
    }

    /**
     * @return the iPlanillaService
     */
    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    /**
     * @param iPlanillaService the iPlanillaService to set
     */
    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    /**
     * @return the delegado
     */
    public boolean isDelegado() {
        return delegado;
    }

    /**
     * @param delegado the delegado to set
     */
    public void setDelegado(boolean delegado) {
        this.delegado = delegado;
    }

    public boolean isConsolidado() {
        return consolidado;
    }

    public void setConsolidado(boolean consolidado) {
        this.consolidado = consolidado;
    }

    public boolean isSucursal() {
        return sucursal;
    }

    public void setSucursal(boolean sucursal) {
        this.sucursal = sucursal;
    }
}
