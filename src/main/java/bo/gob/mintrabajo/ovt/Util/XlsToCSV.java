package bo.gob.mintrabajo.ovt.Util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.faces.context.FacesContext;
import java.io.*;
import java.sql.Timestamp;
import java.util.Iterator;


/**
 * User: Renato Velasquez
 * Date: 11/5/13
 */
public class XlsToCSV {
    public static OutputStream xlsToCsv(InputStream inputFile){
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();
        try{

            // Get the workbook object for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputFile);
            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell;
            Row row;

            FacesContext facesContext = FacesContext.getCurrentInstance();

            File file = new File(facesContext.getExternalContext()+"/archivosVarios/xlsToCsv"+new Timestamp(System.currentTimeMillis()).getTime());
            OutputStream os= new FileOutputStream(file);

            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()){
                row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    cell = cellIterator.next();
                    switch (cell.getCellType()){
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            break;

                        default:
                            data.append(cell + ",");
                    }
                }
            }
            os.write(data.toString().getBytes());
            os.close();
            return os;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}


