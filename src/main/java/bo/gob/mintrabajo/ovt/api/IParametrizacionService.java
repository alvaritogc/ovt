package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/4/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IParametrizacionService {
    public ParParametrizacion obtenerParametro(String idParametro, String valor);
}
