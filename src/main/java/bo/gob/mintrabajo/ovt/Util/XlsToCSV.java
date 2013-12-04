package bo.gob.mintrabajo.ovt.Util;

import au.com.bytecode.opencsv.CSVWriter;
import bo.gob.mintrabajo.ovt.entities.DocBinario;
import org.apache.poi.ss.usermodel.*;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
/**
 * User: Renato Velasquez
 * Date: 11/5/13
 */


public class XlsToCSV {
    public static File conversion(DocBinario binario) throws Exception{
        int columnas=0;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //First we read the Excel file in binary format into FileInputStream
//        FileInputStream input_document = new FileInputStream(new File("C:\\excel_to_csv.xls"));

        // Read workbook into HSSFWorkbook
//        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(binario.getInputStream());
        Workbook wb = WorkbookFactory.create(binario.getInputStream());
        // Read worksheet into HSSFSheet
        Sheet my_worksheet = wb.getSheetAt(0);
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        // OpenCSV writer object to create CSV file
        String rutaCSV=servletContext.getRealPath("/")+"/archivosVarios/"+binario.getTipoDocumento()+".csv";
        File file= new File(rutaCSV);
        FileWriter my_csv=new FileWriter(rutaCSV);
        CSVWriter my_csv_output=new CSVWriter(my_csv);
        //Loop through rows.
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int i=0;//String array
            //change this depending on the length of your sheet
            if(columnas==0)
                columnas=row.getLastCellNum();
            String[] csvdata = new String[columnas];
            Iterator<Cell> cellIterator = row.iterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL
                switch(cell.getCellType()) { //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    case Cell.CELL_TYPE_STRING:
                        csvdata[i]= cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        csvdata[i]= String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        csvdata[i]= "-1";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        csvdata[i]= String.valueOf(cell.getBooleanCellValue());
                        break;
                }
                i=i+1;
            }
            my_csv_output.writeNext(csvdata);
        }
        my_csv_output.close(); //close the CSV file

        //we created our file..!!
        binario.getInputStream().close(); //close xls
        return file;
    }
}