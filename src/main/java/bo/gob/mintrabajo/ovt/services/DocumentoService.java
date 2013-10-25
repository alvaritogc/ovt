package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService{

    private final DocumentoRepository repository;
    private final DocumentoEstadoRepository documentoEstadoRepository;
    private final PlanillaRepository planillaRepository;
    private final BinarioRepository binarioRepository;
    private final UnidadRepository unidadRepository;
    private final NumeracionRepository numeracionRepository;
    private final LogEstadoRepository logEstadoRepository;
    private final IUtilsService utils;

    @Inject
    public DocumentoService(DocumentoRepository repository, DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository, BinarioRepository binarioRepository, UnidadRepository unidadRepository, NumeracionRepository numeracionRepository, IUtilsService utils,LogEstadoRepository logEstadoRepository) {
        this.repository = repository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
        this.utils = utils;
        this.logEstadoRepository=logEstadoRepository;
    }

//    @Override
    public List<DocDocumento> getAllDocumentos() {
        List<DocDocumento> lista;
        lista = repository.findAll();
        return lista;
    }
    
//    @Override
    public DocDocumento save(DocDocumento documento) {
        DocDocumento entity;
        entity = repository.save(documento);
        return entity;
    }

//    @Override
    public boolean delete(DocDocumento documento) {
        boolean deleted = false;
        repository.delete(documento);
        return deleted;
    }

//    @Override
    public DocDocumento findById(Long id) {
        DocDocumento entity;
        entity = repository.findOne(id);
        return entity;
    }
    
//    @Override
    public List<DocDocumento> listarPorPersona(String idPersona) {
        List<DocDocumento> lista;
        lista = repository.findByAttribute("idPersona", idPersona, -1, -1);
        return lista;
    }
    
}
