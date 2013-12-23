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
package bo.gob.mintrabajo.ovt.bean.administracion;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUsuarioRolService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lvaldez
 */
@ManagedBean
@ViewScoped
public class CambioLoginEmpleadorBean implements Serializable {

    @ManagedProperty(value = "#{rolService}")
    private IRolService iRolService;
    @ManagedProperty(value = "#{usuarioRolService}")
    private IUsuarioRolService iUsuarioRolService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    private String mostrarFormularioMensaje;
    private boolean mostrarFormulario;
    private HttpSession session;
    private String REGISTRO_BITACORA;
    private String idEmpleador;
    private PerPersona empleador;
    private UsrUsuario usuario;
    private String usuarioNuevo;
    private String usuarioConfirma;
    private List<PerUnidad> listaUnidades;

    @PostConstruct
    public void ini() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            REGISTRO_BITACORA = (String) session.getAttribute("bitacoraSession");
            idEmpleador = (String) session.getAttribute("idEmpleador");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("idEmpleador " + idEmpleador);
        if (idEmpleador == null) {
            mostrarFormulario = false;
            mostrarFormularioMensaje = "No se encontro al empleador";
        } else {
            mostrarFormulario = true;
            mostrarFormularioMensaje = "";
            empleador = iPersonaService.findById(idEmpleador);
            usuario = iUsuarioService.obtenerUsuarioPorIdPersona(idEmpleador).get(0);
            listaUnidades = iUnidadService.buscarPorPersona(idEmpleador);
        }
    }

    public void guardarCambios() {
        if(!validarEmail(usuarioNuevo)){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL formato del correo electronico de nuevo usuario es incorrecto ."));
                ini();
                return ;
            }
        if(!validarEmail(usuarioConfirma)){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","EL formato del correo electronico de confirme usuario es incorrecto."));
                ini();
                return ;
            }
        if (!usuarioNuevo.equals(usuarioConfirma)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nuevo usuario que esta ingresando no coincide con la confirmacion de usuario, verifique que sean iguales."));
            ini();
            return;
        }
        if (usuarioNuevo.equals(usuarioConfirma)) {
            iUsuarioService.cambiarLogin(usuario.getIdUsuario(), usuarioNuevo);
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,"Información","Datos modificados con exito."));
            canselar();
            ini();
        }

    }
    
    public void canselar(){
        usuarioNuevo="";
        usuarioConfirma="";
    }
    
    public boolean validarEmail(String email){
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patron = Pattern.compile(EMAIL_PATTERN);
        Matcher encajador = patron.matcher(email);
        if (encajador.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the iRolService
     */
    public IRolService getiRolService() {
        return iRolService;
    }

    /**
     * @param iRolService the iRolService to set
     */
    public void setiRolService(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    /**
     * @return the iUsuarioRolService
     */
    public IUsuarioRolService getiUsuarioRolService() {
        return iUsuarioRolService;
    }

    /**
     * @param iUsuarioRolService the iUsuarioRolService to set
     */
    public void setiUsuarioRolService(IUsuarioRolService iUsuarioRolService) {
        this.iUsuarioRolService = iUsuarioRolService;
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
     * @return the iPersonaService
     */
    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    /**
     * @param iPersonaService the iPersonaService to set
     */
    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    /**
     * @return the mostrarFormularioMensaje
     */
    public String getMostrarFormularioMensaje() {
        return mostrarFormularioMensaje;
    }

    /**
     * @param mostrarFormularioMensaje the mostrarFormularioMensaje to set
     */
    public void setMostrarFormularioMensaje(String mostrarFormularioMensaje) {
        this.mostrarFormularioMensaje = mostrarFormularioMensaje;
    }

    /**
     * @return the mostrarFormulario
     */
    public boolean isMostrarFormulario() {
        return mostrarFormulario;
    }

    /**
     * @param mostrarFormulario the mostrarFormulario to set
     */
    public void setMostrarFormulario(boolean mostrarFormulario) {
        this.mostrarFormulario = mostrarFormulario;
    }

    /**
     * @return the empleador
     */
    public PerPersona getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador the empleador to set
     */
    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the usuario
     */
    public UsrUsuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the listaUnidades
     */
    public List<PerUnidad> getListaUnidades() {
        return listaUnidades;
    }

    /**
     * @param listaUnidades the listaUnidades to set
     */
    public void setListaUnidades(List<PerUnidad> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    /**
     * @return the iUnidadService
     */
    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    /**
     * @param iUnidadService the iUnidadService to set
     */
    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    /**
     * @return the usuarioNuevo
     */
    public String getUsuarioNuevo() {
        return usuarioNuevo;
    }

    /**
     * @param usuarioNuevo the usuarioNuevo to set
     */
    public void setUsuarioNuevo(String usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    /**
     * @return the usuarioConfirma
     */
    public String getUsuarioConfirma() {
        return usuarioConfirma;
    }

    /**
     * @param usuarioConfirma the usuarioConfirma to set
     */
    public void setUsuarioConfirma(String usuarioConfirma) {
        this.usuarioConfirma = usuarioConfirma;
    }
}
