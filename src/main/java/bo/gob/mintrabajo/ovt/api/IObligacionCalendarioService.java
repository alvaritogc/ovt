package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendarioEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/10/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IObligacionCalendarioService {
    public List<ParObligacionCalendarioEntity> obtenerObligacionCalendario();
    public String obtenerGestionActual();
}
