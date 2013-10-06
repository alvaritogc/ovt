package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.repositories.DocumentoRepository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService{

    private final DocumentoRepository repository;

    @Inject
    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
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
    
     //@Override
    public DocDocumentoEntity guardar(DocDocumentoEntity documento) {
        if(documento!=null){
            throw new RuntimeException("Error en el documento");
        }
        //documento.setIdPersona
        //documento.setIdUni 
        //documento.setCodDocumento
        //documento.setVersion
        //documento.set
        documento.setNumeroDocumento(documento.getIdDocumento());
        Date date= new java.util.Date();
        documento.setFechaDocumento(new Timestamp(date.getTime()));
        //documento.setiddocumentoREf
        documento.setCodEstado("000");
        documento.setFechaReferenca(null);
        documento.setTipoMedioRegistro("OFVIR");
        documento.setFechaBitacora(new Timestamp(date.getTime()));
        documento.setRegistroBitacora("");
        //
        DocDocumentoEntity entity;
        try {
            entity = repository.save(documento);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
            throw new RuntimeException("Error al guardar el documento");
        }
        return entity;
    }
    
    public List<DocDocumentoEntity> listarPorPersona(String idPersona) {
        List<DocDocumentoEntity> lista;
        try {
            lista = repository.findByAttribute("idPersona", idPersona, -1, -1);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
}
