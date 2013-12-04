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
    public ParMensajeContenido save(ParMensajeContenido mensajeContenido) {
        System.out.println("Metadata service: " + mensajeContenido.getMetadata());

        //mensajeContenido.setIdMensajeContenido(new Long(repository.findAll().size()+1));
        mensajeContenido.setIdMensajeContenido(utils.valorSecuencia("PAR_MENSAJE_CONTENIDO_SEC"));
        mensajeContenido.setFechaBitacora(new Date());
        mensajeContenido.setRegistroBitacora("OVT");
        mensajeContenido = repository.save(mensajeContenido);
        //
        ParMensajeBinario parMensajeBinario = new ParMensajeBinario();
        parMensajeBinario.setIdMensajeBinario(utils.valorSecuencia("PAR_MENSAJE_BINARIO_SEC"));
        parMensajeBinario.setBinario(mensajeContenido.getBinario());
        parMensajeBinario.setIdMensajeContenido(mensajeContenido);
        parMensajeBinario.setFechaBitacora(new Date());
        parMensajeBinario.setRegistroBitacora("OVt");
        mensajeBinarioRepository.save(parMensajeBinario);

        return mensajeContenido;
    }

    @Override
    public boolean delete(Long idMensajeContenido) {
        try {
            //ParMensajeBinario parMensajeBinario=mensajeBinarioRepository.buscarPorMensajeContenido(idMensajeContenido);
            //mensajeBinarioRepository.delete(parMensajeBinario.getIdMensajeBinario());
            //
            repository.delete(idMensajeContenido);
            //repository.flush();
            //
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
