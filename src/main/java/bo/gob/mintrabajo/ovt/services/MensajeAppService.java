package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IMensajeAppService;
import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;
import bo.gob.mintrabajo.ovt.repositories.MensajeAppRepository;

import javax.ejb.TransactionAttribute;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("mensajeAppService")
@TransactionAttribute
public class MensajeAppService implements IMensajeAppService{

    private final MensajeAppRepository repository;

    @Inject
    public MensajeAppService(MensajeAppRepository repository) {
        this.repository = repository;
    }
    
    
    @Override
    public ParMensajeApp findById(Long id) {
        return repository.findOne(id);
    }
    
    @Override
    public ParMensajeApp BuscarPorId(Long id) {
        return repository.findByAttribute("idMensajeApp", id, -1, -1).get(0);
        //return repository.findOne(id);
    }
    
    @Override
    public ParMensajeApp buscarPorRecurso(String etiqueta) {
        List<ParMensajeApp> lista=null;
        try{
            lista=repository.buscarPorRecursoFecha(etiqueta,new Date());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al buscar ParMensajeApp.");
        }
        if(lista.size()==0){
            throw new RuntimeException("Error al encontrar ParMensajeApp");
        }
        return lista.get(0);
    }
    
     @Override
    public ParMensajeApp buscarPorRecurso(Long idRecurso) {
        List<ParMensajeApp> lista=null;
        try{
            lista=repository.buscarPorRecurso(idRecurso);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al buscar ParMensajeApp.");
        }
        if(lista.size()==0){
            throw new RuntimeException("Error al encontrar ParMensajeApp");
        }
        return lista.get(0);
    }
     
    @Override
    public List<ParMensajeApp> listarPorRecurso(Long idRecurso) {
        return repository.buscarPorRecurso(idRecurso);
    }
    
}
