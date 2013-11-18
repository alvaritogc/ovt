package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParMulta;
import java.math.BigDecimal;
import java.util.List;


public interface IMultaService {
    public List<ParMulta> getAll();
    public ParMulta findById(Long id);
    public ParMulta buscarTipoMulta(String tipoMulta);
    
}
