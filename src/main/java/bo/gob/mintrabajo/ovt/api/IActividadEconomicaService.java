
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerDireccion;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;

import java.math.BigDecimal;
import java.util.List;


public interface IActividadEconomicaService {
    public ParActividadEconomica save(ParActividadEconomica actividadEconomica);
    public List<BigDecimal> obtenerActividadEconomicaParaRegistro();
    public ParActividadEconomica findByIdActividadEconomica(Long idActividadEconomica);
    public ParActividadEconomica obtieneActividadEconomicaPorId(Long idActividadEconomica);
    public boolean guardarActividadEconomica(ParActividadEconomica actividadEconomica, boolean estadoActividadEconomica,
            String REGISTRO_BITACORA, String tipoNodo, Long idPadre, Long idHijo);
    public boolean delete(Long idActividadEconimica);
}