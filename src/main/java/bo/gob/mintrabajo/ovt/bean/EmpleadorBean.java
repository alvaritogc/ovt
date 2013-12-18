package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUnidadService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

//

@ManagedBean
@SessionScoped
public class EmpleadorBean implements Serializable {
    //
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Long idUsuario;
    private String idPersona;
    private String idEmpleador;
    private String idUnidad;
    private static final Logger logger = LoggerFactory.getLogger(EmpleadorBean.class);
    //
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;
    @ManagedProperty(value = "#{personaService}")
    private IPersonaService iPersonaService;
    @ManagedProperty(value = "#{unidadService}")
    private IUnidadService iUnidadService;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;


    //
    //
    private PerPersona persona;
    private List<PerPersona> listaPersonas;
    private PerPersona personaABM;
    //
    private String busquedaNroIdentificacion;
    private String busquedaNombreRazonSocial;

    @PostConstruct
    public void ini() {
        logger.info("EmpleadorBean.init()");
        idUsuario = (Long) session.getAttribute("idUsuario");
//         List<PerPersona>listPersona =new ArrayList<PerPersona>();
//                 listPersona = iPersonaService.getAllPersonas();
        limpiar();
/*        System.out.println("=============================>>>>>");
        System.out.println("=============================>>>>>");
        System.out.println("=============================>>>>>");
        List<PerPersona>listaPersona=iPersonaService.buscarPorNroNombre("1579592010","SUSANA HEREDIA PRADO");
        System.out.println("=============================>>>>> RESULTADO "+listaPersona.size());*/
    }

    public void limpiar() {
        busquedaNroIdentificacion = "";
        busquedaNombreRazonSocial = "";
        cargar();
    }

    public String cargar() {
        if (busquedaNroIdentificacion.trim().equals("") && busquedaNombreRazonSocial.trim().equals("")) {
            listaPersonas = new ArrayList<PerPersona>();
        } else {
            //validacion de busquedaNombreRazonSocial mayor a 4 caracteres
            if (busquedaNombreRazonSocial.trim().length() > 0) {
                if (busquedaNombreRazonSocial.trim().length() < 4) {
                    listaPersonas = new ArrayList<PerPersona>();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El campo Nombre razón social debe tener una longitud mínima de 4 caracteres."));
                    return "";
                }
            }
            listaPersonas = iPersonaService.buscarPorNroNombre(busquedaNombreRazonSocial, "", busquedaNroIdentificacion);
        }
        return "";
    }

    public String seleccionarEmpleador() {
        session.setAttribute("idEmpleador", personaABM.getIdPersona());
        return "irEscritorio";
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public IPersonaService getiPersonaService() {
        return iPersonaService;
    }

    public void setiPersonaService(IPersonaService iPersonaService) {
        this.iPersonaService = iPersonaService;
    }

    public PerPersona getPersona() {
        return persona;
    }

    public void setPersona(PerPersona persona) {
        this.persona = persona;
    }

    public IUnidadService getiUnidadService() {
        return iUnidadService;
    }

    public void setiUnidadService(IUnidadService iUnidadService) {
        this.iUnidadService = iUnidadService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public List<PerPersona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<PerPersona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public PerPersona getPersonaABM() {
        return personaABM;
    }

    public void setPersonaABM(PerPersona personaABM) {
        this.personaABM = personaABM;
    }

    public String getBusquedaNroIdentificacion() {
        return busquedaNroIdentificacion;
    }

    public void setBusquedaNroIdentificacion(String busquedaNroIdentificacion) {
        this.busquedaNroIdentificacion = busquedaNroIdentificacion;
    }

    public String getBusquedaNombreRazonSocial() {
        return busquedaNombreRazonSocial;
    }

    public void setBusquedaNombreRazonSocial(String busquedaNombreRazonSocial) {
        this.busquedaNombreRazonSocial = busquedaNombreRazonSocial;
    }
}
