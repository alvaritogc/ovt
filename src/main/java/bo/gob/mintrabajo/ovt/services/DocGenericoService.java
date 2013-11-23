
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.Util.Util;
import bo.gob.mintrabajo.ovt.api.IDocGenericoService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import bo.gob.mintrabajo.ovt.entities.DocGenerico;
import bo.gob.mintrabajo.ovt.entities.UsrUsuario;
import bo.gob.mintrabajo.ovt.repositories.DocGenericoRepository;
import bo.gob.mintrabajo.ovt.repositories.DocumentoRepository;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;
import bo.gob.mintrabajo.ovt.repositories.RolRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author VHTC
 */
@Named("docGenericoService")
@TransactionAttribute
public class DocGenericoService implements IDocGenericoService{
    private static final Logger logger = LoggerFactory.getLogger(DocGenericoService.class);
    private final DocGenericoRepository docGenericoRepository;
    private final DocumentoRepository documentoRepository;


    @Inject
    public DocGenericoService(DocGenericoRepository docGenericoRepository,DocumentoRepository documentoRepository) {
        this.docGenericoRepository = docGenericoRepository;
        this.documentoRepository=documentoRepository;
    }
    
    @Override
    public DocGenerico findById(Long id){
        return docGenericoRepository.findOne(id);
    }
    
    @Override
    public DocGenerico buscarPorDocumento(Long idDocumento){
        return docGenericoRepository.buscarPorDocumento(idDocumento);
    }
    
    public DocGenerico modificar(DocGenerico docGenerico, Long idDocumento){
        DocDocumento documento=documentoRepository.findOne(idDocumento);
        docGenerico.setIdDocumento(documento);
        docGenerico.setParCalendario(null);
        return docGenericoRepository.save(docGenerico);
    }
    
}
