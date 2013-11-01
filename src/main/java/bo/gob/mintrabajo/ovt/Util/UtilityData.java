package bo.gob.mintrabajo.ovt.Util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * User: Erick R. Gutierrez G.
 * Date: 5/14/13
 * Time: 4:45 PM
 */
public class UtilityData {
    public static Session session = null;
    public static Channel channel = null;
    public static ChannelSftp channelSftp = null;


    public static int stringToInt(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static Integer stringToInteger(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isDecimal(String number) {
        try {
            Double.parseDouble(number.replace(",","."));
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}