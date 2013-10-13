package login;

import bo.gob.mintrabajo.ovt.api.IDocumentoEstado;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.ITransicionService;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.DocTransicionEntity;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstadoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//

@ManagedBean
@ViewScoped
public class TransicionBean {
    private int idUsuario;
    private String idPersona;
    private String idEmpleador;

    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private static final Logger logger = LoggerFactory.getLogger(TransicionBean.class);

    @ManagedProperty(value = "#{transicionService}")
    private ITransicionService iTransicionService;
    @ManagedProperty(value = "#{documentoEstadoService}")
    private IDocumentoEstado iDocumentoEstado;
    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;


    private String codEstadoInicial;
    private String codDocumento;
    private int version;
    private int idDocumento;
    private List<DocTransicionEntity> listaTransicion;
    private List<ParDocumentoEstadoEntity> listaParDocumentoEstado;

    private ParDocumentoEstadoEntity parDocumentoEstadoEntity;
    private List<String> lista = new ArrayList<String>();
    private DocTransicionEntity docTransicionEntity;
    private DocTransicionEntity trans = new DocTransicionEntity();
    private ParDocumentoEstadoEntity descripCodIncial;
    private DocDocumentoEntity docDocumentoEntity= new DocDocumentoEntity();
    private String estadoNuevo;
    private int tamaño;
    private boolean esUsuarioInterno;

    @PostConstruct
    public void ini() {
        parDocumentoEstadoEntity = new ParDocumentoEstadoEntity();
        idDocumento = (Integer) session.getAttribute("idDocumento");

        BigDecimal bi = BigDecimal.valueOf(idDocumento);
        docDocumentoEntity=iDocumentoService.findById(bi);
        codEstadoInicial=(String) session.getAttribute("codEstadoInicial");
        codDocumento=(String) session.getAttribute("codDocumento");
        version=(Integer) session.getAttribute("version");
        descripCodIncial= iDocumentoEstado.retornaDocEstado(codEstadoInicial);


        System.out.println(codDocumento);
        System.out.println(version);
        System.out.println(codEstadoInicial);

        trans.setCodDocumento(codDocumento);
        trans.setVersion(version);
        trans.setCodEstadoInicial(codEstadoInicial);





        listaTransicion= new ArrayList<DocTransicionEntity>();
        
        //idUsuario=(Integer)session.getAttribute("idUsuario");
        idPersona=(String)session.getAttribute("idPersona");
        idEmpleador=(String)session.getAttribute("idEmpleador");
        if(idPersona!=null && idEmpleador!=null && idPersona.equals(idEmpleador)){
            esUsuarioInterno=false;
        }
        else{
            esUsuarioInterno=true;
        }
        cargar();
        
    }

    public void cargar() {
        listaTransicion=iTransicionService.listarTransicionesSiguientes(trans);
        lista.clear();

        for(DocTransicionEntity valor: listaTransicion){
            parDocumentoEstadoEntity = new ParDocumentoEstadoEntity();
            parDocumentoEstadoEntity = iDocumentoEstado.retornaDocEstado(valor.getCodEstadoFinal());
            lista.add(parDocumentoEstadoEntity.getDescripcion());
        }
        tamaño = lista.size();

    }

    public String guardaCambioEstado(){
        docDocumentoEntity.setCodEstado(iDocumentoEstado.retornaCodEstado(estadoNuevo).getCodEstado());
        iDocumentoService.save(docDocumentoEntity);
        tamaño = lista.size();

        return "irBienvenida";
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String irBienvenida(){
        tamaño = lista.size();
        return "irBienvenida";
    }

    public String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public ITransicionService getiTransicionService() {
        return iTransicionService;
    }

    public void setiTransicionService(ITransicionService iTransicionService) {
        this.iTransicionService = iTransicionService;
    }


    public DocTransicionEntity getDocTransicionEntity() {
        return docTransicionEntity;
    }

    public void setDocTransicionEntity(DocTransicionEntity docTransicionEntity) {
        this.docTransicionEntity = docTransicionEntity;
    }

    public List<DocTransicionEntity> getListaTransicion() {
        return listaTransicion;
    }

    public void setListaTransicion(List<DocTransicionEntity> listaTransicion) {
        this.listaTransicion = listaTransicion;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public IDocumentoEstado getiDocumentoEstado() {
        return iDocumentoEstado;
    }

    public void setiDocumentoEstado(IDocumentoEstado iDocumentoEstado) {
        this.iDocumentoEstado = iDocumentoEstado;
    }

    public ParDocumentoEstadoEntity getDescripCodIncial() {
        return descripCodIncial;
    }

    public void setDescripCodIncial(ParDocumentoEstadoEntity descripCodIncial) {
        this.descripCodIncial = descripCodIncial;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }

    public boolean isEsUsuarioInterno() {
        return esUsuarioInterno;
    }

    public void setEsUsuarioInterno(boolean esUsuarioInterno) {
        this.esUsuarioInterno = esUsuarioInterno;
    }
}
