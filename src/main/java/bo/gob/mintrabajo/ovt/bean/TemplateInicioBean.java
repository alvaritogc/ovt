package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bo.gob.mintrabajo.wsclient.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bo.gob.mintrabajo.ovt.Util.Parametricas.*;

@ManagedBean(name = "templateInicioBean")
@ViewScoped
public class TemplateInicioBean implements Serializable {
    //
    private HttpSession session;
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String loginNuevo;
    private String loginConfirmacion;
    private boolean loginValido = true;
    private static final Logger logger = LoggerFactory.getLogger(TemplateInicioBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    public IUsuarioService getiUsuarioCambiarContraseniaService() {
        return iUsuarioCambiarContraseniaService;
    }

    public void setiUsuarioCambiarContraseniaService(IUsuarioService iUsuarioCambiarContraseniaService) {
        this.iUsuarioCambiarContraseniaService = iUsuarioCambiarContraseniaService;
    }

    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioCambiarContraseniaService;
    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{perUsuarioService}")
    private IPerUsuarioService iPerUsuarioService;
    @ManagedProperty(value = "#{usuarioUnidadService}")
    private IUsuarioUnidadService iUsuarioUnidadService;
    //
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;

    @ManagedProperty(value="#{dominioService}")
    public IDominioService iDominioService;
    //
    private UsrUsuario usuario;
    private PerPersona persona;
    private PerPersona empleador;
    //

    private List<UsrRecurso> listaRecursos;
    private List<UsrRecurso> listaRecursosHijos;
    private List<UsrRecurso> listaRecursosPadres;
    private List<UsrRecurso> listaRecursosFinales;
    //
    private MenuModel model;
    //
    private String username;
    private String password;
    //
    private String nombreDeUsuario;
    private String nombreDeUnidad;
    // Variables que se utilizan cuando el usuario olvido contrasenia
    private String nit;
    private String email;
    //Varibles que se utilizan cuando el usuario quiere cambiar su contrasenia

    // *** Varibles que se utilizan cuando el usuario quiere cambiar su contrasenia *** //
    private String contrasenia;
    private String nuevaContrasenia;
    private String confirmarContrasenia;
    private final int LONGITUD_MINIMA = 7;

    //*** Cache para guardar dominios guava ***//
    public static Cache<ParDominioPK, ParDominio> mapaDominio = CacheBuilder.newBuilder().maximumSize(600).build();

    
    //Variables para los servicios publicos
    private List<ParMensajeApp> listaMensajeApp;
    private ParMensajeApp mensajeApp;
    //
    private ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
    // envio de email
    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacion;

    @PostConstruct
    public void ini() {
        logger.info("TemplateInicioBean.init()");
        //
        username = "";
        password = "";
        //
        idUsuario = null;
        idPersona = null;
        idEmpleador = null;
        listaRecursos = new ArrayList<UsrRecurso>();
        usuario = null;
        persona = null;
        empleador = new PerPersona();
        //
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
            //
            logger.info("Buscando usuario" + idUsuario);
            usuario = iUsuarioService.findById(idUsuario);
            //
            idPersona = (String) session.getAttribute("idPersona");
            persona = iPersonaService.findById(idPersona);

            idEmpleador = (String) session.getAttribute("idEmpleador");
            if (idEmpleador != null) {
                empleador = iPersonaService.findById(idEmpleador);
                nombreDeUnidad = empleador.getNombreRazonSocial();
                nombreDeUnidad = empleador.getApellidoPaterno()!=null?(nombreDeUnidad+" "+empleador.getApellidoPaterno()):(nombreDeUnidad);
                nombreDeUnidad = empleador.getApellidoMaterno()!=null?(nombreDeUnidad+" "+empleador.getApellidoMaterno()):(nombreDeUnidad);
            } else {
                nombreDeUnidad = "N/A";
            }
            //
            nombreDeUsuario = usuario.getUsuario();
            loginValido = Util.validaCorreo(usuario.getUsuario());
            //
            logger.info("usuario ok");
            cargar();
        } catch (Exception e) {
//            e.printStackTrace();
            model = new DefaultMenuModel();
            DefaultMenuItem item = new DefaultMenuItem("Salir");
            item.setIcon("ui-icon-arrowthickstop-1-e");
            item.setCommand("#{templateInicioBean.logout}");
            //reo
            //TODO controlar DDJJ
            model.addElement(item);
        }
        cargarServiciosPublicos();
    }

