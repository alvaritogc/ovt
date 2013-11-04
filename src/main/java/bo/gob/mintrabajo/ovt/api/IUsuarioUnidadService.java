package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad;

/**
 *
 * @author pc01
 */
public interface IUsuarioUnidadService {
    public PerUsuarioUnidad obtenerPorNITyEmail(String NIT, String email);

}
