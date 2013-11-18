/*
 * Copyright 2013 lvaldez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bo.gob.mintrabajo.ovt.bean.parametrizacion;

//import bo.gob.mintrabajo.ovt.api.ICalendarioService;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.api.IObligacionService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class obligacionCalendarioBean implements Serializable{
    @ManagedProperty(value = "#{obligacionCalendarioService}")
    private IObligacionCalendarioService iObligacionCalendarioService;
    @ManagedProperty(value = "#{obligacionService}")
    private IObligacionService iObligacionService;
    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value = "#{calendarioService}")
    //private ICalendarioService iCalendarioService;
    
    private HttpSession session;
    private Long idUsuario;
    
    private List<ParObligacionCalendario> listaObligacionCalendario;
    private ParObligacionCalendario obligacionCalendario= new ParObligacionCalendario();
    
    //para el formulario
    private boolean evento=false;
    private List<ParObligacion> listaObligacion;
    private List<ParDominio> listaDominio;
    private List<ParDominio> listaDominioPeriodo;
    private List<ParCalendario> listaCalendario;
    private ParObligacion obligacion= new ParObligacion();
    private String codObligacion="";
    private String codObligacionForm="";
    private String gestion;
    private String periodo;
    private List<Integer> listaAnio;
    private Integer anioActual;
            
    
    @PostConstruct
    public void ini() {
        idUsuario = null;
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaObligacionCalendario =new ArrayList<ParObligacionCalendario>();
        //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
        //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
        listaObligacion= new ArrayList<ParObligacion>();
        listaObligacion= iObligacionService.listaObligacion();
        listaDominio = new ArrayList<ParDominio>();
        listaDominio = iDominioService.obtenerItemsDominio("TCALENDARIO");
        listaCalendario = new ArrayList<ParCalendario>();
//        Calendar calendar = Calendar.getInstance();
//        anioActual=calendar.get(Calendar.YEAR);
//        int year = anioActual - 5;
//        listaAnio=new ArrayList<Integer>();
//        for (int i = 0; i < 21; i++) {
//            listaAnio.add(year);
//            year++;
//        }
    }
    
    public void listarPeriodo(){
        listaDominioPeriodo = new ArrayList<ParDominio>();
        listaDominioPeriodo = iDominioService.obtenerDominioPorNombrePadreYValorPadre("TCALENDARIO",obligacionCalendario.getTipoCalendario());
        periodo=listaDominioPeriodo.get(0).getParDominioPK().getValor();
        System.out.println("== valor periodo " + periodo);
        System.out.println("== valor tipocalendario " + obligacionCalendario.getTipoCalendario());
        FacesContext context = FacesContext.getCurrentInstance();  
        try {
           // listaCalendario=iCalendarioService.listaCalendarioPorTipoPeriodoTipoCalendario(periodo, obligacionCalendario.getTipoCalendario());
            gestion=listaCalendario.get(0).getParCalendarioPK().getGestion();
            System.out.println("000>>>> " + gestion);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Atencion", "No existe una gestion para el periodo " + listaDominioPeriodo.get(0).getDescripcion()));  
            e.printStackTrace();
        }
    }
    
    public void listaGestiong(){
        //listaCalendario=iCalendarioService.listaCalendarioPorTipoPeriodoTipoCalendario(periodo, obligacionCalendario.getTipoCalendario());
    }
    
    public void listarObligacionCalendario(){
        if(codObligacion.isEmpty() || codObligacion.equals(" ")){
            //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
           // listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
        }else{
           // listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioPorObligacion(codObligacion);
        }
    }
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();
        
        ParObligacion parObligacion= new ParObligacion();
        parObligacion= iObligacionService.obligacionPorCod(codObligacionForm);
        
        String  REGISTRO_BITACORA=idUsuario.toString();
        System.out.println("=====> "  +" = "+ obligacionCalendario.getTipoCalendario() +" = "+
                obligacionCalendario.getFechaDesde() +" = "+ obligacionCalendario.getFechaHasta() +" = "+ obligacionCalendario.getFechaPlazo());
        System.out.println("=== parObligacion " + parObligacion.getDescripcion());
        try {
       //     ParObligacionCalendario ob = iObligacionCalendarioService.saveObligacionCalendario(obligacionCalendario,gestion, periodo, REGISTRO_BITACORA, parObligacion, evento);
           context.execute("dlgFormObligacionCalendario.hide();");
            nuevo();
          //  listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void nuevo(){
        obligacionCalendario=new ParObligacionCalendario();
        evento=false;
        codObligacion="";
        codObligacionForm="";
        gestion="";
        periodo="";
    }
    
    public void confirmaEliminar(){  
/*        try {
            if(iObligacionCalendarioService.deleteObligacionCalendario(obligacionCalendario)){
                //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
                listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
                obligacionCalendario=new ParObligacionCalendario();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    } 

    /**
     * @return the iObligacionCalendarioService
     */
    public IObligacionCalendarioService getiObligacionCalendarioService() {
        return iObligacionCalendarioService;
    }

    /**
     * @param iObligacionCalendarioService the iObligacionCalendarioService to set
     */
    public void setiObligacionCalendarioService(IObligacionCalendarioService iObligacionCalendarioService) {
        this.iObligacionCalendarioService = iObligacionCalendarioService;
    }

    /**
     * @return the listaObligacionCalendario
     */
    public List<ParObligacionCalendario> getListaObligacionCalendario() {
        return listaObligacionCalendario;
    }

    /**
     * @param listaObligacionCalendario the listaObligacionCalendario to set
     */
    public void setListaObligacionCalendario(List<ParObligacionCalendario> listaObligacionCalendario) {
        this.listaObligacionCalendario = listaObligacionCalendario;
    }

    /**
     * @return the obligacionCalendario
     */
    public ParObligacionCalendario getObligacionCalendario() {
        return obligacionCalendario;
    }

    /**
     * @param obligacionCalendario the obligacionCalendario to set
     */
    public void setObligacionCalendario(ParObligacionCalendario obligacionCalendario) {
        this.obligacionCalendario = obligacionCalendario;
    }

    /**
     * @return the evento
     */
    public boolean isEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(boolean evento) {
        this.evento = evento;
    }

    /**
     * @return the iUtilsService
     */
    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    /**
     * @param iUtilsService the iUtilsService to set
     */
    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    /**
     * @return the iObligacionService
     */
    public IObligacionService getiObligacionService() {
        return iObligacionService;
    }

    /**
     * @param iObligacionService the iObligacionService to set
     */
    public void setiObligacionService(IObligacionService iObligacionService) {
        this.iObligacionService = iObligacionService;
    }
