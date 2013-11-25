
package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParDominio;
import bo.gob.mintrabajo.ovt.entities.ParDominioPK;

import java.util.List;


public interface IDominioService {
    public ParDominio save(ParDominio dominio);
    public List<ParDominio> obtenerItemsDominio(String dominio);
    public ParDominio obtenerDominioPorNombreYValor(String dominio,String valor);
    public List<ParDominio> obtenerDominioLista();
    public List<ParDominio> listaDominioPorOrdenDominioAndValor();
    public ParDominio editarGuardarDominio(ParDominio parDominio);
    public List<ParDominio> obtenerDominioPorNombrePadreYValorPadre(String dominioP,String valorP);
    public List<ParDominio> obtenerDominioPorNombreDistintoValor(String dominio,String valor);
    public ParDominio obtenerDominioPorValor(String valor);
    public List<ParDominio> obtenerDominioPorDominioPadreOrderByValor(ParDominioPK parDominioPK);
}