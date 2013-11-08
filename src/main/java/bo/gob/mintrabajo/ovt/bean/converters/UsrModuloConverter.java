package bo.gob.mintrabajo.ovt.bean.converters;

import bo.gob.mintrabajo.ovt.api.IUsrModuloService;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/8/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
@FacesConverter("moduloConverter")
public class UsrModuloConverter implements Converter {

    @ManagedProperty(value = "#{usrModuloService}")
    private IUsrModuloService iUsrModuloService;
    private List<UsrModulo> usrModuloConverterLista;
    public static List<UsrModulo> usrModuloStaticLista;


    @PostConstruct
    public void cargarModulo(){
        usrModuloConverterLista = iUsrModuloService.obtenerUsrModuloLista();
        usrModuloStaticLista = usrModuloConverterLista;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                String id = submittedValue;
                for (UsrModulo p : usrModuloStaticLista) {
                    if (p.getIdModulo().equals(id)) {
                        return p;
                    }
                }
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid object"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return ((UsrModulo) value).getIdModulo();
        }
    }

    public List<UsrModulo> getUsrModuloConverterLista() {
        return usrModuloConverterLista;
    }

    public void setUsrModuloConverterLista(List<UsrModulo> usrModuloConverterLista) {
        this.usrModuloConverterLista = usrModuloConverterLista;
    }

    public IUsrModuloService getiUsrModuloService() {
        return iUsrModuloService;
    }

    public void setiUsrModuloService(IUsrModuloService iUsrModuloService) {
        this.iUsrModuloService = iUsrModuloService;
    }
}
