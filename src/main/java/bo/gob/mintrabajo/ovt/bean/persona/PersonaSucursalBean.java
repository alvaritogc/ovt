/*
 * Copyright 2014 lvaldez.
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
package bo.gob.mintrabajo.ovt.bean.persona;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_ESTADO_UNIDAD;
import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_ESTADO_USUARIO;
import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPOS_IDENTIFICACION;
import static bo.gob.mintrabajo.ovt.Util.Dominios.DOM_TIPO_AUTENTICACION;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO_UNIDAD_ACTIVO;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_ESTADO__USUARIO_SINCONFIRMAR;
import static bo.gob.mintrabajo.ovt.Util.Dominios.PAR_TIPO_AUTENTICACION_LOCAL;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.ID_PARAMETRO_MENSAJERIA;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_ASUNTO;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_CUENTA_EMAIL;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_MENSAJE;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_PASSWORD;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_PUERTO;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_SERVIDOR;
import static bo.gob.mintrabajo.ovt.Util.Parametricas.VALOR_URL;
import static bo.gob.mintrabajo.ovt.Util.Sequencias.PER_PERSONA_SEC;
import static bo.gob.mintrabajo.ovt.Util.Sequencias.USR_USUARIO_SEC;
import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.api.ILocalidadService;
import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import static bo.gob.mintrabajo.ovt.bean.persona.PersonaBean.REGISTRO_BITACORA;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParLocalidad;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidadPK;
import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * 13/01/2014
 */
@ManagedBean(name = "personaSucursalBean")
@ViewScoped
public class PersonaSucursalBean implements Serializable {

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;

    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;

    @ManagedProperty(value = "#{localidadService}")
    private ILocalidadService iLocalidadService;

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;

    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacion;

    private String numIdentificacion;

    private PerPersona persona;
    private List<SelectItem> listaTipoIdentificacion;
    private String idLocalidad;
    private List<SelectItem> listaLocalidad;
    private List<PerUnidad> listaSucursales;
    private List<PerUsuarioUnidad> listaSucursalesDelegados;
    private Long idSucursal;
    private Long idSucursalAnterior;
    private boolean activarForm;

    private List<UsrUsuario> listaUsuarioPersona;
    private UsrUsuario usuarioDelegado;

    private String contrasenia;
    private String confirmarContrasenia;
    private final int LONGITUD_MINIMA = 7;
    static String REGISTRO_BITACORA;
    private boolean personaNueva;
    private boolean estadoDelegado;
    private boolean estadoDelegadoAnterior;
    private boolean sucursalAsignada;

    private boolean mostrarFormulario;
    private String mostrarFormularioMensaje;

    private PerUnidad sucursal;

    private boolean delegadoNoFuncionario;//10
    private boolean noDelegadoFuncionario;//01
    private boolean noDelegadoNoFuncionario;//00
    private boolean delegado;

