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

import bo.gob.mintrabajo.ovt.api.IObligacionService;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
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
public class obligacionBean implements Serializable{
    @ManagedProperty(value = "#{obligacionService}")
    private IObligacionService iObligacionService;
    
    private HttpSession session;
    private Long idUsuario;
    
    private List<ParObligacion> listaObligacion;
    
    //Para el formulario
    private ParObligacion obligacion= new ParObligacion();
    private boolean evento=false;
    private boolean estadoObligacion=false;
     
    @PostConstruct
    public void ini() {
        idUsuario = null;
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaObligacion =new ArrayList<ParObligacion>();
        //listaObligacion= iObligacionService.listaObligacion();
        listaObligacion= iObligacionService.listaObligacionPorOrden();
        limpiar();
    }
    
    public void confirmaEliminar(){  
        try {
            if(iObligacionService.deleteObligacion(obligacion)){
                //listaObligacion= iObligacionService.listaObligacion();
                listaObligacion= iObligacionService.listaObligacionPorOrden();
                limpiar();
            }
        } catch (Exception e) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgMensaje.show()");
            e.printStackTrace();
        }
    } 
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();
        final String  REGISTRO_BITACORA=idUsuario.toString();
        try {
            iObligacionService.saveObligacion(obligacion, REGISTRO_BITACORA, estadoObligacion);
           limpiar();
           context.execute("dlgFormObligacion.hide();");
        } catch (Exception e) {
            e.printStackTrace();                    
        }
    }
    
    public void limpiar(){
        listaObligacion= iObligacionService.listaObligacionPorOrden();
        nuevo();
    }
    
    public void nuevo(){
        obligacion=new ParObligacion();
        evento=false;
        estadoObligacion=false;
    }
    
    //GET and SET

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

    /**
     * @return the listaObligacion
     */
    public List<ParObligacion> getListaObligacion() {
        return listaObligacion;
    }

    /**
     * @param listaObligacion the listaObligacion to set
     */
    public void setListaObligacion(List<ParObligacion> listaObligacion) {
        this.listaObligacion = listaObligacion;
    }

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
     * @return the estado
     */
    public boolean isEstadoObligacion() {
        return estadoObligacion;
    }

    /**
     * @param estadoObligacion the estado to set
     */
    public void setEstadoObligacion(boolean estadoObligacion) {
        this.estadoObligacion = estadoObligacion;
    }
    
}