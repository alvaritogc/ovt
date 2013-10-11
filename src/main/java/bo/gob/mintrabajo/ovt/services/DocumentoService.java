package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.DocNumeracionEntity;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstadoEntity;
import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;
import bo.gob.mintrabajo.ovt.repositories.DocumentoRepository;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;
import bo.gob.mintrabajo.ovt.repositories.NumeracionRepository;

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
    private final UnidadRepository unidadRepository;
    private final NumeracionRepository numeracionRepository;

    @Inject
    public DocumentoService(DocumentoRepository repository,DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository,NumeracionRepository numeracionRepository,UnidadRepository unidadRepository) {
        this.repository = repository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository=numeracionRepository;
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
    public DocDocumentoEntity guardar(DocDocumentoEntity documento, DocPlanillaEntity docPlanillaEntity) {
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

            //TODO renato PLANILLA
            docPlanillaEntity.setIdDocumento(entity.getIdDocumento());
            planillaRepository.save(docPlanillaEntity);

        return entity;
    }
    
    @Override
    public List<DocDocumentoEntity> listarPorPersona(String idPersona) {
        List<DocDocumentoEntity> lista;
        try {
            lista = repository.findByAttribute("idPersona", idPersona, -1, -1);
            for(DocDocumentoEntity documento:lista){
                ParDocumentoEstadoEntity documentoEstado=documentoEstadoRepository.findByAttribute("codEstado", documento.getCodEstado(), -1, -1).get(0);
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
        //System.out.println("codNumero: " + codNumero);
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
        System.out.println("numeroSinVerificacion:"+numeroSinVerificacion);
        System.out.println("numeroVerificacion   :"+numeroVerificacion);
        for (int i = 0; i < numeroSinVerificacion.length(); i++) {
            //System.out.println("i " + i + ": " + numeroSinVerificacion.charAt(i));
            Long multiplicacion = (new Long("" + numeroSinVerificacion.charAt(i))) * (new Long("" + numeroVerificacion.charAt(i)));
            //System.out.println("Multi: " + numeroSinVerificacion.charAt(i) + "*" + numeroVerificacion.charAt(i));
            sumatoria = sumatoria + multiplicacion;
            //System.out.println("sumatoria " + i + ": " + sumatoria);
        }
        Long modulo = sumatoria % 11;
        //System.out.println("modulo:" + modulo);
        Long verificacion = 11 - modulo;
        //System.out.println("verificacion:" + verificacion);
        //
        //System.out.println("===============4");
        numeracion.setUltimoNumero(numeracion.getUltimoNumero()+1);
        //numeracionBusqueda.set
        numeracionRepository.save(numeracion);
        //
        Long nuevoNumero = new Long("" + codNumero + numeroFormato + verificacion);
        //System.out.println("===============5");
        return nuevoNumero;
    }
    
}
