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

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.ICalendarioService;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CalendarioBean implements Serializable{
    @ManagedProperty(value = "#{calendarioService}")
    private ICalendarioService iCalendarioService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService; 
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    
    private List<ParDominio> listaDominio;
    private List<ParCalendario> listaCalendario;
    
    private List<Integer> listaAnio;
    private Integer anioActual;
    private UsrUsuario usuario;
    private HttpSession session;
    private String REGISTRO_BITACORA;
    
    //para el form
    private Integer gestion;
    
    
    @PostConstruct
    public void ini() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            REGISTRO_BITACORA = (String) session.getAttribute("bitacoraSession");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaDominio=new ArrayList<ParDominio>();
        listaCalendario=new ArrayList<ParCalendario>();
        listaCalendario=iCalendarioService.listaCalendario();
        Calendar calendar = Calendar.getInstance();
        anioActual=calendar.get(Calendar.YEAR);
        int year = anioActual - 4;
        listaAnio=new ArrayList<Integer>();
        for (int i = 0; i < 21; i++) {
            listaAnio.add(year);
            year++;
        }
    }
    
     public void guardar(){
        RequestContext context = RequestContext.getCurrentInstance();
        //String  REGISTRO_BITACORA=usuario.getUsuario();
        listaDominio = iDominioService.obtenerItemsDominio("TPERIODO");
        if(iCalendarioService.listaCalendarioPorGestion(gestion).size() <= 0){
            for (ParDominio parDominio : listaDominio) {
                try {
                    iCalendarioService.saveCalendario(gestion, parDominio.getParDominioPK().getValor(), parDominio.getParDominio().getParDominioPK().getValor(), REGISTRO_BITACORA);
                    context.execute("dlgCalendario.hide();");
                    listaCalendario=iCalendarioService.listaCalendario();
                } catch (Exception e) {
                    e.printStackTrace();
                    context.execute("dlgMensajeInfo.show()");
                }
                
            }
            try {
                iCalendarioService.saveCalendario(gestion, "ANUAL", "ANUAL", REGISTRO_BITACORA);
            } catch (Exception e) {
                e.printStackTrace();
                    context.execute("dlgMensajeInfo.show()");
            }
        }else{
            context.execute("dlgMensajeInfo.show()");
        }
    }
     
     public String descripcionPeriodo(String valor){
         if(valor.equals("ANUAL")){
             return Util.descripcionDominio("TCALENDARIO", valor);
         }else{
             return Util.descripcionDominio("TPERIODO", valor);
         }
    }
     
    public String descripcionCalendario(String valor){
        return Util.descripcionDominio("TCALENDARIO", valor);
    } 

    /**
     * @return the iCalendarioService
     */
    public ICalendarioService getiCalendarioService() {
        return iCalendarioService;
    }

    /**
     * @param iCalendarioService the iCalendarioService to set
     */
    public void setiCalendarioService(ICalendarioService iCalendarioService) {
        this.iCalendarioService = iCalendarioService;
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
     * @return the gestion
     */
    public Integer getGestion() {
        return gestion;
    }

    /**
     * @param gestion the gestion to set
     */
    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

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
     * @return the iUsuarioService
     */
    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    /**
     * @param iUsuarioService the iUsuarioService to set
     */
    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }
    
}
