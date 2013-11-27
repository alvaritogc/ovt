
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeBinario;
import java.util.List;


public interface IMensajeBinarioService {
    public ParMensajeBinario findById(Long id) ;
    public List<ParMensajeBinario> findByAll();
    public ParMensajeBinario buscarPorMensajeContenido(Long idMensajeContenido);
    
    
}
