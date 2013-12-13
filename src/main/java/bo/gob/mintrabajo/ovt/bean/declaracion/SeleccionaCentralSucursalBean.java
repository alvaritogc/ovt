package bo.gob.mintrabajo.ovt.bean.declaracion;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 12/7/13
 */
@ManagedBean
@ViewScoped
public class SeleccionaCentralSucursalBean implements Serializable{
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
    private ParObligacionCalendario parObligacionCalendario;

    private String mensajeValidacion;
    private boolean habilitado;


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

    @PostConstruct
    public void ini() {
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null)
                parametro = 1;
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null|| !((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p").equals(""))
                parametro = Integer.valueOf(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p"));
        }catch (Exception e) {
            parametro = 1;
        }
        habilitado=true;
        idPersona = (String) session.getAttribute("idEmpleador");
        persona = iPersonaService.buscarPorId(idPersona);
        idUsuario = (Long) session.getAttribute("idUsuario");
        usuario = iUsuarioService.findById(idUsuario);
        tipoEmpresa=1;
        cargar();
    }

    public void cargar(){
        listaCentralSucursales();
    }

    public String seleccionaUnidad(){
        if(tipoEmpresa!=2)
            unidadSeleccionada=central;
        else
            unidadSeleccionada=iUnidadService.obtienePorId(new PerUnidadPK(idPersona, idUnidad));

        if(verEstadoPlanilla()){
            session.setAttribute("parametro", parametro);
            session.setAttribute("unidadSeleccionada", unidadSeleccionada);
            session.setAttribute("tipoEmpresa", tipoEmpresa);
            if(parametro<=3)
                return "irDeclaracionTrimestral";
            if(parametro<=5)
                return "irDeclaracionAguinaldo";
        }
        return null;
    }

    public void listaCentralSucursales(){
        central = new PerUnidad();
        sucursales = new ArrayList<PerUnidad>();
        List<PerUnidad> listaUnidades=iUnidadService.listarUnidadesSucursales(idPersona);
        for(PerUnidad sucursal:listaUnidades)  {
            if(sucursal.getPerUnidadPK().getIdUnidad()==0)
                central=sucursal;
            sucursales.add(sucursal);
        }
    }

    public boolean verEstadoPlanilla(){
        List<DocDocumento> listaDocumentos = new ArrayList<DocDocumento>();
        habilitado = true;

        switch (parametro){
            case 1:
                parObligacionCalendario=iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo(DateUtils.truncate(new Date(), Calendar.DATE));
                if(parObligacionCalendario==null){
                    mensajeValidacion="Solo puede realizar la Declaración Jurada Trimestral dentro del plazo establecido.";
                    habilitado=false;
                    return false;
                }else{
                    listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo(), "LC1010");
                    if(listaDocumentos.size()==0){
                        listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1011");
                        if(listaDocumentos.size()!=0){
                            mensajeValidacion="No se puede realizar la Declaración Jurada Trimestral si ya declaro una sin movimiento.";
                            habilitado=false;
                            return false;
                        }
                        else
                            listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1010");
                    }
                }
                break;
            case 2:
                parObligacionCalendario=iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo(DateUtils.truncate(new Date(), Calendar.DATE));
                if(parObligacionCalendario==null){
                    mensajeValidacion="Solo puede realizar la Declaración Jurada Trimestral Sin Movimiento dentro del plazo establecido.";
                    habilitado=false;
                    return false;
                }else{
                    listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo(), "LC1011");
                    if(listaDocumentos.size()==0){
                        listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1010");
                        if(listaDocumentos.size()!=0){
                            mensajeValidacion="No se puede realizar la Declaración Jurada Trimestral Sin Movimiento si ya declaro una con movimiento.";
                            habilitado=false;
                            return false;
                        }
                        else
                            listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1011");
                    }
                }
                break;
            case 3:
                parObligacionCalendario=iObligacionCalendarioService.listarPlanillaTrimPorFechaHastaFechaPlazo2(DateUtils.truncate(new Date(), Calendar.DATE));
                if(parObligacionCalendario==null){
                    mensajeValidacion="Solo puede realizar la Declaración Jurada Trimestral Rectificatoria dentro del plazo establecido.";
                    habilitado=false;
                    return false;
                }else{
                    listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1012");
                    if(listaDocumentos.size()==0){
                        listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumentos(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1010", "LC1011");
                        if(listaDocumentos.size()==0){
                            mensajeValidacion="No existe alguna Declaración Jurada Trimestral para rectificar.";
                            habilitado=false;
                            return false;
                        }
                        else
                            listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1012");
                    }
                }
                break;
            case 4:
                parObligacionCalendario=iObligacionCalendarioService.listarPlanillaAguiPorFechaHastaFechaPlazo(DateUtils.truncate(new Date(), Calendar.DATE));
                if(parObligacionCalendario==null){
                    mensajeValidacion="Solo puede realizar la Declaración Jurada de Aguinaldo dentro del plazo establecido.";
                    habilitado=false;
                    return false;
                }else
                    listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo(), "LC1020");
                break;
            case 5:
                parObligacionCalendario=iObligacionCalendarioService.listarPlanillaAguiPorFechaHastaFechaPlazo2(DateUtils.truncate(new Date(), Calendar.DATE));
                if(parObligacionCalendario==null){
                    mensajeValidacion="Solo puede realizar la Declaración Jurada de Aguinaldo Rectificatoria dentro del plazo establecido.";
                    habilitado=false;
                    return false;
                }else{
                    listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1021");
                    if(listaDocumentos.size()==0){
                        listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1020");
                        if(listaDocumentos.size()==0){
                            mensajeValidacion="No existe alguna Declaración Jurada de Aguinaldo para rectificar.";
                            habilitado=false;
                            return false;
                        }
                        else
                            listaDocumentos = iDocumentoService.listarDocumentosPorpersonaUnidadFechasCodDocumento(unidadSeleccionada.getPerUnidadPK().getIdPersona(), parObligacionCalendario.getFechaHasta(), parObligacionCalendario.getFechaPlazo2(), "LC1021");
                    }
                }
                break;
            default:
                return false;
        }
        if(parametro==1){
            for(DocDocumento documento:listaDocumentos){
                if(documento.getTipoMedioRegistro().toUpperCase().equals("CONSOLIDADO") && !documento.getCodEstado().getCodEstado().equals("999")){
                    mensajeValidacion="La Declaración Jurada Trimestral ya fue declarada Consolidada.";
                    habilitado=false;
                    return false;
                }
            }
        }

