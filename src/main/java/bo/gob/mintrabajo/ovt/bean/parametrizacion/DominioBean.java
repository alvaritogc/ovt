package bo.gob.mintrabajo.ovt.bean.parametrizacion;

import bo.gob.mintrabajo.ovt.api.IDominioService;
import bo.gob.mintrabajo.ovt.bean.converters.UsrModuloConverter;
import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/8/13
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class DominioBean {

    @ManagedProperty(value = "#{dominioService}")
    private IDominioService iDominioService;
    private List<ParDominio> dominioLista;
    private List<ParDominio> dominioFiltroLista;
    private ParDominio dominioSelected;

    private static final Logger logger = LoggerFactory.getLogger(ParametrizacionBean.class);

    @PostConstruct
    public void cargaDominiosLista(){
        //dominioLista = iDominioService.obtenerDominioLista();
        dominioLista = new ArrayList<ParDominio>();
        dominioLista.addAll(iDominioService.listaDominioPorOrdenDominioAndValor());
    }

    public void inicializaDominio(){
        ParDominioPK pk = new ParDominioPK();
        dominioSelected = new ParDominio();
        dominioSelected.setParDominioPK(pk);
    }

    public void guardarDominio(){
        try{
        iDominioService.editarGuardarDominio(dominioSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        cargaDominiosLista();
        context.execute("dominioNuevo.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Dominio creado correctamente"));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El dominio no pudo crearse, intente más tarde"));
        }
    }

    public void editarDominio(){
        try{
        iDominioService.editarGuardarDominio(dominioSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dominioEdicion.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Dominio editado correctamente"));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El dominio no se pudo editar, intente más tarde"));
        }
    }

    // *** Getters y Setters *** //
    public IDominioService getiDominioService() {
        return iDominioService;
    }

    public void setiDominioService(IDominioService iDominioService) {
        this.iDominioService = iDominioService;
    }

    public ParDominio getDominioSelected() {
        return dominioSelected;
    }

    public void setDominioSelected(ParDominio dominioSelected) {
        this.dominioSelected = dominioSelected;
    }

    public List<ParDominio> getDominioLista() {
        return dominioLista;
    }

    public void setDominioLista(List<ParDominio> dominioLista) {
        this.dominioLista = dominioLista;
    }

    public List<ParDominio> getDominioFiltroLista() {
        return dominioFiltroLista;
    }

    public void setDominioFiltroLista(List<ParDominio> dominioFiltroLista) {
        this.dominioFiltroLista = dominioFiltroLista;
    }
}
