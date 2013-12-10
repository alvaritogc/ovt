package bo.gob.mintrabajo.ovt.bean.parametrizacion;

import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacionPK;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/8/13
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ParametrizacionBean {

    @ManagedProperty(value = "#{parametrizacionService}")
    private IParametrizacionService iParametrizacionService;

    private ParParametrizacion parametrizacionSelected;
    private List<ParParametrizacion> parametrizacionLista;

    private static final Logger logger = LoggerFactory.getLogger(ParametrizacionBean.class);

    @PostConstruct
    public void cargaParametricasLista(){
        //parametrizacionLista = iParametrizacionService.obtenerParametroLista();
        parametrizacionLista = iParametrizacionService.listaPorOrdenParametroValor();
    }

    public void guardarParametro() {
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + "metodo guardarParametro()");

        if (iParametrizacionService.obtenerParametro(parametrizacionSelected.getParParametrizacionPK().getIdParametro(), parametrizacionSelected.getParParametrizacionPK().getValor()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Ya existe una paramétrica con estos mismos valores de Parámetro y Código"));
        } else {
            try {
                ParParametrizacion pp = iParametrizacionService.editarGuardarParametro(parametrizacionSelected);
                cargaParametricasLista();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("parametroNuevo.hide();");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Parámetro creado correctamente"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El parámetro no pudo crearse, intente más tarde"));
            }
        }
    }

    public void editarParametro(){
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + "metodo editarParametro()");
        try{
        ParParametrizacion pp = iParametrizacionService.editarGuardarParametro(parametrizacionSelected);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("parametroEdicion.hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Parámetro editado correctamente"));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "El parámetro no se pudo editar, intente más tarde"));
        }
    }

    public void inicializaParametro(){
        ParParametrizacionPK pk = new ParParametrizacionPK();
        parametrizacionSelected = new ParParametrizacion();
        parametrizacionSelected.setParParametrizacionPK(pk);
    }

    // *** Getter and Setter ***//

    public ParParametrizacion getParametrizacionSelected() {
        return parametrizacionSelected;
    }

    public void setParametrizacionSelected(ParParametrizacion parametrizacionSelected) {
        this.parametrizacionSelected = parametrizacionSelected;
    }

    public IParametrizacionService getiParametrizacionService() {
        return iParametrizacionService;
    }

    public void setiParametrizacionService(IParametrizacionService iParametrizacionService) {
        this.iParametrizacionService = iParametrizacionService;
    }

    public List<ParParametrizacion> getParametrizacionLista() {
        return parametrizacionLista;
    }

    public void setParametrizacionLista(List<ParParametrizacion> parametrizacionLista) {
        this.parametrizacionLista = parametrizacionLista;
    }
}
