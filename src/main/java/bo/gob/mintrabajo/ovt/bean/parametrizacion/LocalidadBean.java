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
import bo.gob.mintrabajo.ovt.api.ILocalidadService;
import bo.gob.mintrabajo.ovt.api.IVParLocalidadService;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParLocalidad;
import bo.gob.mintrabajo.ovt.entities.VparLocalidad;
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
public class LocalidadBean implements Serializable {
//    @ManagedProperty(value = "#{moduloService}")
//    private IModuloService iModuloService;

    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;
    @ManagedProperty(value = "#{vParLocalidadService}")
    private IVParLocalidadService iVParLocalidadService;

    private HttpSession session;
    private String REGISTRO_BITACORA;

    //private List<UsrModulo> listaModulos;
    private List<VparLocalidad> listaLocalidades;
    private List<ParDominio> listaDominioRecurso;
    private List<ParDominio> listaDominio;
    private ParDominio dominio;
    private ParLocalidad parLocalidad;
    private ParLocalidad parLocalidadPadre;
    private VparLocalidad vparLocalidad;
    //private UsrModulo usrModulo;
    private String tipoLocalidad;
    private boolean estadoRecurso;

    private TreeNode root;
    private TreeNode nodoSeleccionado;

    //para los botones
    private boolean estadoBoton;
    private boolean estadoCod;
    private String nivelNodo;
    private String tipoNodo;

