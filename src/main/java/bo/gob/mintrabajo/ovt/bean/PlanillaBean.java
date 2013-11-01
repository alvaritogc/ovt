package bo.gob.mintrabajo.ovt.bean;

import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IPlanillaDetalleService;
import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
* User: Renato Velasquez
* Date: 10/25/13
*/
@ManagedBean
@ViewScoped
public class PlanillaBean {
    private int parametro;
//    private Dummy dummy = new Dummy();
//    private List<Dummy> dummyList = new ArrayList<Dummy>();



    @ManagedProperty(value = "#{planillaDetalleService}")
    private IPlanillaDetalleService iPlanillaDetalleService;

    @ManagedProperty(value = "#{planillaService}")
    private IPlanillaService iPlanillaService;

    @ManagedProperty(value = "#{utilsService}")
    private IUtilsService iUtilsService;

    @ManagedProperty(value = "#{documentoService}")
    private IDocumentoService iDocumentoService;

    @PostConstruct
    public void ini() {
        try {
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") == null) {
                parametro = 1;
                return;
            }
            if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p") != null|| !((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("a").equals("")) {
                parametro = Integer.valueOf(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("p"));
                return;
            }
        } catch (Exception e) {
            System.out.println("Error al cargar a: " + e.getMessage());
            parametro = 1;
            return;
        }
    }


    public void checkingFile() {

    }

    public int getParametro() {
        return parametro;
    }

    public void setParametro(int parametro) {
        this.parametro = parametro;
    }

    public IPlanillaDetalleService getiPlanillaDetalleService() {
        return iPlanillaDetalleService;
    }

    public void setiPlanillaDetalleService(IPlanillaDetalleService iPlanillaDetalleService) {
        this.iPlanillaDetalleService = iPlanillaDetalleService;
    }

    public IPlanillaService getiPlanillaService() {
        return iPlanillaService;
    }

    public void setiPlanillaService(IPlanillaService iPlanillaService) {
        this.iPlanillaService = iPlanillaService;
    }

    public IUtilsService getiUtilsService() {
        return iUtilsService;
    }

    public void setiUtilsService(IUtilsService iUtilsService) {
        this.iUtilsService = iUtilsService;
    }

    public IDocumentoService getiDocumentoService() {
        return iDocumentoService;
    }

    public void setiDocumentoService(IDocumentoService iDocumentoService) {
        this.iDocumentoService = iDocumentoService;
    }
}