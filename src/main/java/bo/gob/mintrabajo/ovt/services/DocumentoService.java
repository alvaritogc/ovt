package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstadoEntity;
import bo.gob.mintrabajo.ovt.repositories.DocumentoEstadoRepository;
import bo.gob.mintrabajo.ovt.repositories.DocumentoRepository;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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

    @Inject
    public DocumentoService(DocumentoRepository repository,DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository) {
        this.repository = repository;
        this.documentoEstadoRepository=documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
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

        documento.setNumeroDocumento(repository.findAll().size()+10101000001L);
//        documento.setNumeroDocumento(repository.findAll().size()+1);
        Date date= new java.util.Date();
        documento.setFechaDocumento(new Timestamp(date.getTime()));
        //
        documento.setIdDocumentoRef(null);
        //
        documento.setCodEstado("000");
        documento.setFechaReferenca(null);
        documento.setTipoMedioRegistro("OFVIR");
        documento.setFechaBitacora(new Timestamp(date.getTime()));
        //
        documento.setIdEstadoDocumento("1");
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
    
}
