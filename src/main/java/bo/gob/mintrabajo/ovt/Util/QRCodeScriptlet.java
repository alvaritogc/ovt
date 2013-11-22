package bo.gob.mintrabajo.ovt.Util;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.JRDefaultScriptlet;

import java.io.*;

/**
 * User: Renato Velasquez
 * Date: 11/20/13
 */

public class QRCodeScriptlet extends JRDefaultScriptlet {
    public static byte[] generaQR(String url) {
        ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();

            return (out.toByteArray());
    }

//    public static void generaQR(String url) {
//        ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();
//
//        try {
//            FileOutputStream fout = new FileOutputStream(new File("/home/rvelasquez/Desktop/QR.png"));
//
//            fout.write(out.toByteArray());
//
//            fout.flush();
//            fout.close();
//
//        } catch (FileNotFoundException e) {
//            // Do Logging
//        } catch (IOException e) {
//            // Do Logging
//        }
//    }
}
