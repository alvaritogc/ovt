package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParCalendarioPK;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;

import java.util.Date;
import java.util.List;

public interface IObligacionCalendarioService {
    public ParObligacionCalendario buscarAguinaldoPorGestion(String gestion);
    public List<ParObligacionCalendario> listaObligacionCalendario();
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario,
                                                            ParCalendarioPK parCalendarioPK,String REGISTRO_BITACORA, String parObligacion, boolean evento);
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public List<ParObligacionCalendario> listaObligacionCalendarioPorObligacion(String codObligacion);
    public List<ParObligacionCalendario> listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();

    //List<ParObligacionCalendario> listaObligacionCalendarioPorGestion(String gestionActual);
    public ParObligacionCalendario findById(Long id);
    public ParObligacionCalendario buscarPorPlatriALaFecha();
    public List<ParObligacionCalendario> buscarPorPlatriPorFecha(Date fechaActual);
    List<ParObligacionCalendario> listarPlanillaAguiPorFechaHastaFechaPlazo(Date fechaActual);
    List<ParObligacionCalendario> listarPlanillaAguiPorFechaHastaFechaPlazo2(Date fechaActual);
}
