package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMultaRango;
import java.math.BigDecimal;
import java.util.List;


public interface IMultaRangoService {
    public List<ParMultaRango> getAll();
    public ParMultaRango findById(Long id);
    public ParMultaRango buscarPorRango(Long idMulta,BigDecimal rango);
    
}