    @PostConstruct
    public void ini() {
        listaTipoIdentificacion = cargarListas(listaTipoIdentificacion, DOM_TIPOS_IDENTIFICACION);
        activarForm = false;
        delegadoNoFuncionario = false;
        noDelegadoFuncionario = false;
        noDelegadoNoFuncionario = false;
        estadoDelegado = true;
        estadoDelegadoAnterior = true;
        personaNueva = false;
        sucursalAsignada = false;
        idLocalidad = "";
        numIdentificacion = "";
        idSucursalAnterior = -1L;
        idSucursal = -1L;
        persona = new PerPersona();
        usuarioDelegado = new UsrUsuario();
        sucursal = new PerUnidad();
        listaSucursales = new ArrayList<PerUnidad>();
        listaSucursalesDelegados = new ArrayList<PerUsuarioUnidad>();
        delegado = "siDelegado".equals((String) session.getAttribute("delegado"));
        try {
            List<PerUnidad> listaUnidadAux = iUnidadService.buscarPorPersona((String) session.getAttribute("idPersona"));
            if(delegado){
                mostrarFormulario = false;
                mostrarFormularioMensaje = "No tiene permisos para ver esta pagina";
            } else {
                mostrarFormulario = true;
            }
            listaSucursales = listaUnidadAux.subList(0, listaUnidadAux.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarFormulario = false;
            mostrarFormularioMensaje = "No tiene permisos para ver esta pagina";
//            FacesContext contex = FacesContext.getCurrentInstance();
//            try {
//                contex.getExternalContext().redirect("/ovt/faces/pages/escritorio.xhtml");
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        }
        listaSucursalesDelegados = iPersonaService.listaUsuarioUnidadPorIdPersona((String) session.getAttribute("idPersona"));
        cargarLocalidad();
    }

    public List<SelectItem> cargarListas(List<SelectItem> lista, String dominio) {
        lista = new ArrayList<SelectItem>();
        try {
            List<ParDominio> valoresDominio = iDominioService.obtenerItemsDominio(dominio);
            for (ParDominio d : valoresDominio) {
                lista.add(new SelectItem(d.getParDominioPK().getValor(), d.getDescripcion()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void cargarLocalidad() {
        try {
            List<ParLocalidad> localidades = new ArrayList<ParLocalidad>();
            listaLocalidad = new ArrayList<SelectItem>();
            localidades = iLocalidadService.getAllLocalidades();
            for (ParLocalidad l : localidades) {
                if (!l.getDescripcion().equalsIgnoreCase("BOLIVIA") && l.getCodLocalidadPadre().getCodLocalidad().equalsIgnoreCase("BOL")) {
                    listaLocalidad.add(new SelectItem(l.getCodLocalidad(), l.getDescripcion()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarPersona() {
        persona = iPersonaService.findByNroIdentificacion(numIdentificacion);
        if (persona == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontro a la persona", "Registre a la persona o vuelva a intentarlo"));
            persona = new PerPersona();
            personaNueva = true;
            persona.setNroIdentificacion(numIdentificacion);
        } else {
            activarForm = true;
            idLocalidad = persona.getCodLocalidad().getCodLocalidad();
            revisaDelegadoFuncionario(persona.getIdPersona());
        }
    }

    public void revisaDelegadoFuncionario(String idPersona) {
        listaUsuarioPersona = iUsuarioService.obtenerUsuarioPorIdPersona(idPersona);
        for (UsrUsuario us : listaUsuarioPersona) {
            if (us.getEsDelegado() == 0 && us.getEsInterno() == 0) {
                noDelegadoNoFuncionario = true;
            }
            if (us.getEsDelegado() == 1 && us.getEsInterno() == 0) {
                delegadoNoFuncionario = true;
                usuarioDelegado = us;
            }
            if (us.getEsDelegado() == 0 && us.getEsInterno() == 1) {
                noDelegadoFuncionario = true;
            }
        }

    }

    public void registrar() {
        if (personaNueva) {
            //validar que nro de identificacion sea unico
            if (iPersonaService.findByNroIdentificacion(persona.getNroIdentificacion()) != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "EL valor del campo Nro. de identificacion ya existe. Modifique este valor"));
                return;
            }
        }

        if (!delegadoNoFuncionario) {
            //validar que el usuario sea unico
            if (iUsuarioService.obtenerUsuarioPorNombreUsuario(usuarioDelegado.getUsuario()) != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "EL valor del campo Usuario ya existe. Modifique este valor."));
                return;
            }

            if (!validarEmail(usuarioDelegado.getUsuario())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "EL formato del correo electronico es incorrecto."));
                return;
            }

            if (contrasenia.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar la Contraseña."));
                return;
            }

            if (!contrasenia.equals(confirmarContrasenia)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La valor de la Contraseña debe ser igual al valor del campo Confirmar contraseña."));
                return;
            }

            String contraseniaEsValida = validarContrasenia(contrasenia, LONGITUD_MINIMA);
            if (!contraseniaEsValida.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", contraseniaEsValida));
                return;
            }
        }

        try {
            REGISTRO_BITACORA = (String) session.getAttribute("bitacoraSession");
            //registrando persona
            if (personaNueva) {
                Long seq = iLocalidadService.localidadSecuencia(PER_PERSONA_SEC);
                persona.setIdPersona(seq.toString());
                persona.setCodLocalidad(iLocalidadService.findById(idLocalidad));
                persona.setRegistroBitacora(REGISTRO_BITACORA);
            } else {
                System.out.println("no es nueva persona");
            }
            
            String passw=usuarioDelegado.getClave();
            if (!delegadoNoFuncionario) {
                usuarioDelegado.setIdUsuario(iUsuarioService.obtenerSecuencia(USR_USUARIO_SEC));
                usuarioDelegado.setRegistroBitacora(REGISTRO_BITACORA);
                usuarioDelegado.setEsDelegado((short) 1);
                usuarioDelegado.setEsInterno((short) 0);
                usuarioDelegado.setClave(contrasenia);
                usuarioDelegado.setClave(Util.encriptaMD5(usuarioDelegado.getClave()));
                //ParDominio d = iDominioService.obtenerDominioPorNombreYValor(DOM_ESTADO_USUARIO, PAR_ESTADO__USUARIO_SINCONFIRMAR);
                //usuarioDelegado.setEstadoUsuario(d.getParDominioPK().getValor());
                usuarioDelegado.setEstadoUsuario(Dominios.PAR_ESTADO_ACTIVO);

                usuarioDelegado.setTipoAutenticacion(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_AUTENTICACION, PAR_TIPO_AUTENTICACION_LOCAL).getParDominioPK().getValor());
                usuarioDelegado.setFechaBitacora(new Date());
                usuarioDelegado.setRegistroBitacora(REGISTRO_BITACORA);
                usuarioDelegado.setIdPersona(persona);
                usuarioDelegado.setTipoAutenticacion(iDominioService.obtenerDominioPorNombreYValor(DOM_TIPO_AUTENTICACION, PAR_TIPO_AUTENTICACION_LOCAL).getParDominioPK().getValor());
                System.out.println("=== entro a user delegado");

            }
            if (!contrasenia.equals("") && !confirmarContrasenia.equals("")) {
                usuarioDelegado.setClave(contrasenia);
                usuarioDelegado.setClave(Util.encriptaMD5(usuarioDelegado.getClave()));
            }
//            ServicioEnvioEmail see = new ServicioEnvioEmail();
//            Map<String, String> configuracionEmail = new HashMap<String, String>();
//            configuracionEmail = cargaParametricasEmail();
//            see.envioEmail2(usuarioDelegado, configuracionEmail);

            for (PerUsuarioUnidad usuarioUnidad : listaSucursalesDelegados) {
                if ((usuarioUnidad.getPerUsuarioUnidadPK().getIdPersona().equals((String) session.getAttribute("idPersona")))
                        && (usuarioUnidad.getPerUsuarioUnidadPK().getIdUnidad() == idSucursal)
                        && (usuarioUnidad.getPerUsuarioUnidadPK().getIdUsuario() == usuarioDelegado.getIdUsuario())) {
                    sucursalAsignada = true;
                }
            }

            //idSucursal para unidad
            sucursal = iUnidadService.obtenerPorIdPersonaIdUnidad((String) session.getAttribute("idPersona"), idSucursal);
            
            if (sucursalAsignada && (estadoDelegadoAnterior == estadoDelegado) && (passw.equals(usuarioDelegado.getClave()))) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "La persona ya tiene asignada esa sucursal"));
                sucursalAsignada = false;
                return;
            }
            
            if (idSucursalAnterior != -1L) {
                PerUnidad sucursalAux = iUnidadService.obtenerPorIdPersonaIdUnidad((String) session.getAttribute("idPersona"), idSucursalAnterior);
                iPersonaService.registrarDependiente(persona, sucursalAux, usuarioDelegado, REGISTRO_BITACORA, false);
            }

            boolean d = iPersonaService.registrarDependiente(persona, sucursal, usuarioDelegado, REGISTRO_BITACORA, estadoDelegado);
            if (d) {
                System.out.println("guardo");
                RequestContext.getCurrentInstance().execute("dlgAsisgnacion.hide();");
                ini();
            } else {
                System.out.println("No guardo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El registro no fue completado, verifique su configuración de email o base de datos"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "O el usuario ya tiene"));
        }
    }

    static private boolean validarEmail(String email) {
        final String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern patron = Pattern.compile(EMAIL_PATTERN);
        Matcher encajador = patron.matcher(email);
        if (encajador.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public String validarContrasenia(String pass, int longitudMinima) {

        String mensaje = "";

        if (pass.length() < longitudMinima) {
            mensaje = "La longitud minima de la contrasenia es " + longitudMinima + ". Intente nuevamente";
        } else {
            String validacion = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_={}+\\|:;\"'<>,-.?/]).{" + String.valueOf(longitudMinima) + ",50})";
            /*Pattern pattern = Pattern
             .compile("((?=.*\\d)(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_={}+\\|:;\"'<>,-.?/]).{3,50})");*/
            Pattern pattern = Pattern.compile(validacion);
            if (!pattern.matcher(pass).matches()) {
                mensaje = "La contraseña debe contener al menos un caracter númerico, alfabetico y especial.";
            } else {
                //La contrasenia es valida
                mensaje = "OK";
            }
        }
        return mensaje;
    }

    public Map<String, String> cargaParametricasEmail() {
        Map<String, String> configuracionEmail = new HashMap<String, String>();
        try {
            String from = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_CUENTA_EMAIL).getDescripcion();
            String subject_confirm = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_ASUNTO).getDescripcion();
            String urlRedireccion = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_URL).getDescripcion();
            String cuerpoMensaje = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_MENSAJE).getDescripcion();
            String password = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PASSWORD).getDescripcion();
            String host = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_SERVIDOR).getDescripcion();
            String port = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PUERTO).getDescripcion();
            configuracionEmail.put("from", from);
            configuracionEmail.put("subject", subject_confirm);
            configuracionEmail.put("urlRedireccion", urlRedireccion);
            configuracionEmail.put("cuerpoMensaje", cuerpoMensaje);
            configuracionEmail.put("password", password);
            configuracionEmail.put("host", host);
            configuracionEmail.put("port", port);
            configuracionEmail.put("sw", "0");
            return configuracionEmail;
        } catch (NullPointerException ne) {
            System.out.println("El parámetro no existe en base de datos ...");
            return null;
        }
    }

    public String descripcionEstado(String valor) {
        return Util.descripcionDominio("ESTADO", valor);
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
     * @return the persona
     */
    public PerPersona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    /**
     * @return the numIdentificacion
     */
    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * @param numIdentificacion the numIdentificacion to set
     */
    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    /**
     * @return the listaTipoIdentificacion
     */
    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    /**
     * @param listaTipoIdentificacion the listaTipoIdentificacion to set
     */
    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
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
     * @return the idLocalidad
     */
    public String getIdLocalidad() {
        return idLocalidad;
    }

    /**
     * @param idLocalidad the idLocalidad to set
     */
    public void setIdLocalidad(String idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    /**
     * @return the listaLocalidad
     */
    public List<SelectItem> getListaLocalidad() {
        return listaLocalidad;
    }

    /**
     * @param listaLocalidad the listaLocalidad to set
     */
    public void setListaLocalidad(List<SelectItem> listaLocalidad) {
        this.listaLocalidad = listaLocalidad;
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
     * @return the delegadoNoFuncionario
     */
    public boolean isDelegadoNoFuncionario() {
        return delegadoNoFuncionario;
    }

    /**
     * @param delegadoNoFuncionario the delegadoNoFuncionario to set
     */
    public void setDelegadoNoFuncionario(boolean delegadoNoFuncionario) {
        this.delegadoNoFuncionario = delegadoNoFuncionario;
    }

    /**
     * @return the noDelegadoFuncionario
     */
    public boolean isNoDelegadoFuncionario() {
        return noDelegadoFuncionario;
    }

    /**
     * @param noDelegadoFuncionario the noDelegadoFuncionario to set
     */
    public void setNoDelegadoFuncionario(boolean noDelegadoFuncionario) {
        this.noDelegadoFuncionario = noDelegadoFuncionario;
    }

    /**
     * @return the noDelegadoNoFuncionario
     */
    public boolean isNoDelegadoNoFuncionario() {
        return noDelegadoNoFuncionario;
    }

    /**
     * @param noDelegadoNoFuncionario the noDelegadoNoFuncionario to set
     */
    public void setNoDelegadoNoFuncionario(boolean noDelegadoNoFuncionario) {
        this.noDelegadoNoFuncionario = noDelegadoNoFuncionario;
    }

    /**
     * @return the usuarioDelegado
     */
    public UsrUsuario getUsuarioDelegado() {
        return usuarioDelegado;
    }

    /**
     * @param usuarioDelegado the usuarioDelegado to set
     */
    public void setUsuarioDelegado(UsrUsuario usuarioDelegado) {
        this.usuarioDelegado = usuarioDelegado;
    }

    /**
     * @return the listaSucursales
     */
    public List<PerUnidad> getListaSucursales() {
        return listaSucursales;
    }

    /**
     * @param listaSucursales the listaSucursales to set
     */
    public void setListaSucursales(List<PerUnidad> listaSucursales) {
        this.listaSucursales = listaSucursales;
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
     * @return the idSucursal
     */
    public Long getIdSucursal() {
        return idSucursal;
    }

    /**
     * @param idSucursal the idSucursal to set
     */
    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    /**
     * @return the activarForm
     */
    public boolean isActivarForm() {
        return activarForm;
    }

    /**
     * @param activarForm the activarForm to set
     */
    public void setActivarForm(boolean activarForm) {
        this.activarForm = activarForm;
    }

    /**
     * @return the confirmarContrasenia
     */
    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    /**
     * @param confirmarContrasenia the confirmarContrasenia to set
     */
    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }

    /**
     * @return the LONGITUD_MINIMA
     */
    public int getLONGITUD_MINIMA() {
        return LONGITUD_MINIMA;
    }

    /**
     * @return the sucursal
     */
    public PerUnidad getSucursal() {
        return sucursal;
    }

    /**
     * @param sucursal the sucursal to set
     */
    public void setSucursal(PerUnidad sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * @return the iParametrizacion
     */
    public IParametrizacionService getiParametrizacion() {
        return iParametrizacion;
    }

    /**
     * @param iParametrizacion the iParametrizacion to set
     */
    public void setiParametrizacion(IParametrizacionService iParametrizacion) {
        this.iParametrizacion = iParametrizacion;
    }

    /**
     * @return the listaSucursalesDelegados
     */
    public List<PerUsuarioUnidad> getListaSucursalesDelegados() {
        return listaSucursalesDelegados;
    }

    /**
     * @param listaSucursalesDelegados the listaSucursalesDelegados to set
     */
    public void setListaSucursalesDelegados(List<PerUsuarioUnidad> listaSucursalesDelegados) {
        this.listaSucursalesDelegados = listaSucursalesDelegados;
    }

    /**
     * @return the estadoDelegado
     */
    public boolean isEstadoDelegado() {
        return estadoDelegado;
    }

    /**
     * @param estadoDelegado the estadoDelegado to set
     */
    public void setEstadoDelegado(boolean estadoDelegado) {
        this.estadoDelegado = estadoDelegado;
    }

    /**
     * @return the sucursalAsignada
     */
    public boolean isSucursalAsignada() {
        return sucursalAsignada;
    }

    /**
     * @param sucursalAsignada the sucursalAsignada to set
     */
    public void setSucursalAsignada(boolean sucursalAsignada) {
        this.sucursalAsignada = sucursalAsignada;
    }

    /**
     * @return the idSucursalAnterior
     */
    public Long getIdSucursalAnterior() {
        return idSucursalAnterior;
    }

    /**
     * @param idSucursalAnterior the idSucursalAnterior to set
     */
    public void setIdSucursalAnterior(Long idSucursalAnterior) {
        this.idSucursalAnterior = idSucursalAnterior;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the estadoDelegadoAnterior
     */
    public boolean isEstadoDelegadoAnterior() {
        return estadoDelegadoAnterior;
    }

    /**
     * @param estadoDelegadoAnterior the estadoDelegadoAnterior to set
     */
    public void setEstadoDelegadoAnterior(boolean estadoDelegadoAnterior) {
        this.estadoDelegadoAnterior = estadoDelegadoAnterior;
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
}
