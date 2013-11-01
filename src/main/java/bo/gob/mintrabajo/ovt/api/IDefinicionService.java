package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;

public interface IDefinicionService {
    DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK);
  
}
