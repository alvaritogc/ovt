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
import bo.gob.mintrabajo.ovt.api.IModuloService;
import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.api.IVRecursosService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import bo.gob.mintrabajo.ovt.entities.VparRecurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class RecursosBean implements Serializable{
    @ManagedProperty(value = "#{moduloService}")
    private IModuloService iModuloService;
    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursosService;
    @ManagedProperty(value = "#{vRecursosService}")
    private IVRecursosService iVRecursosService;
    
    private HttpSession session;
    private String REGISTRO_BITACORA;
    
    private List<UsrModulo> listaModulos;
    private List<VparRecurso> listaRecursos;
    private List<ParDominio> listaDominioRecurso;
    private List<ParDominio> listaDominio;
    private UsrRecurso usrRecurso;
    private UsrRecurso usrRecursoPadre;
    private VparRecurso vparRecurso;
    private UsrModulo usrModulo;
    private String usrModuloId;
    private boolean estadoRecurso;
    
    private TreeNode root;
    private TreeNode nodoSeleccionado;
    
    //para los botones
    private boolean estadoBoton;
    private String nivelNodo;
    private String tipoNodo;
    
    private Long idHijo;
    private Long idPadre;
    private String registro;
    
    @PostConstruct
    public void ini() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            REGISTRO_BITACORA = (String) session.getAttribute("bitacoraSession");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tipoNodo = "Padre";
        estadoBoton = true;
        listaModulos=new ArrayList<UsrModulo>();
        listaDominioRecurso=new ArrayList<ParDominio>();
        listaRecursos = new ArrayList<VparRecurso>();
        nuevo();
        cargarNodo();
    }
    
    public void cargarNodo() {
        nuevo();
        listaRecursos = iVRecursosService.listarVRecursos();
        usrRecursoPadre = new UsrRecurso();
        usrRecurso = new UsrRecurso();
        root = new DefaultTreeNode("Root", null);
        for (VparRecurso vp : listaRecursos) {
            String nivel = vp.getNivel().toString();
            //esto funciona *****************************************************
            if (nivel.equals("1")) {
                vparRecurso = vp;
                TreeNode nodePadre = new DefaultTreeNode(vparRecurso.getEtiqueta(), root);
                llenarHijo(iVRecursosService.listarVRecursosHijo(vparRecurso.getIdRecurso()),
                        "2", nodePadre);
            }
        }
    }
    
    public void llenarHijo(List<VparRecurso> listaHijo, String nivel, TreeNode nodeHijo) {
        for (VparRecurso vh : listaHijo) {
            if (vh.getNivel().toString().equals(nivel)) {
                TreeNode nodeHijo1 = new DefaultTreeNode(vh.getEtiqueta(), nodeHijo);
                int num = Integer.parseInt(nivel) + 1;
                llenarHijo(iVRecursosService.listarVRecursosHijo(vh.getIdRecurso()),
                        num + "", nodeHijo1);
            }
        }
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado", event.getTreeNode().toString()));
        String nivel = event.getTreeNode().getRowKey();
        estadoBoton = true;
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(nivel, "_");

        while (tokens.hasMoreTokens()) {
            i++;
            System.out.println(tokens.nextToken());
        }
        usrRecurso = new UsrRecurso();
        nivelNodo = i + "";
        vparRecurso = iVRecursosService.ObtienePorEtiquetaYNivel(event.getTreeNode().toString(), i + "");
        UsrRecurso nuevoRecurso = new UsrRecurso();
        if (vparRecurso.getNivel().toString().equals("1")) {
            tipoNodo = "Padre";
        } else {
            tipoNodo = "Hijo";
        }

        System.out.println("===>>> recurdo id " + vparRecurso.getIdRecurso());
        idHijo = vparRecurso.getIdRecurso();
        System.out.println("===>>> recurdo id " + idHijo);
        nuevoRecurso.setIdRecurso(idHijo);
        nuevoRecurso.setIdModulo(iModuloService.findById(vparRecurso.getIdModulo()));
        usrModuloId=vparRecurso.getIdModulo();
        nuevoRecurso.setTipoRecurso(vparRecurso.getTipoRecurso());
        nuevoRecurso.setTipoPlataforma(vparRecurso.getTipoPlataforma());
        nuevoRecurso.setOrden(vparRecurso.getOrden());
        nuevoRecurso.setEtiqueta(vparRecurso.getEtiqueta());
        System.out.println("===>>> etiqurta  " + vparRecurso.getEtiqueta());
        nuevoRecurso.setDescripcion(vparRecurso.getDescripcion());
        nuevoRecurso.setEjecutable(vparRecurso.getEjecutable());
        nuevoRecurso.setEsVerificable(vparRecurso.getEsVerificable());
        try {
            if(vparRecurso.getIdRecursoPadre()!= null){
                idPadre=vparRecurso.getIdRecursoPadre();
            }else{
                idPadre=new Long(-1);
            }
            if (idPadre >= 0) {
                usrRecursoPadre = iRecursosService.findById(idPadre);
                nuevoRecurso.setIdRecursoPadre(usrRecursoPadre);
            } else {
                System.out.println("Sin Idrecursopadre");
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "No se cargarón los datos corectamente, por favor vuelva a seleccionar el elemento"));
            e.printStackTrace();
        }
        
        if (vparRecurso.getEstado().equals("A")) {
            estadoRecurso = true;
        } else {
            estadoRecurso = false;
        }
        nuevoRecurso.setFechaBitacora(vparRecurso.getFechaBitacora());
        nuevoRecurso.setRegistroBitacora(vparRecurso.getRegistroBitacora());

        usrRecurso = nuevoRecurso;
    }
    
    public void agregaNodoPadre() {
        estadoBoton = false;
        registro = "Agregar";
        tipoNodo = "Padre";
        idHijo = new Long(-1);
        nivelNodo="1";
        nuevo();
    }
    
    public void agregaNodoHijo() {
        FacesContext context = FacesContext.getCurrentInstance();
        registro = "Agregar";
        UsrRecurso rec= usrRecurso;
        idPadre = usrRecurso.getIdRecurso();
        try {
            if (idPadre != null) {
                tipoNodo = "Hijo";
                idHijo = new Long(-1);
                estadoBoton = false;
                nuevo();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Selecciono nodo " + rec.getEtiqueta()));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            e.printStackTrace();
        }
    }
    
    public void editar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (usrRecurso.getIdRecurso()!= null) {
                estadoBoton = false;
                registro = "Editar";
                System.out.println("===> registro " + registro);
                //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado para edicion: ", usrRecurso.getEtiqueta()));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            e.printStackTrace();
        }
    }
    
    public void guardarNodo() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            VparRecurso aux = new VparRecurso();
            String niv; 
            if (tipoNodo.equals("Hijo")) {
                int n = Integer.parseInt(nivelNodo) + 1;
                niv = n + "";
            }else{
                niv=nivelNodo;
            }
            if (registro.equals("Editar")){
                niv = nivelNodo;
            }
            aux = iVRecursosService.ObtienePorEtiquetaYNivel(usrRecurso.getEtiqueta(), niv);
            System.out.println("====>> aux.getNivel() " + aux.getNivel());
            if (registro.equals("Editar")) {
                System.out.println("===>> " + aux.getIdRecurso() +" "+  usrRecurso.getIdRecurso());
                if(aux.getEtiqueta() == null){
                    guarda();
                }else{
                    if (aux.getIdRecurso() == usrRecurso.getIdRecurso()) {
                        System.out.println("iguales");
                        guarda();
                    }else {
                        System.out.println("error de diferencia");
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Nodo con esas caracteristicas ya existe en este nivel"));
                    }
                }
                
            } else {
                System.out.println("distintos");
                if (aux.getEtiqueta() == null) {
                    System.out.println("nuevo");
                    System.out.println("tipo nodo " + tipoNodo);
                    if (tipoNodo.equals("Padre")) {
                        nivelNodo = 1 + "";
                    }
                    if (nivelNodo != null && tipoNodo.equals("Hijo")) {
                        int n = Integer.parseInt(nivelNodo) + 1;
                        nivelNodo = n + "";
                    }
                    guarda();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Nodo con esas caracteristicas ya existe en este nivel"));
                }
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Error No se pudo guardar el nodo"));
            e.printStackTrace();
        }
    }
    
     public void guarda() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean guardado = false;
        guardado = iRecursosService.guardarRecurso(usrRecurso,usrModuloId, estadoRecurso, REGISTRO_BITACORA, tipoNodo, idPadre, idHijo);
        if (guardado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Nodo guardado con exito"));
            estadoBoton = true;
            cargarNodo();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No se pudo guardar el nodo"));
        }
    }
    
    public void canselar() {
        tipoNodo = "Padre";
        estadoBoton = true;
        nuevo();
    }
    
    public void preparaEliminar() {
        RequestContext context1 = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (usrRecurso.getIdRecurso() != null) {
                context1.execute("confirmation.show()");
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            e.printStackTrace();
        }
    }
    
    public void confirmaEliminar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (usrRecurso.getIdRecurso() != null) {
                estadoBoton = true;
                if (iRecursosService.delete(usrRecurso.getIdRecurso())) {
                    cargarNodo();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Error al eliminar nodo"));
                }
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Error al eliminar el nodo, puede que este en uso o tenga una dependencia"));
            e.printStackTrace();
        }
    }
    
    public void nuevo(){
        estadoRecurso = true;
        usrRecurso = new UsrRecurso();
        usrModuloId="";
        registro = "Agregar";
        listaDominioRecurso=iDominioService.obtenerItemsDominio("TRECURSO");
        listaModulos=iModuloService.getAllModulos();
    }

            
    /**
     * @return the iModuloService
     */
    public IModuloService getiModuloService() {
        return iModuloService;
    }

    /**
     * @param iModuloService the iModuloService to set
     */
    public void setiModuloService(IModuloService iModuloService) {
        this.iModuloService = iModuloService;
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
     * @return the listaModulos
     */
    public List<UsrModulo> getListaModulos() {
        return listaModulos;
    }

    /**
     * @param listaModulos the listaModulos to set
     */
    public void setListaModulos(List<UsrModulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    /**
     * @return the listaRecursos
     */
    public List<VparRecurso> getListaRecursos() {
        return listaRecursos;
    }

    /**
     * @param listaRecursos the listaRecursos to set
     */
    public void setListaRecursos(List<VparRecurso> listaRecursos) {
        this.listaRecursos = listaRecursos;
    }

    /**
     * @return the iVRecursosService
     */
    public IVRecursosService getiVRecursosService() {
        return iVRecursosService;
    }

    /**
     * @param iVRecursosService the iVRecursosService to set
     */
    public void setiVRecursosService(IVRecursosService iVRecursosService) {
        this.iVRecursosService = iVRecursosService;
    }

    /**
     * @return the usrRecurso
     */
    public UsrRecurso getUsrRecurso() {
        return usrRecurso;
    }

    /**
     * @param usrRecurso the usrRecurso to set
     */
    public void setUsrRecurso(UsrRecurso usrRecurso) {
        this.usrRecurso = usrRecurso;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the nodoSeleccionado
     */
    public TreeNode getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    /**
     * @param nodoSeleccionado the nodoSeleccionado to set
     */
    public void setNodoSeleccionado(TreeNode nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    /**
     * @return the vparRecurso
     */
    public VparRecurso getVparRecurso() {
        return vparRecurso;
    }

    /**
     * @param vparRecurso the vparRecurso to set
     */
    public void setVparRecurso(VparRecurso vparRecurso) {
        this.vparRecurso = vparRecurso;
    }

    /**
     * @return the estadoBoton
     */
    public boolean isEstadoBoton() {
        return estadoBoton;
    }

    /**
     * @param estadoBoton the estadoBoton to set
     */
    public void setEstadoBoton(boolean estadoBoton) {
        this.estadoBoton = estadoBoton;
    }

    /**
     * @return the nivelNodo
     */
    public String getNivelNodo() {
        return nivelNodo;
    }

    /**
     * @param nivelNodo the nivelNodo to set
     */
    public void setNivelNodo(String nivelNodo) {
        this.nivelNodo = nivelNodo;
    }

    /**
     * @return the tipoNodo
     */
    public String getTipoNodo() {
        return tipoNodo;
    }

    /**
     * @param tipoNodo the tipoNodo to set
     */
    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    /**
     * @return the idHijo
     */
    public Long getIdHijo() {
        return idHijo;
    }

    /**
     * @param idHijo the idHijo to set
     */
    public void setIdHijo(Long idHijo) {
        this.idHijo = idHijo;
    }

    /**
     * @return the idPadre
     */
    public Long getIdPadre() {
        return idPadre;
    }

    /**
     * @param idPadre the idPadre to set
     */
    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    /**
     * @return the estadoRecurso
     */
    public boolean isEstadoRecurso() {
        return estadoRecurso;
    }

    /**
     * @param estadoRecurso the estadoRecurso to set
     */
    public void setEstadoRecurso(boolean estadoRecurso) {
        this.estadoRecurso = estadoRecurso;
    }

    /**
     * @return the usrRecursoPadre
     */
    public UsrRecurso getUsrRecursoPadre() {
        return usrRecursoPadre;
    }

    /**
     * @param usrRecursoPadre the usrRecursoPadre to set
     */
    public void setUsrRecursoPadre(UsrRecurso usrRecursoPadre) {
        this.usrRecursoPadre = usrRecursoPadre;
    }

    /**
     * @return the iRecursosService
     */
    public IRecursoService getiRecursosService() {
        return iRecursosService;
    }

    /**
     * @param iRecursosService the iRecursosService to set
     */
    public void setiRecursosService(IRecursoService iRecursosService) {
        this.iRecursosService = iRecursosService;
    }

    /**
     * @return the usrModulo
     */
    public UsrModulo getUsrModulo() {
        return usrModulo;
    }

    /**
     * @param usrModulo the usrModulo to set
     */
    public void setUsrModulo(UsrModulo usrModulo) {
        this.usrModulo = usrModulo;
    }

    /**
     * @return the usrModuloId
     */
    public String getUsrModuloId() {
        return usrModuloId;
    }

    /**
     * @param usrModuloId the usrModuloId to set
     */
    public void setUsrModuloId(String usrModuloId) {
        this.usrModuloId = usrModuloId;
    }

    /**
     * @return the listaDominioRecurso
     */
    public List<ParDominio> getListaDominioRecurso() {
        return listaDominioRecurso;
    }

    /**
     * @param listaDominioRecurso the listaDominioRecurso to set
     */
    public void setListaDominioRecurso(List<ParDominio> listaDominioRecurso) {
        this.listaDominioRecurso = listaDominioRecurso;
    }

    /**
     * @return the registro
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }
}
