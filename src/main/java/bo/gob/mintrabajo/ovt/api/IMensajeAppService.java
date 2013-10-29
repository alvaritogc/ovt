package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;

public interface IMensajeAppService {
    public ParMensajeApp buscarPorRecurso(String etiqueta);
    
}
