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

import bo.gob.mintrabajo.ovt.api.IObligacionCalendarioService;
import bo.gob.mintrabajo.ovt.api.IObligacionService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
    
    private HttpSession session;
    private Long idUsuario;
    
    private List<ParObligacionCalendario> listaObligacionCalendario;
    private ParObligacionCalendario obligacionCalendario= new ParObligacionCalendario();
    
    //para el formulario
    private boolean evento=false;
    //private List<SelectItem> listaObligacion;
    private List<ParObligacion> listaObligacion;
    private ParObligacion obligacion= new ParObligacion();
    private String codObligacion="";
    private String codObligacionForm="";
            
    
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
        listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
        listaObligacion= new ArrayList<ParObligacion>();
        listaObligacion= iObligacionService.listaObligacion();
    }
    
    public void listarObligacionCalendario(){
        if(codObligacion.isEmpty() || codObligacion.equals(" ")){
            //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
            listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
        }else{
            listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioPorObligacion(codObligacion);
        }
    }
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();
        if(codObligacionForm.isEmpty() || codObligacionForm== null ){ return;}
        if(obligacionCalendario.getTipoCalendario().isEmpty()){return;}
        if(obligacionCalendario.getGestion().isEmpty()){return;}
        if(obligacionCalendario.getFechaDesde().toString().isEmpty()){return;}
        if(obligacionCalendario.getFechaHasta().toString().isEmpty()){return;}
        if(obligacionCalendario.getFechaPlazo().toString().isEmpty()){return;}
        context.execute("dlgFormObligacionCalendario.hide();");
        
        ParObligacion parObligacion= new ParObligacion();
        parObligacion= iObligacionService.obligacionPorCod(codObligacionForm);
        
        //final String  REGISTRO_BITACORA="OVT";
        final String  REGISTRO_BITACORA=idUsuario.toString();
        Date fechaBitacora = new Date();
        try {
            if(obligacionCalendario.getIdObligacionCalendario()==null && evento==false){
                obligacionCalendario.setIdObligacionCalendario(iUtilsService.valorSecuencia("PAR_ENTIDAD_SEC"));
            }
            obligacionCalendario.setCodObligacion(parObligacion);
            obligacionCalendario.setFechaBitacora(fechaBitacora);
            obligacionCalendario.setRegistroBitacora(REGISTRO_BITACORA);
            ParObligacionCalendario ob = iObligacionCalendarioService.saveObligacionCalendario(obligacionCalendario);
                //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
           limpiar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void limpiar(){
        listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
        obligacionCalendario=new ParObligacionCalendario();
        evento=false;
        codObligacion="";
        codObligacionForm="";
    }
    
    public void confirmaEliminar(){  
        try {
            if(iObligacionCalendarioService.deleteObligacionCalendario(obligacionCalendario)){
                //listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendario();
                listaObligacionCalendario= iObligacionCalendarioService.listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
                obligacionCalendario=new ParObligacionCalendario();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
