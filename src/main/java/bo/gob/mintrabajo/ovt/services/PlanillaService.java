package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPlanillaService;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.repositories.PlanillaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("planillaService")
@TransactionAttribute
public class PlanillaService implements IPlanillaService {
    private final PlanillaRepository planillaRepository;

    @Inject
    public PlanillaService(PlanillaRepository planillaRepository) {
        this.planillaRepository = planillaRepository;
    }

    public void guardar(DocPlanillaEntity objeto){
        planillaRepository.save(objeto);
    }


    public void generaReporte(BigDecimal idDocPlanillaEntity){
        DocPlanillaEntity docPlanillaEntity = new DocPlanillaEntity();
        docPlanillaEntity = planillaRepository.findOne(idDocPlanillaEntity);

        Map map = new HashMap();
        map.put("mesPresentacion",docPlanillaEntity.getPeriodo());
        map.put("nroAsegurados",docPlanillaEntity.getNroAsegCaja());
        map.put("montoAportadoAsegurados",docPlanillaEntity.getMontoAsegCaja());
        map.put("gestorSalud",docPlanillaEntity.getIdEntidadSalud()); //----------------
        map.put("nroAfiliados",docPlanillaEntity.getNroAsegAfp());
        map.put("montoAportadoAfiliados",docPlanillaEntity.getMontoAsegAfp());
        map.put("haberBasico",docPlanillaEntity.getHaberBasico());
        map.put("bonoAntiguedad",docPlanillaEntity.getBonoAntiguedad());
        map.put("bonoProduccion",docPlanillaEntity.getBonoProduccion());
        map.put("subsidioFrontera",docPlanillaEntity.getSubsidioFrontera());
        map.put("laborExtraordinaria",docPlanillaEntity.getLaborExtra());
        map.put("otrosBono",docPlanillaEntity.getOtrosBonos());
        map.put("aporteAFP",docPlanillaEntity.getAporteAfp());
        map.put("rcIVA",docPlanillaEntity.getRciva());
        map.put("otrosDescuentos",docPlanillaEntity.getOtrosDescuentos());
        map.put("totalMujeres",docPlanillaEntity.getNroM());
        map.put("totalVarones",docPlanillaEntity.getNroH());
        map.put("mujeresJubiladas",docPlanillaEntity.getNroJubiladosM());
        map.put("varonesJubilados",docPlanillaEntity.getNroJubiladosH());
        map.put("mujeresExtranjeras",docPlanillaEntity.getNroExtranjerosM());
        map.put("varonesExtranjeros",docPlanillaEntity.getNroExtranjerosH());
        map.put("mujeresDiscapacidad",docPlanillaEntity.getNroDiscapacidadM());
        map.put("varonesDiscapacidad",docPlanillaEntity.getNroDiscapacidadH());
        map.put("mujeresContratadas",docPlanillaEntity.getNroContratadosM());
        map.put("varonesContratados",docPlanillaEntity.getNroContratadosH());
        map.put("mujeresRetiradas",docPlanillaEntity.getNroRetiradosM());
        map.put("varonesRetirados",docPlanillaEntity.getNroRetiradosH());
        map.put("totalAccidentes",docPlanillaEntity.getNroAccidentes());
        map.put("accidentesMuerte",docPlanillaEntity.getNroMuertes());
        map.put("enfermedadesTrabajos",docPlanillaEntity.getNroEnfermedades());
        map.put("email",docPlanillaEntity.getIdEntidadBanco()); //----------------
        map.put("diaDeposito",docPlanillaEntity.getFechaOperacion().getDay());
        map.put("mesDeposito",docPlanillaEntity.getFechaOperacion().getMonth());
        map.put("anioDeposito",docPlanillaEntity.getFechaOperacion().getYear());
        map.put("montoDeposito",docPlanillaEntity.getMontoOperacion());
        map.put("nroComprobante",docPlanillaEntity.getNumOperacion());

        //TODO mandar al jasper


        try {
            generateReport();
        } catch (Exception e) {
            System.out.println("ERROR al generar el reporte: "+e.getMessage());
        }
    }

