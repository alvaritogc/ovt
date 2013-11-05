package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;

public interface IMensajeAppService {
    public ParMensajeApp findById(Long id);
    public ParMensajeApp BuscarPorId(Long id);
    public ParMensajeApp buscarPorRecurso(String etiqueta);
    public ParMensajeApp buscarPorRecurso(Long idRecurso);
    
}
