package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDefinicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import bo.gob.mintrabajo.ovt.repositories.DefinicionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("definicionService")
@TransactionAttribute
public class DefinicionService implements IDefinicionService {

    private final DefinicionRepository definicionRepository;

    @Inject
    public DefinicionService(DefinicionRepository definicionRepository) {
        this.definicionRepository = definicionRepository;
    }

    public DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK){
        return definicionRepository.findOne(docDefinicionPK);
    }
    

//    @Override
    public DocDefinicion guardarDefincion(DocDefinicion docDefinicion) {
        return definicionRepository.save(docDefinicion);
    }

//    @Override
    public long getSize() {
        return definicionRepository.count();
    }

    @Override
    public List<DocDefinicion> getAllDefinicion() {
        List<DocDefinicion> lista;
        lista = definicionRepository.findAll();
        return lista;
    }

//    @Override
    public DocDefinicion save(DocDefinicion definicion) {
        DocDefinicion entity;
        entity = definicionRepository.save(definicion);
        return entity;
    }

//    @Override
    public boolean delete(DocDefinicion definicion) {
        boolean deleted = false;
        definicionRepository.delete(definicion);

        return deleted;
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
}
