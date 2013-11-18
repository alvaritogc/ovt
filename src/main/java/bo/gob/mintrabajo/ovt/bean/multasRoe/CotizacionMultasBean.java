package bo.gob.mintrabajo.ovt.bean.multasRoe;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IMultaRangoService;
import bo.gob.mintrabajo.ovt.api.IMultaService;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.bean.EmpleadorBean;
import bo.gob.mintrabajo.ovt.entities.ParMulta;
import bo.gob.mintrabajo.ovt.entities.ParMultaRango;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import org.apache.bcel.generic.D2F;

@ManagedBean
@ViewScoped
public class CotizacionMultasBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadorBean.class);
    //
    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacionService;
    @ManagedProperty(value = "#{multaService}")
    private IMultaService iMultaService;
    @ManagedProperty(value = "#{multaRangoService}")
    private IMultaRangoService iMultaRangoService;
    //
    //Tab Roe
    private ParParametrizacion parametrizacion;
    private ParMulta multa;
    private ParMultaRango multaRangoNumeroTrabajadores;
    private ParMultaRango multaRangoDiasTranscurridos;
    //
    private int numeroTrabajadores;
    private BigDecimal multaPorNoContarConElRoe;
    //
    private Date fechaLimitePlazo;
    private Date fechaSolicitudRoe;
    private int diasTranscurridos;
    private BigDecimal totalAPagarParaObtenerRoe;
    private String fechaLimiteRoe;
    //
    private ParObligacionCalendario obligacionCalendario;
    private List<ParObligacionCalendario> listaoObligacionCalendario;
    private String tipoPeriodo;
    private Date fechaPresentacion;
    private String fechaPresentacionString;
    private List<String> listaMontoBase;
    private String montoBase;
    private BigDecimal montoTotalPlanillaTrimestral;
    //
            
    
    @PostConstruct
    public void ini() {
        cargarRoe();
        cargarPlanillaTrimestral();
    }

    public void cargarRoe() {
        numeroTrabajadores = 0;
        multaPorNoContarConElRoe = BigDecimal.ZERO;
        //
        parametrizacion = null;
        //
        try {
            parametrizacion=iParametrizacionService.obtenerParametro("MULTAS", "FECHA_LIMITE_ROE");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //fechaLimitePlazo = sdf.parse("22/06/2005");   
            //fechaLimitePlazo = sdf.parse(sdf.format(new Date()));
            fechaLimiteRoe=parametrizacion.getDescripcion();
            fechaLimitePlazo = sdf.parse(parametrizacion.getDescripcion());
        } catch (Exception e) {
            e.printStackTrace();
            fechaLimitePlazo=new Date();;
        }
        //fechaLimitePlazo=new Date();;
        fechaSolicitudRoe = new Date();
        diasTranscurridos = 0;
        totalAPagarParaObtenerRoe = BigDecimal.ZERO;
    }
    public void cargarPlanillaTrimestral(){
        ParObligacionCalendario obligacionCalendario=new ParObligacionCalendario();
        listaoObligacionCalendario=new ArrayList<ParObligacionCalendario>();
        tipoPeriodo="";
        fechaPresentacion=new Date();
        fechaPresentacionString="";
        listaMontoBase=new ArrayList<String>();
        listaMontoBase.add("80");
        listaMontoBase.add("40");
        montoBase="";
        montoTotalPlanillaTrimestral=BigDecimal.ZERO;
    }

    public String calcularMultaPorMora() {
        System.out.println("=========================================");
        System.out.println("=========================================");
        System.out.println("calcularMultaPorMora");
        System.out.println("=========================================");
        System.out.println("=========================================");
        if (numeroTrabajadores == 0) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar el numero de Trabajadores .")); 
            return "";
        }
        multa = iMultaService.buscarTipoMulta("Multa");
        multaRangoNumeroTrabajadores = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(numeroTrabajadores));
        if(multaRangoNumeroTrabajadores==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No se encontro un rango, para el número de trabajadores.")); 
            return "";
        }
        //
        long numeroT = (long) numeroTrabajadores;
        //multaPorNoContarConElRoe = multaRangoNumeroTrabajadores.getFactor().multiply(new BigDecimal(numeroT));
        multaPorNoContarConElRoe = multaRangoNumeroTrabajadores.getFactor();
        //
        if (fechaSolicitudRoe == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Debe ingresar la fecha de la solicitud .")); 
            return "";
        }
        if (fechaSolicitudRoe.before(fechaLimitePlazo) || fechaSolicitudRoe.equals(fechaLimitePlazo)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información", "La fecha de solicitud debe ser mayor a la fecha limite para calcular la multa.")); 
            return "";
        }

        long diferenciaEnMilis = fechaSolicitudRoe.getTime() - fechaLimitePlazo.getTime();
        System.out.println("Tiempo: " + (diferenciaEnMilis / (1000 * 60 * 60 * 24)));
        diasTranscurridos=(int)(diferenciaEnMilis / (1000 * 60 * 60 * 24));
        multa = iMultaService.buscarTipoMulta("Multa 2");
        System.out.println("ID multa: " + multa.getIdMulta());
        System.out.println("Dias transcurridos: " + diasTranscurridos);
        multaRangoDiasTranscurridos = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(diasTranscurridos));
        if(multaRangoDiasTranscurridos==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No se encontro un rango, para los días transcurridos.")); 
            return "";
        }
        //
        totalAPagarParaObtenerRoe = multaRangoDiasTranscurridos.getFactor().multiply(multaPorNoContarConElRoe);
        System.out.println("totalAPagarParaObtenerRoe: " + totalAPagarParaObtenerRoe);
        return "";
    }
    
    public String calcularMultaPlanillaTrimestral(){
        return "";
    }

    public IParametrizacionService getiParametrizacionService() {
        return iParametrizacionService;
    }

    public void setiParametrizacionService(IParametrizacionService iParametrizacionService) {
        this.iParametrizacionService = iParametrizacionService;
    }

    public Date getFechaLimitePlazo() {
        return fechaLimitePlazo;
    }

    public void setFechaLimitePlazo(Date fechaLimitePlazo) {
        this.fechaLimitePlazo = fechaLimitePlazo;
    }

    public ParParametrizacion getParametrizacion() {
        return parametrizacion;
    }

    public void setParametrizacion(ParParametrizacion parametrizacion) {
        this.parametrizacion = parametrizacion;
    }

    public int getNumeroTrabajadores() {
        return numeroTrabajadores;
    }

    public void setNumeroTrabajadores(int numeroTrabajadores) {
        this.numeroTrabajadores = numeroTrabajadores;
    }

    public BigDecimal getMultaPorNoContarConElRoe() {
        return multaPorNoContarConElRoe;
    }

    public void setMultaPorNoContarConElRoe(BigDecimal multaPorNoContarConElRoe) {
        this.multaPorNoContarConElRoe = multaPorNoContarConElRoe;
    }

    public Date getFechaSolicitudRoe() {
        return fechaSolicitudRoe;
    }

    public void setFechaSolicitudRoe(Date fechaSolicitudRoe) {
        this.fechaSolicitudRoe = fechaSolicitudRoe;
    }

    public int getDiasTranscurridos() {
        return diasTranscurridos;
    }

    public void setDiasTranscurridos(int diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    public BigDecimal getTotalAPagarParaObtenerRoe() {
        return totalAPagarParaObtenerRoe;
    }

    public void setTotalAPagarParaObtenerRoe(BigDecimal totalAPagarParaObtenerRoe) {
        this.totalAPagarParaObtenerRoe = totalAPagarParaObtenerRoe;
    }

    public IMultaRangoService getiMultaRangoService() {
        return iMultaRangoService;
    }

    public void setiMultaRangoService(IMultaRangoService iMultaRangoService) {
        this.iMultaRangoService = iMultaRangoService;
    }

    public IMultaService getiMultaService() {
        return iMultaService;
    }

    public void setiMultaService(IMultaService iMultaService) {
        this.iMultaService = iMultaService;
    }

    public String getFechaLimiteRoe() {
        return fechaLimiteRoe;
    }

    public void setFechaLimiteRoe(String fechaLimiteRoe) {
        this.fechaLimiteRoe = fechaLimiteRoe;
    }

    public ParObligacionCalendario getObligacionCalendario() {
        return obligacionCalendario;
    }

    public void setObligacionCalendario(ParObligacionCalendario obligacionCalendario) {
        this.obligacionCalendario = obligacionCalendario;
    }

    public List<ParObligacionCalendario> getListaoObligacionCalendario() {
        return listaoObligacionCalendario;
    }

    public void setListaoObligacionCalendario(List<ParObligacionCalendario> listaoObligacionCalendario) {
        this.listaoObligacionCalendario = listaoObligacionCalendario;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getFechaPresentacionString() {
        return fechaPresentacionString;
    }

    public void setFechaPresentacionString(String fechaPresentacionString) {
        this.fechaPresentacionString = fechaPresentacionString;
    }

    public List<String> getListaMontoBase() {
        return listaMontoBase;
    }

    public void setListaMontoBase(List<String> listaMontoBase) {
        this.listaMontoBase = listaMontoBase;
    }

    public String getMontoBase() {
        return montoBase;
    }

    public void setMontoBase(String montoBase) {
        this.montoBase = montoBase;
    }


    public BigDecimal getMontoTotalPlanillaTrimestral() {
        return montoTotalPlanillaTrimestral;
    }

    public void setMontoTotalPlanillaTrimestral(BigDecimal montoTotalPlanillaTrimestral) {
        this.montoTotalPlanillaTrimestral = montoTotalPlanillaTrimestral;
    }

}
