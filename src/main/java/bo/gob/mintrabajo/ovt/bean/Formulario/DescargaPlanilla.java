package bo.gob.mintrabajo.ovt.bean.Formulario;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@ViewScoped
public class DescargaPlanilla {

    private String strPathXLS = "/home/pc01/Escritorio/ministerio/ovt/src/main/webapp/archivos/LC1010-10 Formato de planilla industrial.xlsx";

    public void descargarExcel() {
        try {
            File ficheroXLS = new File(strPathXLS);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;

            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = "application/vnd.ms-excel";
                //String contentType = "application/pdf";
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();

                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                System.out.println("\nDescargado\n");
                ctx.responseComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void descargarPlanillaIndustrial(){
        descargarArchivoExcel("LC1010-10 Formato de planilla industrial.xlsx");
    }
    public void descargarPlanillaComercial(){
        descargarArchivoExcel("LC1010-20 Formato de planilla comercial.xlsx");
    }
    public void descargarPlanillaReducido(){
        descargarArchivoExcel("LC1010-30 Formato de planilla reducido(MyPEs).xlsx");
    }
    
    public void descargarPdfPlanillaIndustrial(){
        descargarArchivoExcel("LC1010-10 Formato de planilla industrial.xlsx");
    }
    public void descargarPdfPlanillaComercial(){
        descargarArchivoExcel("LC1010-20 Formato de planilla comercial.xlsx");
    }
    public void descargarPdfPlanillaReducido(){
        descargarArchivoExcel("LC1010-30 Formato de planilla reducido(MyPEs).xlsx");
    }
    
    
    
    
    public void descargarArchivoExcel(String nombreArchivo) {
        String pathServidorWar = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/").replace('\\', '/');
        String direccionWebInf = "/archivos/";
        String pathArchivoXML = pathServidorWar + direccionWebInf + nombreArchivo;
        //
        String pathArchivo="/home/pc01/Escritorio/ministerio/ovt/src/main/webapp/archivos/"+nombreArchivo;
        pathArchivo=pathArchivoXML;
        System.out.println("pathArchivoXML: "+pathArchivoXML);
        try {
            File ficheroXLS = new File(pathArchivoXML);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;

            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = "application/vnd.ms-excel";
                //String contentType = "application/pdf";
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();

                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                System.out.println("\nDescargado\n"+nombreArchivo);
                ctx.responseComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