    private String idHijo;
    private String idPadre;
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
        estadoCod = false;
        listaDominioRecurso = new ArrayList<ParDominio>();
        listaLocalidades = new ArrayList<VparLocalidad>();
//        nuevo();
        cargarNodo();
    }

    public void cargarNodo() {
        //nuevo();
        listaLocalidades = iVParLocalidadService.listarVLocalidades();
        parLocalidadPadre = new ParLocalidad();
        parLocalidad = new ParLocalidad();
        root = new DefaultTreeNode("Root", null);
        for (VparLocalidad vp : listaLocalidades) {
            String nivel = vp.getNivel().toString();
            //esto funciona *****************************************************
            if (nivel.equals("1")) {
                vparLocalidad = vp;
                TreeNode nodePadre = new DefaultTreeNode(vparLocalidad.getDescripcion(), root);
                llenarHijo(iVParLocalidadService.listarVLocalidadesHijo(vparLocalidad.getCodLocalidad()),
                        "2", nodePadre);
            }
        }
    }

    public void llenarHijo(List<VparLocalidad> listaHijo, String nivel, TreeNode nodeHijo) {
        for (VparLocalidad vh : listaHijo) {
            if (vh.getNivel().toString().equals(nivel)) {
                TreeNode nodeHijo1 = new DefaultTreeNode(vh.getDescripcion(), nodeHijo);
                int num = Integer.parseInt(nivel) + 1;
                llenarHijo(iVParLocalidadService.listarVLocalidadesHijo(vh.getCodLocalidad()),
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
        parLocalidad = new ParLocalidad();
        nivelNodo = i + "";
        vparLocalidad = iVParLocalidadService.ObtienePorDescripcionYNivel(event.getTreeNode().toString(), i + "");
        ParLocalidad nuevaLocalidad = new ParLocalidad();
        if (vparLocalidad.getNivel().toString().equals("1")) {
            tipoNodo = "Padre";
        } else {
            tipoNodo = "Hijo";
        }
        idHijo = vparLocalidad.getCodLocalidad();
        nuevaLocalidad.setCodLocalidad(idHijo);
        nuevaLocalidad.setDescripcion(vparLocalidad.getDescripcion());
        dominio = iDominioService.obtenerDominioPorNombreYValor("TLOCALIDAD", vparLocalidad.getTipoLocalidad());
        tipoLocalidad = dominio.getDescripcion();
        nuevaLocalidad.setTipoLocalidad(vparLocalidad.getTipoLocalidad());
        nuevaLocalidad.setCodigoOtr(vparLocalidad.getCodigoOtr());
        nuevaLocalidad.setCodigoRef(vparLocalidad.getCodigoRef());
        try {
            System.out.println("vparLocalidad.getCodLocalidadPadre() " + vparLocalidad.getCodLocalidadPadre());
            if (vparLocalidad.getCodLocalidadPadre() != null) {
                idPadre = vparLocalidad.getCodLocalidadPadre();
                parLocalidadPadre = iLocalidadService.findById(idPadre);
                nuevaLocalidad.setCodLocalidadPadre(parLocalidadPadre);
            } else {
                System.out.println("Sin Idpadre");
                idPadre = "";
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "No se cargarón los datos corectamente, por favor vuelva a seleccionar el elemento"));
            e.printStackTrace();
        }

        nuevaLocalidad.setFechaBitacora(vparLocalidad.getFechaBitacora());
        nuevaLocalidad.setRegistroBitacora(vparLocalidad.getRegistroBitacora());

        parLocalidad = nuevaLocalidad;
    }

    public void agregaNodoPadre() {
        estadoBoton = false;
        estadoCod = true;
        registro = "Agregar";
        tipoNodo = "Padre";
        idHijo = "";
        nivelNodo = "1";
        dominio = iDominioService.obtenerDominioPorNombreYValor("TLOCALIDAD", "PAIS");
        tipoLocalidad = dominio.getDescripcion();
        nuevo();
    }

    public void nuevo() {
        estadoRecurso = true;
        parLocalidad = new ParLocalidad();
        registro = "Agregar";
    }

    public void agregaNodoHijo() {
        FacesContext context = FacesContext.getCurrentInstance();
        registro = "Agregar";
        estadoCod = true;
        String niv;
        int n = Integer.parseInt(nivelNodo) + 1;
        if (n > 3) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No se puede agregar mas nodos"));
            return;
        }
        dominio = iDominioService.obtenerDominioPorNombrePadreYValorPadre("TLOCALIDAD", parLocalidad.getTipoLocalidad()).get(0);
        tipoLocalidad = dominio.getDescripcion();
        ParLocalidad rec = parLocalidad;
        idPadre = parLocalidad.getCodLocalidad();
        try {
            if (idPadre != null) {
                tipoNodo = "Hijo";
                idHijo = "";
                estadoBoton = false;
                nuevo();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Selecciono nodo " + rec.getDescripcion()));
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
            VparLocalidad aux = new VparLocalidad();
            String niv;
            if (tipoNodo.equals("Hijo")) {
                int n = Integer.parseInt(nivelNodo) + 1;
                niv = n + "";
            } else {
                niv = nivelNodo;
            }
            if (registro.equals("Editar")) {
                niv = nivelNodo;
            }
            aux = iVParLocalidadService.ObtienePorDescripcionYNivel(parLocalidad.getDescripcion(), niv);
            if (registro.equals("Editar")) {
                if (aux.getDescripcion() == null) {
                    guarda();
                } else {
                    if (aux.getCodLocalidad().equals(parLocalidad.getCodLocalidad())) {
                        System.out.println("iguales");
                        guarda();
                    } else {
                        System.out.println("error de diferencia");
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Nodo con esa descripción ya existe en este nivel"));
                    }
                }

            } else {
                System.out.println("distintos");
                ParLocalidad aux1 = iLocalidadService.findById(parLocalidad.getCodLocalidad());
                if (aux1 == null) {
                    if (aux.getDescripcion() == null) {
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
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Nodo con esa descripción ya existe en este nivel"));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El Codigo para ese Nodo ya existe"));
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
        guardado = iLocalidadService.guardaLocalidad(parLocalidad, dominio.getParDominioPK().getValor(), REGISTRO_BITACORA, tipoNodo, idPadre, idHijo);
        if (guardado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Nodo guardado con exito"));
            estadoBoton = true;
            tipoLocalidad="";
            cargarNodo();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No se pudo guardar el nodo"));
        }
    }

    public void editar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (parLocalidad.getCodLocalidad() != null) {
                estadoBoton = false;
                registro = "Editar";
                estadoCod = false;
                //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Elemento seleccionado para edicion: ", usrRecurso.getEtiqueta()));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No selecciono ningun nodo"));
            e.printStackTrace();
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
            if (parLocalidad.getCodLocalidad() != null) {
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
            if (parLocalidad.getCodLocalidad() != null) {
                estadoBoton = true;
                if (iLocalidadService.eliminarLocalidad(parLocalidad.getCodLocalidad())) {
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

    /**
     * @return the iLocalidadService
     */
    public ILocalidadService getiLocalidadService() {
        return iLocalidadService;
    }

    /**
     * @param iLocalidadService the iLocalidadService to set
     */
    public void setiLocalidadService(ILocalidadService iLocalidadService) {
        this.iLocalidadService = iLocalidadService;
    }

    /**
     * @return the iVParLocalidadService
     */
    public IVParLocalidadService getiVParLocalidadService() {
        return iVParLocalidadService;
    }

    /**
     * @param iVParLocalidadService the iVParLocalidadService to set
     */
    public void setiVParLocalidadService(IVParLocalidadService iVParLocalidadService) {
        this.iVParLocalidadService = iVParLocalidadService;
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
    public String getIdHijo() {
        return idHijo;
    }

    /**
     * @param idHijo the idHijo to set
     */
    public void setIdHijo(String idHijo) {
        this.idHijo = idHijo;
    }

    /**
     * @return the idPadre
     */
    public String getIdPadre() {
        return idPadre;
    }

    /**
     * @param idPadre the idPadre to set
     */
    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    /**
     * @return the listaLocalidades
     */
    public List<VparLocalidad> getListaLocalidades() {
        return listaLocalidades;
    }

    /**
     * @param listaLocalidades the listaLocalidades to set
     */
    public void setListaLocalidades(List<VparLocalidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    /**
     * @return the parLocalidad
     */
    public ParLocalidad getParLocalidad() {
        return parLocalidad;
    }

    /**
     * @param parLocalidad the parLocalidad to set
     */
    public void setParLocalidad(ParLocalidad parLocalidad) {
        this.parLocalidad = parLocalidad;
    }

    /**
     * @return the parLocalidadPadre
     */
    public ParLocalidad getParLocalidadPadre() {
        return parLocalidadPadre;
    }

    /**
     * @param parLocalidadPadre the parLocalidadPadre to set
     */
    public void setParLocalidadPadre(ParLocalidad parLocalidadPadre) {
        this.parLocalidadPadre = parLocalidadPadre;
    }

    /**
     * @return the vparLocalidad
     */
    public VparLocalidad getVparLocalidad() {
        return vparLocalidad;
    }

    /**
     * @param vparLocalidad the vparLocalidad to set
     */
    public void setVparLocalidad(VparLocalidad vparLocalidad) {
        this.vparLocalidad = vparLocalidad;
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
     * @return the tipoLocalidad
     */
    public String getTipoLocalidad() {
        return tipoLocalidad;
    }

    /**
     * @param tipoLocalidad the tipoLocalidad to set
     */
    public void setTipoLocalidad(String tipoLocalidad) {
        this.tipoLocalidad = tipoLocalidad;
    }

    /**
     * @return the dominio
     */
    public ParDominio getDominio() {
        return dominio;
    }

    /**
     * @param dominio the dominio to set
     */
    public void setDominio(ParDominio dominio) {
        this.dominio = dominio;
    }

    /**
     * @return the estadoCod
     */
    public boolean isEstadoCod() {
        return estadoCod;
    }

    /**
     * @param estadoCod the estadoCod to set
     */
    public void setEstadoCod(boolean estadoCod) {
        this.estadoCod = estadoCod;
    }

}
