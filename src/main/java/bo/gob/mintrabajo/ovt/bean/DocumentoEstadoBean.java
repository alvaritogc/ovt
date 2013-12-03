package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IDocumentoEstadoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class DocumentoEstadoBean implements Serializable{

    private HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private static final Logger logger = LoggerFactory.getLogger(DocumentoEstadoBean.class);

    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstadoService iDocumentoEstadoService;
    @ManagedProperty(value = "#{usuarioService}")
    private IUsuarioService iUsuarioService;

    private Long idUsuario;
    private UsrUsuario usrUsuario;
    private ParDocumentoEstado parDocumentoEstado;
    private List<ParDocumentoEstado> listaParDocumentoEstado;
    private boolean estado;
    private boolean modifica;


    @PostConstruct
    public void ini() {
        idUsuario = (Long) session.getAttribute("idUsuario");
        usrUsuario = iUsuarioService.findById(idUsuario);
        parDocumentoEstado = new ParDocumentoEstado();
        modifica = false;
        cargar();
    }

    public void cargar(){
        listaParDocumentoEstado = new ArrayList<ParDocumentoEstado>();
        listaParDocumentoEstado = iDocumentoEstadoService.listarDocumentoEstados();
    }

    public void cargarModificar(){
        modifica=true;
        if(parDocumentoEstado.getEstado().equals(Dominios.PAR_ESTADO_ACTIVO))
            estado=true;
        else
            estado=false;
    }

    public void cargarNuevo(){
        modifica = false;
        estado=false;
        parDocumentoEstado= new ParDocumentoEstado();
        cargar();
    }
    
     public String descripcionEstado(String valor){
        return Util.descripcionDominio("ESTADO", valor);
    }

    public void guardaModificaDocumentoEstado(){
        parDocumentoEstado.setFechaBitacora(new Date());
        parDocumentoEstado.setRegistroBitacora(usrUsuario.getUsuario());
        if(estado==true)
            parDocumentoEstado.setEstado(Dominios.PAR_ESTADO_ACTIVO);
        else
            parDocumentoEstado.setEstado(Dominios.PAR_ESTADO_INACTIVO);
        iDocumentoEstadoService.guardarModificarDocumentoEstado(parDocumentoEstado);
        parDocumentoEstado = new ParDocumentoEstado();
        cargar();
    }

    public void eliminaDocumentoEstado(){
        iDocumentoEstadoService.eliminarDocumentoEstado(parDocumentoEstado);
        cargar();
    }

    public List<ParDocumentoEstado> getListaParDocumentoEstado() {
        return listaParDocumentoEstado;
    }

    public void setListaParDocumentoEstado(List<ParDocumentoEstado> listaParDocumentoEstado) {
        this.listaParDocumentoEstado = listaParDocumentoEstado;
    }

    public ParDocumentoEstado getParDocumentoEstado() {
        return parDocumentoEstado;
    }

    public void setParDocumentoEstado(ParDocumentoEstado parDocumentoEstado) {
        this.parDocumentoEstado = parDocumentoEstado;
    }

    public IDocumentoEstadoService getiDocumentoEstadoService() {
        return iDocumentoEstadoService;
    }

    public void setiDocumentoEstadoService(IDocumentoEstadoService iDocumentoEstadoService) {
        this.iDocumentoEstadoService = iDocumentoEstadoService;
    }

    public IUsuarioService getiUsuarioService() {
        return iUsuarioService;
    }

    public void setiUsuarioService(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isModifica() {
        return modifica;
    }

    public void setModifica(boolean modifica) {
        this.modifica = modifica;
    }

//    public void confirmaEliminar(){
//        RequestContext context = RequestContext.getCurrentInstance();
//        try {
//            boolean tmp = iEntidadService.deleteEntidad(entidad);
//            if(tmp){
//               listaEntidad= iEntidadService.listaEntidadPorOrden();
//            }else{
//               context.execute("dlgMensaje.show()");
//            }
//        } catch (Exception e) {
//            context.execute("dlgMensaje.show()");
//            e.printStackTrace();
//        }
//    }
//
//    public void nuevo(){
//        entidad=new ParEntidad();
//        evento=false;
//    }
//
//    public void guardarModificar(){
//        RequestContext context = RequestContext.getCurrentInstance();
//        final String  REGISTRO_BITACORA=idUsuario.toString();
//        try {
//            ParEntidad pe = iEntidadService.saveEntidad(entidad,REGISTRO_BITACORA,unidad, evento);
//            nuevo();
//            listaEntidad= iEntidadService.listaEntidadPorOrden();
//            context.execute("dlgFormEntidad.hide();");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}