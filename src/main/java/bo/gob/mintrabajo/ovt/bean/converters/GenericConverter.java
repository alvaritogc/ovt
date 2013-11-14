package bo.gob.mintrabajo.ovt.bean.converters;

import bo.gob.mintrabajo.ovt.Util.AbstractEntity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/11/13
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
@FacesConverter(value = "genericConverter", forClass = AbstractEntity.class)
public class GenericConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null) {
            return this.getAttributesFrom(component).get(submittedValue);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && ! "".equals(value)) {
            AbstractEntity entity = (AbstractEntity) value;

            if (entity.getId() != null) {
                this.addAttribute(component, entity);

                if (entity.getId() != null) {
                    return String.valueOf(entity.getId());
                }
                return (String) value;
            }
        }
        return "";
    }

    private void addAttribute(UIComponent component, AbstractEntity o) {
        this.getAttributesFrom(component).put(o.getId().toString(), o);
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
