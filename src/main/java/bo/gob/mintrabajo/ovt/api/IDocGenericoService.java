package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocGenerico;

public interface IDocGenericoService {
    public DocGenerico findById(Long id);
    public DocGenerico buscarPorDocumento(Long idDocumento);
    public DocGenerico modificar(DocGenerico docGenerico, Long idDocumento);
    
}
