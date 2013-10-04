package bo.gob.mintrabajo.ovt.bean.Planillas;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * User: Renato Velasquez.
 * Date: 04-10-13
 */
@ManagedBean
public class PresentacionPlanillasBean {
    @ManagedProperty(value = "#{binarioService}")
    private IBinarioService binarioService;

    private DocBinarioEntity docBinarioEntity = new DocBinarioEntity();

    public void guardarBinario(){
        System.out.println("guardando binario");
        binarioService.guardarBinario(docBinarioEntity);
    }

    public IBinarioService getBinarioService() {
        return binarioService;
    }

    public void setBinarioService(IBinarioService binarioService) {
        this.binarioService = binarioService;
    }

    public DocBinarioEntity getDocBinarioEntity() {
        return docBinarioEntity;
    }

    public void setDocBinarioEntity(DocBinarioEntity docBinarioEntity) {
        this.docBinarioEntity = docBinarioEntity;
    }
}
