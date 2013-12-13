package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IMensajeAppService;
import bo.gob.mintrabajo.ovt.api.IMensajeContenidoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.DocBinario;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.entities.ParMensajeApp;
import bo.gob.mintrabajo.ovt.entities.ParMensajeBinario;
import bo.gob.mintrabajo.ovt.entities.ParMensajeContenido;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;
import bo.gob.mintrabajo.ovt.repositories.MensajeAppRepository;
import bo.gob.mintrabajo.ovt.repositories.MensajeBinarioRepository;
import bo.gob.mintrabajo.ovt.repositories.MensajeContenidoRepository;

import javax.ejb.TransactionAttribute;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Named("mensajeContenidoService")
@TransactionAttribute
public class MensajeContenidoService implements IMensajeContenidoService {

    private final MensajeContenidoRepository repository;
    private final MensajeBinarioRepository mensajeBinarioRepository;
    private final IUtilsService utils;

    @Inject
    public MensajeContenidoService(MensajeContenidoRepository repository, IUtilsService utils, MensajeBinarioRepository mensajeBinarioRepository) {
        this.repository = repository;
        this.mensajeBinarioRepository = mensajeBinarioRepository;
        this.utils = utils;
    }

    @Override
    public ParMensajeContenido findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ParMensajeContenido> findByAll() {
        return repository.findAll();
    }

    @Override
    public List<ParMensajeContenido> listarPorMensajeApp(Long idMensajeApp) {
        return repository.listarPorMensajeApp(idMensajeApp);
    }

    @Override
    public ParMensajeContenido save(ParMensajeContenido mensajeContenido, byte[] binario, String bitacoraSession) {
        System.out.println("Metadata service: " + mensajeContenido.getMetadata());

        ParMensajeBinario parMensajeBinario;
        if (mensajeContenido.getIdMensajeContenido() == null) {
            mensajeContenido.setIdMensajeContenido(utils.valorSecuencia("PAR_MENSAJE_CONTENIDO_SEC"));
            parMensajeBinario = new ParMensajeBinario();
        } else {
            parMensajeBinario = null;
        }

        mensajeContenido.setFechaBitacora(new Date());
        mensajeContenido.setRegistroBitacora(bitacoraSession);
        mensajeContenido = repository.save(mensajeContenido);
        //
        if (parMensajeBinario != null) {
            parMensajeBinario.setIdMensajeBinario(utils.valorSecuencia("PAR_MENSAJE_BINARIO_SEC"));
            parMensajeBinario.setBinario(binario);
            parMensajeBinario.setIdMensajeContenido(mensajeContenido);
            parMensajeBinario.setFechaBitacora(new Date());
            parMensajeBinario.setRegistroBitacora(bitacoraSession);
            mensajeBinarioRepository.save(parMensajeBinario);
        }

        return mensajeContenido;
    }
    @Override
    public ParMensajeContenido modificarBinario(Long idMensajeContenido, String archivo, String metadata,byte[] binario, String bitacoraSession){
        ParMensajeContenido parMensajeContenido=repository.findOne(idMensajeContenido);
        parMensajeContenido.setArchivo(archivo);
        parMensajeContenido.setMetadata(metadata);
        parMensajeContenido=repository.save(parMensajeContenido);
        repository.flush();
        
        ParMensajeBinario parMensajeBinario=mensajeBinarioRepository.buscarPorMensajeContenido(idMensajeContenido);
        parMensajeBinario.setBinario(binario);
        parMensajeBinario.setIdMensajeContenido(parMensajeContenido);
        parMensajeBinario.setRegistroBitacora(bitacoraSession);
        parMensajeBinario=mensajeBinarioRepository.save(parMensajeBinario);
        mensajeBinarioRepository.flush();
        
        return parMensajeContenido;
    }

    @Override
    public boolean delete(Long idMensajeContenido) {
        try {
            ParMensajeBinario parMensajeBinario = mensajeBinarioRepository.buscarPorMensajeContenido(idMensajeContenido);
            if (parMensajeBinario != null) {
                mensajeBinarioRepository.delete(parMensajeBinario.getIdMensajeBinario());
                mensajeBinarioRepository.flush();
            }
            repository.delete(idMensajeContenido);
            repository.flush();
            //
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tieneImagenes(Long idMensajeApp) {
        List<ParMensajeContenido> listaMensajeContenido = repository.tieneImagenes(idMensajeApp);
        if (listaMensajeContenido != null && listaMensajeContenido.size() > 0) {
            return true;
        }
        return false;
    }
}
