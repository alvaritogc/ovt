package bo.gob.mintrabajo.ovt.bean.recurso;

import bo.gob.mintrabajo.ovt.api.IRecursoService;
import bo.gob.mintrabajo.ovt.entities.UsrRecurso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/12/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RecursoBean {

    @ManagedProperty(value = "#{recursoService}")
    private IRecursoService iRecursoService;

    private List<UsrRecurso> recursoLista;

    private static final Logger logger = LoggerFactory.getLogger(RecursoBean.class);

    @PostConstruct
    public void cargaRecursoLista(){
        //recursoLista = iRecursoService.getAllRecursos();
    }



    // **** Getters && Setters **** //
    public IRecursoService getiRecursoService() {
        return iRecursoService;
    }

    public void setiRecursoService(IRecursoService iRecursoService) {
        this.iRecursoService = iRecursoService;
    }

    public List<UsrRecurso> getRecursoLista() {
        return recursoLista;
    }

    public void setRecursoLista(List<UsrRecurso> recursoLista) {
        this.recursoLista = recursoLista;
    }
}
