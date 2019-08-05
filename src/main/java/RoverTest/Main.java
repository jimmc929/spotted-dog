/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoverTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author jimmc
 */
public class Main {
    
    private static final Logger LOGGER = Logger.getLogger("RoverTest");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        URL url;
        
        try {
            
            url = new URL("https://www.spotteddogsolutions.com/desktopmodules/pageconfirm/cadapi.asmx?WSDL");
            
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "text/plain");

            // Without the next line, the request fails with an HTTP 403 Forbidden error
            // With the line, everything works property
            // Web service and/or server is doing filtering on User Agent, which is 
            // not appropriate because there is no user agent- the remote is not a 
            // person with a browser, but a computer
            // con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            
            try (
                    InputStream in = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                ) {
                
                char[] cbuf = new char[128];
                
                while (reader.read(cbuf, 0, 128) > 0) {
                    String logLine = new String(cbuf);
                    LOGGER.log(Level.INFO, ">> " + logLine);
                }
                
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Unable to open stream: ", e);
            }
            
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to open URL: ", e);
        } 
        
    }
    
}
