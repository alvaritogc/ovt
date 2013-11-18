package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import java.util.List;

public interface IRecursoService {
    public UsrRecurso findById(Long id);
    public List<UsrRecurso> buscarPorUsuario(Long idUsuario);
    public List<UsrRecurso> listarPorTipoRecurso(String tipoRecurso);
    public List<UsrRecurso> obtenerTodosRecursoLista();
    public List<UsrRecurso> obtenerRecursoEnUsuarioRecurso(Long idUsuario);

}
