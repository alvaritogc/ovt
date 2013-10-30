package bo.gob.mintrabajo.ovt.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bo.gob.mintrabajo.ovt.api.*;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
//
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class EmpleadorBean implements Serializable{
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
    
    public void cargar() {
        if(busquedaNroIdentificacion.trim().equals("") && busquedaNombreRazonSocial.trim().equals("")){
            listaPersonas=new ArrayList<PerPersona>();
        }
        else{
           // listaPersonas=iPersonaService.buscarPorNroNombre(busquedaNroIdentificacion, busquedaNombreRazonSocial);

        }
    }

    public String seleccionarEmpleador(){
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