    public void cargar() {
        //System.out.println("cargar()");
        model = new DefaultMenuModel();
        try {
            //listaRecursos = iRecursoService.buscarPorUsuario(idUsuario);
            cargarRecursos();
            //logger.info("nro recursos del usuario:" + listaRecursos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        crearMenuRecurso();
        DefaultMenuItem item = new DefaultMenuItem("Salir");
        item.setIcon("ui-icon-arrowthickstop-1-e");
        item.setCommand("#{templateInicioBean.logout}");
        model.addElement(item);
    }

    public void cargarRecursos() {
        listaRecursos = iRecursoService.buscarPorUsuario(idUsuario);
        listaRecursosHijos = new ArrayList<UsrRecurso>();
        listaRecursosPadres = new ArrayList<UsrRecurso>();
        listaRecursosFinales = new ArrayList<UsrRecurso>();
        //System.out.println("====MEN GUI==="+listaRecursos.size()+"=================");
        for (UsrRecurso r : listaRecursos) {
            if (r.getTipoRecurso().equals("GUI")) {
                listaRecursosHijos.add(r);
            }
        }
        int nroDeRecursos = listaRecursosHijos.size();
        //System.out.println("=====GUI====="+nroDeRecursos+"==================");
        while (nroDeRecursos > 0) {
            listaRecursosPadres = new ArrayList<UsrRecurso>();
            for (UsrRecurso r : listaRecursosHijos) {
                if(r.getIdRecursoPadre()!=null){
                    adicionarPadres(r.getIdRecursoPadre().getIdRecurso());
                }
            }
            for (UsrRecurso r : listaRecursosHijos) {
                listaRecursosFinales.add(r);
            }
            listaRecursosHijos = new ArrayList<UsrRecurso>();
            for (UsrRecurso r : listaRecursosPadres) {
                listaRecursosHijos.add(r);
            }
            nroDeRecursos = listaRecursosHijos.size();
        }
        listaRecursos=listaRecursosFinales;
    }

    public String adicionarPadres(Long idPadre) {
        for (UsrRecurso r : listaRecursos) {
            if (r.getIdRecurso().equals(idPadre)) {
                if ((!listaRecursosPadres.contains(r)) && (!listaRecursosFinales.contains(r))) {
                    listaRecursosPadres.add(r);
                    return "";
                }
                else{
                    return "";
                }
            }
        }
        return "";
    }

    public void crearMenuRecurso() {
        for (UsrRecurso recurso : listaRecursos) {
            if (recurso.getIdRecursoPadre() == null) {
                DefaultSubMenu subMenu = crearMenuHijos(recurso);
                model.addElement(subMenu);
            }
        }
    }

    public DefaultSubMenu crearMenuHijos(UsrRecurso recurso) {
        DefaultSubMenu subMenu = new DefaultSubMenu(recurso.getEtiqueta());
        for (UsrRecurso recursoHijo : listaRecursos) {
            if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().getIdRecurso().equals(recurso.getIdRecurso())) {
                if (tieneHijos(recursoHijo)) {
                    DefaultSubMenu subMenuHijo = crearMenuHijos(recursoHijo);
                    subMenu.addElement(subMenuHijo);
                } else {
                    DefaultMenuItem item = new DefaultMenuItem(recursoHijo.getEtiqueta());
                    item.setUrl(recursoHijo.getEjecutable());
                    subMenu.addElement(item);
                }
            }

        }
        return subMenu;
    }

