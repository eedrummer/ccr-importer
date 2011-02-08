package org.ohd.umls;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*****************************************************************************
 * This class provides a way of storing and retrieving configurations.
 ****************************************************************************/
public class UMLSConfiguration {


    // public methods
    public UMLSConfiguration(String fName) {
        String line = null;

        try // load configuration from file
        {
            BufferedReader in = new BufferedReader(new FileReader(fName));

            // read in line by line from a file
            while ((line = in.readLine()) != null) {
                // skip the line if it is empty or comments (#)
                if ((line.length() > 0) && (line.charAt(0) != '#')) {
                    setConfiguration(line);
                }
            }
            in.close();
        } catch (Exception e) {
            Logger.getLogger(UMLSConfiguration.class.getName()).log(Level.SEVERE, "Problem of opening/reading config file: '"
                    + fName + "'", e);
        }
    }

    public UMLSConfiguration(InputStream is) {
        try {
            String conf = convertStreamToString(is);
            StringTokenizer c = new StringTokenizer(conf, "\n");
            while (c.hasMoreTokens()) {
                String line = c.nextToken();
                // skip the line if it is empty or comments (#)
                if ((line.length() > 0) && (line.charAt(0) != '#')) {
                    setConfiguration(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UMLSConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getConfiguration(String key) {
        String out = (String) ht.get(key);
        return out;
    }

    // private methods
    private void setConfiguration(String line) {
        StringTokenizer buf = new StringTokenizer(line, "=");
        String nameStr = buf.nextToken();
        ht.put(nameStr, buf.nextToken());
    }

    public static String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
    // data member
    public final static String DIR = "DIR";
    public final static String DB_DRIVER = "DB_DRIVER";
    public final static String DB_HOST = "DB_HOST";
    public final static String DB_NAME = "DB_NAME";
    public final static String DB_USERNAME = "DB_USERNAME";
    public final static String DB_PASSWORD = "DB_PASSWORD";
    // private data member
    private Hashtable ht = new Hashtable();

    public static Hashtable<String, String> makeLuiProperties(InputStream is){
        Properties p = new Properties();
        try {
            p.load(is);
            Set<String> pNames = p.stringPropertyNames();
            Hashtable<String, String> conf = new Hashtable<String,String>();
            for (String n : pNames){
                conf.put(n, p.getProperty(n));
            }
            return conf;
        } catch (IOException ex) {
            Logger.getLogger(UMLSConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