     public void generateReport()throws IOException {

//         FacesContext facesContext = FacesContext.getCurrentInstance();
//         HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//         //Obteniendo el origen del jasper
//         InputStream reportStream = facesContext.getExternalContext().getResourceAsStream("/pages/administracion/instrumentos/tramite/hojaRuta/reporte/RHreporteEnvio.jasper");
//         //Obteniendo la salida del pdf
//         FacesContext.getCurrentInstance().responseComplete();
//         ServletOutputStream servletOutputStream = response.getOutputStream();
//         //generando el tipo de documento a mostrar
//         response.setContentType("application/pdf");
//         //generando el JasperRunManager con los datos
////         JasperRunManager.runReportToPdfStream(reportStream,servletOutputStream, map, new JRBeanCollectionDataSource(listaGuiaEnvio));
//
//
//         JasperRunManager.runReportToPdfStream(reportStream,servletOutputStream, map);
//
//        //terminando el proceso
//         servletOutputStream.flush();
//         servletOutputStream.close();
     }

}

//        package com.comibol.administracion.instrumentos.tramite.hojaRuta.command;
//
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.model.GuiaEnvio;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.model.ReporteEnvio;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.model.ReporteGuiaEnvio;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.dao.ReporteGuiaEnvioDAO;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.dao.ReporteEnvioDAO;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.dao.hibernate.ReporteGuiaEnvioDAOHibernate;
//        import
//        com.comibol.administracion.instrumentos.tramite.hojaRuta.dao.hibernate.ReporteEnvioDAOHibernate;
//
//
//        import com.comibolcore.core.command.DataAccessCommand;
//        import com.comibolcore.core.exception.BusinessException;
//
//        import java.io.ByteArrayOutputStream;
//        import java.io.File;
//        import java.io.FileOutputStream;
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.io.OutputStream;
//        import java.io.Serializable;
//        import java.sql.SQLException;
//        import java.text.SimpleDateFormat;
//        import java.util.ArrayList;
//        import java.util.Date;
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//        import javax.faces.context.ExternalContext;
//        import javax.faces.context.FacesContext;
//        import javax.servlet.ServletContext;
//        import javax.servlet.ServletOutputStream;
//        import javax.servlet.http.HttpServletResponse;
//
//        import net.sf.jasperreports.engine.JRException;
//        import net.sf.jasperreports.engine.JRExporter;
//        import net.sf.jasperreports.engine.JRExporterParameter;
//        import net.sf.jasperreports.engine.JasperExportManager;
//        import net.sf.jasperreports.engine.JasperFillManager;
//        import net.sf.jasperreports.engine.JasperPrint;
//        import net.sf.jasperreports.engine.JasperReport;
//        import net.sf.jasperreports.engine.JasperRunManager;
//        import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//        import net.sf.jasperreports.engine.export.JRPdfExporter;
//        import net.sf.jasperreports.engine.export.JRRtfExporter;
//        import net.sf.jasperreports.engine.util.JRLoader;
//
//        import org.slf4j.Logger;
//        import org.slf4j.LoggerFactory;

