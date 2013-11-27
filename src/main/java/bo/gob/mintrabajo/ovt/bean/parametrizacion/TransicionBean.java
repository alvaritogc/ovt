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

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import bo.gob.mintrabajo.ovt.api.ITransicionService;
import bo.gob.mintrabajo.ovt.api.IDocumentoEstadoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicionPK;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class TransicionBean implements Serializable{
    @ManagedProperty(value = "#{transicionService}")
    private ITransicionService iTransicionService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{definicionService}")
    private IDefinicionService iDefinicionService;
    
    private HttpSession session;
    private UsrUsuario usuario;
    
    private List<DocTransicion> listaTransicion;
    
    //Para el formulario
    private DocTransicion docTransicion= new DocTransicion();
    private List<ParDocumentoEstado> listaEstadoInicial;
    private List<ParDocumentoEstado> listaEstadoFinal;
    private List<DocDefinicion> listaDefinicion;
    private List<DocDefinicion> listaVersion;
    
    private boolean evento=false;
    private boolean estadoTransicion=true;
    
    private String codigo;
    private short version;
    private String estadoInicial;
    private String estadoFinal;
    
    @PostConstruct
    public void ini() {
        docTransicion= new DocTransicion();
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Long idUsuario = (Long) session.getAttribute("idUsuario");
            usuario = iUsuarioService.findById(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaTransicion =new ArrayList<DocTransicion>();
        listaEstadoInicial= new ArrayList<ParDocumentoEstado>();
        listaEstadoFinal= new ArrayList<ParDocumentoEstado>();
        listaDefinicion= new ArrayList<DocDefinicion>();
        listaVersion= new ArrayList<DocDefinicion>();
        limpiar();
    }
    
    public void confirmaEliminar(){  
        try {
            if(iTransicionService.deleteTransicion(docTransicion)){
                listaTransicion= iTransicionService.listaTransicion();
                limpiar();
            }
        } catch (Exception e) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgMensaje.show()");
            e.printStackTrace();
        }
    } 
    
    public void mostrarVersion(){
        listaVersion=iDefinicionService.listaVersionesPorCodDocumento(codigo);
    }
    
    public void guardarModificar(){
        RequestContext context = RequestContext.getCurrentInstance();
        final String  REGISTRO_BITACORA=usuario.getUsuario();
        try {
            DocTransicionPK docTransicionPK=new DocTransicionPK();
            docTransicionPK.setCodDocumento(codigo);
            docTransicionPK.setCodEstadoFinal(estadoFinal);
            docTransicionPK.setCodEstadoInicial(estadoInicial);
            docTransicionPK.setVersion(version);
            
            if(iTransicionService.saveTransicion(docTransicion, estadoTransicion, iDefinicionService.obtenerDefinicion(codigo, version),
                    iDocumentoEstadoService.buscarPorId(estadoInicial), iDocumentoEstadoService.buscarPorId(estadoFinal),
                    REGISTRO_BITACORA,evento ,docTransicionPK)){
                context.execute("dlgFormTransicion.hide();");
                limpiar();
            }else{
                context.execute("dlgMensajeInfo.show()");
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            context.execute("dlgMensajeInfo.show()");
        }
    }
    
    public void limpiar(){
        listaTransicion= iTransicionService.listaTransicion();
        listaEstadoInicial=iDocumentoEstadoService.listarDocumentoEstados();
        listaEstadoFinal=iDocumentoEstadoService.listarDocumentoEstados();
        listaDefinicion=iDefinicionService.listarDefiniciones();
        listaVersion=iDefinicionService.listaVersionesPorCodDocumento(listaDefinicion.get(0).getDocDefinicionPK().getCodDocumento());
        nuevo();
    }
    
    public void nuevo(){
        docTransicion= new DocTransicion();
        evento=false;
        estadoTransicion=true;
        codigo="";
        version=0;
        estadoInicial="";
        estadoFinal="";
    }
    

    /**
     * @return the iTransicionService
     */
    public ITransicionService getiTransicionService() {
        return iTransicionService;
    }

    /**
     * @param iTransicionService the iTransicionService to set
     */
    public void setiTransicionService(ITransicionService iTransicionService) {
        this.iTransicionService = iTransicionService;
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

    /**
     * @return the listaTransicion
     */
    public List<DocTransicion> getListaTransicion() {
        return listaTransicion;
    }

    /**
     * @param listaTransicion the listaTransicion to set
     */
    public void setListaTransicion(List<DocTransicion> listaTransicion) {
        this.listaTransicion = listaTransicion;
    }

    /**
     * @return the docTransicion
     */
    public DocTransicion getDocTransicion() {
        return docTransicion;
    }

    /**
     * @param docTransicion the docTransicion to set
     */
    public void setDocTransicion(DocTransicion docTransicion) {
        this.docTransicion = docTransicion;
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
     * @return the estadoTransicion
     */
    public boolean isEstadoTransicion() {
        return estadoTransicion;
    }

    /**
     * @param estadoTransicion the estadoTransicion to set
     */
    public void setEstadoTransicion(boolean estadoTransicion) {
        this.estadoTransicion = estadoTransicion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the version
     */
    public short getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(short version) {
        this.version = version;
    }

    /**
     * @return the estadoInicial
     */
    public String getEstadoInicial() {
        return estadoInicial;
    }

    /**
     * @param estadoInicial the estadoInicial to set
     */
    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    /**
     * @return the EstadoFinal
     */
    public String getEstadoFinal() {
        return estadoFinal;
    }

    /**
     * @param estadoFinal the EstadoFinal to set
     */
    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    /**
     * @return the listaEstadoInicial
     */
    public List<ParDocumentoEstado> getListaEstadoInicial() {
        return listaEstadoInicial;
    }

    /**
     * @param listaEstadoInicial the listaEstadoInicial to set
     */
    public void setListaEstadoInicial(List<ParDocumentoEstado> listaEstadoInicial) {
        this.listaEstadoInicial = listaEstadoInicial;
    }

    /**
     * @return the listaEstadoFinal
     */
    public List<ParDocumentoEstado> getListaEstadoFinal() {
        return listaEstadoFinal;
    }

    /**
     * @param listaEstadoFinal the listaEstadoFinal to set
     */
    public void setListaEstadoFinal(List<ParDocumentoEstado> listaEstadoFinal) {
        this.listaEstadoFinal = listaEstadoFinal;
    }

    /**
     * @return the iDocumentoEstadoService
     */
    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    /**
     * @param iDocumentoEstadoService the iDocumentoEstadoService to set
     */
    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    /**
     * @return the iDefinicionService
     */
    public IDefinicionService getiDefinicionService() {
        return iDefinicionService;
    }

    /**
     * @param iDefinicionService the iDefinicionService to set
     */
    public void setiDefinicionService(IDefinicionService iDefinicionService) {
        this.iDefinicionService = iDefinicionService;
    }

    /**
     * @return the listaDefinicion
     */
    public List<DocDefinicion> getListaDefinicion() {
        return listaDefinicion;
    }

    /**
     * @param listaDefinicion the listaDefinicion to set
     */
    public void setListaDefinicion(List<DocDefinicion> listaDefinicion) {
        this.listaDefinicion = listaDefinicion;
    }

    /**
     * @return the listaVersion
     */
    public List<DocDefinicion> getListaVersion() {
        return listaVersion;
    }

    /**
     * @param listaVersion the listaVersion to set
     */
    public void setListaVersion(List<DocDefinicion> listaVersion) {
        this.listaVersion = listaVersion;
    }
    
}
