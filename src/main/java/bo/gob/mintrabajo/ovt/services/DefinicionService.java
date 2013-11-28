package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.repositories.DefinicionRepository;
import bo.gob.mintrabajo.ovt.repositories.ParametrizacionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("definicionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {

    private final DefinicionRepository definicionRepository;
    private final ParametrizacionRepository parametrizacionRepository;

    @Inject
    public DefinicionService(DefinicionRepository definicionRepository,ParametrizacionRepository parametrizacionRepository) {
        this.definicionRepository = definicionRepository;
        this.parametrizacionRepository= parametrizacionRepository;
    }

    public DocDefinicion guardarDefincion(DocDefinicion docDefinicion){
        return definicionRepository.save(docDefinicion);
    }

    public DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK){
        return definicionRepository.findOne(docDefinicionPK);
    }

    public List<DocDefinicion> listarDefiniciones() {
        return definicionRepository.findAll();
    }

    public boolean eliminarDefinicion(DocDefinicion docDefinicion){
        boolean deleted = false;
        try {
            definicionRepository.delete(docDefinicion);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<DocDefinicion> listaPorOrdenDocDefinicion(){
        List<DocDefinicion> lista;
        try{
            lista=definicionRepository.listaPorOrdenDocDefinicion();
        }
        catch (Exception e){
            e.printStackTrace();
            lista=null;
        }
        return lista;
    }

    public List<DocDefinicion> listaCodDocumento(String cod, Short ver){
        List<DocDefinicion> lista;
        try{
            lista=definicionRepository.listaCodDocumento(cod, ver);
        }
        catch (Exception e){
            e.printStackTrace();
            lista=null;
        }
        return lista;
    }

    @Override
    public List<DocDefinicion> listaVersionesPorCodDocumento(String codDocumento) {
        List<DocDefinicion> lista;
        lista = definicionRepository.listaVersionesPorCodDocumento(codDocumento);
        return lista;
    }
    
    @Override
    public DocDefinicion obtenerDefinicion(String codigo, short vesion) {
        DocDefinicion entity;
        entity = definicionRepository.obtenerDocDefinicion(codigo, vesion);
        return entity;
    }
    
    @Override
    public DocDefinicion buscarActivoPorParametro(String parametroDocDefinicion) {
        ParParametrizacion parParametrizacion =parametrizacionRepository.obtenerParametro("DOCUMENTOS", parametroDocDefinicion);
        return definicionRepository.buscarPorCodDocumentoActivo(parParametrizacion.getDescripcion());
    }
    
    public DocDefinicion buscarActivoPorCodDocumento(String codDocumento) {
        return definicionRepository.buscarPorCodDocumentoActivo(codDocumento);
    }
}