//        public class GenerarReporteEnvioCommand extends DataAccessCommand
//                implements Serializable{
//            private static final long serialVersionUID = 1L;
//            private static final Logger logger =
//                    LoggerFactory.getLogger(GenerarReporteEnvioCommand.class);
//            private Long idUsuario;
//            private Map<String, Object> parametros = new HashMap<String, Object>();
//            private List<GuiaEnvio> listaGuiaEnvio=new ArrayList<GuiaEnvio>();
//            //Ej el contenido de pathReport sera lo siguiente
//            //pathReport = /home/pc01/Escritorio/scriniaLocal/WebContent/ si
//            esta en linux
//            //pathReport = c:/ ... /scriniaLocal/WebContent/ si esta en windows
//            ServletContext servletContext = (ServletContext)
//                    FacesContext.getCurrentInstance().getExternalContext().getContext();
//            String pathReport = (String) servletContext.getRealPath("/");
//
//            /* Parametros de entrada */
//            private ReporteEnvio reporteEnvio;
//            private String nroReporteEnvio;
//            /* Parametros de salida */
//            private String rutaPdf;
//
//            /**
//             * Carga los valores de entrada del comando clase.
//             *
//             * @param idUsuario
//             * @param reporteEnvio
//             */
//            public GenerarReporteEnvioCommand(Long idUsuario, ReporteEnvio
//                    reporteEnvio) {
//                this.idUsuario = idUsuario;
//                this.reporteEnvio=reporteEnvio;
//            }
//
//            /**
//             * Método que se llama cuando el comando clase es ejecutado.
//             *
//             * @throws BusinessException
//             */
//            public void execute() throws BusinessException {
//                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                Date fecha = new Date();
//                //String fechaActual=""+format.format(fecha);
//                String fechaActual=""+format.format(reporteEnvio.getFechaEnvio());
//
//                parametros.put("unccOrigen",
//                        reporteEnvio.getUnccOrigen().getNombre());
//                parametros.put("unccDestino",
//                        reporteEnvio.getUnccDestino().getNombre());
//                parametros.put("nombreEmpresa",
//                        reporteEnvio.getNombreEmpresaTransporte());
//                parametros.put("fechaEnvio", fechaActual);
//                parametros.put("direccionEmpresa",
//                        reporteEnvio.getDireccionEmpresaTransporte());
//                parametros.put("telefonoEmpresa",
//                        reporteEnvio.getTelefonoEmpresaTransporte());
//                parametros.put("referenciasEmpresa",
//                        reporteEnvio.getReferenciaEmpresaTransporte());
//                int nro=Integer.parseInt(reporteEnvio.getNroReporte());
//                nroReporteEnvio=""+nro;
//                parametros.put("numeroReporte", nroReporteEnvio);
//                parametros.put("rutaOrigen", pathReport);
//
//                ReporteGuiaEnvioDAO a = (ReporteGuiaEnvioDAOHibernate)
//                        daoFactory.getObjectDAO(ReporteGuiaEnvioDAOHibernate.class);
//                List<ReporteGuiaEnvio>
//                        listaReporteGuiaEnvio=a.listar(reporteEnvio.getIdReporteEnvio());
//                for(ReporteGuiaEnvio reporteGuiaEnvio:listaReporteGuiaEnvio)
//                {
//                    listaGuiaEnvio.add(reporteGuiaEnvio.getGuiaEnvio());
//                }
//         /*
//         if(listaReporteGuiaEnvio.isEmpty())
//         {
//             //llenando la tabla con datos ficticions si no cuenta con datos
//             for(int i=1;i<=100;i++)
//             {
//                 GuiaEnvio guiaEnvio= new GuiaEnvio();
//                 guiaEnvio.setNroGuia("GuiaEnvio : "+i);
//                 listaGuiaEnvio.add(guiaEnvio);
//             }
//         }*/
//
//                try {
//                    generateReport();
//                } catch (Exception e) {
//                    logger.error("ERROR al generar el reporte: "+e.getMessage());
//                }
//
//            }
//
//            public void generateReport()throws ClassNotFoundException,
//                    SQLException, IOException,JRException {
//                //GENERANDO LAS RUTAS
//                String rutaJasper=pathReport+
//                        "/resources/reports/HRreporteEnvio.jasper";
//                String nombreReportePdf="RHreporteEnvio"+nroReporteEnvio;
//                rutaPdf=pathReport+"/pages/administracion/instrumentos/tramite/hojaRuta/reporte/temp/"+nombreReportePdf+".pdf";
//
//                //GENERANDO EL PDF
//                File archivo = new File(rutaJasper);
//                JasperReport reporte = (JasperReport)
//                        JRLoader.loadObject(archivo);
//                JasperPrint jasperPrint =
//                        JasperFillManager.fillReport(reporte,parametros, new
//                                JRBeanCollectionDataSource(listaGuiaEnvio));
//                JRExporter exporter = new JRPdfExporter();
//                exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
//                exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new
//                        java.io.File(rutaPdf));
//                exporter.exportReport();
//            }
//
//            //No borrar este metodo generateReport2 permite vizualizar el
//            reporte generado en
//
// to do el navegador
//     /*
//     public void generateReport2()throws ClassNotFoundException,
//SQLException, IOException,JRException {
//         FacesContext facesContext = FacesContext.getCurrentInstance();
//         HttpServletResponse response = (HttpServletResponse)
//facesContext.getExternalContext().getResponse();
//         //Obteniendo el origen del jasper
//         InputStream reportStream =
//facesContext.getExternalContext().getResourceAsStream("/pages/administracion/instrumentos/tramite/hojaRuta/reporte/RHreporteEnvio.jasper");
//         //Obteniendo la salida del pdf
//         FacesContext.getCurrentInstance().responseComplete();
//         ServletOutputStream servletOutputStream =
//response.getOutputStream();
//         //generando el tipo de documento a mostrar
//         response.setContentType("application/pdf");
//         //generando el JasperRunManager con los datos
//  JasperRunManager.runReportToPdfStream(reportStream,servletOutputStream, parametros, new JRBeanCollectionDataSource(listaGuiaEnvio));
//          //terminando el proceso
//         servletOutputStream.flush();
//         servletOutputStream.close();
//     }*/
//
//            public String getRutaPdf() {
//                return rutaPdf;
//            }
//
//            public void setRutaPdf(String rutaPdf) {
//                this.rutaPdf = rutaPdf;
//            }
//
//
//        }
//
//
//
//
//
//    }
//}
