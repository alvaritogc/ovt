package bo.gob.mintrabajo.ovt.bean.Planillas;

import bo.gob.mintrabajo.ovt.api.IBinarioService;
import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.ByteArrayInputStream;
import java.sql.*;

import java.util.Random;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
@ManagedBean
public class PresentacionPlanillasBean {

    @ManagedProperty(value = "#{binService}")
    private IBinarioService iBinarioService;
    private DocBinarioEntity docBinarioEntity = new DocBinarioEntity();

    public void guardarBinario(FileUploadEvent event) {
        System.out.println("entrando a guardarBinario...............................");
        UploadedFile file = event.getFile();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);
            Random r= new Random();
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.50.7:1521:DESA", "roe", "roe");
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("insert into DYMMY values(?,?)");
            ps.setInt(1, r.nextInt(100));
            ps.setBinaryStream(2, new ByteArrayInputStream(file.getContents()), (int) file.getSize());
            ps.executeUpdate();
            con.commit();
            con.close();
            System.out.println("archivo añadido exitosamente");
        }
        catch(Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
        }
    }

    public void mensaje(){
        System.out.println("mensajeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }

    public IBinarioService getiBinarioService() {
        return iBinarioService;
    }

    public void setiBinarioService(IBinarioService iBinarioService) {
        this.iBinarioService = iBinarioService;
    }

    public DocBinarioEntity getDocBinarioEntity() {
        return docBinarioEntity;
    }

    public void setDocBinarioEntity(DocBinarioEntity docBinarioEntity) {
        this.docBinarioEntity = docBinarioEntity;
    }
}
