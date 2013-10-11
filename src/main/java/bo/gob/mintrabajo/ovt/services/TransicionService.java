package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ITransicionService;
import bo.gob.mintrabajo.ovt.entities.DocTransicionEntity;
import bo.gob.mintrabajo.ovt.repositories.TransicionRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/10/13
 */

@Named("transicionService")
@TransactionAttribute
public class TransicionService implements ITransicionService {

    private final TransicionRepository transicionRepository;
    private List <DocTransicionEntity> listaTransicion= new ArrayList<DocTransicionEntity>();

    @Inject
    public TransicionService(TransicionRepository transicionRepository) {
        this.transicionRepository = transicionRepository;
    }

    @Override
    public List<DocTransicionEntity> listarTransicionesSiguientes(DocTransicionEntity trans){



//        listaTransicion= transicionRepository.findByExample(trans, trans, null, -1, -1);
        listaTransicion= transicionRepository.findByCodDocumentoAndVersionAndCodEstadoInicial(trans.getCodDocumento(), trans.getVersion(), trans.getCodEstadoInicial());

        for(DocTransicionEntity docTransicionEntity:listaTransicion){
            System.out.println(docTransicionEntity);
        }


        return listaTransicion;
    }
}
