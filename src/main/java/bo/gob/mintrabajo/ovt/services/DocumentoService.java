package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
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

    @Inject
    public DocumentoService(DocumentoRepository repository, DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository, BinarioRepository binarioRepository, UnidadRepository unidadRepository, NumeracionRepository numeracionRepository) {
        this.repository = repository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
    }

    @Override
    public List<DocDocumentoEntity> getAllDocumentos() {
        List<DocDocumentoEntity> lista;

        try {
            lista = repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public DocDocumentoEntity save(DocDocumentoEntity documento) {
        DocDocumentoEntity entity;
        try {
            entity = repository.save(documento);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public boolean delete(DocDocumentoEntity documento) {
        boolean deleted = false;
        try {
            repository.delete(documento);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public DocDocumentoEntity findById(BigDecimal id) {
        DocDocumentoEntity entity;

        try {
            entity = repository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }
    
    @Override
    public DocDocumentoEntity guardar(DocDocumentoEntity documento) {
        if(documento==null){
            System.out.println("Error en el documento");
            throw new RuntimeException("Error en el documento");
        }
        //
        documento.setIdDocumento(repository.findAll().size()+1);
        //
        documento.setCodDocumento("LC1010");
        documento.setVersion(1);
        //documento.setNumeroDocumento(1);
        //
        //
        //
        //
        
        //Long a=actualizarNumeroDeOrden("LC1010", 1);
        documento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", 1));
        //
        //
        //documento.setNumeroDocumento(repository.findAll().size()+10101000001L);
        //
//        documento.setNumeroDocumento(repository.findAll().size()+1);
        Date date= new java.util.Date();
        documento.setFechaDocumento(new Timestamp(date.getTime()));
        //
        documento.setIdDocumentoRef(null);
        //
        documento.setCodEstado("110");
        documento.setFechaReferenca(null);
        documento.setTipoMedioRegistro("OFVIR");
        documento.setFechaBitacora(new Timestamp(date.getTime()));
        //
//        documento.setIdEstadoDocumento("1");
        //
        DocDocumentoEntity entity;
        //
        //System.out.println(""+documento.toString());
        entity = repository.save(documento);

        return entity;
    }
    
    @Override
    public List<DocDocumentoEntity> listarPorPersona(String idPersona) {
        List<DocDocumentoEntity> lista;
        try {
            lista = repository.findByAttribute("idPersona", idPersona, -1, -1);
            for(DocDocumentoEntity documento:lista){
                ParDocumentoEstadoEntity documentoEstado=documentoEstadoRepository.findByAttribute("codEstado", documento.getCodEstado(), -1, -1).get(0);
                documento.setDocumentoEstado(documentoEstado);
                documento.setDocumentoEstadoDescripcion(documentoEstado.getDescripcion());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public DocPlanillaEntity retornaPlanilla(int idDocumento){

//        idDocumento = 1;


        return planillaRepository.findByAttribute("idDocumento",idDocumento,-1,-1).get(0);
    }
    @Override
    public PerUnidadEntity retornaUnidad(String idPersona){

//        idDocumento = 1;


        return unidadRepository.findByAttribute("idPersona",idPersona,-1,-1).get(0);
    }
    
    @Override
    public Long actualizarNumeroDeOrden(String codDocumento, Integer version) {
        DocNumeracionEntity numeracionBusqueda = new DocNumeracionEntity();
        numeracionBusqueda.setCodDocumento(codDocumento);
        numeracionBusqueda.setVersion(version);
        DocNumeracionEntity numeracion;
        try {
            numeracion = numeracionRepository.findByExample(numeracionBusqueda, null, null, -1, -1).get(0);
        } catch (Exception e) {
            throw new RuntimeException("DocNumeracionEntity no encontrado");
        }
        String codNumeroS =numeracion.getCodDocumento();
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
        Long nuevoNumero = new Long("" + codNumero + numeroFormato + fmtVerificacion.toString());
        return nuevoNumero;
    }

    public void guardaDocumentoBinarioPlanilla(DocDocumentoEntity docDocumentoEntity, List<DocBinarioEntity> listaBinarios, DocPlanillaEntity docPlanillaEntity){
        docDocumentoEntity.setIdDocumento(repository.findAll().size()+1);
        docDocumentoEntity.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", 1));
        docDocumentoEntity=repository.save(docDocumentoEntity);
        int a= 1;
        for(DocBinarioEntity elementoBinario:listaBinarios){
            elementoBinario.setIdDocumento(docDocumentoEntity.getIdDocumento());
            elementoBinario.setIdBinario(a++);
            binarioRepository.save(elementoBinario);
        }
        docPlanillaEntity.setIdDocumento(docDocumentoEntity.getIdDocumento());
        docPlanillaEntity.setIdPlanilla(planillaRepository.findAll().size()+1);
        planillaRepository.save(docPlanillaEntity);
    }
}
