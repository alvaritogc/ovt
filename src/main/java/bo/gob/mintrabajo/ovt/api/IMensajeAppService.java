package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;

import java.util.List;

public interface IMensajeAppService {
    public ParMensajeApp findById(Long id);

    public ParMensajeApp BuscarPorId(Long id);

    public List<ParMensajeApp> listarPorRecursoYFechaActual(Long idRecurso);

    public ParMensajeApp buscarPorRecurso(Long idRecurso);

    public List<ParMensajeApp> listarPorRecurso(Long idRecurso);

    public ParMensajeApp guardar(ParMensajeApp mensajeApp, Long idRecurso, String bitacoraSession);

    public boolean delete(Long idMensajeApp);
}
