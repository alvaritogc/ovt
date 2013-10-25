package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;
import java.util.List;

public interface IPersonaService {
    PerPersona findById(String id);
    List<PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial);
}
