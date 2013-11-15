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

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.api.ITipoCambioService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.entities.ParTipoCambio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class TipoCambioBean implements Serializable{
    @ManagedProperty(value = "#{tipoCambioService}")
    private ITipoCambioService iTipoCambioService;
    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacionService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    
    private HttpSession session;
    private Long idUsuario;
    
    private List<ParTipoCambio> listaTipoCambio;
    private ParTipoCambio tipoCambioForm;
    
    private String seleccion;
    private boolean accion;
    
    private String monedaBase;
    private String monedaCambio;
    private String monedaBaseForm;
    private String monedaCambioForm;
    private Date fecha;
    
    private ParDominio monedaBaseInicial;
    private ParDominio monedaCambioInicial;
    
    private List<ParDominio> listaMonedasBase;
    private List<ParDominio> listaMonedasCambio;
    
    private List<ParDominio> listaMonedasBaseForm;
    private List<ParDominio> listaMonedasCambioForm;
    
    @PostConstruct
    public void ini() {
        idUsuario = null;
        fecha= new Date();
        tipoCambioForm= new ParTipoCambio();
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaTipoCambio =new ArrayList<ParTipoCambio>();
        ParParametrizacion monedaBaseI=iParametrizacionService.obtenerParametro("MONEDAS", "MONEDA_BASE");
        ParParametrizacion monedaCambioI=iParametrizacionService.obtenerParametro("MONEDAS", "MONEDA_PARALELA");
        monedaBaseInicial=iDominioService.obtenerDominioPorNombreYValor("TMONEDA", monedaBaseI.getDescripcion());
        monedaCambioInicial=iDominioService.obtenerDominioPorNombreYValor("TMONEDA", monedaCambioI.getDescripcion());
        
        monedaBase=monedaBaseInicial.getParDominioPK().getValor();
        monedaCambio=monedaCambioInicial.getParDominioPK().getValor();
        
        listaMonedasBase =new ArrayList<ParDominio>();
        listaMonedasBase = iDominioService.obtenerItemsDominio("TMONEDA");
        
        listaMonedasCambio =new ArrayList<ParDominio>();
        listaMonedasCambio = iDominioService.obtenerDominioPorNombreDistintoValor("TMONEDA",monedaBase);
        
        seleccion="vista";
        accion=false;
        listaTipoCambio=iTipoCambioService.listaTipoDeCambios(monedaBase, monedaCambio);
    }
    
    public void mostrarLista(){
        String moneda1;
        String moneda2;
        if(seleccion.equals("form")){
            moneda1=monedaBaseForm;
            monedaCambioForm=valorMonedaCambio(moneda1);
            moneda2=monedaCambioForm;
        }else{
            moneda1=monedaBase;
            monedaCambio=valorMonedaCambio(moneda1);
            moneda2=monedaCambio;
        }

        listaTipoCambio=iTipoCambioService.listaTipoDeCambios(moneda1, moneda2);
    }
    
    public String valorMonedaCambio(String valorMonedaBase){
        listaMonedasCambio = iDominioService.obtenerDominioPorNombreDistintoValor("TMONEDA",valorMonedaBase);
        return listaMonedasCambio.get(0).getParDominioPK().getValor();
    }
    
    public void mostrarDatos(){
        String moneda1;
        String moneda2;
        if(seleccion.equals("form")){
            moneda1=monedaBaseForm;
            moneda2=monedaCambioForm;
        }else{
            moneda1=monedaBase;
            moneda2=monedaCambio;
        }
        listaTipoCambio=iTipoCambioService.listaTipoDeCambios(moneda1, moneda2);
    }
    
    public void nuevo(){     
        tipoCambioForm= new ParTipoCambio();
        fecha= new Date();
        monedaBaseForm=monedaBaseInicial.getParDominioPK().getValor();
        monedaCambioForm=monedaCambioInicial.getParDominioPK().getValor();      
        listaMonedasBase = iDominioService.obtenerItemsDominio("TMONEDA");
        listaMonedasCambio = iDominioService.obtenerDominioPorNombreDistintoValor("TMONEDA",monedaBaseForm);
    }
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();        
        String  REGISTRO_BITACORA=idUsuario.toString();    
        try {
            if(!iTipoCambioService.saveTipoCambio(fecha, tipoCambioForm,REGISTRO_BITACORA,monedaBaseForm,monedaCambioForm, accion)){
                context.execute("dlgMensajeInfo.show()");
            }else{
                context.execute("dlgTipoCambio.hide();");
            }
            
            mostrarDatos();
        } catch (Exception e) {
            context.execute("dlgMensajeInfo.show()");
            e.printStackTrace();
        }
        seleccion="vista";
        accion=false;
        monedaBase=monedaBaseForm;
        monedaCambio=monedaCambioForm;
    }
    
    public void eliminar(){  
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            boolean tmp = iTipoCambioService.deleteTipoCambio(tipoCambioForm);
            if(tmp){
               mostrarDatos(); 
            }else{
               context.execute("dlgMensaje.show()");
            }
        } catch (Exception e) {
            context.execute("dlgMensaje.show()");
            e.printStackTrace();
        }
    }

    /**
     * @return the iTipoCambioService
     */
    public ITipoCambioService getiTipoCambioService() {
        return iTipoCambioService;
    }

    /**
     * @param iTipoCambioService the iTipoCambioService to set
     */
    public void setiTipoCambioService(ITipoCambioService iTipoCambioService) {
        this.iTipoCambioService = iTipoCambioService;
    }

    /**
     * @return the listaTipoCambio
     */
    public List<ParTipoCambio> getListaTipoCambio() {
        return listaTipoCambio;
    }

    /**
     * @param listaTipoCambio the listaTipoCambio to set
     */
    public void setListaTipoCambio(List<ParTipoCambio> listaTipoCambio) {
        this.listaTipoCambio = listaTipoCambio;
    }

    /**
     * @return the monedaBase
     */
    public String getMonedaBase() {
        return monedaBase;
    }

    /**
     * @param monedaBase the monedaBase to set
     */
    public void setMonedaBase(String monedaBase) {
        this.monedaBase = monedaBase;
    }

    /**
     * @return the monedaCambio
     */
    public String getMonedaCambio() {
        return monedaCambio;
    }

    /**
     * @param monedaCambio the monedaCambio to set
     */
    public void setMonedaCambio(String monedaCambio) {
        this.monedaCambio = monedaCambio;
    }

    /**
     * @return the iParametrizacionService
     */
    public IParametrizacionService getiParametrizacionService() {
        return iParametrizacionService;
    }

    /**
     * @param iParametrizacionService the iParametrizacionService to set
     */
    public void setiParametrizacionService(IParametrizacionService iParametrizacionService) {
        this.iParametrizacionService = iParametrizacionService;
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
     * @return the monedaBaseInicial
     */
    public ParDominio getMonedaBaseInicial() {
        return monedaBaseInicial;
    }

    /**
     * @param monedaBaseInicial the monedaBaseInicial to set
     */
    public void setMonedaBaseInicial(ParDominio monedaBaseInicial) {
        this.monedaBaseInicial = monedaBaseInicial;
    }

    /**
     * @return the monedaCambioInicial
     */
    public ParDominio getMonedaCambioInicial() {
        return monedaCambioInicial;
    }

    /**
     * @param monedaCambioInicial the monedaCambioInicial to set
     */
    public void setMonedaCambioInicial(ParDominio monedaCambioInicial) {
        this.monedaCambioInicial = monedaCambioInicial;
    }

    /**
     * @return the listaMonedasBase
     */
    public List<ParDominio> getListaMonedasBase() {
        return listaMonedasBase;
    }

    /**
     * @param listaMonedasBase the listaMonedasBase to set
     */
    public void setListaMonedasBase(List<ParDominio> listaMonedasBase) {
        this.listaMonedasBase = listaMonedasBase;
    }

    /**
     * @return the listaMonedasCambio
     */
    public List<ParDominio> getListaMonedasCambio() {
        return listaMonedasCambio;
    }

    /**
     * @param listaMonedasCambio the listaMonedasCambio to set
     */
    public void setListaMonedasCambio(List<ParDominio> listaMonedasCambio) {
        this.listaMonedasCambio = listaMonedasCambio;
    }

    /**
     * @return the monedaBaseForm
     */
    public String getMonedaBaseForm() {
        return monedaBaseForm;
    }

    /**
     * @param monedaBaseForm the monedaBaseForm to set
     */
    public void setMonedaBaseForm(String monedaBaseForm) {
        this.monedaBaseForm = monedaBaseForm;
    }

    /**
     * @return the monedaCambioForm
     */
    public String getMonedaCambioForm() {
        return monedaCambioForm;
    }

    /**
     * @param monedaCambioForm the monedaCambioForm to set
     */
    public void setMonedaCambioForm(String monedaCambioForm) {
        this.monedaCambioForm = monedaCambioForm;
    }

    /**
     * @return the seleccion
     */
    public String getSeleccion() {
        return seleccion;
    }

    /**
     * @param seleccion the seleccion to set
     */
    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    /**
     * @return the tipoCambioForm
     */
    public ParTipoCambio getTipoCambioForm() {
        return tipoCambioForm;
    }

    /**
     * @param tipoCambioForm the tipoCambioForm to set
     */
    public void setTipoCambioForm(ParTipoCambio tipoCambioForm) {
        this.tipoCambioForm = tipoCambioForm;
    }

    /**
     * @return the accion
     */
    public boolean isAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(boolean accion) {
        this.accion = accion;
    }

    /**
     * @return the listaMonedasBaseForm
     */
    public List<ParDominio> getListaMonedasBaseForm() {
        return listaMonedasBaseForm;
    }

    /**
     * @param listaMonedasBaseForm the listaMonedasBaseForm to set
     */
    public void setListaMonedasBaseForm(List<ParDominio> listaMonedasBaseForm) {
        this.listaMonedasBaseForm = listaMonedasBaseForm;
    }

    /**
     * @return the listaMonedasCambioForm
     */
    public List<ParDominio> getListaMonedasCambioForm() {
        return listaMonedasCambioForm;
    }

    /**
     * @param listaMonedasCambioForm the listaMonedasCambioForm to set
     */
    public void setListaMonedasCambioForm(List<ParDominio> listaMonedasCambioForm) {
        this.listaMonedasCambioForm = listaMonedasCambioForm;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
