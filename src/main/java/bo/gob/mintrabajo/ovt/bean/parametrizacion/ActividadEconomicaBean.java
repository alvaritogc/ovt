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

import bo.gob.mintrabajo.ovt.api.IActividadEconomicaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.api.IVActividadEconomicaService;
import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import bo.gob.mintrabajo.ovt.entities.VparActividadEconomica;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.util.StringTokenizer;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class ActividadEconomicaBean implements Serializable {

    @ManagedProperty(value = "#{actividadEconomicaService}")
    private IActividadEconomicaService iActividadEconomicaService;
    @ManagedProperty(value = "#{vActividadEconomicaService}")
    private IVActividadEconomicaService ivActividadEconomicaService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    private HttpSession session;

    private TreeNode root;
    private TreeNode nodoSeleccionado;

    private ParActividadEconomica parActividadEconomica;
    private VparActividadEconomica vparActividadEconomica;

    //para el formulario edicion y agregar
    private List<VparActividadEconomica> listaActividadEconomica;
    private ParActividadEconomica actividadEconomicaPadre;
    private Long idHijo;
    private Long idPadre;
    private ParActividadEconomica actividadEconomica;
    private boolean estadoActividadEconomica;

    private String nivelNodo;

    //para los botones
    private boolean estadoBoton;
    private String tipoNodo;
    private String REGISTRO_BITACORA;
    private String registro;

    @PostConstruct
    public void ini() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            REGISTRO_BITACORA = (String) session.getAttribute("bitacoraSession");
        } catch (Exception e) {
            e.printStackTrace();
        }
        estadoBoton = true;
        tipoNodo = "Padre";
        registro = "Agregar";
        estadoActividadEconomica = true;
        listaActividadEconomica = new ArrayList<VparActividadEconomica>();
        cargarNodo();
    }

    public void cargarNodo() {
        listaActividadEconomica = ivActividadEconomicaService.listarVActividadEconomica();
        actividadEconomicaPadre = new ParActividadEconomica();
        parActividadEconomica = new ParActividadEconomica();
        root = new DefaultTreeNode("Root", null);
        for (VparActividadEconomica vp : listaActividadEconomica) {
            String nivel = vp.getNivel().toString();
            //esto funciona *****************************************************
            if (nivel.equals("1")) {
                vparActividadEconomica = vp;
                TreeNode nodePadre = new DefaultTreeNode(vparActividadEconomica.getDescripcion(), root);
                llenarHijo(ivActividadEconomicaService.listarVActividadEconomicaHijo(vparActividadEconomica.getIdActividadEconomica()),
                        "2", nodePadre);
            }
        }
    }

    public void llenarHijo(List<VparActividadEconomica> listaHijo, String nivel, TreeNode nodeHijo) {
        for (VparActividadEconomica vh : listaHijo) {
            if (vh.getNivel().toString().equals(nivel)) {
                TreeNode nodeHijo1 = new DefaultTreeNode(vh.getDescripcion(), nodeHijo);
                int num = Integer.parseInt(nivel) + 1;
                llenarHijo(ivActividadEconomicaService.listarVActividadEconomicaHijo(vh.getIdActividadEconomica()),
                        num + "", nodeHijo1);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado", event.getTreeNode().toString()));
        String nivel = event.getTreeNode().getRowKey();
        estadoBoton = true;
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(nivel, "_");

        while (tokens.hasMoreTokens()) {
            i++;
            System.out.println(tokens.nextToken());
        }
        actividadEconomica = new ParActividadEconomica();
        nivelNodo = i + "";
        vparActividadEconomica = ivActividadEconomicaService.ObtienePorDescripcionYNivel(event.getTreeNode().toString(), i + "");
        ParActividadEconomica nuevoAE = new ParActividadEconomica();
        if (vparActividadEconomica.getNivel().toString().equals("1")) {
            tipoNodo = "Padre";
        } else {
            tipoNodo = "Hijo";
        }

        idHijo = vparActividadEconomica.getIdActividadEconomica();
        System.out.println("== id " + idHijo);
        nuevoAE.setIdActividadEconomica(idHijo);
        nuevoAE.setCodActividadEconomica(vparActividadEconomica.getCodActividadEconomica());
        nuevoAE.setDescripcion(vparActividadEconomica.getDescripcion());
        nuevoAE.setCodImpuestos(vparActividadEconomica.getCodImpuestos());
        nuevoAE.setDescricpionImpuestos(vparActividadEconomica.getDescricpionImpuestos());
        try {
            if (vparActividadEconomica.getIdActividadEconomica2() != null) {
                idPadre = vparActividadEconomica.getIdActividadEconomica2();
                actividadEconomicaPadre = iActividadEconomicaService.obtieneActividadEconomicaPorId(idPadre);
                nuevoAE.setIdActividadEconomica2(actividadEconomicaPadre);
            } else {
                System.out.println("Sin IdActividadEconomica2");
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "No se cargarón los datos corectamente, por favor vuelva a seleccionar el elemento"));
            e.printStackTrace();
        }
        nuevoAE.setEstado(vparActividadEconomica.getEstado());
        if (nuevoAE.getEstado().equals("A")) {
            estadoActividadEconomica = true;
        } else {
            estadoActividadEconomica = false;
        }
        nuevoAE.setFechaBitacora(vparActividadEconomica.getFechaBitacora());
        nuevoAE.setRegistroBitacora(vparActividadEconomica.getRegistroBitacora());

        parActividadEconomica = nuevoAE;
    }

    public void agregaNodoPadre() {
        estadoBoton = false;
        registro = "Agregar";
        System.out.println("botones " + estadoBoton);
        tipoNodo = "Padre";
        idHijo = new Long(-1);
        nuevo();
    }

    public void agregaNodoHijo() {
        FacesContext context = FacesContext.getCurrentInstance();
        registro = "Agregar";
        ParActividadEconomica des = parActividadEconomica;
        idPadre = parActividadEconomica.getIdActividadEconomica();
        try {
            if (idPadre != null) {
                tipoNodo = "Hijo";
                idHijo = new Long(-1);
                estadoBoton = false;
                nuevo();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Selecciono nodo " + des.getDescripcion()));
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
            if (parActividadEconomica.getIdActividadEconomica() != null) {
                estadoBoton = false;
                registro = "Editar";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado", parActividadEconomica.getDescripcion()));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            e.printStackTrace();
        }
    }

    public void preparaEliminar() {
        RequestContext context1 = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (parActividadEconomica.getIdActividadEconomica() != null) {
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
            if (parActividadEconomica.getIdActividadEconomica() != null) {
                estadoBoton = true;
                if (iActividadEconomicaService.delete(parActividadEconomica.getIdActividadEconomica())) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Error al eliminar nodo"));
                } else {
                    cargarNodo();
                }
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Error al eliminar el nodo, puede que este en uso o tenga una dependencia"));
            e.printStackTrace();
        }
    }

    public void guardarNodo() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            VparActividadEconomica aux = new VparActividadEconomica();
            if (tipoNodo.equals("Padre")) {
                nivelNodo = 1 + "";
            }
            if (nivelNodo != null && tipoNodo.equals("Hijo")) {
                int n = Integer.parseInt(nivelNodo) + 1;
                nivelNodo = n + "";
            }
            aux = ivActividadEconomicaService.ObtienePorDescripcionYNivel(parActividadEconomica.getDescripcion(), nivelNodo);
            System.out.println("aa=== " + aux.getIdActividadEconomica());
            System.out.println("ab=== " + parActividadEconomica.getIdActividadEconomica());
            if (registro.equals("Editar")) {
                if (aux.getIdActividadEconomica() == parActividadEconomica.getIdActividadEconomica()) {
                    System.out.println("iguales");
                    guarda();
                }else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Nodo con esas caracteristicas ya existe en este nivel"));
                }
            } else {
                System.out.println("distintos");
                if (aux.getDescripcion() == null) {
                    System.out.println("nuevo");
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
        guardado = iActividadEconomicaService.guardarActividadEconomica(parActividadEconomica,
                estadoActividadEconomica, REGISTRO_BITACORA, tipoNodo, idPadre, idHijo);
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

    public void nuevo() {
        estadoActividadEconomica = true;
        parActividadEconomica = new ParActividadEconomica();
    }

    /**
     * @return the iActividadEconomicaService
     */
    public IActividadEconomicaService getiActividadEconomicaService() {
        return iActividadEconomicaService;
    }

    /**
     * @param iActividadEconomicaService the iActividadEconomicaService to set
     */
    public void setiActividadEconomicaService(IActividadEconomicaService iActividadEconomicaService) {
        this.iActividadEconomicaService = iActividadEconomicaService;
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
     * @return the listaActividadEconomica
     */
    public List<VparActividadEconomica> getListaActividadEconomica() {
        return listaActividadEconomica;
    }

    /**
     * @param listaActividadEconomica the listaActividadEconomica to set
     */
    public void setListaActividadEconomica(List<VparActividadEconomica> listaActividadEconomica) {
        this.listaActividadEconomica = listaActividadEconomica;
    }

    /**
     * @return the actividadEconomica
     */
    public ParActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * @param actividadEconomica the actividadEconomica to set
     */
    public void setActividadEconomica(ParActividadEconomica actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
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
     * @return the ivActividadEconomicaService
     */
    public IVActividadEconomicaService getIvActividadEconomicaService() {
        return ivActividadEconomicaService;
    }

    /**
     * @param ivActividadEconomicaService the ivActividadEconomicaService to set
     */
    public void setIvActividadEconomicaService(IVActividadEconomicaService ivActividadEconomicaService) {
        this.ivActividadEconomicaService = ivActividadEconomicaService;
    }

    private Integer Integer(BigInteger nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private BigInteger BigInteger(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the parActividadEconomica
     */
    public ParActividadEconomica getParActividadEconomica() {
        return parActividadEconomica;
    }

    /**
     * @param parActividadEconomica the parActividadEconomica to set
     */
    public void setParActividadEconomica(ParActividadEconomica parActividadEconomica) {
        this.parActividadEconomica = parActividadEconomica;
    }

    /**
     * @return the vparActividadEconomica
     */
    public VparActividadEconomica getVparActividadEconomica() {
        return vparActividadEconomica;
    }

    /**
     * @param vparActividadEconomica the vparActividadEconomica to set
     */
    public void setVparActividadEconomica(VparActividadEconomica vparActividadEconomica) {
        this.vparActividadEconomica = vparActividadEconomica;
    }

    /**
     * @return the actividadEconomicaPadre
     */
    public ParActividadEconomica getActividadEconomicaPadre() {
        return actividadEconomicaPadre;
    }

    /**
     * @param actividadEconomicaPadre the actividadEconomicaPadre to set
     */
    public void setActividadEconomicaPadre(ParActividadEconomica actividadEconomicaPadre) {
        this.actividadEconomicaPadre = actividadEconomicaPadre;
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
     * @return the estadoActividadEconomica
     */
    public boolean isEstadoActividadEconomica() {
        return estadoActividadEconomica;
    }

    /**
     * @param estadoActividadEconomica the estadoActividadEconomica to set
     */
    public void setEstadoActividadEconomica(boolean estadoActividadEconomica) {
        this.estadoActividadEconomica = estadoActividadEconomica;
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

}