    public boolean tieneHijos(UsrRecurso recurso) {
        for (UsrRecurso recursoHijo : listaRecursos) {
            if (recurso.getIdRecurso() != recursoHijo.getIdRecurso()) {
                if (recursoHijo.getIdRecursoPadre() != null && recursoHijo.getIdRecursoPadre().getIdRecurso().equals(recurso.getIdRecurso())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String logout() {
        logger.info("logout()");
        //ExternalContext ctx = FacesContext.getCuirrentInstance().getExternalContext();
        // Usar el contexto de JSF para invalidar la sesión,
        // NO EL DE SERVLETS (nada de HttpServletRequest)
        SecurityUtils.getSubject().logout();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redirección de nuevo con el contexto de JSF,
        // si se usa una HttpServletResponse fallará.
        // Sin embargo, como ya está fuera del ciclo de vida
        // de JSF se debe usar la ruta completa 
        //ctx.redirect(ctxPath + "/faces/index.xhtml");
        return "irInicio";
    }

    public String login() {
        logger.info("login()");
        try {
            logger.info("iUsuarioService.login(" + username + "," + password + ")");
            String passwordEncripted = Util.encriptaMD5(password);
            Long idUsuario = iUsuarioService.login(username, passwordEncripted);
            boolean usuarioValido = true;
            logger.info("usuario aceptado");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("idUsuario", idUsuario);
            UsrUsuario usuario = iUsuarioService.findById(idUsuario);
            session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());
            String ipCliente = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("X-FORWARDED-FOR");
            if(ipCliente == null){
                ipCliente = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
            }
            String bitacoraSession = usuario.getUsuario() + "|" + ipCliente;
            session.setAttribute("bitacoraSession", bitacoraSession);
            int activarWebservice = Integer.parseInt(iParametrizacion.obtenerParametro(ID_PARAMETRO_WEBSERVICE, VALOR_ACTIVAR).getDescripcion());

            if (usuario.getEsInterno() == 1 && activarWebservice == 1) {
                String descSinEspacio = usuario.getIdPersona().getCodLocalidad().getDescripcion().replaceAll(" ","");
                usuarioValido = consumoWebservice(usuario.getIdPersona().getNroIdentificacion(), descSinEspacio);
                if(!usuarioValido){
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Atención", "El usuario no se encuentra activo!"));
                    return null;
                }
            }

            cargarDominio();

            if (usuario.getEsInterno() == 1) {
                session.setAttribute("idEmpleador", null);
                UsernamePasswordToken token = new UsernamePasswordToken(username, passwordEncripted);
                Subject subject = SecurityUtils.getSubject();
                token.setRememberMe(true);
                subject.login(token);
                //ini();
                token.clear();
                return "irEmpleadorBusqueda";
            } else {
                session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                UsernamePasswordToken token = new UsernamePasswordToken(username, passwordEncripted);
                Subject subject = SecurityUtils.getSubject();
                token.setRememberMe(true);
                subject.login(token);
                //ini();
                token.clear();
                return "irEscritorio";
            }
            //
        } catch (RuntimeException e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }

        password = "";
        return "";
    }

    public String irUnidad() throws IOException {
        session.setAttribute("idEmpleador", idEmpleador);
        return "irUnidad";
    }

    public String irInicio() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
            usuario = iUsuarioService.findById(idUsuario);
            if (usuario.getEsInterno() == 1) {
                return "irEmpleadorBusqueda";
            } else {
                return "irEscritorio";
            }
        } catch (Exception e) {
            System.out.println("No se encontro la session");
        }
        return "irInicio";
    }

    public void olvidoContrasenia() {
        logger.info("=======>>>> OLVIDO SU CONTRASENIA ");
        logger.info("==============>>>>  NIT: " + nit + " EMAIL" + " emial");
        PerUsuarioUnidad perUsuarioUnidad = iUsuarioUnidadService.obtenerPorNITyEmail(nit, email);

        if (perUsuarioUnidad == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", "No existe un usuario cuyo Nro. de identificacion (NIT)  y correo electronico sean: " + nit + ", " + email));
        } else {
            PerPersona persona = iPersonaService.findById(perUsuarioUnidad.getPerUsuarioUnidadPK().getIdPersona());
            UsrUsuario usuario = iUsuarioService.findById(perUsuarioUnidad.getPerUsuarioUnidadPK().getIdUsuario());
            //enviarEmail
            ServicioEnvioEmail envioEmail = new ServicioEnvioEmail();
            Map<String, String> configuracionEmail = new HashMap<String, String>();
            configuracionEmail = cargaParametricasEmail();
            envioEmail.envioEmail2(usuario, configuracionEmail);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlg2.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Se envió un mail a su correo electrónico, para completar su cambio password "));
        }
        limpiar();
    }

    public Map<String, String> cargaParametricasEmail() {
        Map<String, String> configuracionEmail = new HashMap<String, String>();
        try {
            String from = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_CUENTA_EMAIL).getDescripcion();
            String subject = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_ASUNTO_RECUPERAR).getDescripcion();
            String urlRedireccion = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_URL).getDescripcion();
            String cuerpoMensaje = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_MENSAJE_RECUPERAR).getDescripcion();
            String password = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PASSWORD).getDescripcion();
            String host = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_SERVIDOR).getDescripcion();
            String port = iParametrizacion.obtenerParametro(ID_PARAMETRO_MENSAJERIA, VALOR_PUERTO).getDescripcion();
            configuracionEmail.put("from", from);
            configuracionEmail.put("subject", subject);
            configuracionEmail.put("urlRedireccion", urlRedireccion);
            configuracionEmail.put("cuerpoMensaje", cuerpoMensaje);
            configuracionEmail.put("password", password);
            configuracionEmail.put("host", host);
            configuracionEmail.put("port", port);
            configuracionEmail.put("sw", "1");
            return configuracionEmail;
        } catch (NullPointerException ne) {
            logger.info("El parámetro no existe en base de datos ...");
            return null;
        }
    }

    public void cambiarContrasenia() {
        logger.info("=======>>>> CAMBIAR CONTRASENIA ");
        logger.info("==============>>>>  contrasenia: " + contrasenia + " nueva contrasenia: " + nuevaContrasenia + " confirmar Contrasenia: " + confirmarContrasenia);
        session.setAttribute("idUsuario", idUsuario);
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        String contraseniaEsValida = validarContrasenia(nuevaContrasenia, LONGITUD_MINIMA);
        if (!contraseniaEsValida.equals("OK")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", contraseniaEsValida));
            ini();
            return;
        }

        String mensaeje = iUsuarioService.cambiarContrasenia(idUsuario, contrasenia, nuevaContrasenia, confirmarContrasenia);
        if (mensaeje.equalsIgnoreCase("OK")) {

            limpiar();
           // logout();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dlgCambiarContrasenia.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO ", "Se cambio la contraseñia con exito."));
            return;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ", mensaeje));
            return;
        }
    }

    public void editarLogin() {
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + " metodo editarLogin()");
        try {
            if (Util.validaCorreo(loginNuevo)) {
                if (loginConfirmacion.equals(loginNuevo)) {
                    iUsuarioService.cambiarLogin(idUsuario, loginNuevo);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", " El lógin se actualizó correctamente"));
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("cambioLoginObligadoDlg.hide();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El login nuevo no coincide con el login de confirmación!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El login no es una cuenta de correo valida!"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "No se pudo actualizar el login intente más tarde"));
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioLoginObligadoDlg.hide();");
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

    public void limpiar() {
        nit = "";
        email = "";
        password = "";
        nuevaContrasenia = "";
        confirmarContrasenia = "";
    }

    public void verMensajeApp() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("/ovt/faces/pages/contenidos/contenidoPublico.xhtml?p=" + mensajeApp.getIdMensajeApp());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Cache<ParDominioPK, ParDominio> cargarDominio() {
        List<ParDominio> todosDominioLista = iDominioService.obtenerDominioLista();
        logger.info("El numero de dominios en base de datos " + todosDominioLista.size() + ", tamaño de objetos guardados en cache " + mapaDominio.size());
        if(todosDominioLista.size() != mapaDominio.size()){

        for (ParDominio d : todosDominioLista) {
            ParDominio parDominio = mapaDominio.getIfPresent(d.getParDominioPK());

            if (parDominio == null) {
                logger.debug("No existe en la cache este objeto es agregado a cache " + d.getParDominioPK().getIdDominio() + " " + d.getParDominioPK().getValor());
                mapaDominio.put(d.getParDominioPK(), d);
            }else{
                logger.info("Este dominio existe en la cache pasamos al siguiente " + parDominio.getParDominioPK().getIdDominio() + " " + d.getParDominioPK().getValor());
                continue;
            }
        }
        }else{
            logger.info("El mapa esta cargado con la misma cantidad de datos en cache ");
        }
        return mapaDominio;
    }

    public void  abrirCambioLoginDlg() {
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + " poll listener abrirCambioLoginDlg()");
        if(!loginValido) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioLoginObligadoDlg.show();");
            loginValido = true;
        }
    }

    public boolean consumoWebservice(String nroDocumento, String ciudadEx){
        logger.info("Consultando a webservice el numero de documento " + nroDocumento + " " + ciudadEx);
        String codExp = "NN";
        if (ciudadEx.trim().toLowerCase().equals("lapaz")) {
            codExp = "LP";
        }
        if (ciudadEx.trim().toLowerCase().equals("cochabamba")) {
            codExp = "CB";
        }
        if (ciudadEx.trim().toLowerCase().equals("santacruz")) {
            codExp = "SC";
        }
        if (ciudadEx.trim().toLowerCase().equals("oruro")) {
            codExp = "OR";
        }
        if (ciudadEx.trim().toLowerCase().equals("chuquisaca")) {
            codExp = "CH";
        }
        if (ciudadEx.trim().toLowerCase().equals("tarija")) {
            codExp = "TR";
        }
        if (ciudadEx.trim().toLowerCase().equals("beni")) {
            codExp = "BE";
        }
        if (ciudadEx.trim().toLowerCase().equals("potosi")) {
            codExp = "PT";
        }

        logger.info("Lo que se envía al webservice nroDocumento " + nroDocumento + " Expedición " + codExp);

        try {
            Service1 s = new Service1();
            Service1Soap service1Soap = s.getService1Soap();          //4332051 LP
            WSSAPWEBPARAMResponse.WSSAPWEBPARAMResult lp = service1Soap.wssapwebparam(nroDocumento, codExp);
            ElementNSImpl element = (ElementNSImpl) lp.getAny();
            ElementNSImpl dataSet = (ElementNSImpl) element.getElementsByTagName("NewDataSet").item(0);
            if (dataSet != null) {
                ElementNSImpl consulta = (ElementNSImpl) dataSet.getFirstChild();
                if (consulta != null) {
                    String idFuncionario = consulta.getElementsByTagName("fun_id").item(0).getTextContent();
                    if (idFuncionario != null) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "Ocurrio un problema en webservice comuniquese con el administrador!"));
        }
        return false;
    }


    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public void cargarServiciosPublicos(){
        listaMensajeApp=iMensajeAppService.listarPorRecursoYFechaActual(new Long("1000"));
    }

    public IUsuarioUnidadService getiUsuarioUnidadService() {
        return iUsuarioUnidadService;
    }

    public void setiUsuarioUnidadService(IUsuarioUnidadService iUsuarioUnidadService) {
        this.iUsuarioUnidadService = iUsuarioUnidadService;
    }

    public IPerUsuarioService getiPerUsuarioService() {
        return iPerUsuarioService;
    }

    public void setiPerUsuarioService(IPerUsuarioService iPerUsuarioService) {
        this.iPerUsuarioService = iPerUsuarioService;
    }

    public String irRegistro() {
        return "irRegistro";
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public UsrUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public PerPersona getEmpleador() {
        return empleador;
    }

    public void setEmpleador(PerPersona empleador) {
        this.empleador = empleador;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IMensajeAppService getiMensajeAppService() {
        return iMensajeAppService;
    }

    public void setiMensajeAppService(IMensajeAppService iMensajeAppService) {
        this.iMensajeAppService = iMensajeAppService;
    }

    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }

    public String getConfirmarContrasenia() {
        return confirmarContrasenia;
    }

    public void setConfirmarContrasenia(String confirmarContrasenia) {
        this.confirmarContrasenia = confirmarContrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getNombreDeUnidad() {
        return nombreDeUnidad;
    }

    public void setNombreDeUnidad(String nombreDeUnidad) {
        this.nombreDeUnidad = nombreDeUnidad;
    }

    public List<ParMensajeApp> getListaMensajeApp() {
        return listaMensajeApp;
    }

    public void setListaMensajeApp(List<ParMensajeApp> listaMensajeApp) {
        this.listaMensajeApp = listaMensajeApp;
    }

    public ParMensajeApp getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(ParMensajeApp mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public int getLONGITUD_MINIMA() {
        return LONGITUD_MINIMA;
    }

    public String getLoginNuevo() {
        return loginNuevo;
    }

    public void setLoginNuevo(String loginNuevo) {
        this.loginNuevo = loginNuevo;
    }

    public String getLoginConfirmacion() {
        return loginConfirmacion;
    }

    public void setLoginConfirmacion(String loginConfirmacion) {
        this.loginConfirmacion = loginConfirmacion;
    }

    public boolean isLoginValido() {
        return loginValido;
    }

    public void setLoginValido(boolean loginValido) {
        this.loginValido = loginValido;
    }

    public IParametrizacionService getiParametrizacion() {
        return iParametrizacion;
    }

    public void setiParametrizacion(IParametrizacionService iParametrizacion) {
        this.iParametrizacion = iParametrizacion;
    }
}
