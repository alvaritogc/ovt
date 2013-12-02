package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.entities.ParParametrizacion;
import bo.gob.mintrabajo.ovt.repositories.DefinicionRepository;
import bo.gob.mintrabajo.ovt.repositories.ParametrizacionRepository;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;
import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("definicionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {

    private final DefinicionRepository definicionRepository;
    private final ParametrizacionRepository parametrizacionRepository;
    private final DocumentoEstadoRepository documentoEstadoRepository;

    @Inject
    public DefinicionService(DefinicionRepository definicionRepository,ParametrizacionRepository parametrizacionRepository,
            DocumentoEstadoRepository documentoEstadoRepository) {
        this.definicionRepository = definicionRepository;
        this.parametrizacionRepository= parametrizacionRepository;
        this.documentoEstadoRepository=documentoEstadoRepository;
    }

    public DocDefinicion guardarDefincion(DocDefinicion docDefinicion){
        return definicionRepository.save(docDefinicion);
    }
    
    @Override
    public boolean saveDefincion(DocDefinicion docDefinicion, String codDocumento, short version, String documentoEstado,
            String REGISTRO_BITACORA, boolean docEstado, String tipoGrupoDocumento){
        boolean guardado=false;
        DocDefinicion definicion = new DocDefinicion();
        DocDefinicionPK docDefinicionPK = new DocDefinicionPK();
        docDefinicionPK.setCodDocumento(codDocumento);
        docDefinicionPK.setVersion(version);
        
        if(definicionRepository.findOne(docDefinicionPK)==null){
            definicion.setDocDefinicionPK(docDefinicionPK);
        }else{
            definicion=definicionRepository.findOne(docDefinicionPK);
        }
        if(docEstado){
            definicion.setEstado("A");
        }else{
            definicion.setEstado("X");
        }
        definicion.setNombre(docDefinicion.getNombre());
        definicion.setTipoGrupoDocumento(tipoGrupoDocumento);
        definicion.setAlias(docDefinicion.getAlias());
        definicion.setCodEstado(documentoEstadoRepository.findOne(documentoEstado));
        definicion.setFechaBitacora(new Date());
        definicion.setRegistroBitacora(REGISTRO_BITACORA);
        
        try {
            definicionRepository.save(definicion);
            guardado=true;
        } catch (Exception e) {
            guardado=false;
        }
        return guardado;
    }

    @Override
    public DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK){
        return definicionRepository.findOne(docDefinicionPK);
    }

    @Override
    public List<DocDefinicion> listarDefiniciones() {
        return definicionRepository.findAll();
    }

    @Override
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

    @Override
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

    @Override
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
