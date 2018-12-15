package name.edds.mileageservice;

public class Formatter {

    /**
     * Format a message to JSON
     * @param msg
     * @return
     */
   public static String formatErrorAsJson(String msg) {
        return "{\"error\": \"" +  msg + "\"}";
    }

}