//
//    /**
//     * @return the listaObligacion
//     */
//    public List<SelectItem> getListaObligacion() {
//        return listaObligacion;
//    }
//
//    /**
//     * @param listaObligacion the listaObligacion to set
//     */
//    public void setListaObligacion(List<SelectItem> listaObligacion) {
//        this.listaObligacion = listaObligacion;
//    }

    /**
     * @return the obligacion
     */
    public ParObligacion getObligacion() {
        return obligacion;
    }

    /**
     * @param obligacion the obligacion to set
     */
    public void setObligacion(ParObligacion obligacion) {
        this.obligacion = obligacion;
    }

    /**
     * @return the codObligacion
     */
    public String getCodObligacion() {
        return codObligacion;
    }

    /**
     * @param codObligacion the codObligacion to set
     */
    public void setCodObligacion(String codObligacion) {
        this.codObligacion = codObligacion;
    }

    /**
     * @return the listaObligacion
     */
    public List<ParObligacion> getListaObligacion() {
        return listaObligacion;
    }

    /**
     * @param listaObligacion the listaObligacion to set
     */
    public void setListaObligacion1(List<ParObligacion> listaObligacion) {
        this.listaObligacion = listaObligacion;
    }

    /**
     * @return the codObligacionForm
     */
    public String getCodObligacionForm() {
        return codObligacionForm;
    }

    /**
     * @param codObligacionForm the codObligacionForm to set
     */
    public void setCodObligacionForm(String codObligacionForm) {
        this.codObligacionForm = codObligacionForm;
    }

    /**
     * @return the iDominioService
     */
    public IDominioService getiDominioService() {
        return iDominioService;
    }

    /**
     * @param iDominioService the iDominioService to set
     */
    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    /**
     * @return the listaDominio
     */
    public List<ParDominio> getListaDominio() {
        return listaDominio;
    }

    /**
     * @param listaDominio the listaDominio to set
     */
    public void setListaDominio(List<ParDominio> listaDominio) {
        this.listaDominio = listaDominio;
    }

    /**
     * @return the listaAnio
     */
    public List<Integer> getListaAnio() {
        return listaAnio;
    }

    /**
     * @param listaAnio the listaAnio to set
     */
    public void setListaAnio(List<Integer> listaAnio) {
        this.listaAnio = listaAnio;
    }

    /**
     * @return the anioActual
     */
    public Integer getAnioActual() {
        return anioActual;
    }

    /**
     * @param anioActual the anioActual to set
     */
    public void setAnioActual(Integer anioActual) {
        this.anioActual = anioActual;
    }

    /**
     * @return the listaDominioPeriodo
     */
    public List<ParDominio> getListaDominioPeriodo() {
        return listaDominioPeriodo;
    }

    /**
     * @param listaDominioPeriodo the listaDominioPeriodo to set
     */
    public void setListaDominioPeriodo(List<ParDominio> listaDominioPeriodo) {
        this.listaDominioPeriodo = listaDominioPeriodo;
    }

    /**
     * @return the iCalendarioService
     */
/*    public ICalendarioService getiCalendarioService() {
        return iCalendarioService;
    }

    *//**
     * @param iCalendarioService the iCalendarioService to set
     *//*
    public void setiCalendarioService(ICalendarioService iCalendarioService) {
        this.iCalendarioService = iCalendarioService;
    }*/

    /**
     * @return the listaCalendario
     */
    public List<ParCalendario> getListaCalendario() {
        return listaCalendario;
    }

    /**
     * @param listaCalendario the listaCalendario to set
     */
    public void setListaCalendario(List<ParCalendario> listaCalendario) {
        this.listaCalendario = listaCalendario;
    }

    /**
     * @return the gestion
     */
    public String getGestion() {
        return gestion;
    }

    /**
     * @param gestion the gestion to set
     */
    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
