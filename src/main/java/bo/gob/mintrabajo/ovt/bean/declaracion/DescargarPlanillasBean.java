package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.Util.UtilityData;
import bo.gob.mintrabajo.ovt.Util.XlsToCSV;
import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import com.csvreader.CsvReader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//

@ManagedBean
@ViewScoped
public class DescargarPlanillasBean {
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(DescargarPlanillasBean.class);

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;
    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;

    private Long idUsuario;
    private Long idDocumento;
    private List<DocBinario> listaBinarios;
    private DocBinario docBinario;
//    private StreamedContent file;
    private UsrUsuario usuario;
    private DocDocumento docDocumento;

    //  uploadarchivo
    public static Cache<String, List<DocBinario>> binarios = CacheBuilder.newBuilder().maximumSize(600).build();
    public static Cache<String, List<DocPlanillaDetalle>> planillaDetalles = CacheBuilder.newBuilder().maximumSize(600).build();
    private List<DocPlanillaDetalle> docPlanillaDetalles;
    private UploadedFile file;
    private String nombres[]= new String[3];
//    private List<DocBinario> listaBinarios;
    private DocBinario binario;
    private boolean habilita = true;
    private String bitacoraSession;
    private List<String> errores = new ArrayList<String>();
    private List<DocAlerta> alertas;
    private String nombreBinario;
    private int salarioMinimo;
    private int tamanioErrores;
    private boolean verificaValidacion;
    private int trimestralAuto;
    private int aguinaldoAuto;

    private double haberBasico;
    private double bonoAntiguedad;
    private double bonoProduccion;
    private double subsidioFrontera;
    private double laborExtraordinaria;
    private double otrosBonos;
    private double aporteAFP;
    private double rcIVA;
    private double otrosDescuentos;

    private boolean validacion= false;

    @PostConstruct
    public void ini() {
        docBinario= new DocBinario();
        listaBinarios= new ArrayList<DocBinario>();
        idDocumento = (Long) session.getAttribute("idDocumento");
        docDocumento = iDocumentoService.findById(idDocumento);
        idUsuario = (Long) session.getAttribute("idUsuario");
        usuario = iUsuarioService.findById(idUsuario);
        cargar();
    }

    public void cargar(){
        listaBinarios.clear();
        listaBinarios= iBinarioService.listarPorIdDocumento(idDocumento);
        for(DocBinario binario:listaBinarios)
            binario.setInputStream(new ByteArrayInputStream(binario.getBinario()));
        if(listaBinarios.size()==3&&listaBinarios.get(0).isValidado()&&listaBinarios.get(1).isValidado()&&listaBinarios.get(2).isValidado()){
            validacion=true;
            return;
        }
        if(listaBinarios.size()==1&&listaBinarios.get(0).isValidado())
            validacion=true;
    }

    public void download(){
        DocLogImpresion docLogImpresion= new DocLogImpresion();
        if(docBinario!=null)     {
            docLogImpresion.setIdDocumento(docDocumento);
            docLogImpresion.setFechaBitacora(new Date());
            docLogImpresion.setRegistroBitacora(usuario.getUsuario());
            docLogImpresion.setTipoImpresion(Dominios.DOC_TIPO_DOWNLOAD);
            docLogImpresion.setIdDoclogimpresion(iUtilsService.valorSecuencia("DOC_LOG_IMPRESION_SEC"));
            iBinarioService.download(docBinario, docLogImpresion);
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se encuentra el archivo correspondiente"));
    }

    public String mensajeError(int i, String titulo){
        return nombreBinario+": Fila: \""+i+"\" y Columna: \""+ titulo+ "\"; ";
    }

    public void validaArchivo(){
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
                haberBasico=0;
                bonoAntiguedad=0;
                bonoProduccion=0;
                subsidioFrontera=0;
                laborExtraordinaria=0;
                otrosBonos=0;
                aporteAFP=0;
                rcIVA=0;
                otrosDescuentos=0;
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
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setTipoDocumento(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//2
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))&&!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNumeroDocumento(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//3
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setExtencionDocumento(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//4
                    docPlanillaDetalle.setAfp(registro.get(registro.getHeader(columna)));

                    columna++;//5
                    docPlanillaDetalle.setNuaCua(registro.get(registro.getHeader(columna)));

                    columna++;//6
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNombre(registro.get(registro.getHeader(columna))+" ");
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));
                    columna++;//7
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre()+" "+registro.get(registro.getHeader(columna)));

