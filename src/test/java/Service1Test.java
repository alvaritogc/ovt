import bo.gob.mintrabajo.wsclient.Service1;
import bo.gob.mintrabajo.wsclient.Service1Soap;
import bo.gob.mintrabajo.wsclient.WSSAPWEBPARAMResponse;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/28/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Service1Test {

    @Test
    public void bienvenidaTest() {
        Service1 s = new Service1();
        Service1Soap service1Soap = s.getService1Soap();
        WSSAPWEBPARAMResponse.WSSAPWEBPARAMResult lp = service1Soap.wssapwebparam("4332051", "LP");
        ElementNSImpl element = (ElementNSImpl) lp.getAny();
        System.out.println(">>>>"+element.getLocalName());

        ElementNSImpl dataSet = (ElementNSImpl) element.getElementsByTagName("NewDataSet").item(0);
        if(dataSet!=null){
            ElementNSImpl consulta = (ElementNSImpl) dataSet.getFirstChild();
            if(consulta!=null){
                System.out.println("fun_id>>>"+consulta.getElementsByTagName("fun_id").item(0).getTextContent());
            }
        }
    }
}
