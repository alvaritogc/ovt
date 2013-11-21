package bo.gob.mintrabajo.ovt.bean.multasRoe;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IMultaRangoService;
import bo.gob.mintrabajo.ovt.api.IMultaService;
import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.bean.EmpleadorBean;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
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
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    //
    //
    //Tab Roe
    private ParParametrizacion parametrizacion;
    private ParMulta multa;
    private ParMultaRango multaRangoNumeroTrabajadores;
    private ParMultaRango multaRangoDiasTranscurridos;
    //
    private int numeroTrabajadores;
    private BigDecimal multaPorNoContarConElRoe;
    private BigDecimal totalGanadoDeLaPlanillaRoe;
    //
    private Date fechaInicioActividades;
    private Date fechaLimitePlazo;
    private Date fechaPresentacionRoe;
    //
    private int diasTranscurridos;
    private BigDecimal totalAPagarParaObtenerRoe;
    private BigDecimal multaRoeDiasDeRetraso;
    private String fechaLimiteRoe;
    //
    //Planilla trimestral
    private List<ParObligacionCalendario> listaObligacionCalendario;
    private Long idObligacionCalendario;
    private ParObligacionCalendario obligacionCalendario;
    //
    private Date fechaVencimientoDeLaPresentacion;
    private Date fechaLimitePlazoPlanillaTrimestral;
    private Date fechaPresentacionPlanillaTrimestral;
    private int diasTranscurridosPlanillaTrimestral;
    private int numeroTrabajadoresPlanillaTrimestral;
    private BigDecimal multaPlanillaTrimestral;
    private BigDecimal totalGanadoDeLaPlanillaTrimestral;
    private BigDecimal multaPlanillaTrimestralDiasDeRetraso;
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
        totalGanadoDeLaPlanillaRoe = BigDecimal.ZERO;
        //
        fechaInicioActividades = null;
        fechaLimitePlazo = null;
        fechaPresentacionRoe = new Date();
        diasTranscurridos = 0;
        multaRoeDiasDeRetraso= BigDecimal.ZERO;
        totalAPagarParaObtenerRoe = BigDecimal.ZERO;
        
    }

    public void calcularFechaLimiteRoe() {
        parametrizacion = iParametrizacionService.obtenerParametro("MULTAS", "MESES_TOLERANCIA_ROE");
        //
        Calendar ca = Calendar.getInstance();
        ca.setTime(fechaInicioActividades);
        ca.add(Calendar.MONTH, Integer.valueOf(parametrizacion.getDescripcion()));
        //
        fechaLimitePlazo = ca.getTime();
    }

    public String calcularMultaPorMora() {
        if (fechaInicioActividades == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar la fecha de inicio de actividades."));
            return "";
        }
        if (fechaPresentacionRoe.before(fechaLimitePlazo) || fechaPresentacionRoe.equals(fechaLimitePlazo)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La Fecha de inscripción del roe debe ser mayor a la fecha límite para calcular la multa."));
            return "";
        }
        if (fechaPresentacionRoe == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar la de la presentacion ."));
            return "";
        }
        if (numeroTrabajadores == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el numero de Trabajadores ."));
            return "";
        }
        multa = iMultaService.buscarTipoMulta("Multa");
        multaRangoNumeroTrabajadores = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(numeroTrabajadores));
        if (multaRangoNumeroTrabajadores == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro un rango, para el número de trabajadores."));
            return "";
        }
        //
        multaPorNoContarConElRoe = multaRangoNumeroTrabajadores.getFactor();
        //

        long diferenciaFechas = fechaPresentacionRoe.getTime() - fechaLimitePlazo.getTime();
        diasTranscurridos = (int) (diferenciaFechas / (1000 * 60 * 60 * 24));

        //
        multa = iMultaService.buscarTipoMulta("Multa 2");
        multaRangoDiasTranscurridos = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(diasTranscurridos));
        if (multaRangoDiasTranscurridos == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro un rango, para los días transcurridos."));
            return "";
        }
        if (!(totalGanadoDeLaPlanillaRoe.compareTo(BigDecimal.ZERO) == 1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el campo Total ganado de la planilla."));
            return "";
        }
        //
        multaRoeDiasDeRetraso=(multaRangoDiasTranscurridos.getFactor().multiply(totalGanadoDeLaPlanillaRoe));
        totalAPagarParaObtenerRoe = multaRoeDiasDeRetraso.add(multaPorNoContarConElRoe);
        return "";
    }

    public void cargarPlanillaTrimestral() {
        listaObligacionCalendario=iObligacionCalendarioService.listaObligacionCalendarioPorObligacion("PLATRI");
        idObligacionCalendario=new Long(0l);
        fechaVencimientoDeLaPresentacion=null;
        fechaLimitePlazoPlanillaTrimestral=null;
        fechaPresentacionPlanillaTrimestral=new Date();
        diasTranscurridosPlanillaTrimestral=0;
        numeroTrabajadoresPlanillaTrimestral=0;
        multaPlanillaTrimestral= BigDecimal.ZERO;
        totalGanadoDeLaPlanillaTrimestral=BigDecimal.ZERO;
        multaPlanillaTrimestralDiasDeRetraso= BigDecimal.ZERO;
        montoTotalPlanillaTrimestral=BigDecimal.ZERO;
    }
    
    public String calcularFechaLimitePlanillaTrimestral(){
        if(idObligacionCalendario!=null){
            obligacionCalendario=iObligacionCalendarioService.findById(idObligacionCalendario);
        fechaLimitePlazoPlanillaTrimestral=obligacionCalendario.getFechaHasta();
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar el trimestre."));
            fechaLimitePlazoPlanillaTrimestral=null;
        }
        
        return "";
    }

    public String calcularMultaPlanillaTrimestral() {
        if (fechaLimitePlazoPlanillaTrimestral == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar el campo Trimestre."));
            return "";
        }
        multa = iMultaService.buscarTipoMulta("Multa");
        multaRangoNumeroTrabajadores = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(numeroTrabajadoresPlanillaTrimestral));
        if (multaRangoNumeroTrabajadores == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro un rango, para el número de trabajadores."));
            return "";
        }
        multaPlanillaTrimestral=multaRangoNumeroTrabajadores.getFactor();
        //
        long diferenciaFechas = fechaPresentacionPlanillaTrimestral.getTime() - fechaLimitePlazoPlanillaTrimestral.getTime();
        diasTranscurridosPlanillaTrimestral = (int) (diferenciaFechas / (1000 * 60 * 60 * 24));

        //
        multa = iMultaService.buscarTipoMulta("Multa 2");
        multaRangoDiasTranscurridos = iMultaRangoService.buscarPorRango(multa.getIdMulta(), new BigDecimal(diasTranscurridosPlanillaTrimestral));
        if (multaRangoDiasTranscurridos == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro un rango, para los días transcurridos."));
            return "";
        }
        if (!(totalGanadoDeLaPlanillaTrimestral.compareTo(BigDecimal.ZERO) == 1)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar el campo Total ganado de la planilla."));
            return "";
        }
        //
        multaPlanillaTrimestralDiasDeRetraso=(multaRangoDiasTranscurridos.getFactor().multiply(totalGanadoDeLaPlanillaTrimestral));
        montoTotalPlanillaTrimestral = multaPlanillaTrimestralDiasDeRetraso.add(multaPlanillaTrimestral);
        //
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

    public BigDecimal getMontoTotalPlanillaTrimestral() {
        return montoTotalPlanillaTrimestral;
    }

    public void setMontoTotalPlanillaTrimestral(BigDecimal montoTotalPlanillaTrimestral) {
        this.montoTotalPlanillaTrimestral = montoTotalPlanillaTrimestral;
    }

    public IObligacionCalendarioService getiObligacionCalendarioService() {
        return iObligacionCalendarioService;
    }

    public void setiObligacionCalendarioService(IObligacionCalendarioService iObligacionCalendarioService) {
        this.iObligacionCalendarioService = iObligacionCalendarioService;
    }

    public BigDecimal getTotalGanadoDeLaPlanillaRoe() {
        return totalGanadoDeLaPlanillaRoe;
    }

    public void setTotalGanadoDeLaPlanillaRoe(BigDecimal totalGanadoDeLaPlanillaRoe) {
        this.totalGanadoDeLaPlanillaRoe = totalGanadoDeLaPlanillaRoe;
    }

    public BigDecimal getTotalGanadoDeLaPlanillaTrimestral() {
        return totalGanadoDeLaPlanillaTrimestral;
    }

    public void setTotalGanadoDeLaPlanillaTrimestral(BigDecimal totalGanadoDeLaPlanillaTrimestral) {
        this.totalGanadoDeLaPlanillaTrimestral = totalGanadoDeLaPlanillaTrimestral;
    }

    public Date getFechaInicioActividades() {
        return fechaInicioActividades;
    }

    public void setFechaInicioActividades(Date fechaInicioActividades) {
        this.fechaInicioActividades = fechaInicioActividades;
    }

    public Date getFechaPresentacionRoe() {
        return fechaPresentacionRoe;
    }

    public void setFechaPresentacionRoe(Date fechaPresentacionRoe) {
        this.fechaPresentacionRoe = fechaPresentacionRoe;
    }

    public Date getFechaVencimientoDeLaPresentacion() {
        return fechaVencimientoDeLaPresentacion;
    }

    public void setFechaVencimientoDeLaPresentacion(Date fechaVencimientoDeLaPresentacion) {
        this.fechaVencimientoDeLaPresentacion = fechaVencimientoDeLaPresentacion;
    }

    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public Date getFechaLimitePlazoPlanillaTrimestral() {
        return fechaLimitePlazoPlanillaTrimestral;
    }

    public void setFechaLimitePlazoPlanillaTrimestral(Date fechaLimitePlazoPlanillaTrimestral) {
        this.fechaLimitePlazoPlanillaTrimestral = fechaLimitePlazoPlanillaTrimestral;
    }

    public Date getFechaPresentacionPlanillaTrimestral() {
        return fechaPresentacionPlanillaTrimestral;
    }

    public void setFechaPresentacionPlanillaTrimestral(Date fechaPresentacionPlanillaTrimestral) {
        this.fechaPresentacionPlanillaTrimestral = fechaPresentacionPlanillaTrimestral;
    }

    public int getDiasTranscurridosPlanillaTrimestral() {
        return diasTranscurridosPlanillaTrimestral;
    }

    public void setDiasTranscurridosPlanillaTrimestral(int diasTranscurridosPlanillaTrimestral) {
        this.diasTranscurridosPlanillaTrimestral = diasTranscurridosPlanillaTrimestral;
    }

    public int getNumeroTrabajadoresPlanillaTrimestral() {
        return numeroTrabajadoresPlanillaTrimestral;
    }

    public void setNumeroTrabajadoresPlanillaTrimestral(int numeroTrabajadoresPlanillaTrimestral) {
        this.numeroTrabajadoresPlanillaTrimestral = numeroTrabajadoresPlanillaTrimestral;
    }

    public List<ParObligacionCalendario> getListaObligacionCalendario() {
        return listaObligacionCalendario;
    }

    public void setListaObligacionCalendario(List<ParObligacionCalendario> listaObligacionCalendario) {
        this.listaObligacionCalendario = listaObligacionCalendario;
    }

    public Long getIdObligacionCalendario() {
        return idObligacionCalendario;
    }

    public void setIdObligacionCalendario(Long idObligacionCalendario) {
        this.idObligacionCalendario = idObligacionCalendario;
    }

    public BigDecimal getMultaPlanillaTrimestral() {
        return multaPlanillaTrimestral;
    }

    public void setMultaPlanillaTrimestral(BigDecimal multaPlanillaTrimestral) {
        this.multaPlanillaTrimestral = multaPlanillaTrimestral;
    }

    public BigDecimal getMultaPlanillaTrimestralDiasDeRetraso() {
        return multaPlanillaTrimestralDiasDeRetraso;
    }

    public void setMultaPlanillaTrimestralDiasDeRetraso(BigDecimal multaPlanillaTrimestralDiasDeRetraso) {
        this.multaPlanillaTrimestralDiasDeRetraso = multaPlanillaTrimestralDiasDeRetraso;
    }

    public BigDecimal getMultaRoeDiasDeRetraso() {
        return multaRoeDiasDeRetraso;
    }

    public void setMultaRoeDiasDeRetraso(BigDecimal multaRoeDiasDeRetraso) {
        this.multaRoeDiasDeRetraso = multaRoeDiasDeRetraso;
    }
}
