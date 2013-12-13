package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeContenido;

import java.util.List;

public interface IMensajeContenidoService {
    public ParMensajeContenido findById(Long id);

    public List<ParMensajeContenido> findByAll();

    public ParMensajeContenido save(ParMensajeContenido mensajeContenido, byte[] binario, String bitacoraSession);

    public boolean delete(Long idMensajeContenido);

    public List<ParMensajeContenido> listarPorMensajeApp(Long idMensajeApp);

    public boolean tieneImagenes(Long idMensajeApp);
    public ParMensajeContenido modificarBinario(Long idMensajeContenido, String archivo, String metadata,byte[] binario,String bitacoraSession);
}
