package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import java.util.List;

public interface IRecursoService {
    public List<UsrRecurso> buscarPorUsuario(Long idUsuario);
}
