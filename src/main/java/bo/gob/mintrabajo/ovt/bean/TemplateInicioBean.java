package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.Util.ServicioEnvioEmail;
import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "templateInicioBean")
@ViewScoped
public class TemplateInicioBean implements Serializable {
    //

    private HttpSession session;
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
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

    @ManagedProperty(value="#{usuarioUnidadService}")
    private IUsuarioUnidadService iUsuarioUnidadService;
    //
    @ManagedProperty(value = "#{mensajeAppService}")
    private IMensajeAppService iMensajeAppService;
    //
    private UsrUsuario usuario;
    private PerPersona persona;
    private PerPersona empleador;
    //
    //
    private List<UsrRecurso> listaRecursos;
    private MenuModel model;
    //
    private String username;
    private String password;
    //
    //
    private String nombreDeUsuario;
    private String nombreDeUnidad;



    // Variables que se utilizan cuando el usuario olvido contrasenia
    private String nit;
    private String email;

    //Varibles que se utilizan cuando el usuario quiere cambiar su contrasenia
    private String contrasenia;
    private String nuevaContrasenia;
    private String confirmarContrasenia;
    
    //Variables para los servicios publicos
    private List<ParMensajeApp> listaMensajeApp;
    private ParMensajeApp mensajeApp;
    //

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
        empleador = null;
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
                nombreDeUnidad=empleador.getNombreRazonSocial();
            }
            else{
                nombreDeUnidad="N/A";
            }
            //
            nombreDeUsuario=usuario.getUsuario();
            
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
        System.out.println("cargar()");
        model = new DefaultMenuModel();
        try {
            listaRecursos = iRecursoService.buscarPorUsuario(idUsuario);
            logger.info("nro recursos del usuario:" + listaRecursos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        crearMenuRecurso();
        DefaultMenuItem item = new DefaultMenuItem("Salir");
        item.setIcon("ui-icon-arrowthickstop-1-e");
        item.setCommand("#{templateInicioBean.logout}");
        model.addElement(item);
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
        //ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
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
            Long idUsuario = iUsuarioService.login(username, password);
            logger.info("usuario aceptado");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("idUsuario", idUsuario);
            UsrUsuario usuario = iUsuarioService.findById(idUsuario);
            session.setAttribute("idPersona", usuario.getIdPersona().getIdPersona());

            if (usuario.getEsInterno() == 1) {
                session.setAttribute("idEmpleador", null);
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject subject = SecurityUtils.getSubject();
                token.setRememberMe(true);
                subject.login(token);
                //ini();
                token.clear();
                return "irEmpleadorBusqueda";
            } else {
                session.setAttribute("idEmpleador", usuario.getIdPersona().getIdPersona());
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
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
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Hello "));
        } /**catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Hello "));
        }    */
        password = "";
        return "";
    }

    public String irInicio() {
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            idUsuario = (Long) session.getAttribute("idUsuario");
            usuario = iUsuarioService.findById(idUsuario);
            if(usuario.getEsInterno()==1){
                return "irEmpleadorBusqueda";
            }
            else{
                return "irEscritorio";
            }
        } catch (Exception e) {
            System.out.println("No se encontro la session");
        }
        return "irInicio";
    }


    public void olvidoContrasenia(){
        logger.info("=======>>>> OLVIDO SU CONTRASENIA ");
        logger.info("==============>>>>  NIT: "+nit+" EMAIL"+" emial");
        PerUsuarioUnidad perUsuarioUnidad=iUsuarioUnidadService.obtenerPorNITyEmail(nit,email);

       if(perUsuarioUnidad==null) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR ", "No existe un usuario cuyo Nro. de identificacion (NIT)  y correo electronico sean: "+nit+", "+email));
       }
       else {
           PerPersona persona=iPersonaService.findById( perUsuarioUnidad.getPerUsuarioUnidadPK().getIdPersona());
           UsrUsuario usuario=iUsuarioService.findById(perUsuarioUnidad.getPerUsuarioUnidadPK().getIdUsuario());
           //enviarEmail
           ServicioEnvioEmail envioEmail=new ServicioEnvioEmail();
           envioEmail.envioEmail2(usuario);
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO ", " Verifique su correo electronico"));
       }
       limpiar();
    }

    public void cambiarContrasenia(){
        logger.info("=======>>>> CAMBIAR CONTRASENIA ");
        logger.info("==============>>>>  contrasenia: "+contrasenia+" nueva contrasenia: "+nuevaContrasenia+" confirmar Contrasenia: "+confirmarContrasenia);
        session.setAttribute("idUsuario", idUsuario);
        Long idUsuario=(Long)session.getAttribute("idUsuario");

           String mensaeje= iUsuarioService.cambiarContrasenia(idUsuario,contrasenia,nuevaContrasenia,confirmarContrasenia);
           if(mensaeje.equalsIgnoreCase("OK")){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO ", "Se cambio la contraseni con exito."));
               limpiar();
               logout();
           }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR ", mensaeje));
           }
    }

    public void limpiar(){
        nit="";
        email="";
        password="";
        nuevaContrasenia="";
        confirmarContrasenia="";
    }
    
    
    public void verMensajeApp(){
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect( "/ovt/faces/pages/contenidos/contenidoPublico.xhtml?p="+mensajeApp.getIdMensajeApp());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void cargarServiciosPublicos(){
        listaMensajeApp=iMensajeAppService.listarPorRecurso(new Long("1000"));
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

    public String irRegistro(){
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
}
