package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    
    @Override
    public List<DocDocumento> listarPorPersona(String idPersona) {
        List<DocDocumento> lista;
        lista = repository.buscarPorPersona(idPersona);
        return lista;
    }

    public List<DocDocumento> listarPorNumero(String idPersona){
        return repository.findByAttribute("idPersona", idPersona, -1,-1);
    }

    public void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla){
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", 1)); *************************************** REVISAR !!!!!
        docDocumento.setNumeroDocumento(new BigInteger("11111")); //****************************************************
        docDocumento=repository.save(docDocumento);
        int idBinario= 1;
        for(DocBinario elementoBinario:listaBinarios){
            DocBinarioPK docBinarioPK = new DocBinarioPK();
            docBinarioPK.setIdDocumento(docDocumento.getIdDocumento());
            docBinarioPK.setIdBinario(idBinario++);
            binarioRepository.save(elementoBinario);
        }
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(new Long(utils.planillaSecuencia("DOC_PLANILLA_SEC")));
        planillaRepository.save(docPlanilla);
    }


//    public DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario) {
//        //
//        DocLogEstado logEstado=new DocLogEstado();
//        logEstado.setIdDocumento(documento);
//        logEstado.setCodEstadoFinal(codEstadoFinal);
//        logEstado.setCodEstadoInicial(documento.getCodEstado());
//        logEstado.setRegistroBitacora(idUsuario);
//        Date date=new Date();
//        logEstado.setFechaBitacora(new Timestamp(date.getTime()));
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
//        //
//        documento.setCodEstado(codEstadoFinal);
//        return repository.save(documento);
//    }
    
//    public DocDocumento guardarCambioEstado(DocDocumento documento, DocLogEstado logEstado) {
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
//        return repository.save(documento);
//    }
    
}
