package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ITransicionService;
import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicionPK;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;
import bo.gob.mintrabajo.ovt.repositories.TransicionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/10/13
 */

@Named("transicionService")
@TransactionAttribute
public class TransicionService implements ITransicionService {

    private final TransicionRepository transicionRepository;
    private final DominioRepository dominioRepository;
    private List <DocTransicion> listaTransicion= new ArrayList<DocTransicion>();

    @Inject
    public TransicionService(TransicionRepository transicionRepository, DominioRepository dominioRepository) {
        this.transicionRepository = transicionRepository;
        this.dominioRepository=dominioRepository;
    }

    @Override
    public List<DocTransicion> listaTransicion(){
        List<DocTransicion> lista;
        try {
            lista = transicionRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    public DocTransicion guardar(DocTransicion docTransicion){
        docTransicion.setFechaBitacora(new Date());
        docTransicion.setRegistroBitacora("OVT");
        transicionRepository.save(docTransicion);
        return null;
    }
    @Override
    public boolean saveTransicion(
            DocTransicion transicion,
            boolean estadoTransicion,
            DocDefinicion definicion,
            ParDocumentoEstado parDocumentoEstado1,
            ParDocumentoEstado parDocumentoEstado2,
            String REGISTRO_BITACORA,             
            boolean evento, DocTransicionPK docTransicionPK){ 
        
        DocTransicion docTransicion= new DocTransicion();
        docTransicion.setDocDefinicion(definicion);
        docTransicion.setParDocumentoEstado(parDocumentoEstado1);
        docTransicion.setParDocumentoEstado1(parDocumentoEstado2);
        
        if(!evento && transicionRepository.findOne(docTransicionPK)!= null){
            System.out.println("== esntrando a error");
            return false;
        }else{
            System.out.println("== esntrando a sin error");
        }

        if(!evento){
            docTransicion.setDocTransicionPK(docTransicionPK);
        }else{
            docTransicion = transicionRepository.findOne(docTransicionPK);
        }

        if(estadoTransicion==true){
            docTransicion.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "A").getParDominioPK().getValor());
        }
        else{
            docTransicion.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "X").getParDominioPK().getValor());
        }

        docTransicion.setFechaBitacora(new Date());
        docTransicion.setRegistroBitacora(REGISTRO_BITACORA);
        try {
            transicionRepository.save(docTransicion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteTransicion(DocTransicion transicion){
        boolean deleted = false;
        try {
            DocTransicion dt=transicionRepository.findOne(transicion.getDocTransicionPK());
            transicionRepository.delete(dt);
            transicionRepository.flush();
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
   
}
