package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.entities.DocPlanilla;
import bo.gob.mintrabajo.ovt.repositories.BinarioRepository;
import bo.gob.mintrabajo.ovt.repositories.EntidadRepository;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Named("planillaService")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PlanillaService implements IPlanillaService {

    private final PlanillaRepository planillaRepository;
    private final EntidadRepository entidadRepository;
    private final BinarioRepository binarioRepository;
    private HashMap<String,Object> map = new HashMap<String,Object>();

    @Inject
    public PlanillaService(PlanillaRepository planillaRepository, EntidadRepository entidadRepository, BinarioRepository binarioRepository) {
        this.planillaRepository = planillaRepository;
        this.entidadRepository = entidadRepository;
        this.binarioRepository = binarioRepository;
    }

//    @Override
    public void guardar(DocPlanilla objeto){
        objeto.setIdPlanilla(new Long(planillaRepository.findAll().size() + 1));
        planillaRepository.save(objeto);
    }

    public void generateReport()throws ClassNotFoundException, IOException,JRException {
        List<String> lista= new ArrayList<String>();
        lista.add("asd");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        //Obteniendo el origen del jasper
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream("/pages/reportes/formularioLC1010V1.jasper");
        //Obteniendo la salida del pdf
        FacesContext.getCurrentInstance().responseComplete();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //generando el tipo de documento a mostrar
        response.setContentType("application/pdf");
        //generando el JasperRunManager con los datos
        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, new JRBeanCollectionDataSource(lista));

        //terminando el proceso
        servletOutputStream.flush();
        servletOutputStream.close();

        facesContext.responseComplete();
    }


    private static void redirecionarReporte (String rutaReporte) throws IOException {
        FacesContext facesContext=FacesContext.getCurrentInstance();
        HttpServletResponse response=(HttpServletResponse) facesContext.getExternalContext().getResponse();

        File file=new File(rutaReporte);
        BufferedInputStream input=null;
        BufferedOutputStream output=null;
        try{

            input=new BufferedInputStream(new FileInputStream(file),10240);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            output=new BufferedOutputStream(response.getOutputStream(),10240);

            byte[] buffer=new byte[10240];
            int length;

            while((length=input.read(buffer))>0){
                output.write(buffer,0,length);
            }
            output.flush();
        }finally{
            close(output);
            close(input);
        }

        facesContext.responseComplete();
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public DocPlanilla buscarPorDocumento(Long idDocumento){
        List<DocPlanilla> list=planillaRepository.buscarPorDocumento(idDocumento);
        if(list==null || list.size()==0){
            throw new RuntimeException("No se encontro el documento");
        }
        return list.get(0);
    }

    public DocPlanilla findById(Long id){
        return planillaRepository.findOne(id);
    }

    public List<DocPlanilla> listarporPersona(String idPersona){
        return planillaRepository.findByIdDocumento_PerUnidad_PerPersona_IdPersona(idPersona);
    }

    public List<DocPlanilla> listarPlanillasParaRectificar(String idPersona, String codDocumento) {
        return planillaRepository.listarPlanillasParaRectificar(idPersona, codDocumento);
    }

    public List<DocPlanilla> listarPlanillasTrimestralesParaRectificar(String idPersona, Long idUnidad, Date fechaHasta, Date fechaPlazo2) {
        return planillaRepository.listarPlanillasTrimestralesParaRectificar(idPersona, idUnidad, fechaHasta, fechaPlazo2);
    }
}