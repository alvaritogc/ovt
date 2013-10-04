package login;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import bo.gob.mintrabajo.ovt.api.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
//
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import org.primefaces.event.FileUploadEvent;  
import org.primefaces.model.UploadedFile;  

@ManagedBean(name = "inicioBean")
@RequestScoped
//@ViewScoped
public class InicioBean {

    private int idPlanilla;
    private List<SelectItem> listaPlanillas;
    private List<SelectItem> listaPeriodosPendientes;

    @PostConstruct
    public void ini() {
        System.out.println("===================================================");
        System.out.println("===================================================");
        System.out.println("InicioBean.init");
        System.out.println("===================================================");
        System.out.println("===================================================");
        cargar();
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cargar() {
        idPlanilla=0;
        listaPlanillas = new ArrayList<SelectItem>();
        for (int i = 1; i <= 10; i++) {
            listaPlanillas.add(new SelectItem((Object) i, ("Planilla " + i)));
            System.out.println("adicionado " + i);
        }
        listaPeriodosPendientes = new ArrayList<SelectItem>();
    }

    public void cargarPeridosPendientes() {
        listaPeriodosPendientes = new ArrayList<SelectItem>();
        for (int i = 0; i < 10; i++) {
            listaPeriodosPendientes.add(new SelectItem(i, ("Periodo pendiente " + i)));
            System.out.println("adicionados " + i);
        }
    }

    public List<SelectItem> getListaPlanillas() {
        return listaPlanillas;
    }

    public void setListaPlanillas(List<SelectItem> listaPlanillas) {
        this.listaPlanillas = listaPlanillas;
    }

    public List<SelectItem> getListaPeriodosPendientes() {
        return listaPeriodosPendientes;
    }

    public void setListaPeriodosPendientes(List<SelectItem> listaPeriodosPendientes) {
        this.listaPeriodosPendientes = listaPeriodosPendientes;
    }

    public int getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(int idPlanilla) {
        this.idPlanilla = idPlanilla;
    }
}
