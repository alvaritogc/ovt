package bo.gob.mintrabajo.ovt.bean.parametrizacion;

import bo.gob.mintrabajo.ovt.api.IParametrizacionService;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacionPK;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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

    private static final Logger logger = LoggerFactory.getLogger(ParametrizacionBean.class);


    public List<ParParametrizacion> getParametricasLista(){
        return iParametrizacionService.obtenerParametroLista();
    }

    public void guardarParametro(){
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + "metodo guardarParametro()");
        ParParametrizacion pp = iParametrizacionService.editarGuardarParametro(parametrizacionSelected);
        getParametricasLista();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("parametroNuevo.hide();");
    }

    public void editarParametro(){
        logger.info("Ingresando a la clase " + getClass().getSimpleName() + "metodo editarParametro()");
        ParParametrizacion pp = iParametrizacionService.editarGuardarParametro(parametrizacionSelected);
        getParametricasLista();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("parametroEdicion.hide();");
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
}
