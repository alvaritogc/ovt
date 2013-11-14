package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeContenido;
import java.util.List;

public interface IMensajeContenidoService {
    public ParMensajeContenido findById(Long id);
    public List<ParMensajeContenido> findByAll();
    public ParMensajeContenido save(ParMensajeContenido mensajeContenido);
    public boolean delete(Long idMensajeContenido);
    public List<ParMensajeContenido> listarPorMensajeApp(Long idMensajeApp);
}
