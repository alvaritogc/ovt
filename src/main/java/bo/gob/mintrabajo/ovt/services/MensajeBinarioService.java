package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.api.IMensajeAppService;
import bo.gob.mintrabajo.ovt.api.IMensajeBinarioService;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("mensajeBinarioService")
@TransactionAttribute
public class MensajeBinarioService implements IMensajeBinarioService {

    private final MensajeBinarioRepository mensajeBinarioRepository;
    private final IUtilsService utils;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public MensajeBinarioService(IUtilsService utils, MensajeBinarioRepository mensajeBinarioRepository) {
        this.mensajeBinarioRepository = mensajeBinarioRepository;
        this.utils = utils;
    }

    @Override
    public ParMensajeBinario findById(Long id) {
        return mensajeBinarioRepository.findOne(id);
    }

    @Override
    public List<ParMensajeBinario> findByAll() {
        return mensajeBinarioRepository.findAll();
    }

    @Override
    public ParMensajeBinario buscarPorMensajeContenido(Long idMensajeContenido) {
        ParMensajeBinario mb = mensajeBinarioRepository.buscarPorMensajeContenido(idMensajeContenido);
        entityManager.detach(mb);
        return mb;
    }

}
