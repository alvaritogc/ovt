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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
    private ParDominio dominioSelected;

    private static final Logger logger = LoggerFactory.getLogger(ParametrizacionBean.class);

    @PostConstruct
    public void cargaDominiosLista(){
        //dominioLista = iDominioService.obtenerDominioLista();
        dominioLista = iDominioService.listaDominioPorOrdenDominioAndValor();
    }

    public void inicializaDominio(){
        ParDominioPK pk = new ParDominioPK();
        dominioSelected = new ParDominio();
        dominioSelected.setParDominioPK(pk);
    }

    public void guardarDominio(){
        iDominioService.editarGuardarDominio(dominioSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        cargaDominiosLista();
        context.execute("dominioNuevo.hide();");
    }

    public void editarDominio(){
        iDominioService.editarGuardarDominio(dominioSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dominioEdicion.hide();");
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
}
