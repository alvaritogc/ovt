package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import bo.gob.mintrabajo.ovt.entities.ParCalendarioPK;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;

import java.util.List;

public interface IObligacionCalendarioService {
    public List<ParObligacionCalendario> listaObligacionCalendario();
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario,
                                                            ParCalendarioPK parCalendarioPK,String REGISTRO_BITACORA, String parObligacion, boolean evento);
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public List<ParObligacionCalendario> listaObligacionCalendarioPorObligacion(String codObligacion);
    public List<ParObligacionCalendario> listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();

    //List<ParObligacionCalendario> listaObligacionCalendarioPorGestion(String gestionActual);

    public List<ParObligacionCalendario> listaObligacionCalendarioPorGestion(String gestionActual);
    public ParObligacionCalendario findById(Long id);
    public ParObligacionCalendario buscarPorPlatriALaFecha();

}
