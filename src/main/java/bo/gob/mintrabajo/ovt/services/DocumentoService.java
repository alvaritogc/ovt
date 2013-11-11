package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Formatter;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService{

    private final DocumentoRepository documentoRepository;
    private final DocumentoEstadoRepository documentoEstadoRepository;
    private final PlanillaRepository planillaRepository;
    private final BinarioRepository binarioRepository;
    private final UnidadRepository unidadRepository;
    private final NumeracionRepository numeracionRepository;
    private final LogEstadoRepository logEstadoRepository;
    private final PlanillaDetalleRepository planillaDetalleRepository;
    private final IUtilsService utils;

    @Inject
    public DocumentoService(DocumentoRepository documentoRepository, DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository, BinarioRepository binarioRepository, UnidadRepository unidadRepository, NumeracionRepository numeracionRepository, LogEstadoRepository logEstadoRepository, PlanillaDetalleRepository planillaDetalleRepository, IUtilsService utils) {
        this.documentoRepository = documentoRepository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
        this.logEstadoRepository = logEstadoRepository;
        this.planillaDetalleRepository = planillaDetalleRepository;
        this.utils = utils;
    }

    //    @Override
    public List<DocDocumento> getAllDocumentos() {
        List<DocDocumento> lista;
        lista = documentoRepository.findAll();
        return lista;
    }

    //    @Override
    public DocDocumento save(DocDocumento documento) {
        DocDocumento entity;
        entity = documentoRepository.save(documento);
        return entity;
    }

    //    @Override
    public boolean delete(DocDocumento documento) {
        boolean deleted = false;
        documentoRepository.delete(documento);
        return deleted;
    }

    //    @Override
    public DocDocumento findById(Long id) {
        DocDocumento entity;
        entity = documentoRepository.findOne(id);
        return entity;
    }

    public List<DocDocumento> listarPorPersona(String idPersona) {
        return documentoRepository.findByIdPersona_IdPersona(idPersona);
    }

    
     @Override
    public DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario) {
//        DocLogEstado logEstado=new DocLogEstado();
//        logEstado.setIdDocumento(documento);
//        logEstado.setCodEstadoFinal(codEstadoFinal);
//        logEstado.setCodEstadoInicial(documento.getCodEstado());
//        logEstado.setRegistroBitacora(idUsuario);
//        Date date=new Date();
//        logEstado.setFechaBitacora(new Timestamp(date.getTime()));
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
        //
        documento.setCodEstado(codEstadoFinal);
        return documentoRepository.save(documento);
    }

    public void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios){
        //guarda documento
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", (short) 1));
        docDocumento=documentoRepository.save(docDocumento);

        //guarda planilla
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(utils.valorSecuencia("DOC_PLANILLA_SEC"));
        docPlanilla=planillaRepository.save(docPlanilla);


        //guarda binarios
        int idBinario= 1;
        for(DocBinario elementoBinario:listaBinarios){
            elementoBinario.setDocDocumento(docDocumento);
            elementoBinario.setDocBinarioPK(new DocBinarioPK(idBinario++, docDocumento.getIdDocumento()));
            binarioRepository.save(elementoBinario);
        }
    }



    public long actualizarNumeroDeOrden(String codDocumento, short version) {
        DocNumeracion numeracionBusqueda = new DocNumeracion(new DocNumeracionPK(codDocumento, version));
//        numeracionBusqueda.setCodDocumento(codDocumento);
//        numeracionBusqueda.setVersion(version);
        DocNumeracion numeracion;
        try {
            numeracion = numeracionRepository.findByExample(numeracionBusqueda, null, null, -1, -1).get(0);
        } catch (Exception e) {
            throw new RuntimeException("DocNumeracionEntity no encontrado");
        }
        String codNumeroS =numeracion.getDocNumeracionPK().getCodDocumento();
        String codNumero = "";
        for (int i = 2; i < codNumeroS.length(); i++) {
            codNumero = codNumero + codNumeroS.charAt(i);
        }
        Long numero=new Long(numeracion.getUltimoNumero()+1);
        //
        Formatter fmt = new Formatter();
        fmt.format("%07d", numero);
        String numeroFormato = fmt.toString();
        //
        String numeroSinVerificacion = "" + codNumero + numeroFormato;
        String numeroVerificacion = "";
        int contador = 2;
        for (int i = 0; i < numeroSinVerificacion.length(); i++) {
            numeroVerificacion = "" + contador + numeroVerificacion;
            contador++;
            if (contador > 7) {
                contador = 2;
            }
        }
        //
        Long sumatoria = new Long(0);
        //
        for (int i = 0; i < numeroSinVerificacion.length(); i++) {
            Long multiplicacion = (new Long("" + numeroSinVerificacion.charAt(i))) * (new Long("" + numeroVerificacion.charAt(i)));
            sumatoria = sumatoria + multiplicacion;
        }
        Long modulo = sumatoria % 11;
        Long verificacion = 11 - modulo;
        //
        numeracion.setUltimoNumero(numeracion.getUltimoNumero()+1);
        numeracionRepository.save(numeracion);
        //
        Formatter fmtVerificacion = new Formatter();
        fmtVerificacion.format("%02d", verificacion);
        return (new Long("" + codNumero + numeroFormato + fmtVerificacion.toString()));
    }
}