                    columna++;//8
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre()+" "+registro.get(registro.getHeader(columna)));

                    columna++;//9
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre()+" "+registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//10
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNombre(docPlanillaDetalle.getNombre()+" "+registro.get(registro.getHeader(columna)));

                    columna++;//11
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setNacionalidad(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//12
                    if(!registro.get(registro.getHeader(columna)).isEmpty()){
                        if(registro.get(registro.getHeader(columna)).length()>=15){
                            try {
                                docPlanillaDetalle.setFechaNacimiento(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'BOT' yyyy", Locale.ENGLISH).parse(registro.get(registro.getHeader(columna)))));
                            }catch (Exception e){
                                docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna)));
                            }
                        }
                        else
                            docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna)));
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//13
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setSexo(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//14
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setJubilado(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//15
                    docPlanillaDetalle.setClasificacionLaboral(registro.get(registro.getHeader(columna)));

                    columna++;//16
                    if(!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setCargo(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//17
                    if(!registro.get(registro.getHeader(columna)).isEmpty()) {
                        if(registro.get(registro.getHeader(columna)).length()>15){
                            try {
                                docPlanillaDetalle.setFechaIngreso(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'BOT' yyyy", Locale.ENGLISH).parse(registro.get(registro.getHeader(columna)))));
                            }catch (Exception e){
                                docPlanillaDetalle.setFechaIngreso(registro.get(registro.getHeader(columna)));
                            }
                        }
                        else
                            docPlanillaDetalle.setFechaNacimiento(registro.get(registro.getHeader(columna)));
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//18
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))&&!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setHorasPagadasDia(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//19
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))&&!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setDiasHaberBasico(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//20
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))&&!registro.get(registro.getHeader(columna)).isEmpty())
                        docPlanillaDetalle.setDiasPagados(registro.get(registro.getHeader(columna)));
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//21
                    if(UtilityData.isInteger(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                if(!registro.get(registro.getHeader(columna)).isEmpty())
                                    docPlanillaDetalle.setDominicalMes(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                if(!registro.get(columna).isEmpty()){
                                    if((int) Double.parseDouble(registro.get(columna).replace(",","."))<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario+": El registro en la fila \""+c+"\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if(!docPlanillaDetalle.getHaberBasico().isEmpty())
                                    haberBasico = haberBasico + Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//22
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setDominicalTrabajado(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                if(!registro.get(columna).isEmpty()){
                                    int numero=(int) Double.parseDouble(registro.get(columna).replace(",","."));
                                    if(numero<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if(!docPlanillaDetalle.getHaberBasico().isEmpty())
                                    haberBasico = haberBasico + Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getBonoAntiguedad().isEmpty())
                                    bonoAntiguedad= bonoAntiguedad+Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++;//23
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getBonoOtros().isEmpty())
                                otrosBonos = otrosBonos + Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //24
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasNocturno(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getBonoAntiguedad().isEmpty())
                                    bonoAntiguedad=bonoAntiguedad+Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                                break;
                            case 3:
                                if(!registro.get(columna).isEmpty())
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //25
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHorasDominicales(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getBonoOtros().isEmpty())
                                    otrosBonos= otrosBonos+Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoAfp().isEmpty())
                                    aporteAFP=aporteAFP+Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //26
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                if(!registro.get(columna).isEmpty()){
                                    if((int) Double.parseDouble(registro.get(columna).replace(",","."))<salarioMinimo)
                                        docAlerta.setObservacion(nombreBinario + ": El registro en la fila \"" + c + "\" y la columna \"Haber Básico\" es menor al salario mínimo establecido por ley.");
                                    docPlanillaDetalle.setHaberBasico(registro.get(registro.getHeader(columna)));
                                    if(!docPlanillaDetalle.getHaberBasico().isEmpty())
                                    haberBasico=haberBasico+Double.parseDouble(docPlanillaDetalle.getHaberBasico());
                                }
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 2:
                                if(!registro.get(columna).isEmpty())
                                    docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                                else
                                    errores.add(mensajeError(c, registro.getHeader(columna)));
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoRciva().isEmpty())
                                rcIVA=rcIVA+Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //27
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setHaberDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoAfp().isEmpty())
                                aporteAFP= aporteAFP+Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoOtro().isEmpty())
                                otrosDescuentos=otrosDescuentos+Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //28
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setMontoDominical(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoRciva().isEmpty())
                                rcIVA=rcIVA+Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                                break;
                            case 3:
                                docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                                break;
                        }
                    }
                    else
                        errores.add(mensajeError(c, registro.getHeader(columna)));

                    columna++; //29
                    if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                        switch (valorPlanilla){
                            case 1:
                                docPlanillaDetalle.setMontoHorasExtra(registro.get(registro.getHeader(columna)));
                                break;
                            case 2:
                                docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                                if(!docPlanillaDetalle.getDescuentoOtro().isEmpty())
                                otrosDescuentos= otrosDescuentos+Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                                break;
                            case 3:
                                if(!registro.get(columna).isEmpty())
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
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
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
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            switch (valorPlanilla){
                                case 1:
                                    docPlanillaDetalle.setMontoHorasDominical(registro.get(registro.getHeader(columna)));
                                    break;
                                case 2:
                                    if(!registro.get(columna).isEmpty())
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
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setBonoAntiguedad(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getBonoAntiguedad().isEmpty())
                                bonoAntiguedad=bonoAntiguedad+Double.parseDouble(docPlanillaDetalle.getBonoAntiguedad());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //33
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setBonoProduccion(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getBonoProduccion().isEmpty())
                                bonoProduccion=bonoProduccion+Double.parseDouble(docPlanillaDetalle.getBonoProduccion());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //34
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setBonoFrontera(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getBonoFrontera().isEmpty())
                                subsidioFrontera= subsidioFrontera+Double.parseDouble(docPlanillaDetalle.getBonoFrontera());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //35
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setBonoOtros(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getBonoOtros().isEmpty())
                            otrosBonos= otrosBonos+Double.parseDouble(docPlanillaDetalle.getBonoOtros());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //36
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))&&!registro.get(columna).isEmpty())
                            docPlanillaDetalle.setTotalGanado(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //37
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setDescuentoAfp(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getDescuentoAfp().isEmpty())
                            aporteAFP= aporteAFP+Double.parseDouble(docPlanillaDetalle.getDescuentoAfp());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //38
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setDescuentoRciva(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getDescuentoRciva().isEmpty())
                            rcIVA=rcIVA+Double.parseDouble(docPlanillaDetalle.getDescuentoRciva());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //39
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty()){
                            docPlanillaDetalle.setDescuentoOtro(registro.get(registro.getHeader(columna)));
                            if(!docPlanillaDetalle.getDescuentoOtro().isEmpty())
                            otrosDescuentos= otrosDescuentos+ Double.parseDouble(docPlanillaDetalle.getDescuentoOtro());
                        }
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //40
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))||registro.get(columna).isEmpty())
                            docPlanillaDetalle.setDescuentosTotal(registro.get(registro.getHeader(columna)));
                        else
                            errores.add(mensajeError(c, registro.getHeader(columna)));

                        columna++; //41
                        if(UtilityData.isDecimal(registro.get(registro.getHeader(columna)))&&!registro.get(columna).isEmpty())
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
            logger.error("====>>>> Error al validar el archivo <<<<<=====");
            logger.error(e.getMessage());
        }
        if(tamanioErrores==0&&verificaValidacion==true){
            iDocumentoService.guardaDetallesAlertasActualizaInfLab(docDocumento, docPlanillaDetalles, alertas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Los documentos fueron validados correctamente."));
            validacion=true;
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los documentos no fueron validados correctamente."));
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public List<DocBinario> getListaBinarios() {
        return listaBinarios;
    }

    public void setListaBinarios(List<DocBinario> listaBinarios) {
        this.listaBinarios = listaBinarios;
    }

//    public StreamedContent getFile() {
//        return file;
//    }
//
//    public void setFile(StreamedContent file) {
//        this.file = file;
//    }

    public DocBinario getDocBinario() {
        return docBinario;
    }

    public void setDocBinario(DocBinario docBinario) {
        this.docBinario = docBinario;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public int getAguinaldoAuto() {
        return aguinaldoAuto;
    }

    public void setAguinaldoAuto(int aguinaldoAuto) {
        this.aguinaldoAuto = aguinaldoAuto;
    }

    public int getTrimestralAuto() {
        return trimestralAuto;
    }

    public void setTrimestralAuto(int trimestralAuto) {
        this.trimestralAuto = trimestralAuto;
    }

    public boolean isVerificaValidacion() {
        return verificaValidacion;
    }

    public void setVerificaValidacion(boolean verificaValidacion) {
        this.verificaValidacion = verificaValidacion;
    }

    public String getNombreBinario() {
        return nombreBinario;
    }

    public void setNombreBinario(String nombreBinario) {
        this.nombreBinario = nombreBinario;
    }

    public List<DocAlerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<DocAlerta> alertas) {
        this.alertas = alertas;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public int getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(int salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }
}
