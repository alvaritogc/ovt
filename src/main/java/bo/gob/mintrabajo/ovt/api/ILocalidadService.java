package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParLocalidad;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;

import java.util.List;

/**
 * @author pc01
 */
public interface ILocalidadService {
    public List<ParLocalidad> getAllLocalidades();

    Long localidadSecuencia(String nombreSecuencia);

    public ParLocalidad findById(String idLocalidad);

    public List<ParLocalidad> listarDepartamentos();
    public boolean guardaLocalidad(ParLocalidad parLocalidad, String tipoLocalidad,
            String REGISTRO_BITACORA, String tipoNodo, String idPadre, String idHijo);
    public boolean eliminarLocalidad(String idLoc);
}
