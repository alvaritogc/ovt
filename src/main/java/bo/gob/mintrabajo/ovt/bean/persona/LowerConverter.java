package bo.gob.mintrabajo.ovt.bean.persona;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: aquiroz
 * Date: 11/9/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
@FacesConverter("lowerConverter")
public class LowerConverter implements Converter {

    public Object getAsObject(FacesContext fx,UIComponent component,
                              String value)throws ConverterException {
        if(value==null)
            return null;

        Locale locale=fx.getExternalContext().getRequestLocale();
        return value.toUpperCase(locale);
    }

    public String getAsString(FacesContext cx, UIComponent component,
                              Object value) throws ConverterException {
        if(value == null) {
            return null;
        }
        return value.toString();
    }
}