        if(parametro==2){
            for(DocDocumento documento:listaDocumentos){
                if(documento.getTipoMedioRegistro().toUpperCase().equals("CONSOLIDADO") && !documento.getCodEstado().getCodEstado().equals("999")){
                    mensajeValidacion="La Declaración Jurada Trimestral ya fue declarada Consolidada.";
                    habilitado=false;
                    return false;
                }
            }
        }

        if(parametro==3){
            for(DocDocumento documento:listaDocumentos){
                if(documento.getTipoMedioRegistro().toUpperCase().equals("CONSOLIDADO") && !documento.getCodEstado().getCodEstado().equals("999")){
                    mensajeValidacion="La Declaración Jurada Trimestral ya fue declarada Consolidada.";
                    habilitado=false;
                    return false;
                }
            }
        }

        if(parametro==4 || parametro==5){
            for(DocDocumento documento:listaDocumentos){
                if(documento.getTipoMedioRegistro().toUpperCase().equals("CONSOLIDADO") && !documento.getCodEstado().getCodEstado().equals("999")){
                    mensajeValidacion="La Declaración Jurada de Aguinaldo ya fue declarada Consolidada.";
                    habilitado=false;
                    return false;
                }
            }

            if (tipoEmpresa==1) {
                for(DocDocumento documento:listaDocumentos){
                    if(documento.getTipoMedioRegistro().toUpperCase().equals("SUCURSAL") && !documento.getCodEstado().getCodEstado().equals("999")){
                        mensajeValidacion="La Declaración Jurada de Aguinaldo ya fue declarada por Sucursal.";
                        habilitado=false;
                        return false;
                    }
                }
            }

            for(DocDocumento documento:listaDocumentos){
                if(documento.getTipoMedioRegistro().toUpperCase().equals("SUCURSAL") && !documento.getCodEstado().getCodEstado().equals("999") && documento.getPerUnidad().getPerUnidadPK().getIdUnidad() == unidadSeleccionada.getPerUnidadPK().getIdUnidad()){
                    if(parametro==4)
                        mensajeValidacion="La Declaración Jurada de Aguinaldo ya fue declarada para esta Sucursal.";
                    if(parametro==5)
                        mensajeValidacion="La Declaración Jurada de Aguinaldo ya fue rectificada para esta Sucursal.";
                    habilitado=false;
                    return false;
                }
            }
        }
        return true;
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
}