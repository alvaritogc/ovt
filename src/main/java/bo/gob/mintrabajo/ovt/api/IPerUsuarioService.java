package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerUsuario;

import java.util.List;

/**
 *
 * @author pc01
 */
public interface IPerUsuarioService {
    public PerUsuario obtenerPorNITyEmail(String NIT, String email);

}
